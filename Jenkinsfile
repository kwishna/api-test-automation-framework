pipeline {
    agent any

    parameters {
            choice(name: 'Env', choices: ['DEV', 'QA', 'UAT', 'PROD'], description: 'Passing the Environment')
            booleanParam(name: 'executeTests', defaultValue: true, description: '')
            string(name: 'color', defaultValue: 'blue', description: 'The build\'s color')

        }

    triggers {
        cron('H H * * 1-5') // Scheduled trigger for weekdays
    }

    environment {
            TESTDIR = "${WORKSPACE}/${PROJ_PATH}/"
            MEMORY = "4096"
        }

        options {
                timeout(time: 240, unit: 'MINUTES')
                timestamps()
                ansiColor('xterm')
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
