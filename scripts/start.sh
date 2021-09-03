#!/bin/bash
# BUILD_JAR=$(ls /home/ec2-user/build/*.jar)  # 필요없. 도커 쓸거라서
# JAR_NAME=$(basename "$BUILD_JAR")           # 필요없
CONTAINER_NAME="project-flow"
IMAGE_NAME="project-flow-image"
# echo "파일명: $JAR_NAME" >> /home/ubuntu/deploy.log

# echo "파일 복사" >> /home/ubuntu/deploy.log
# DEPLOY_PATH=/home/ubuntu/                   # 필요없
# cp "$BUILD_JAR" $DEPLOY_PATH                # 필요없

# echo "현재 실행중인 Application 확인" >> /home/ubuntu/deploy.log
sudo docker build -t $IMAGE_NAME /home/ubuntu/build
STATUS=[-z "$(sudo docker ps -a | grep $CONTAINER_NAME)"]
echo "status: $STATUS" > /home/ubuntu/deploy.log

if [ "$(sudo docker ps -aq -f status=running -f name=$CONTAINER_NAME)" ]; then
  sudo docker stop $CONTAINER_NAME
fi
if [ "$(sudo docker ps -aq -f status=exited -f name=$CONTAINER_NAME)" ]; then
    # 만약 exited 상태의 container가 존재한다면 삭제
  sudo docker rm $CONTAINER_NAME
fi
echo "컨테이너 실행"
# 컨테이너 실행
sudo docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME