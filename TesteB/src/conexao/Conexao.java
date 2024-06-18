package conexao;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String url = "jdbc:mysql://localhost:3306/ecobanco";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection connn;

    public static Connection getConexao(){
        try {
        if(connn == null){
            connn = DriverManager.getConnection(url, user, password);
            return connn;
        }else {
            return connn;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    }
}
