#!/usr/bin/env bash

# 이 배포 스크립트는 Spring Boot 애플리케이션을 배포하기 위해 사용되며
# 이전에 실행중인 프로세스를 중단하고 최신 버전의 JAR 파일을 실행하는 작업을 수행한다.
REPOSITORY=/home/ubuntu/wakuwaku
cd $REPOSITORY

APP_NAME=wakuwaku
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 애플리케이션이 없습니다."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploy - $JAR_PATH "
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
