pipeline {
  agent any

  stages {
    stage('Prepare') {
      steps {
        sh '''
          #!/bin/bash

          export CIDR_IP="10.16.0.0/16"

          sshRule='[
            {
              "IpProtocol": "tcp",
              "FromPort": 22,
              "ToPort": 22,
              "IpRanges": [
                {
                  "CidrIp": "'${CIDR_IP}'",
                  "Description": "Testing"
                }
              ]
            }
          ]'

          errorFlag=0
          echo "Adding"
          {
            aws ec2 authorize-security-group-ingress --group-id sg-0bf8de7b0ac4b3f40 --ip-permissions "$sshRule";
            if [ $? -eq 0 ]; then
              echo "Successfully";
            else
              errorFlag=1;
            fi
          } &&e { 
            echo "Hi" 
          }

          if [ $errorFlag -eq 1 ]; then 
            echo "Error found"
            exit 1            
          fi
        '''
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
