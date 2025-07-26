
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS tbcliente (
  Codigo INTEGER PRIMARY KEY,
  Nome TEXT NOT NULL,
  CPF TEXT NOT NULL,
  DataNascimento DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS tbfornecedor (
  Codigo INTEGER PRIMARY KEY,
  Nome TEXT NOT NULL,
  CNPJ TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tbproduto (
  Codigo INTEGER PRIMARY KEY,
  Nome TEXT NOT NULL,
  PrecoCompra REAL NOT NULL,
  PrecoVenda REAL NOT NULL,
  QuantidadeEstoque INTEGER NOT NULL,
  CodigoProduto TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tbvenda (
  Codigo INTEGER PRIMARY KEY,
  CodigoCliente INTEGER NOT NULL,
  DataVenda DATE NOT NULL,
  ValorTotal REAL NOT NULL,
  Situacao INTEGER NOT NULL,
  Forma_Pagamento INTEGER NOT NULL,
  FOREIGN KEY (CodigoCliente) REFERENCES tbcliente (Codigo)
);

CREATE TABLE IF NOT EXISTS tbcompra (
  Codigo INTEGER PRIMARY KEY,
  CodigoFornecedor INTEGER NOT NULL,
  DataCompra DATE NOT NULL,
  ValorTotal REAL NOT NULL,
  Situacao INTEGER NOT NULL,
  FOREIGN KEY (CodigoFornecedor) REFERENCES tbfornecedor (Codigo)
);

CREATE TABLE IF NOT EXISTS tbitemvenda (
  Codigo INTEGER PRIMARY KEY,
  CodigoProduto INTEGER NOT NULL,
  CodigoVenda INTEGER NOT NULL,
  Quantidade INTEGER NOT NULL,
  ValorUnitario REAL NOT NULL,
  FOREIGN KEY (CodigoProduto) REFERENCES tbproduto (Codigo),
  FOREIGN KEY (CodigoVenda) REFERENCES tbvenda (Codigo)
);

CREATE TABLE IF NOT EXISTS tbitemcompra (
  Codigo INTEGER PRIMARY KEY,
  CodigoProduto INTEGER NOT NULL,
  CodigoCompra INTEGER NOT NULL,
  Quantidade INTEGER NOT NULL,
  ValorUnitario REAL NOT NULL,
  FOREIGN KEY (CodigoProduto) REFERENCES tbproduto (Codigo),
  FOREIGN KEY (CodigoCompra) REFERENCES tbcompra (Codigo)
);
