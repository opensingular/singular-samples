CREATE TABLE TB_ENDERECO (
  COD INT,
  CEP INT,
  SIGLA_UF VARCHAR(2),
  ENDERECO VARCHAR(200),
  NUMERO INT,
  BAIRRO VARCHAR(200)
);

CREATE TABLE TB_UF (
  SIGLA VARCHAR(2),
  NOME VARCHAR(100)
);