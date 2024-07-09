<h2>Rest Assured com PetAPI</h2>

- Projeto destinado a ao teste da API PetStore (https://petstore.swagger.io/) com Rest Assured.
  - A API escolhida é open-source, permitindo sua análise de forma exploratória, afim de conhecer mais sobre as asserções possíveis com rest Assured e Java.

---
<h3>Sequência de requisições</h3>

1. CreateNewUser_WithValidData_ReturnOk():
   - Cria um novo usuário;
   - Verifica se o retorno da requisição é igual a 200;
<p>

2. GetLogin_ValidUser_ReturnOk()
  - Consulta o login (usuário) criado;
  - Informa login e senha como parâmetros;
  - Consulta o username;
  - Valida se o Status code é iqual a 200;

<p>

3. DeleteUser_UserExists_Return200():
  - Consulta o usuário criado;
  - Valida se o status code é 200;
  - Deleta o usuário criado;

---