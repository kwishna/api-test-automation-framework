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

        stage('Static Code Analysis') {
            parallel {
                stage('SonarQube') {
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

                // ✅ ESLint (for JS/TS projects)
                stage('ESLint') {
                    steps {
                        sh '''
                            if [ -f .eslintrc.js ] || [ -f .eslintrc.json ]; then
                                npm run lint || true
                            else
                                echo "No ESLint config found."
                            fi
                        '''
                    }
                }

                // ✅ Jacoco (Java code coverage)
                stage('Jacoco Coverage') {
                    steps {
                        sh 'mvn clean test jacoco:report'
                    }
                    post {
                        success {
                            recordCoverage tools: [jacoco()]
                        }
                    }
                }

                // ✅ Black Duck Scan
                stage('BlackDuck Scan') {
                    steps {
                        script {
                            withCredentials([string(credentialsId: 'blackduck-api-token', variable: 'BD_TOKEN')]) {
                                sh '''
                                    ./synopsys-detect.sh \
                                        --blackduck.url=https://your-blackduck-url \
                                        --blackduck.api.token=$BD_TOKEN \
                                        --detect.project.name=my-project \
                                        --detect.project.version.name=1.0.0 \
                                        --detect.tools=SIGNATURE_SCAN \
                                        --detect.source.path=.
                                '''
                            }
                        }
                    }
                }

                // ✅ OWASP Dependency-Check
                stage('Dependency Vulnerabilities (OWASP)') {
                    steps {
                        sh '''
                            dependency-check.sh \
                                --project "MyProject" \
                                --scan ./ \
                                --format HTML \
                                --out dependency-check-report
                        '''
                    }
                    post {
                        success {
                            publishHTML([
                                reportDir: 'dependency-check-report',
                                reportFiles: 'dependency-check-report.html',
                                reportName: 'OWASP Dependency Check'
                            ])
                        }
                    }
                }

                // ✅ Checkstyle
                stage('Checkstyle') {
                    steps {
                        sh 'mvn checkstyle:checkstyle'
                    }
                    post {
                        success {
                            recordIssues tools: [checkStyle(pattern: '**/target/checkstyle-result.xml')]
                        }
                    }
                }

                // ✅ PMD
                stage('PMD') {
                    steps {
                        sh 'mvn pmd:pmd'
                    }
                    post {
                        success {
                            recordIssues tools: [pmdParser(pattern: '**/target/pmd.xml')]
                        }
                    }
                }

                // ✅ SpotBugs
                stage('SpotBugs') {
                    steps {
                        sh 'mvn com.github.spotbugs:spotbugs-maven-plugin:spotbugs'
                    }
                    post {
                        success {
                            recordIssues tools: [spotBugs(pattern: '**/target/spotbugsXml.xml')]
                        }
                    }
                }

                // ✅ Prettier (for formatting check)
                stage('Prettier Check') {
                    steps {
                        sh 'npm run prettier -- --check "**/*.{js,ts,jsx,tsx}"'
                    }
                }

                /*
                // ✅ Other tools to consider:
                - detekt (Kotlin)
                - scalastyle (Scala)
                - rubocop (Ruby)
                - pylint/flake8 (Python)
                */
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

        stage('Cloud and Containers') {
            stage('Docker Build & Push') {
                when {
                    expression { return env.BUILD_IMAGE == 'true' }
                }
                steps {
                    script {
                        def imageName = "nexus-host:port/org/app:${env.BUILD_ID}"

                        sh """
                            docker build -t ${imageName} .
                            docker push ${imageName}
                        """

                        echo "Docker image pushed: ${imageName}"
                    }
                }
            }

            stage('Docker Image Scan (Trivy)') {
                steps {
                    sh '''
                        trivy image --severity CRITICAL,HIGH nexus-host:port/org/app:${BUILD_ID} || true
                    '''
                }
            }

            stage('Terraform Validate & Plan') {
                when {
                    expression { fileExists('infra/main.tf') }
                }
                steps {
                    dir('infra') {
                        sh '''
                            terraform init
                            terraform validate
                            terraform plan -out=tfplan
                        '''
                    }
                }
            }

            stage('Checkov - IaC Security Scan') {
                when {
                    expression { fileExists('infra/main.tf') || fileExists('.checkov.yaml') }
                }
                steps {
                    sh '''
                        checkov -d infra/ --framework terraform
                    '''
                }
            }

            stage('Kubernetes Deployment') {
                when {
                    expression { fileExists('k8s/deployment.yaml') }
                }
                steps {
                    script {
                        withKubeConfig([credentialsId: 'kube-config']) {
                            sh '''
                                kubectl apply -f k8s/deployment.yaml
                                kubectl rollout status deployment/my-app
                            '''
                        }
                    }
                }
            }

            stage('Helm Deployment') {
                when {
                    expression { fileExists('charts/') }
                }
                steps {
                    script {
                        withKubeConfig([credentialsId: 'kube-config']) {
                            sh '''
                                helm upgrade --install my-app charts/my-app \
                                    --namespace prod \
                                    --set image.tag=${BUILD_ID}
                            '''
                        }
                    }
                }
            }

            stage('AWS CLI Operations') {
                when {
                    expression { env.TARGET_CLOUD == 'aws' }
                }
                steps {
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-creds']]) {
                        sh '''
                            aws s3 ls
                            aws ecr describe-repositories
                        '''
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
