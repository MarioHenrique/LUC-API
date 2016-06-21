  -Usuario Banco mysql para dev
  	-INSERT INTO `token` (`id`,`active`,`password`,`token`,`user_name`) VALUES (1,'1','0{#2r,+:zA3/~>J','4i7julhhi97djftrrrd64tk71l','dev');
  -Obter token
   curl -X POST -vu dev:123 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=123&username=dev&grant_type=password&scope=global"