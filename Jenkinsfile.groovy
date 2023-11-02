import java.text.SimpleDateFormat
import java.util.Date

def dateFormat = new SimpleDateFormat("dd-MM-yyyy")
def today = dateFormat.format(new Date())

def BUILD = "mvn clean verify"

pipeline {
    agent any

//    agent {
//        node {
//            label 'AGENT_NAME'
//        }
//    }

    parameters {
        choice(name: 'TEST_ENV', choices: ['DEV', 'QA', 'UAT', 'PROD'], description: 'Select the environment')
        string(name: 'BASE_URL', defaultValue: 'https://reqres.in', description: 'Enter the base URL')
//        booleanParam(name: 'RETRY', defaultValue: true, description: 'Enable retry mechanism')
        string(name: 'RETRY_COUNT', defaultValue: '0', description: 'Number of times retry failed tests')
        string(name: 'CUCUMBER_TAGS', defaultValue: '@smoke', description: 'Cucumber tag to filter tests')
    }

    triggers {
        cron('H H * * 1-5') // Scheduled trigger for weekdays
    }

    environment {
        TESTDIR = "${WORKSPACE}/${PROJ_PATH}/"
        MEMORY = "4096"
    }

    options {
        timeout(time: 60, unit: 'MINUTES')
        timestamps()
        ansiColor('xterm')
    }

    stages {
//        stage('Set Environment Variables') {
//            steps {
//                script {
//                    def selectedEnv = params.TEST_ENV
//                    def baseUrl = params.BASE_URL
//                    def retry = params.RETRY
//                    def retryCount = params.RETRY_COUNT
//
//                    echo "Selected Environment: ${selectedEnv}"
//                    echo "Base URL: ${baseUrl}"
//                    echo "Retry Enabled: ${retry}"
//                    echo "Retry Count: ${retryCount}"
//
//                    // Set the environment variables
//                    withEnv(["TEST_ENV=${selectedEnv}", "BASE_URL=${baseUrl}", "RETRY=${retry}"]) {
//                        // Your build steps go here
//                        // You can access the environment variables like $TEST_ENV, $BASE_URL, $RETRY
//                    }
//                }
//            }
//        }

        stage('Checkout') {
            script {
                // Define the Git repository URL and credentials
                def gitUrl = 'https://github.com/yourusername/your-repo.git'
                def gitCredentialsId = 'your-credentials-id'

                // Checkout the code from Git
                checkout([
                        $class                           : 'GitSCM',
                        branches                         : [[name: '*/main']], // Replace with your branch name
                        doGenerateSubmoduleConfigurations: false,
                        extensions                       : [[$class: 'RelativeTargetDirectory', relativeTargetDir: '']],
                        userRemoteConfigs                : [[
                                                                    url          : gitUrl,
                                                                    credentialsId: gitCredentialsId
                                                            ]]
                ])
            }
        }

        stage('Test') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    def selectedEnv = params.TEST_ENV
                    def baseUrl = params.BASE_URL
                    def retry = params.RETRY
                    def retryCount = params.RETRY_COUNT
                    def tags = params.CUCUMBER_TAGS

                    // Construct Maven command with parameters
                    def mavenCmd = "${BUILD}" +
                            " -Dtest.env=${selectedEnv}" +
                            " -Dbase.url=${baseUrl}" +
                            " -Dcucumber.filter.tags=(${tags}) and (not @skip)" +
                            " -Dfailsafe.rerunFailingTestsCount=${retryCount}"

                    // Run the Maven command
                    sh mavenCmd
                }
            }
        }
    }

    post {
        success {
            script {
                def reportsBaseDir = 'target/' // Base directory containing the report folders

                // Define a map of report folders and their corresponding report files
                def reportFolders = [
                        "cucumber-reports"                       : "cucumber-report.html",
                        "extent-reports_${today}/Html".toString(): "ExtentHtml.html",
                        "site/serenity"                          : "index.html"
                ]

                // Iterate through the report folders and publish the report files
                reportFolders.each { folder, reportFile ->
                    def reportDir = "${reportsBaseDir}${folder}/"
                    def reportName = "${reportFile.split('.')[0]}"
                    publishHTML([
                            allowMissing          : false,
                            alwaysLinkToLastBuild : false,
                            keepAll               : false,
                            reportDir             : reportDir,
                            reportFiles           : reportFile,
                            reportName            : "HTML Report - ${reportName}",
                            reportTitles          : reportName, // Add report titles if needed
                            useWrapperFileDirectly: true
                    ])
                }
            }
        }

        failure {
            mail to: 'admin@example.com', subject: 'Pipeline failed', body: "The pipeline for ${currentBuild.fullDisplayName} has failed."
        }
    }
}
