cd "${0%/*}"
echo "Building gateway-server service"
if [[ "$1" == -t || $2 == -t ]]
then
mvn install package
else
mvn install package -DskipTests
fi
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
docker build -t gateway-server:latest . 
if [[ "$1" == -d || "$2" == -d ]]
then
echo "Deploying gateway-server service"
docker-compose up
fi