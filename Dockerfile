FROM alpine:latest

RUN apk --no-cache add openssl

COPY scripts/gerar-chaves.sh src/main/resources/gerar-chaves.sh

WORKDIR /scripts

RUN chmod +x gerar-chaves.sh

ENTRYPOINT ["sh", "gerar-chaves.sh"]