#!/usr/bin/env groovy

pipeline() {
  agent 'any'

  stages {
    stage('Execute') {
      steps {
        script {
          sh '''
            echo "Hello world!"
          '''
        }
      }
    }
    stage('Send email') {
      steps {
        script {
          sh '''
            echo "Testing"
          '''
        }
      }
    }
  }
  post {
    always {
      mail to: 'andy30082002@gmail.com',
      subject: "Jenkins Build ${currentBuild.fullDisplayName}",
      body: """
        Build Details:
        Status: ${currentBuild.currentResult}
        Build URL: ${env.BUILD_URL}
      """
    }
  }
}