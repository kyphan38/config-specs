pipeline {
  agent any

  parameters {

    booleanParam(
      name: 'ACTION',
      defaultValue: false,
      description: 'Enable to PUT (unchecked = GET, checked = PUT)'
    )

    // PUT 
    activeChoiceHtml(name: 'SECRET_KEY', choiceType: 'ET_FORMATTED_HTML', referencedParameters: 'ACTION', omitValueField: true, description: 'Enter the key name. Leave blank if uploading FILE',
      script: [$class: 'GroovyScript', script: [classpath: [], sandbox: true,
        script: '''
          if (ACTION) {
            return "<input name=\\"value\\" value=\\"abc@gmail.com, fasff@gmail.com\\" class=\\"setting-input\\" type=\\"text\\">"
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
  }

  stages {
    stage('Setup Parameters') {
      steps {
        script {
          sh """
            env
            ls -lah
          """
          unstash 'data'
          sh """
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
