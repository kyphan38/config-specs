#!/usr/bin/env groovy

def expiry15Days = []
def expiry25Days = []
def workingDir = "pipeline/email-pipeline"


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
              if (expiryDays == 15) {
                expiry15Days << ["username": user, "expiry": expiryDays, "env": env] // List of Maps
              } else if (expiryDays == 25) {
                expiry25Days << ["username": user, "expiry": expiryDays, "env": env]
              }
            }
          }
          echo "${expiry15Days}"
          echo "${expiry25Days}"
        }
      }
    }

    stage('Prepare report') {
      steps {
        script {

          sh """
            python3 -m venv ${workingDir}/venv
            source ${workingDir}/venv/bin/activate
            pip3 install -r ${workingDir}/requirements.txt
            python3 ${workingDir}/test.py '${expiry15Days}' '${expiry25Days}'
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