# Relatorio do Trabalho 2 - RMI

## 1. Identificacao

- Disciplina: Sistemas Distribuidos
- Trabalho: Trabalho 2 - RMI
- Integrantes: Antonio Rewelli Oliveira dos Santos, Anaildo nascimento

## 2. URL do Repositorio

- URL do codigo fonte: https://github.com/RewelliOliveira/aeronaves-SD

## 3. Objetivo do Servico Remoto Implementado

O servico remoto implementado foi o gerenciamento de companhias aereas, com operacoes de cadastro, listagem, busca por codigo IATA e remocao. O cliente invoca metodos remotos como se fossem locais, enquanto a execucao real ocorre no servidor.

A comunicacao foi implementada em modo texto, sem GUI, conforme permitido no enunciado.

## 4. Descricao da Solucao

### 4.1 Arquitetura geral

A solucao segue o modelo cliente-servidor com middleware RMI customizado:

- Cliente: realiza chamadas remotas por meio de um proxy
- Servidor: recebe requisicoes, despacha para o servico de negocio e retorna respostas
- Transporte: UDP para envio e recebimento das mensagens
- Serializacao: JSON manual para argumentos e resultados

### 4.2 Componentes principais

- Camada de mensagem remota:
  - MensagemRMI: estrutura das mensagens
  - RemoteObjectRef: referencia do objeto remoto (host, porta, nome)
- Middleware:
  - RMIClient: envio da requisicao e recebimento da resposta
  - RMIServer: escuta de requisicoes e envio de replies
- Servico remoto:
  - ServicoCompanhiaRmiProxy: lado cliente (proxy)
  - ServicoCompanhiaRmiServer: lado servidor (dispatcher)
  - ServicoCompanhia: regra de negocio
- Serializacao JSON:
  - JsonUtil: conversao de objetos para JSON e JSON para objetos

## 5. Operacoes Remotas Disponiveis

Foram implementadas as seguintes operacoes remotas:

1. cadastrarCompanhia(CompanhiaAerea)
2. removerCompanhia(int id)
3. buscarPorIata(String iata)
4. listarCompanhias()

## 6. Formato de Dados e Serializacao

- O cliente converte argumentos para JSON antes do envio
- O servidor converte o JSON recebido para objetos de dominio
- O retorno do servidor tambem e enviado em JSON
- Em caso de erro, a resposta retorna um JSON com o campo erro

Exemplos de payload:

```json
{
  "id": 1,
  "nome": "LATAM Airlines",
  "codigoIATA": "LA",
  "pais": "Brasil",
  "anoFundacao": 1929
}
```

```json
[
  {
    "id": 1,
    "nome": "LATAM Airlines",
    "codigoIATA": "LA",
    "pais": "Brasil",
    "anoFundacao": 1929
  }
]
```

## 7. Como Executar (Modo Texto)

1. Compilar:

```powershell
javac -cp . Main.java model\*.java rmi\*.java streams\*.java TesteInputStream.java TesteOutputStream.java
```

2. Iniciar servidor (terminal 1):

```powershell
java -cp . rmi.ServicoCompanhiaRmiServer
```

3. Iniciar cliente (terminal 2):

```powershell
java -cp . Main rmi
```

Opcao de demonstracao automatica:

```powershell
java -cp . Main rmi-demo
```

## 8. Requisitos Atendidos

- Implementacao de RMI customizado com cliente e servidor
- Definicao de protocolo de mensagem para chamada e retorno
- Implementacao de metodos remotos de negocio
- Serializacao de dados em JSON
- Execucao completa em modo texto, sem interface grafica

## 9. Entrega no Moodle

A entrega deve conter:

1. URL do repositorio do codigo fonte
2. Este relatorio em PDF (ou no formato exigido pela disciplina)
3. Upload no Moodle dentro do prazo da atividade

## 10. Observacoes Finais

O projeto foi desenvolvido para demonstrar os conceitos de invocacao remota, separacao entre cliente e servidor, e serializacao de objetos em sistemas distribuidos, mantendo a operacao em linha de comando conforme permitido no enunciado.
