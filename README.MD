1 - Introdução 
essa é a aplicação desenvolvida para o teste técnico da mobiauto.
A aplicação tem como objetivo principal:
 • gerenciar usuários 
 • gerenciar revendas
 • gerenciar oportunidades
 • gerenciar atendimentos

para iniciar a aplicação, primeiro precisamos fazer o login com o seu usuário e sua senha, caso ainda não tenha, precisaremos cadastrar um usuário com um login de "ADMINISTRADOR" ou "PROPRIETARIO", visto que o proprietário consegue cadastrar usuarios somente para a sua revenda e o administrador consegue cadastrar para varias revendas.

com esse primeiro passo,  então poderemos gerenciar cada aspecto da aplicação, com base nas roles que cada usuário tem.

2 - Pré-requisitos 
para subir a aplicação localmente é preciso:
- Java 17
- WSL (windows) ou Linux
- Docker

o docker será necessário para subirmos o banco de dados postgres que será usado.

o wsl será necessário para a geração de chaves privada e pública utilizada para a autenticação dos usuários

2.1 Gerar chaves de autenticação :
na pasta raiz do projeto, é preciso inserir no terminal:

sudo apt-get install openssl </br>
cd ./src/main/resources </br>
openssl genrsa > app.key </br>
openssl rsa -in app.key -pubout -out app.pub </br>

2.2 Subir docker-compose
com o docker instalado, podemos simplesmente inserir no terminal dentro pasta principal do projeto

docker-compose up

assim, o banco estará pronto para receber conexão.

é recomendável usar uma ide para rodar a aplicação ou se preferir, criar um jar a partir do repositório.

3.0 Suporte
pode entrar em contato pelo canais:
whatsapp: 043996324203
linkedin: https://www.linkedin.com/in/lucasgonzaga00
