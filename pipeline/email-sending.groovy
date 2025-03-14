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
          // Generate HTML table content for email
          def generateTable(env) {
            def tableContent = "<h3>${env} Environment</h3><table border='1'><tr><th>User</th><th>Expiry Date</th></tr>"
            
            // Add rows for users with expiryDays15
            expiryDays15.each { user, expiryDays ->
              tableContent += "<tr><td>${user}</td><td>${expiryDays}</td></tr>"
            }
            
            // Add rows for users with expiryDays25
            expiryDays25.each { user, expiryDays ->
              tableContent += "<tr><td>${user}</td><td>${expiryDays}</td></tr>"
            }
            
            tableContent += "</table><br>"
            return tableContent
          }
          
          // Prepare the email body with the table for each environment
          def emailBody = ""
          def envs = ["dev", "test", "prod"]
          
          envs.each { env ->
            emailBody += generateTable(env)
          }

          emailext mimeType: 'text/html', 
                   body: emailBody,
                   subject: 'Expiry Date Report',
                   from: 'tienky30082002@gmail.com',
                   to: 'andy30082002@gmail.com'
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
  }
  post {
    always {
      cleanWs()
    }
  }
}