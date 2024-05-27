package DAO;

import java.sql.Connection;
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
        String sql = "INSERT INTO VOLUNTARIOS (CODVOL, CPFVOL, NOMEVOL, FONEVOL, EMAILVOL, SENHAVOL) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, voluntario.getCodvol());
            ps.setString(2, voluntario.getCpfvol());
            ps.setString(3, voluntario.getNomevol());
            ps.setString(4, voluntario.getFonevol());
            ps.setString(5, voluntario.getEmailvol());
            ps.setString(6, voluntario.getSenhavol());

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
                usuario.setCodvol(rs.getInt("CODVOL"));
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

    public void cadResiduos(residuos res) {
        String sql = "INSERT INTO RESIDUOS (TIPORESIDUO, CATEGORIA, DESCRICAO, DESCARTE) VALUES (?, ?, ?, ?)";

        try (PreparedStatement psResiduos = Conexao.getConexao().prepareStatement(sql)) {
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
                administrador.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return administrador;
    }

    public void deletarVoluntario(voluntarios vol) {
        String sqlVol = "DELETE FROM VOLUNTARIOS WHERE CPFVOL = ?";

        try (Connection connection = Conexao.getConexao();
                PreparedStatement psDeleteVol = connection.prepareStatement(sqlVol)) {

            connection.setAutoCommit(false);

            psDeleteVol.setString(1, vol.getCpfvol());
            psDeleteVol.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarFuncionario(funcionarios fun) {
        String sqlFunc = "DELETE FROM FUNCIONARIOS WHERE CPFFUNC = ?";

        try (Connection connection = Conexao.getConexao();
                PreparedStatement psDeleteFunc = connection.prepareStatement(sqlFunc)) {

            connection.setAutoCommit(false);

            psDeleteFunc.setString(1, fun.getCpffunc());
            psDeleteFunc.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public voluntarios pesquisarUsuario(String cpfvol) {
        voluntarios dvol = null;

        String query = "SELECT * FROM VOLUNTARIOS WHERE CPFVOL = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, cpfvol);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                dvol = new voluntarios();
                dvol.setCodvol(rs.getInt("CODVOL"));
                dvol.setCpfvol(rs.getString("CPFVOL"));
                dvol.setNomevol(rs.getString("NOMEVOL"));
                dvol.setFonevol(rs.getString("FONEVOL"));
                dvol.setEmailvol(rs.getString("EMAILVOL"));
                dvol.setSenhavol(rs.getString("SENHAVOL"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvol;
    }

    public funcionarios pesquisarFuncionario(String cpffunc) {
        funcionarios dfunc = null;
        String que = "SELECT * FROM FUNCIONARIOS WHERE CPFFUNC = ?";
        try (PreparedStatement sta = Conexao.getConexao().prepareStatement(que)) {
            sta.setString(1, cpffunc);
            ResultSet rt = sta.executeQuery();

            if (rt.next()) {
                dfunc = new funcionarios();
                dfunc.setCpffunc(rt.getString("CPFFUNC"));
                dfunc.setNomefunc(rt.getString("NOMEFUNC"));
                dfunc.setFonefunc(rt.getString("FONEFUNC"));
                dfunc.setEmailfunc(rt.getString("EMAILFUNC"));
                dfunc.setSenhafunc(rt.getString("SENHAFUNC"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dfunc;
    }

    public voluntarios alterarDadosVol(String cpfvol, String nomevol, String fonevol, String emailvol, String senhavol) {
        voluntarios avol = null;
        String alter = "UPDATE VOLUNTARIOS SET nomevol = ?, fonevol = ?, emailvol = ?, senhavol = ? WHERE cpfvol = ?";
    
        try (PreparedStatement altvol = Conexao.getConexao().prepareStatement(alter)) {
            altvol.setString(1, nomevol);
            altvol.setString(2, fonevol);
            altvol.setString(3, emailvol);
            altvol.setString(4, senhavol);
            altvol.setString(5, cpfvol);
    
            int rowsUpdated = altvol.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Voluntário atualizado com sucesso.");
                avol = new voluntarios();
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar os dados do voluntário: " + e.getMessage());
        }
        return avol;
    }

    public funcionarios alterarDadosFunc(String cpffunc, String nomefunc, String fonefunc, String emailfunc, String senhafunc) {
        funcionarios afunc = null;
        String alter = "UPDATE FUNCIONARIOS SET nomefunc = ?, fonefunc = ?, emailfunc = ?, senhafunc = ? WHERE cpffunc = ?";
    
        try (PreparedStatement altfunc = Conexao.getConexao().prepareStatement(alter)) {
            altfunc.setString(1, nomefunc);
            altfunc.setString(2, fonefunc);
            altfunc.setString(3, emailfunc);
            altfunc.setString(4, senhafunc);
            altfunc.setString(5, cpffunc);
    
            int rowsUpdated = altfunc.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Funcionário atualizado com sucesso.");
                afunc = new funcionarios();
                afunc.setCpffunc(cpffunc);
                afunc.setNomefunc(nomefunc);
                afunc.setFonefunc(fonefunc);
                afunc.setEmailfunc(emailfunc);
                afunc.setSenhafunc(senhafunc);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar os dados do funcionário: " + e.getMessage());
        }
        return afunc;
    }
     public void inserirRastreamento(rastreamento rast) {
        String sql = "INSERT INTO RASTREAMENTO (CODRESIDUO, TIPORESIDUO, CODPONTOCOLETA, QUANTCOLETARES, CODVOL) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(sql)) {
            statement.setInt(1, rast.getCodresiduo());
            statement.setString(2, rast.getTiporesiduo());
            statement.setInt(3, rast.getCodpontocoleta());
            statement.setString(4, rast.getQuantcoletares());
            statement.setInt(5, rast.getCodvol());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public voluntarios selectVol(voluntarios cpfvol){
        voluntarios dvol = null;

        String query = "SELECT CPFVOL, NOMEVOL FROM VOLUNTARIOS WHERE CPFVOL = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, cpfvol.getCpfvol());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                dvol = new voluntarios();
                dvol.setCpfvol(rs.getString("CPFVOL"));
                dvol.setNomevol(rs.getString("NOMEVOL"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvol;
    }
    public funcionarios selectFunc(funcionarios cpffunc){
        funcionarios dfunc = null;

        String query = "SELECT CPFFUNC, NOMEFUNC FROM FUNCIONARIOS WHERE CPFFUNC = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, cpffunc.getCpffunc());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                dfunc = new funcionarios();
                dfunc.setCpffunc(rs.getString("CPFFUNC"));
                dfunc.setNomefunc(rs.getString("NOMEFUNC"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dfunc;
    }
}