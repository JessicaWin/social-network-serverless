#!/bin/bash
STAGE=$1
REGION=$2
DELETE=$3

if [ -z "$STAGE" ] || [ -z "$REGION" ]; then
	echo "Invalid param, should run as ./deploy.sh \${STAGE}  \${REGION}"
	exit
fi

echo "Deploying deploy bucket setup to $STAGE in $REGION ..."
if [ "$DELETE" = "remove" ];then
	aws cloudformation delete-stack --stack-name $STAGE-deploy-bucket-setup
else
	stackExist=`aws cloudformation create-stack --stack-name $STAGE-deploy-bucket-setup --template-body file://./deploy-bucket-setup.yml --parameters ParameterKey=Stage,ParameterValue=$STAGE --region $REGION 2>&1 | grep "AlreadyExistsException"`
	if [[ "$stackExist" =~ "AlreadyExistsException" ]]; then
		noUpdate=`aws cloudformation update-stack --stack-name $STAGE-deploy-bucket-setup --template-body file://./deploy-bucket-setup.yml --parameters ParameterKey=Stage,ParameterValue=$STAGE --region $REGION 2>&1 | grep "No updates are to be performed"`
		if [[ "$noUpdate" =~ "No updates are to be performed" ]]; then
			echo "No updates are to be performed"
		else
			echo $noUpdate
		fi
	else
		echo $stackExist
	fi
fi