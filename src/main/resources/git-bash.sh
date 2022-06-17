#/bin/bash

REPO="https://github.91chi.fun//https://github.com/irwinai/JavaInterview.git"
BASE_PATH="/root/docs"
REPO_PATH="/root/docs/JavaInterview"

if [ -d $REPO_PATH ]; then
    cd $REPO_PATH; git pull;
else
    cd $BASE_PATH; git clone $REPO;
fi