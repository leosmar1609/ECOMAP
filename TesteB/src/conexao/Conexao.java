package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/ecobanco";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Conexao() { }

    public static Connection getConexao() throws SQLException {
        if (connection == null || connection.isClosed()) {
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
