package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "@24fev1848@";
    
    // método estático que devolve uma conexão nova toda vez que for chamado
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // carrega o driver JDBC do MySQL na memória
        } catch (ClassNotFoundException e) {
            // se o driver não estiver no classpath (falta a dependência no pom.xml), lança erro claro
            throw new SQLException("Driver JDBC do MySQL não encontrado. Adicione o mysql-connector-j no pom.xml", e);
        }
        // abre e retorna a conexão de fato, usando URL, usuário e senha definidos acima
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}
