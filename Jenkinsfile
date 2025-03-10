pipeline {
    // Agent specifies where the pipeline or individual stage runs
    agent {
        // Current option: running inside a Docker container
        docker {
            label 'container-1'  // Run only on nodes with this label
            image 'nexus-host:port/folder/image-file:version'
            args '--init --ipc=host'
        }

        /*
        // Alternative agent options:
        agent any                         // Run on any available agent
        agent none                        // Use when each stage defines its own agent
        agent label: 'linux'              // Run only on a node with the given label
        agent dockerfile                  // Build image from Dockerfile in repo
        agent {
            node {
                label 'my-node'
                customWorkspace '/custom/workspace'
            }
        }
        */
    }

    // Tools block installs required tools before running steps
    tools {
        jdk 'OpenJDK23'
        /*
        // More options:
        maven 'Maven 3.8.6'
        gradle 'Gradle 7.5'
        nodejs 'Node 18'
        */
    }

    parameters {
        choice(name: 'TEST_ENV', choices: ['DEV', 'QA', 'UAT', 'PROD'], description: 'Passing the Environment')
        booleanParam(name: 'SKIP_TEST', defaultValue: false, description: 'Skip Integration Tests')
        string(name: 'TEST_TAG', defaultValue: '@Smoke', description: 'Test tag name. @Smoke, @Regression')
        string(name: 'MODE', defaultValue: 'SEQUENTIAL', description: 'Execution mode: PARALLEL, SEQUENTIAL')

        /*
        // More parameter types:
        string(name: 'VERSION', defaultValue: '1.0.0', description: 'Version to deploy')
        password(name: 'SECRET_KEY', defaultValue: '', description: 'Secret API key')
        text(name: 'DEPLOY_NOTES', defaultValue: '', description: 'Notes about this deployment')
        */
    }

    triggers {
        parameterizedCron(env.BRANCH_NAME == "master" ? '''
            "0 12 * * * % TEST_ENV=DEV;MODE=PARALLEL;SHOULD_BUILD_RELEASE=no"
        ''' : '')

        github(
            triggerOnPush: true,
            triggerOnMergeRequest: true,
            triggerOnAcceptedMergeRequest: false,
            branchFilterType: 'main',
            cancelPendingBuildsOnUpdate: true,
            acceptMergeRequestOnSuccess: false,
            triggerOnNoteRequest: false,
            triggerOnlyIfNewCommitsPushed: true
        )

        /*
        // Other trigger options:
        cron('H 4 * * 1-5')                         // Daily at 4AM on weekdays
        pollSCM('H/5 * * * *')                     // Poll SCM every 5 minutes
        upstream(upstreamProjects: 'JobA, JobB', threshold: hudson.model.Result.SUCCESS)
        */
    }

    environment {
        TESTDIR = "${WORKSPACE}/${PROJ_PATH}/"
        MEMORY = "4096"
        BUILD_TIMESTAMP = 'yyyyMMddHHmmss'
        SONAR_HOME = tool(name: 'Sonar', type: 'hudson.plugins.sonar.SonarRunnerInstallation')
        TMPDIR = "${env.WORKSPACE_TMP}"
        USER_ACCESS_TOKEN = credentials('user-access-token')
        NODE_VERSION = readFile('./tools/jenkins/.nvmrc').trim()
        PATH = "/home/jenkins/.nave/installed/${NODE_VERSION}/bin:${env.PATH}"

        /*
        // Environment variables can be set via:
        env.MY_VAR = 'value'
        MY_SECRET = credentials('secret-id')       // Using Jenkins credentials
        */
    }

    options {
        timeout(time: 240, unit: 'MINUTES')
        timestamps()
        ansiColor('xterm')
        buildDiscarder(logRotator(
            numToKeepStr: '30',
            daysToKeepStr: "30",
            artifactNumToKeepStr: '30',
            artifactDaysToKeepStr: '30'
        ))
        disableConcurrentBuilds(abortPrevious: true)

        /*
        // Additional options:
        retry(count: 3)                            // Retry failed stages
        preserveStashes(buildCount: 5)             // Keep stashes for N builds
        */
    }

    stages {

        // Any stage can also define:
        // agent { ... }         // Its own agent
        // environment { ... }   // Local env vars
        // tools { ... }         // Local tools

        stage('Environment') {
            steps {
                echo "Selected environment: ${params.TEST_ENV}"
            }
        }

        stage('Initialize System Properties') {
            steps {
                script {
                    def envFile = "dev.properties"
                    load envFile
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'YOUR_GIT_REPO_URL']]
                ])
            }
        }

        stage('Validate Docker & Tooling') {
            steps {
                script {
                    sh 'docker --version'
                    sh 'java -version'
                    sh 'node -v'
                    sh 'npm -v'
                }
            }
        }

        stage('Recent Merges') {
            when {
                expression { env.GIT_BRANCH == 'main' }

                /*
                // More `when` conditions:
                branch 'develop'
                not { branch 'release/*' }
                beforeAgent true
                anyOf { branch 'main'; branch 'hotfix/*' }
                */
            }
            steps {
                script {
                    def commitsSince = '2 days ago'
                    sh "git fetch --prune origin +refs/heads/*:refs/remotes/origin/*"
                    sh 'git checkout -B main origin/main'
                    def merges = sh(script: "git log --since='${commitsSince}' main --merges --pretty=format:'%H'", returnStdout: true).trim()

                    if (merges) {
                        def commitAuthors = sh(script: "git log --since='${commitsSince}' main --no-merges --pretty=format:'%ae'", returnStdout: true).trim().split('\n').unique()
                        echo "Commit authors: ${commitAuthors}"
                    } else {
                        echo "No merges found since ${commitsSince}. Skipping build."
                        currentBuild.result = 'NOT_BUILT'
                        error("Pipeline stopped.")
                    }
                }
            }
        }

        stage('Install') {
            steps {
                sh 'npm install --audit=false'
            }
        }

        stage('SonarQube Analysis (Maven)') {
            steps {
                withSonarQubeEnv(credentialsId: 'i897f773-gg76-77qr-8af7-0472o2893056e', installationName: 'SonarQube Server') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                }
            }
        }

        stage('Integration Tests') {
            when {
                expression { !params.SKIP_TEST }
            }
            steps {
                build job: '/QA/tests/main',
                    wait: false,
                    parameters: [
                        string(name: 'TEST_ENV', value: "${params.TEST_ENV}"),
                        string(name: 'TEST_TAGS', value: "${params.TEST_TAG}")
                    ]
            }
            post {
                success {
                    sh 'echo "Integration tests successful."'
                }
            }
        }

        stage('Build & Test') {
            parallel {
                stage('Lint & Format') {
                    steps {
                        sh 'npm run prettier'
                    }
                    post {
                        always {
                            script {
                                echo "Linting completed"
                            }
                        }
                    }
                }

                stage('Branch Build') {
                    when {
                        expression { !env.CHANGE_BRANCH }
                    }
                    steps {
                        sh 'lint'
                        sh 'stylelint'
                        sh 'coverage'
                    }
                }

                stage('Merge Request Checks') {
                    when {
                        expression { env.CHANGE_BRANCH }
                    }
                    steps {
                        script {
                            echo 'Running Nexus IQ policy evaluation...'
                        }
                    }
                }
            }
        }

        stage('SonarQube Scan (Generic)') {
            steps {
                withSonarQubeEnv('Sonarqube') {
                    script {
                        def sonarParams = ["-Dsonar.branch.name=${env.GIT_BRANCH}"]
                        if (env.CHANGE_TARGET) {
                            sonarParams = [
                                "-Dsonar.pullrequest.key=$CHANGE_ID",
                                "-Dsonar.pullrequest.branch=$CHANGE_BRANCH",
                                "-Dsonar.pullrequest.base=$CHANGE_TARGET"
                            ]
                        }
                        sh """
                            export JAVA_HOME=/usr/local/corretto/17/latest
                            ${SONAR_HOME}/bin/sonar-scanner ${sonarParams.join(" ")}
                        """
                    }
                }
            }
        }

        stage('Test') {
            when {
                branch 'main'
            }
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('Continuous Deployment') {
            when {
                expression {
                    return env.GIT_BRANCH =~ /^(develop|main|release\/.*)$/
                }
            }
            steps {
                script {
                    env.deploymentAndInfraEnvironment = (env.GIT_BRANCH == 'main') ? 'prod' : 'dev'
                    currentBuild.displayName = "Deployment to ${env.deploymentAndInfraEnvironment}"
                    currentBuild.description = "Deployed commit: ${env.GIT_COMMIT}"
                    build job: '/QA/tests/main',
                        wait: true,
                        parameters: [
                            string(name: 'localhost', value: env.deploymentAndInfraEnvironment),
                            string(name: 'GIT_BRANCH', value: env.GIT_BRANCH),
                            booleanParam(name: 'INIT_REMOTE_BACKEND', value: false)
                        ]
                }
            }
        }

        stage('Post-Cleanup') {
            steps {
                script {
                    echo "Running workspace cleanup..."
                }
            }
        }
    }

    post {
        success {
            publishHTML([
                reportDir: 'target/cucumber-reports/',
                reportFiles: 'cucumber.html',
                reportName: 'HTML Report'
            ])
        }
        failure {
            mail to: 'admin@example.com',
                 subject: 'Pipeline failed',
                 body: "The pipeline for ${currentBuild.fullDisplayName} has failed."
        }
        always {
            echo "Build Completed!"
        }
        cleanup {
            cleanWs()
        }

        /*
        // More post options:
        unstable { echo 'Marked as unstable.' }
        aborted { echo 'Build was aborted.' }
        changed { echo 'Build status changed from last run.' }
        */
    }
}
