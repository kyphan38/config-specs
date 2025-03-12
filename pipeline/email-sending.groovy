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
}