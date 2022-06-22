# dhcpd-register-easy

O objetivo deste projeto é facilitar o cadastro de dispositivos no servidor DHCP para os usuários do IFF campus Centro.

## Problemas atuais

Problemas:

 - Cadastro duplo
 - Dificuldade de cadastro
 	- Dificil achar IP
 	- Falta de padronização no nome do host
 - Cadastro na faixa de IP errado
 	
Padronização de nomes do DHCP: 10.12.10.9-1225467839

Grupos de usuários:
 - servidor
 - terceirizado
 - bolsistas
 - estagiário
 - temporário

## Arquitetura

A arquitetura utilizada será a de microsserviços e será descrita minuciosamente nos próximos commits.

## Tecnologias usadas

No backend será utilizado o Spring da linguagem Java interagindo com API no padrão REST.
O frontend por sua vez será implementado com ReactJS.

## Estratégias de versionamento

A estratégia de versionamento será utilizando o git com o fluxo conhecido como Git Flow.

## Responsáveis pela implementação

Luis Gustavo Sales
