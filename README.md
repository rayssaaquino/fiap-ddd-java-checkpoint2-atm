# 🏦 FIAP Bank ATM — Checkpoint 2
### Domain Driven Design - Java | Engenharia de Software | FIAP

---

## 📋 Sobre o Projeto

Refatoração completa do sistema de Caixa Eletrônico (ATM) do FIAP Bank, evoluindo de uma versão procedural (CP1) para uma arquitetura orientada a objetos baseada nos princípios de **Domain-Driven Design (DDD)**.

O sistema simula um terminal bancário real operando via console, com autenticação segura, operações bancárias e histórico de movimentações.

---

## ✅ Funcionalidades

- Cadastro de cliente com nome completo
- Criação de senha com validação de segurança (mínimo 8 caracteres, letra maiúscula, número e caractere especial)
- Autenticação com bloqueio após 3 tentativas incorretas
- Consultar saldo
- Realizar depósito
- Realizar saque (com cobrança automática de taxa para Conta Corrente)
- Histórico de movimentações com data/hora, tipo e valor
- Encerramento de sessão

---

## 🏗️ Arquitetura e Estrutura de Pacotes

O projeto segue a separação de camadas do DDD:

```
src/
└── br/
    └── com/
        └── fiapbank/
            ├── model/              # Domínio — regras de negócio
            │   ├── BaseEntity.java
            │   ├── Cliente.java
            │   ├── Conta.java
            │   ├── ContaAcesso.java
            │   ├── ContaCorrente.java
            │   ├── ContaPoupanca.java
            │   ├── Dinheiro.java
            │   ├── Movimentacao.java
            │   ├── StatusConta.java
            │   └── TipoMovimentacao.java
            ├── application/        # Orquestração — casos de uso
            │   ├── AutorizacaoService.java
            │   ├── ContaFactory.java
            │   ├── ContaService.java
            │   └── Main.java
            ├── presentation/       # Interface com o usuário
            │   └── TerminalBancarioController.java
            └── infrastructure/     # Dados e armazenamento
                └── ContaRepository.java
```

---

## 🎯 Padrões e Conceitos Aplicados

| Conceito | Onde foi aplicado |
|---|---|
| **Encapsulamento** | Atributos `private`/`protected` em todas as classes, sem setters públicos |
| **Herança** | `ContaCorrente` e `ContaPoupanca` estendem a classe abstrata `Conta` |
| **Template Method** | Método `realizarSaque()` em `Conta` define o algoritmo; `aplicarRegraDeTaxa()` é abstrato |
| **Singleton** | `ContaFactory.getInstance()` garante uma única instância |
| **Factory** | `ContaFactory` cria objetos de `ContaCorrente` e `ContaPoupanca` |
| **Value Objects** | `Dinheiro`, `ContaAcesso` e `Movimentacao` com `equals` sobrescrito |
| **Sem tipos primitivos** | Uso exclusivo de `Integer`, `Double`, `Boolean`, `BigDecimal`, `String` |
| **Coleções** | `List<Movimentacao>` para histórico de transações |
| **Data/Hora** | `LocalDateTime` em cada movimentação registrada |

---

## 🔄 Fluxo da Aplicação

```
presentation → application → model

1. Usuário digita nome e cria senha (presentation)
2. ContaFactory cria a conta via padrão Factory/Singleton (application)
3. Usuário se autentica — AutorizacaoService valida via ContaAcesso (application → model)
4. Menu principal em loop até o usuário sair (presentation)
5. Operações delegadas ao ContaService que aciona a Conta (application → model)
6. Conta registra cada transação como Movimentacao na lista interna (model)
```

---

## 💰 Regras de Negócio

**Conta Corrente**
- Taxa de manutenção de **R$ 25,00** cobrada automaticamente a cada saque realizado com sucesso
- A taxa é registrada como uma `Movimentacao` separada do tipo `TAXA`

**Conta Poupança**
- Sem taxa no saque
- Rendimento mensal de **1%** registrado como `RENDIMENTO`

**Autenticação**
- Senha deve ter no mínimo 8 caracteres, uma letra maiúscula, um número e um caractere especial
- Conta bloqueada após **3 tentativas** incorretas de senha

---

## 🚀 Como Executar

### Pré-requisitos
- [JDK 21+](https://adoptium.net)
- [VS Code](https://code.visualstudio.com) com a extensão **Extension Pack for Java**

### Passos
1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/fiap-ddd-java-checkpoint2-atm.git
```

2. Abra a pasta no VS Code:
```bash
code fiap-ddd-java-checkpoint2-atm
```

3. Abra o arquivo `src/br/com/fiapbank/application/Main.java`

4. Clique em **▶ Run** acima do método `main` ou pressione **F5**

### Exemplo de uso
```
╔══════════════════════════════════════════╗
║        FIAP BANK - TERMINAL ATM          ║
║          Bem-vindo ao FIAP Bank          ║
╚══════════════════════════════════════════╝

  Por favor, identifique-se para continuar.
  Nome completo: João Silva
  Olá, João! Vamos cadastrar sua senha de acesso.

  Defina uma senha segura: MinhaSenh@1
  Senha registrada com sucesso!

  Por favor, insira sua senha para acessar sua conta.
  Senha: MinhaSenh@1
  Acesso autorizado. Bem-vindo(a), João!

╔══════════════════════════════════════════╗
║             MENU PRINCIPAL               ║
╠══════════════════════════════════════════╣
║  [ 1 ] Consultar Saldo                   ║
║  [ 2 ] Fazer Depósito                    ║
║  [ 3 ] Fazer Saque                       ║
║  [ 4 ] Histórico de Movimentações        ║
║  [ 5 ] Sair                              ║
╚══════════════════════════════════════════╝
```
