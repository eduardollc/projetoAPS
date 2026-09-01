drop schema if exists locadoraVeiculo;
CREATE SCHEMA IF NOT EXISTS locadoraVeiculo ;
USE locadoraVeiculo ;

CREATE TABLE IF NOT EXISTS `Usuario` (
  `login` VARCHAR(250) NOT NULL,
  `senha` VARCHAR(8) NOT NULL,
  `nome` VARCHAR(250) NOT NULL,
  `cpf` CHAR(11) NOT NULL,
  PRIMARY KEY (`login`));

INSERT INTO usuario (login, senha, nome, cpf)
VALUES ('teste', '123456', 'Usuário Teste', '12345678900');

CREATE TABLE IF NOT EXISTS `Cliente` (
  `cnh` CHAR(11) NOT NULL,
  `telefone` CHAR(11) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `situacaoFinanceira` TINYINT NOT NULL,
  `login` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`login`),
  CONSTRAINT `fk_Cliente_Usuario`
    FOREIGN KEY (`login`)
    REFERENCES `Usuario` (`login`));


CREATE TABLE IF NOT EXISTS `Funcionario` (
  `matricula` CHAR(8) NOT NULL,
  `cargo` VARCHAR(45) NOT NULL,
  `login` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`login`),
  CONSTRAINT `fk_Funcionario_Usuario1`
    FOREIGN KEY (`login`)
    REFERENCES `Usuario` (`login`));


CREATE TABLE IF NOT EXISTS `Pagamento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `formaPagamento` ENUM('Pix', 'Dinheiro', 'Cartão') NOT NULL,
  `valor` DECIMAL NOT NULL,
  `dataPagamento` DATETIME NOT NULL,
  `status` ENUM('Aprovado', 'Recusado') NOT NULL,
  `comprovante` VARCHAR(100) NOT NULL,
  `notaFiscal` VARCHAR(100) NOT NULL,
  `codCliente` CHAR(11) NOT NULL,
  `codFuncionario` CHAR(8) NOT NULL,
  `numeroCartao` CHAR(16),
  `dataValidade` CHAR(4),
  `cvv` CHAR(4),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Pagamento_Cliente1`
  FOREIGN KEY (`codCliente`)
  REFERENCES `Cliente` (`login`),
  CONSTRAINT `fk_Pagamento_Funcionario1`
  FOREIGN KEY (`codFuncionario`)
  REFERENCES `Funcionario` (`login`));
  
  select * from usuario;

INSERT INTO cliente (cnh, telefone, email, situacaoFinanceira, login)
VALUES ('12345678901', '77999998888', 'teste@email.com', 1, 'teste');

INSERT INTO usuario (login, senha, nome, cpf)
VALUES ('func1', '123456', 'Funcionário Teste', '98765432100');

INSERT INTO funcionario (matricula, cargo, login)
VALUES ('F0000001', 'Atendente', 'func1');