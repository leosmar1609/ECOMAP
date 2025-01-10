package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/ecobanco";
    private static final String USER = "root";
    private static final String PASSWORD = "75869470Leo.";

    private Conexao() { }

    public static Connection getConexao() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Carregar explicitamente o Driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC MySQL não encontrado!", e);
            }
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void closeConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
