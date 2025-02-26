pipeline {
    agent any

    // agent {
    //     label "machine-1"
    // }

    agent {
        docker {
            label 'container-1'
            image 'nexus-host:port/folder/image-file:version'
            args '--init --ipc=host'
        }
    }

    tools {
        jdk 'OpenJDK23'
    }

    parameters {
        choice(
            name: 'TEST_ENV',
            choices: ['DEV', 'QA', 'UAT', 'PROD'],
            description: 'Passing the Environment'
        )
        booleanParam(
            name: 'SKIP_TEST',
            defaultValue: false,
            description: 'skip INT Test'
        )
        string(
            name: 'TEST_TAG',
            defaultValue: '@Smoke',
            description: 'Test tag name. @Smoke, @Regression'
        )
        string(
            name: 'MODE',
            defaultValue: 'SEQUENTIAL',
            description: 'Execution mode: PARALLEL, SEQUENTIAL'
        )
    }

    triggers {
        // cron('H H * * 1-5')
        parameterizedCron(
            env.BRANCH_NAME == "master" ? '''
            "0 12 * * * % TEST_ENV=DEV;MODE=PARALLEL;SHOULD_BUILD_RELEASE=no"
            ''' : ''
        )
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
    }

    options {
        timeout(time: 240, unit: 'MINUTES')
        timestamps()
        ansiColor('xterm')

        buildDiscarder(logRotator(numToKeepStr: '30', daysToKeepStr: "30", artifactNumToKeepStr: '30', artifactDaysToKeepStr: '30'))
        disableConcurrentBuilds(abortPrevious: true)

    }

    stages {
        stage('Environment') {
              steps {
                echo " The environment is ${params.Env}"
              }
         }

        stage('Initialize System Properties') {
           steps {
                script {
                       def envFile = "dev.properties" // Define the properties file for the test environment
                        load envFile
                    }
               }
         }

        stage('Checkout') {
            steps {
                script {
                    checkout([$class: 'GitSCM', branches: [[name: 'master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'YOUR_GIT_REPO_URL']]])
                }
            }
        }

        stage('SonarQube analysis') {
            withSonarQubeEnv(credentialsId: 'f225455e-ea59-40fa-8af7-08176e86507a', installationName: 'My SonarQube Server') {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
            }
        }

        stage('Test') {
            when {
                branch 'main' // Trigger this stage on the 'main' branch
            }
            steps {
                script {
                    // Testing commands
                    sh 'mvn clean verify'
                }
            }
        }
    }

    post {
        success {
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: false,
                reportDir: 'target/cucumber-reports/',
                reportFiles: 'cucumber.html',
                reportName: 'HTML Report',
                reportTitles: '',
                useWrapperFileDirectly: true
                ])
        }
        failure {
            mail to: 'admin@example.com', subject: 'Pipeline failed', body: "The pipeline for ${currentBuild.fullDisplayName} has failed."
        }
    }
}