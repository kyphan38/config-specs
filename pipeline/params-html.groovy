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
      description: 'OVERRIDE: Enter the ful path. SKIP if using above APP/ENV'
    )

    // PUT 
    activeChoiceHtml(name: 'SECRET_KEY', choiceType: 'ET_FORMATTED_HTML', referencedParameters: 'ACTION', omitValueField: true, description: 'Enter the key name. SKIP if uploading a FILE below',
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

    activeChoiceHtml(name: 'SECRET_VALUE', choiceType: 'ET_FORMATTED_HTML', referencedParameters: 'ACTION', omitValueField: true, description: 'Enter the secret value. SKIP if uploading a FILE below',
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

    stashedFile (name: 'data', description: 'OVERRIDE: Upload a JSON file with key/value pairs')
  }

  stages {
    stage('Setup Parameters') {
      steps {
        echo "States selected: ${params.States}"
        echo "Action selected: ${params.ACTION}"
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
