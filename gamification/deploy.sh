cd "${0%/*}"
echo "Building gamification service"
if [[ "$1" == -t || $2 == -t ]]
then
mvn install package
else
mvn install package -DskipTests
fi
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
docker build -t gamification:latest . 
if [[ "$1" == -d || "$2" == -d ]]
then
echo "Deploying gamification service"
docker-compose up
fi