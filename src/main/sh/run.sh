#/bin/sh
export JAVA_HOME="C:\Users\Justine\.jdks\temurin-1.8.0_352"
export PATH=$JAVA_HOME/bin:$PATH
FOLDER="../html/";

if [ $# -eq 1 ]; then
    FILE="$FOLDER/$1";
else
    FILE="$FOLDER/ants_default.html";
fi

appletviewer -J-Djava.security.policy=../resources/policy.txt "$FILE"
read -p "Press enter to continue"