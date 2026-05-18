# Demo Rapida - Trabalho 2 (RMI)

## 1) Abrir pasta do projeto

```powershell
cd "C:\Users\rewel\Documents\Projetos GitHub\aeronaves-SD\src"
```

## 2) Compilar

```powershell
javac -cp . Main.java model\*.java rmi\*.java streams\*.java TesteInputStream.java TesteOutputStream.java
```

## 3) Rodar RMI (2 terminais)

### Terminal 1 - Servidor

```powershell
java -cp . rmi.ServicoCompanhiaRmiServer
```

Saida esperada inicial:

- [Middleware] Servidor RMI iniciado na porta 7000
- [RMI Server] Pronto para receber chamadas remotas.

### Terminal 2 - Cliente (via Main)

```powershell
java -cp . Main rmi
```

Ao rodar este comando, abre um menu interativo com opcoes prontas:

- 1: Cadastrar LATAM (dados pre-definidos)
- 2: Cadastrar GOL (dados pre-definidos)
- 3: Cadastrar AZUL (dados pre-definidos)
- 4: Listar companhias
- 5: Buscar por IATA G3
- 6: Remover companhia id 1
- 7: Rodar demo automatica
- 0: Voltar/sair

Opcao alternativa (sem menu, direta para apresentacao):

```powershell
java -cp . Main rmi-demo
```

Saida esperada no cliente:

- Busca por IATA G3: [2] GOL Linhas Aereas (G3) - Brasil | Aeronaves: 0
- Total de companhias remotas: 2
- [1] LATAM Airlines (LA) - Brasil | Aeronaves: 0
- [2] GOL Linhas Aereas (G3) - Brasil | Aeronaves: 0
- Companhia id=1 removida? true
- Total final: 1

## 4) Encerrar servidor

No terminal do servidor, pressione:

- Ctrl + C

## 5) Limpar .class (opcional)

```powershell
Get-ChildItem -Recurse -Filter *.class | Remove-Item
```
