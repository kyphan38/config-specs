#!/bin/bash

action=$1

if [[ $action == "validate" ]]; then
  aws cloudformation validate-template --template-body file://a4l_vpc.yaml

elif [[ $action == "create" ]]; then
  aws cloudformation create-stack \
  --stack-name my-stack \
  --template-body file://a4l_vpc.yaml

elif [[ $action == "delete" ]]; then
  aws cloudformation delete-stack --stack-name my-stack

else
  echo "Usage: $0 {validate|create|delete}"
  exit 1
fi