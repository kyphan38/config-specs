#!/usr/bin/env groovy

def expiry15Days = [:]
// def expiry15DaysFlag = False
def expiry25Days = [:]
// def expiry25DaysFlag = False

pipeline() {
  agent 'any'

  stages {
    stage('Execute') {
      steps {
        script {
          def envs = ["dev", "test", "nonprod", "prod"]

          for (env in envs) {

            // example_output is a map
            def example_output = [:]

            example_output["user_a"] = 25
            if (env == "test") {
              example_output["user_a"] = 15
            }
            example_output["user_b"] = 25
            example_output["user_c"] = 15

            example_output.each { user, expiryDays ->
              if(expiryDays == 15) {
                expiry15Days[user] = expiryDays
              } else if (expiryDays == 25) {
                expiry25Days[user] = expiryDays
              }
            }
          }
        }
      }
    }

    stage('Prepare report') {
      steps {
        script {
          def expiry15Json = groovy.json.JsonOutput.toJson(expiry15Days)
          def expiry25Json = groovy.json.JsonOutput.toJson(expiry25Days)

          sh """
            #!/bin/bash
            set -e  # Exit on error
            
            # Check which python is available
            if command -v python3 &>/dev/null; then
                PYTHON_CMD=python3
            elif command -v python &>/dev/null; then
                PYTHON_CMD=python
                \$PYTHON_CMD -c "import sys; assert sys.version_info >= (3,0)" || { echo "Python 3 required"; exit 1; }
            else
                echo "No Python 3 found"
                exit 1
            fi

            # Create and activate virtual environment
            \$PYTHON_CMD -m venv venv
            . venv/bin/activate

            # Install requirements
            pip install -r pipeline/email-pipeline/requirements.txt

            # Run the script
            \$PYTHON_CMD pipeline/email-pipeline/main.py '${expiry15Json}' '${expiry25Json}'
          """
        }
      }
    }

    stage('Sending email') {
      steps {
        script {
          
          emailext (
            mimeType: 'text/html',
            body: '${FILE,path="formatted-report.html"}',
            subject: 'User Expiry Report',
            from: 'tienky30082002@gmail.com',
            to: 'andy30082002@gmail.com'
          )
        }
      }
    }
  }

  post {
    always {
      cleanWs()
    }
  }
}

    // stage('SSH') {
    //   steps {
    //     script {
    //       def envs = ["a", "b", "c"]
    //       def servers = [
    //         "a": "abc.com",
    //         "b": "bcd.com",
    //         "c": "cde.com"
    //       ]

    //       for (env in envs) {
    //         def res = sh(script: """
    //           ssh -o StrictHostKeyChecking=no abc@${servers[env]} 'cd ./hcv-pipeline-test; ./getABC.sh ${env}'
    //         """, returnStdout: true).trim()
    //       }
    //     }
    //   }
    // }