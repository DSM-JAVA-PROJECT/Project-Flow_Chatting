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
sudo docker build --name $IMAGE_NAME /home/ubuntu/build/Dockerfile
STATUS=!"$(docker ps -a | grep $CONTAINER_NAME)"
echo "상태: $STATUS" > /home/ubuntu/deploy.log

if $STATUS; then
  if [ "$(docker ps -aq -f status=running -f name=$CONTAINER_NAME)" ]; then
    docker stop $CONTAINER_NAME
  fi
  if [ "$(docker ps -aq -f status=exited -f name=$CONTAINER_NAME)" ]; then
      # 만약 exited 상태의 container가 존재한다면 삭제
      docker rm $CONTAINER_NAME
  fi
  echo "컨테이너 실행"
  # 컨테이너 실행
  docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME
fi
