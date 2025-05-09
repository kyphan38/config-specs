pipeline {
  agent any

  parameters {
    stashedFile(name: 'JSONData')
    string(name: 'SECRET_PATH', defaultValue: '')
    validatingString(
      name: 'IP', 
      defaultValue: '10.', 
      regex: /^(10(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}\s?)+$/,
      failedValidationMessage: 'Should not be empty and must be in the range of valid IPs', 
      description: 'Enter the IPs in range of 10. . For multiple ones, separate in space'
    )
  }

  stages {
    stage('Prepare') {
      steps {
        script {
          sh '''
            #!/bin/bash

            export JENKINS_URL="abc"
            if [[ $JENKINS_URL =~ "abc" ]]; then
              echo "This right"
            else
              echo "Not found"
              exit 1
            fi
          '''
        }
      }
    }
  }
  
  post {
    always {
      script {
        cleanWs()
      }
    }
  }
}