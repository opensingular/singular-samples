CREATE TABLE TB_CARRINHO (
  COD INT,
  PRIMARY KEY (COD)
);

CREATE TABLE TB_PRODUTO (
  COD INT,
  NOME VARCHAR(200),
  PRIMARY KEY (COD),
);

CREATE TABLE RL_CARRINHO_PRODUTO (
  COD_CARRINHO INT,
  COD_PRODUTO INT,
  FOREIGN KEY (COD_CARRINHO) REFERENCES TB_CARRINHO(COD),
  FOREIGN KEY (COD_PRODUTO) REFERENCES TB_PRODUTO(COD)
)