#!/bin/bash

set -x

#*****************************************************************
# Clean previously built jars
#*****************************************************************
rm -rf ./target
result=$?
if [ ! ${result} -eq 0 ]; then
    echo "Error: $0[${LINENO}]" 
    echo "result = ${result}"
    exit 1
fi


#*****************************************************************
# Replace tags in the source for the Version class
#*****************************************************************
VERSION="0.0.$((${BUILD_ID}))"
TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')
    
find . -name "Version.java" | while read file; do

    echo "Replacing tags in ${file}"

    sed -i "s@<VERSION>@${VERSION}@g"            ${file}    
    sed -i "s@<BUILD_ID>@${BUILD_ID}@g"          ${file}
    sed -i "s@<BUILD_DATE>@${TIMESTAMP}@g"       ${file}
    sed -i "s@<GIT_COMMIT>@${GIT_COMMIT}@g"      ${file}
    sed -i "s@<GIT_BRANCH>@${GIT_BRANCH}@g"      ${file}
    sed -i "s@<GIT_URL>@${GIT_URL}@g"            ${file}
done

#*****************************************************************
# Update the version in the pom file
#*****************************************************************
mvn --batch-mode --log-file output.txt versions:set -DnewVersion=${VERSION}
result=$?
if [ ! ${result} -eq 0 ]; then
    echo "Error: $0[${LINENO}]"
    echo "result: ${result}"
    echo "----[ output.txt ]--------------------------"
    cat output.txt
    echo "----------------------------------------"
    exit 1
fi
