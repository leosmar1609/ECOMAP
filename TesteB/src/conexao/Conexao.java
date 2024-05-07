package conexao;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String url = "jdbc:mysql://localhost:3306/ecobanco";
    private static final String user = "root";
    private static final String password = "75869470Leo.";

    private static Connection conn;

    public static Connection getConexao(){
        try {
        if(conn == null){
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        }else {
            return conn;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    }
}