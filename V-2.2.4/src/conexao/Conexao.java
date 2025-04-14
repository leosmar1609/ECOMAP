package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://meu-banco.c3i8yc24mm3h.us-east-2.rds.amazonaws.com:3306/ecobanco";
    private static final String USER = "admin";
    private static final String PASSWORD = "75869470";

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
