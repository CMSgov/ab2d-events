#!/usr/bin/env bash

set -e # Turn on exit on error

# Set default AWS region and tag

export AWS_DEFAULT_REGION="us-east-1"
export IMAGE_TAG="event-service"

# assume github-runner-role
OUT=$(aws sts assume-role --role-arn "arn:aws:iam::${ACCOUNT_NUMBER}:role/delegatedadmin/developer/github-actions-runner-role" --role-session-name github-actions);\
aws sts get-caller-identity
export AWS_ACCESS_KEY_ID=$(echo $OUT | jq -r '.Credentials''.AccessKeyId');\
export AWS_SECRET_ACCESS_KEY=$(echo $OUT | jq -r '.Credentials''.SecretAccessKey');\
export AWS_SESSION_TOKEN=$(echo $OUT | jq -r '.Credentials''.SessionToken');
aws sts get-caller-identity
  
            

########### Logging into ECR ####################
echo Logging in to Amazon ECR...
aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $ACCOUNT_NUMBER.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com
export REPOSITORY_URI=$ACCOUNT_NUMBER.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$ECR_REPO_ENV

############# Building docker image ###############
echo Build started on `date`
echo Building the Docker image...          
ls build/libs/
docker build --no-cache -t $ECR_REPO_ENV:$IMAGE_TAG .
docker tag $ECR_REPO_ENV:$IMAGE_TAG $ACCOUNT_NUMBER.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$ECR_REPO_ENV:$IMAGE_TAG




##### pushing docker image to ECR ################
echo Build completed on `date`
echo Pushing the Docker image...
docker push $ACCOUNT_NUMBER.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$ECR_REPO_ENV:$IMAGE_TAG
#
######## update ECS service with new image ################
#
#aws ecs update-service --cluster $DEPLOYMENT_ENV-microservice-cluster  --service ab2d-event-service --force-new-deployment
