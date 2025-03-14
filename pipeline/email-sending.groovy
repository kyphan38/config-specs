#!/usr/bin/env groovy

def expiryDays15 = [:]
def expiryDays25 = [:]


pipeline() {
  agent 'any'

  stages {
    stage('Execute') {
      steps {
        script {
          def envs = ["dev", "test", "prod"]

          for (env in envs) {
            def example_output = [:]

            example_output["user_a"] = 15
            example_output["user_b"] = 25
            example_output["user_c"] = 15

            example_output.each { user, expiryDays ->
              if (expiryDays == 15) {
                expiryDays15[user] = 15
              } else if (expiryDays == 25) {
                expiryDays25[user] = 25
              }
            }
          }
        }
      }
    }

    stage('Sending email') {
      steps {
        script {
          def envs = ["dev", "test", "prod"]
          def template = readFile './template/report.html'

          def binding = [
            "envs": envs,
            "expiry15": expiryDays15,
            "expiry25": expiryDays25
          ]
          
          def engine = new groovy.text.SimpleTemplateEngine()
          def emailBody = engine.createTemplate(template).make(binding).toString()

          emailext (
            mimeType: 'text/html',
            body: emailBody,
            subject: 'User Expiry Report',
            from: 'tienky30082002@gmail.com',
            to: 'andy30082002@gmail.com'
          )
        }
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

  post {
    always {
      cleanWs()
    }
  }
}