pipeline {
  agent any

  parameters {

    choice(
      name: 'ACTION',
      choices: ['get', 'put'],
      description: 'Select an action'
    )

    // PATH - General
    choice(
      name: 'REPO',
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
      choices: [
        'dev',
        'test',
        'prod',
      ]
    )

    string(
      name: 'SECRET_PATH',
      defaultValue: '', 
      description: 'Leave blanks if you used above'
    )

    // PUT 
    activeChoiceHtml(name: 'SECRET_KEY', choiceType: 'ET_FORMATTED_HTML', referencedParameters: 'ACTION', omitValueField: true, description: 'Enter key the name. SKIP for uploading FILE',
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

    activeChoiceHtml(name: 'SECRET_VALUE', choiceType: 'ET_FORMATTED_HTML', referencedParameters: 'ACTION', omitValueField: true, description: 'Enter key the name. SKIP for uploading FILE',
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

    stashedFile (name: 'data', description: 'JSON file contains key and value. NOT: dataJson parameter will be higher priority than manual input')
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
