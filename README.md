Arquitetura do Projeto - Microserviços Product e Stock

Este projeto possui dois microsserviços independentes que se comunicam usando Kafka, seguindo princípios de arquitetura de microserviços.

🔹 Microsserviços
1. Product-Service (8081)

Responsável pelo CRUD de produtos.

Banco de dados: PostgreSQL.

Sempre que um produto é atualizado (PUT /products/{id}), envia uma mensagem para o Kafka com o ID do produto atualizado.

2. Stock-Service (8082)

Ouve mensagens do Kafka.

Para cada ID recebido, consulta o Product-Service via Feign Client para obter informações do produto.

Calcula o estoque e disponibiliza o endpoint GET /stock/{productId}.

3. Kafka

Serviço intermediário para comunicação assíncrona entre os microsserviços.

Tópico utilizado: product-updated.

O Kafka está configurado para ser utilizado no docker, o mesmo se aplica para o banco de dados da aplicação (PostgreSQL).

Obs: Apenas o Kafka e o banco de dados estão configurados com o docker, as aplicações devem ser utilizadas localmente em uma IDE e testadas pelo Postman.
