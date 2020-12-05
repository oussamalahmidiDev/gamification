if [[ "$1" == -b || "$2" == -b ]]
then
echo "Building and deploying gateway-server service"
./gateway-server/deploy.sh
echo "Building and deploying multiplication service"
./multiplication/deploy.sh
echo "Building and deploying gamification service"
./gamification/deploy.sh
cd "${0%/*}" 
fi
echo "Running docker-compose"
if [[ "$1" == -d || "$2" == -d ]]
then
docker-compose up -d
else
docker-compose up
fi
