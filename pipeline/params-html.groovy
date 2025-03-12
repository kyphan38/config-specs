pipeline {
  agent any

  parameters {
    // State and Action Parameters
    activeChoice(
      name: 'States',
      description: 'Select a state option',
      choiceType: 'PT_SINGLE_SELECT',
      script: [
        $class: 'GroovyScript',
        script: [
          classpath: [],
          sandbox: true,
          script: 'return ["A_State", "B_State"]'
        ],
        fallbackScript: [
          classpath: [],
          sandbox: true,
          script: 'return ["Error for States"]'
        ]
      ]
    )

    choice(
      name: 'ACTION',
      choices: ['get', 'put'],
      description: 'Select an action'
    )

      // Dynamic SECRET_KEY
    activeChoiceHtml(
      name: 'SECRET_KEY',
      description: 'Key of secret',
      choiceType: 'ET_FORMATTED_HTML',
      referencedParameters: 'ACTION',
      omitValueField: true,
      script: [
        $class: 'GroovyScript',
        script: [
          classpath: [],
          sandbox: true,
          script: '''
            if (ACTION == "put") {
              return "<input name=\\"value\\" value=\\"\\" class=\\"setting-input\\" type=\\"text\\">"
            } else {
              return ""
            }
          '''
          ],
          fallbackScript: [
            classpath: [],
            sandbox: true,
            script: 'return "error"'
          ]
      ]
    )
  }

  stages {
    stage('Setup Parameters') {
      steps {
        echo "States selected: ${params.States}"
        echo "Action selected: ${params.ACTION}"
      }
    }
  }
}
