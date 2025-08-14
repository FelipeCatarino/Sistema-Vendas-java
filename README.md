# Sistema de Vendas - Oficina Mecânica

Sistema completo de gerenciamento de vendas e estoque desenvolvido em Java com interface gráfica Swing, voltado especificamente para oficinas mecânicas.

## 📋 Sobre o Projeto

Este sistema oferece uma solução completa para gestão de vendas, controle de estoque, cadastro de clientes e fornecedores, além de relatórios detalhados de fechamento de caixa. Foi desenvolvido com foco na usabilidade e eficiência para pequenas e médias oficinas.

## ✨ Funcionalidades Principais

### 🛒 Gestão de Vendas
- Lançamento de vendas com múltiplas formas de pagamento
- Controle de itens vendidos
- Histórico completo de transações
- Cálculo automático de totais e lucros

### 📦 Controle de Estoque
- Cadastro completo de produtos
- Controle de entrada e saída
- Monitoramento de níveis de estoque
- Relatórios de movimentação

### 👥 Cadastro de Clientes e Fornecedores
- Gestão completa de dados pessoais
- Histórico de relacionamento
- Sistema de busca avançada

### 💰 Fechamento de Caixa
- Relatórios diários automáticos
- Separação por forma de pagamento (Dinheiro, Débito, Crédito, PIX)
- Cálculo de lucro bruto
- Geração de PDFs para arquivo
- Histórico de fechamentos anteriores

### 📊 Relatórios
- Geração automática de PDFs
- Relatórios de fechamento diário
- Dados organizados e profissionais

## 🏗️ Arquitetura

O sistema segue o padrão MVC (Model-View-Controller):

- **Model**: Classes TO (Transfer Object) para representação de dados
- **View**: Interfaces gráficas desenvolvidas em Swing
- **Controller**: Lógica de negócio e controle de fluxo
- **DAO**: Camada de acesso a dados com SQLite

## 🛠️ Tecnologias Utilizadas

- **Java 17** (OpenJDK)
- **Swing** - Interface gráfica
- **SQLite** - Banco de dados
- **Maven** - Gerenciamento de dependências
- **iText** - Geração de PDFs

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/sistemaMaster/
│   │       ├── auxiliar/      # Classes auxiliares
│   │       ├── controller/    # Controladores (MVC)
│   │       ├── dao/          # Data Access Objects
│   │       ├── gui/          # Interface gráfica
│   │       └── to/           # Transfer Objects
│   └── resources/            # Recursos da aplicação
└── target/                   # Arquivos compilados
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+

### Passos para execução

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/FelipeCatarino/Sistema-Vendas-java.git
   cd Sistema-Vendas-java
   ```

2. **Compile o projeto:**
   ```bash
   mvn compile
   ```

3. **Execute a aplicação:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.sistemaMaster.Principal"
   ```

   Ou execute o JAR gerado:
   ```bash
   java -jar target/meu-sistema-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

## 💾 Banco de Dados

O sistema utiliza SQLite com criação automática das tabelas na primeira execução. O arquivo do banco (`bancodedadosvendas.db`) é criado automaticamente no diretório do projeto.

### Localização do banco:
- **Linux/Mac**: `./bancodedadosvendas.db`
- **Windows**: `.\bancodedadosvendas.db`

## 📄 Relatórios

Os relatórios em PDF são salvos na pasta `relatorios/` na raiz do projeto. O sistema cria automaticamente esta pasta caso ela não exista.

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para:
- Reportar bugs
- Sugerir melhorias
- Enviar pull requests

## 📝 Licença

Este projeto é fornecido gratuitamente "no estado em que se encontra", sem garantias de qualquer tipo.

## 👨‍💻 Desenvolvimento

**Desenvolvido por:** Felipe da Costa Catarino  
**Email:** felipecatarinu@gmail.com  
**GitHub:** [@FelipeCatarino](https://github.com/FelipeCatarino)

### 🙏 Créditos

Baseado na estrutura inicial de interfaces gráficas desenvolvida por **Juliano Denner da Rocha** como material de apoio ao aprendizado de Java Swing. O sistema atual foi completamente reescrito e expandido com novas funcionalidades, arquitetura MVC, persistência de dados e relatórios.

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!