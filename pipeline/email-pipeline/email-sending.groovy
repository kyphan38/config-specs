#!/usr/bin/env groovy

String expiry15Days = "[[username:a-db-user-1, expiry:7, env:a], [username:a-db-user-2, expiry:3, env:a], [username:b-db-user-1, expiry:4, env:b], [username:b-db-user-2, expiry:7, env:b], [username:c-db-user-1, expiry:1, env:c], [username:c-db-user-2, expiry:2, env:c"
String expiry25Days = "[[username:a-db-user-3, expiry:15, env:a], [username:b-db-user-3, expiry:15, env:b], [username:c-db-user-3, expiry:15, env:c]]"
String workingDir = "pipeline/email-pipeline"
String templatePath = "templates/raw-email-report.html"
String templateOutputPath = "email-report.html"


pipeline() {
  agent 'any'

  stages {
    stage('Prepare report') {
      steps {
        script {

          sh """
            cd ${workingDir}
            python3 -m venv venv
            source venv/bin/activate
            pip3 install -r requirements.txt
            python3 main.py '${expiry15Days}' '${expiry25Days}' '${templatePath}' '${templateOutputPath}'
            cat '${templateOutputPath}'
          """
        }
      }
    }

    stage('Sending email') {
      steps {
        script {
          def reportPath = "${workingDir}/${templateOutputPath}"

          emailext (
            mimeType: 'text/html',
            body: '${FILE,path="' + reportPath + '"}',
            subject: 'User Expiry Report',
            from: 'a@gmail.com',
            to: 'b@gmail.com'
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

    // stage('Execute') {
    //   steps {
    //     script {
    //       def envs = ["dev", "test", "nonprod", "prod"]

    //       for (env in envs) {

    //         // example_output is a map
    //         def example_output = [:]

    //         example_output["user_a"] = 25
    //         if (env == "test") {
    //           example_output["user_a"] = 15
    //         }
    //         example_output["user_b"] = 25
    //         example_output["user_c"] = 15

    //         example_output.each { user, expiryDays ->
    //           if (expiryDays == 15) {
    //             expiry15Days << ["username": user, "expiry": expiryDays, "env": env] // List of Maps
    //           } else if (expiryDays == 25) {
    //             expiry25Days << ["username": user, "expiry": expiryDays, "env": env]
    //           }
    //         }
    //       }
    //       echo "${expiry15Days}"
    //       echo "${expiry25Days}"
    //     }
    //   }
    // }