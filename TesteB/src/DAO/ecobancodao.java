package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.Conexao;
import entity.funcionarios;
import entity.pontosdecoleta;
import entity.rastreamento;
import entity.voluntarios;
import entity.residuos;
import entity.administradores;

public class ecobancodao {
    public void cadastrarVoluntario(voluntarios voluntario) {
        String sql = "INSERT INTO VOLUNTARIOS (CPFVOL, NOMEVOL, FONEVOL, EMAILVOL, SENHAVOL) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, voluntario.getCpfvol());
            ps.setString(2, voluntario.getNomevol());
            ps.setString(3, voluntario.getFonevol());
            ps.setString(4, voluntario.getEmailvol());
            ps.setString(5, voluntario.getSenhavol());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public voluntarios buscarUsuario(String email, String senha) {
        voluntarios usuario = null;
        String sql = "SELECT * FROM VOLUNTARIOS WHERE EMAILVOL = ? AND SENHAVOL = ?";
        
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new voluntarios();
                usuario.setCpfvol(rs.getString("CPFVOL"));
                usuario.setNomevol(rs.getString("NOMEVOL"));
                usuario.setFonevol(rs.getString("FONEVOL"));
                usuario.setEmailvol(rs.getString("EMAILVOL"));
                usuario.setSenhavol(rs.getString("SENHAVOL"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuario;
    }
    public void cadastrarPontoColeta(pontosdecoleta pontoColeta, funcionarios funcionario) {
    String sqlPontoColeta = "INSERT INTO PONTOSDECOLETA (ENDPONTOCOLETA, CEPPONTOCOLETA, BAIRROPONTOCOLETA) VALUES (?, ?, ?)";
    String sqlFuncionario = "INSERT INTO FUNCIONARIOS (CPFFUNC, NOMEFUNC, FONEFUNC, EMAILFUNC, SENHAFUNC, CODPONTOCOLETA) VALUES (?, ?, ?, ?, ?, LAST_INSERT_ID())";

    try (PreparedStatement psPontoColeta = Conexao.getConexao().prepareStatement(sqlPontoColeta);
         PreparedStatement psFuncionario = Conexao.getConexao().prepareStatement(sqlFuncionario)) {

        psPontoColeta.setString(1, pontoColeta.getEndpontocoleta());
        psPontoColeta.setString(2, pontoColeta.getCeppontocoleta());
        psPontoColeta.setString(3, pontoColeta.getBairropontocoleta());

        psPontoColeta.executeUpdate();

        psFuncionario.setString(1, funcionario.getCpffunc());
        psFuncionario.setString(2, funcionario.getNomefunc());
        psFuncionario.setString(3, funcionario.getFonefunc());
        psFuncionario.setString(4, funcionario.getEmailfunc());
        psFuncionario.setString(5, funcionario.getSenhafunc());

        psFuncionario.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public funcionarios buscarFuncionario(String email, String senha) {
        funcionarios funcionario = null;
        String sql = "SELECT * FROM FUNCIONARIOS WHERE EMAILFUNC = ? AND SENHAFUNC = ?";
    
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
    
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                funcionario = new funcionarios();
                funcionario.setCpffunc(rs.getString("CPFFUNC"));
                funcionario.setNomefunc(rs.getString("NOMEFUNC"));
                funcionario.setFonefunc(rs.getString("FONEFUNC"));
                funcionario.setEmailfunc(rs.getString("EMAILFUNC"));
                funcionario.setSenhafunc(rs.getString("SENHAFUNC"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionario;
    }
    public void cadResiduos(residuos res){
        String sql = "INSERT INTO RESIDUOS (TIPORESIDUO, CATEGORIA, DESCRICAO, DESCARTE) VALUES (?, ?, ?, ?)";

        try  (PreparedStatement psResiduos = Conexao.getConexao().prepareStatement(sql)){
            psResiduos.setString(1, res.getTiporesiduo());
            psResiduos.setString(2, res.getCategoria());
            psResiduos.setString(3, res.getDescricao());
            psResiduos.setString(4, res.getDescarte());

            psResiduos.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public administradores buscarAdministrador(String email, String senha) {
        administradores administrador = null;
        String sql = "SELECT * FROM administradores WHERE email = ? AND senha = ?";
    
        try (PreparedStatement psAdministrador = Conexao.getConexao().prepareStatement(sql)) {
            psAdministrador.setString(1, email);
            psAdministrador.setString(2, senha);
    
            ResultSet rs = psAdministrador.executeQuery();
    
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                administrador = new administradores(email, senha, nome);
                administrador.setId(id); // opcional, dependendo do uso
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return administrador;
    }

    public void rastrear (rastreamento quantcoletares){
        String sql= "INSERT INTO RASTREAMENTO (QUANTCOLETARES) VALUES (?)";
        try (PreparedStatement psRastro=Conexao.getConexao().prepareStatement(sql)){
            psRastro.setString(1, quantcoletares.getQuantcoletares());

            psRastro.executeUpdate();
        }
            
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public residuos buscaResiduos(String tiporesiduo, String categoria, String descricao, String descarte){
        residuos residuo=null;
        String sql="SELECT * FROM RESIDUOS WHERE TIPORESIDUO=? AND CATEGORIA=? AND DESCRICAO=? AND DESCARTE=?";
        try (PreparedStatement rt=Conexao.getConexao().prepareStatement(sql)){
            rt.setString(1, tiporesiduo);
            rt.setString(2, categoria);
            rt.setString(3, descricao);
            rt.setString(4, descarte);
            ResultSet rs = rt.executeQuery();
            if (rs.next()){
                residuo=new residuos();
                residuo.setTiporesiduo(rs.getString("TIPORESIDUO"));
                residuo.setCategoria(rs.getString("CATEGORIA"));
                residuo.setDescricao(rs.getString("DESCRICAO"));
                residuo.setDescarte(rs.getString("DESCARTE"));

                

            }rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return residuo;
    }

    }

    