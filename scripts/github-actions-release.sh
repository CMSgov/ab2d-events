#!/usr/bin/env bash

set -e # Turn on exit on error

# Set default AWS region and tag

export AWS_DEFAULT_REGION="us-east-1"
export IMAGE_TAG="event-service"

# assume github-runner-role
#OUT=$(aws sts assume-role --role-arn "arn:aws:iam::${ACCOUNT_NUMBER}:role/delegatedadmin/developer/github-actions-runner-role" --role-session-name github-actions);\
#export AWS_ACCESS_KEY_ID=$(echo $OUT | jq -r '.Credentials''.AccessKeyId');\
#export AWS_SECRET_ACCESS_KEY=$(echo $OUT | jq -r '.Credentials''.SecretAccessKey');\
#export AWS_SESSION_TOKEN=$(echo $OUT | jq -r '.Credentials''.SessionToken');
  

#
######## update ECS service with new image ################
#
aws ecs update-service --cluster $DEPLOYMENT_ENV-microservice-cluster  --service ab2d-event-service --force-new-deployment
