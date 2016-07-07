<h1>Informações inicias</h1>
  
  <h3>Adicionar usuário</h3>
  insert into oauth_client_details(client_id,client_secret,scope,authorized_grant_types)values('dev','779a923d69b2e072747b11975ba86949de167037','global','password');
  
  insert into users_api(username,password)values('dev','779a923d69b2e072747b11975ba86949de167037');
  
  <h3>Obter token</h3>
  curl -X POST -vu dev:123mudar http://localhost:8081/oauth/token -H "Accept: application/json" -d "password=123mudar&username=dev&grant_type=password&scope=global"
