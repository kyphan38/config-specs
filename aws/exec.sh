#!/bin/bash

file=$1
stack_name=$2
action=$3

if [[ $action == "validate" ]]; then
  aws cloudformation validate-template --template-body file://$file

elif [[ $action == "create" ]]; then
  aws cloudformation create-stack \
  --stack-name $stack_name \
  --template-body file://$file

elif [[ $action == "delete" ]]; then
  aws cloudformation delete-stack --stack-name $stack_name

else
  echo "Usage: $0 {validate|create|delete}"
  exit 1
fi