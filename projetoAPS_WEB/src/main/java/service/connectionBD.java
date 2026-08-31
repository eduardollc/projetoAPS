package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/locadoraVeiculo";
    private static final String USER = "root";
    private static final String PASSWORD = "antonio";
    
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // carrega o driver JDBC do MySQL na memória
        } catch (ClassNotFoundException e) {
            
            throw new SQLException("Driver JDBC do MySQL não encontrado. Adicione o mysql-connector-j no pom.xml", e);
        }
        // abre e retorna a conexão de fato, usando URL, usuário e senha definidos acima
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}
