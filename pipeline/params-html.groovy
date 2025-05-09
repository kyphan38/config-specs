pipeline {
  agent any

  parameters {

    choice(
      name: 'ACTION',
      choices: ['get', 'put'],
      description: 'Select action'
    )

    // PATH - General
    choice(
      name: 'APP',
      description: 'Select application',
      choices: [
        'ab/app-a',
        'ab/app-b',
        'ab/app-c',
        'ab/app-d',
        'ab/app-e',
      ]
    )

    choice(
      name: 'ENV',
      description: 'Select enviroment',
      choices: [
        'dev',
        'test',
        'prod',
      ]
    )

    string(
      name: 'SECRET_PATH',
      defaultValue: '', 
      description: 'Enter the full path to override APP/ENV input (e.g., abc/abc). Leave blank if using APP/ENV'
    )

    // PUT 
    activeChoiceHtml(name: 'SECRET_KEY', choiceType: 'ET_FORMATTED_HTML', referencedParameters: 'ACTION', omitValueField: true, description: 'Enter the key name. Leave blank if uploading FILE',
      script: [$class: 'GroovyScript', script: [classpath: [], sandbox: true,
        script: '''
          if (ACTION == "put") {
            return "<input name=\\"value\\" value=\\"\\" class=\\"setting-input\\" type=\\"text\\">"
          } else {
            return ""
          }
        '''
        ],
        fallbackScript: [classpath: [], sandbox: true,
          script: 'return "error"'
        ]
      ]
    )

    activeChoiceHtml(name: 'SECRET_VALUE', choiceType: 'ET_FORMATTED_HTML', referencedParameters: 'ACTION', omitValueField: true, description: 'Enter the secret value. Leave blank if uploading FILE',
      script: [$class: 'GroovyScript', script: [classpath: [], sandbox: true,
        script: '''
          if (ACTION == "put") {
            return "<input name=\\"value\\" value=\\"\\" class=\\"setting-input\\" type=\\"text\\">"
          } else {
            return ""
          }
        '''
        ],
        fallbackScript: [classpath: [], sandbox: true,
          script: 'return "error"'
        ]
      ]
    )

    stashedFile (name: 'data', description: 'Upload a JSON file with key/value pairs to override SECRET_KEY/VALUE input')
  }

  stages {
    stage('Setup Parameters') {
      steps {
        script {
          sh """
            pwd
            ls -lah
          """
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
