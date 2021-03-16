echo "Criando arquivos..."
sudo docker-compose -f docker-compose-build.yml up
echo "Copiando arquivo para backend..."
cp ./dist/app.js ./../src/main/resources/static/js
echo "Removendo arquivos..."
sudo rm dist_homolog.tar.gz
sudo rm -R dist/