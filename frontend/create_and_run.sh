docker run -it --rm -v "$(pwd)":/app:rw -w /app node:12.13-alpine yarn build
echo "Rodando nginx..."
docker run --rm -it -p 8081:80 -v "$(pwd)"/dist:/usr/share/nginx/html nginx