#!/bin/sh

openssl genrsa > app.key
openssl rsa -in app.key -pubout -out app.pub

COPY ./app.key ./src/main/resources/app.key
COPY ./app.pub ./src/main/resources/app.pub

echo "Chaves geradas com sucesso!"