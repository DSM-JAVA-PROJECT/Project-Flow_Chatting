#!/bin/bash
BASE_DIR=/home/ubuntu/
BUILD_JAR=$($BASE_DIR + "build/*.jar")
JAR_NAME=$(basename "$BUILD_JAR")
echo "> build 파일명: $JAR_NAME" >> $BASE_DIR + deploy.log

echo "> build 파일 복사" >> $BASE_DIR + deploy.log
DEPLOY_PATH=$BASE_DIR
cp "$BUILD_JAR" $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> $BASE_DIR/deploy.log
CURRENT_PID=$(pgrep -f "$JAR_NAME")

if [ -z "$CURRENT_PID" ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $BASE_DIR + deploy.log
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 "$CURRENT_PID"
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> $BASE_DIR + deploy.log
nohup java -jar "$DEPLOY_JAR" >> /home/ubuntu/deploy.log 2>/home/ubuntu/deploy_err.log &