#!/usr/bin/env bash

set -e # Turn on exit on error


ECR_REPO_ENV_AWS_ACCOUNT_NUMBER=$ECR_REPO_ENV_AWS_ACCOUNT_NUMBER
ECR_REPO_ENV=$ECR_REPO_ENV
DEPLOYMENT_ENV=$DEPLOYMENT_ENV


if [ "${CLOUD_TAMER}" != "false" ] && [ "${CLOUD_TAMER}" != "true" ]; then
  echo "ERROR: CLOUD_TAMER parameter must be true or false"
  exit 1
elif [ "${CLOUD_TAMER}" = "false" ]; then

  # Turn off verbose logging for Jenkins jobs
  set +x
  echo "Don't print commands and their arguments as they are executed."
  CLOUD_TAMER="${CLOUD_TAMER}"

  # Import the "get temporary AWS credentials via AWS STS assume role" function
  source "./scripts/fn_get_temporary_aws_credentials_via_aws_sts_assume_role.sh"

else # [ "${CLOUD_TAMER}" == "true" ]

  # Turn on verbose logging for development machine testing
  set -x
  echo "Print commands and their arguments as they are executed."
  CLOUD_TAMER="${CLOUD_TAMER}"

  # Import the "get temporary AWS credentials via CloudTamer API" function
  source "./scripts/fn_get_temporary_aws_credentials_via_cloudtamer_api.sh"

fi

if [ "${CLOUD_TAMER}" = "true" ]; then
  fn_get_temporary_aws_credentials_via_cloudtamer_api "${ECR_REPO_ENV_AWS_ACCOUNT_NUMBER}" "${ECR_REPO_ENV}"
else
  fn_get_temporary_aws_credentials_via_aws_sts_assume_role "${ECR_REPO_ENV_AWS_ACCOUNT_NUMBER}" "${ECR_REPO_ENV}"
fi

echo Logging in to Amazon ECR...
aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com
#export REPOSITORY_URI=$AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$IMAGE_REPO_NAME


echo Build started on `date`
echo Building the Docker image...          
docker build -t $IMAGE_REPO_NAME:$IMAGE_TAG .
docker tag $IMAGE_REPO_NAME:$IMAGE_TAG $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$IMAGE_REPO_NAME:$IMAGE_TAG 

#### push image
echo Build completed on `date`
echo Pushing the Docker image...
docker push $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$IMAGE_REPO_NAME:$IMAGE_TAG
