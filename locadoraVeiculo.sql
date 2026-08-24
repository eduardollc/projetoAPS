drop schema if exists locadoraVeiculo;
CREATE SCHEMA IF NOT EXISTS locadoraVeiculo ;
USE locadoraVeiculo ;

CREATE TABLE IF NOT EXISTS `Usuario` (
  `login` VARCHAR(250) NOT NULL,
  `senha` VARCHAR(12) NOT NULL,
  `nome` VARCHAR(250) NOT NULL,
  `cpf` CHAR(11) NOT NULL,
  PRIMARY KEY (`login`));

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
  `dataPagamento` DATE NOT NULL,
  `status` ENUM('Aprovado', 'Recusado') NOT NULL,
  `comprovante` VARCHAR(100) NOT NULL,
  `notaFiscal` VARCHAR(100) NOT NULL,
  `codCliente` CHAR(11) NOT NULL,
  `codFuncionario` CHAR(8) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Pagamento_Cliente1`
    FOREIGN KEY (`codCliente`)
    REFERENCES `Cliente` (`login`),
  CONSTRAINT `fk_Pagamento_Funcionario1`
    FOREIGN KEY (`codFuncionario`)
    REFERENCES `Funcionario` (`login`));


CREATE TABLE IF NOT EXISTS `Cartao` (
  `numeroCartao` CHAR(16) NOT NULL,
  `nomeTitular` VARCHAR(100) NOT NULL,
  `dataValidade` CHAR(4) NOT NULL,
  `cvv` VARCHAR(4) NOT NULL,
  `login` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`numeroCartao`, `login`),
  CONSTRAINT `fk_Cartao_Cliente1`
    FOREIGN KEY (`login`)
    REFERENCES `Cliente` (`login`));