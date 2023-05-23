# how to deploy

```
cd social-network-serverless
mvn package -T4
chmod 777 deploy-bucket-setup.sh
./deploy-bucket-setup.sh develop ap-southeast-1

cd social-network-serverless-lambda
sls deploy

cd social-network-serverless-lambda-warmer
sls deploy
```
