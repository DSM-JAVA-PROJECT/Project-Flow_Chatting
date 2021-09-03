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
# sudo docker build -t $IMAGE_NAME /home/ubuntu/build

if [ -n "$(sudo docker ps -aq -f status=running -f name=$CONTAINER_NAME)" ]; then
  # running이 있다면 stop
  echo "stopping" >> /home/ubuntu/deploy.log
  sudo docker stop $CONTAINER_NAME
fi
if [ -n "$(sudo docker ps -aq -f status=exited -f name=$CONTAINER_NAME)" ]; then
  # 만약 exited 상태의 container가 존재한다면 삭제
  echo "removing.." >> /home/ubuntu/deploy.log
  sudo docker rm $CONTAINER_NAME
fi
echo "running" >> /home/ubuntu/deploy.log
# 컨테이너 실행
sudo docker-compose up -d --build