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

import java.sql.Timestamp;

public class ecobancodao {
    public void cadastrarOuAtualizarVoluntario(voluntarios voluntario) {
        String verificarSql = "SELECT * FROM VOLUNTARIOS WHERE CPFVOL = ?";
        String atualizarSql = "UPDATE VOLUNTARIOS SET NOMEVOL = ?, FONEVOL = ?, EMAILVOL = ?, SENHAVOL = ?, ESTADOVOL = 'ON' WHERE CPFVOL = ?";
        String inserirSql = "INSERT INTO VOLUNTARIOS (CODVOL, CPFVOL, NOMEVOL, FONEVOL, EMAILVOL, SENHAVOL, ESTADOVOL) VALUES (?, ?, ?, ?, ?, AES_ENCRYPT(?, '1234321'), 'ON')";

        try (Connection conexao = Conexao.getConexao()) {
            PreparedStatement psVerificar = conexao.prepareStatement(verificarSql);
            psVerificar.setString(1, voluntario.getCpfvol());
            ResultSet rs = psVerificar.executeQuery();

            if (rs.next()) {
                // Voluntário existente, atualizar dados
                PreparedStatement psAtualizar = conexao.prepareStatement(atualizarSql);
                psAtualizar.setString(1, voluntario.getNomevol());
                psAtualizar.setString(2, voluntario.getFonevol());
                psAtualizar.setString(3, voluntario.getEmailvol());
                psAtualizar.setString(4, voluntario.getSenhavol());
                psAtualizar.setString(5, voluntario.getCpfvol());
                psAtualizar.executeUpdate();
                System.out.println("Voluntário atualizado com sucesso.");
            } else {
                // Novo voluntário, inserir dados
                PreparedStatement psInserir = conexao.prepareStatement(inserirSql);
                psInserir.setInt(1, voluntario.getCodvol());
                psInserir.setString(2, voluntario.getCpfvol());
                psInserir.setString(3, voluntario.getNomevol());
                psInserir.setString(4, voluntario.getFonevol());
                psInserir.setString(5, voluntario.getEmailvol());
                psInserir.setString(6, voluntario.getSenhavol());
                psInserir.executeUpdate();
                System.out.println("Voluntário cadastrado com sucesso.");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public voluntarios buscarUsuario(String email, String senha) {
        voluntarios usuario = null;
        // A consulta SQL descriptografa o CPF e a senha
        String sql = "SELECT codvol, cpfvol, nomevol, fonevol, emailvol, AES_DECRYPT(senhavol, '1234321') AS senhavol " +
                     "FROM VOLUNTARIOS " +
                     "WHERE emailvol = ? AND AES_DECRYPT(senhavol, '1234321') = ?";
    
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            // Usa a mesma chave secreta para descriptografar a senha
            ps.setString(1, email);
            ps.setString(2, senha);
    
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new voluntarios();
                usuario.setCodvol(rs.getInt("CODVOL"));
                usuario.setCpfvol(rs.getString("cpfvol"));
                usuario.setNomevol(rs.getString("NOMEVOL"));
                usuario.setFonevol(rs.getString("FONEVOL"));
                usuario.setEmailvol(rs.getString("emailvol"));
                usuario.setSenhavol(rs.getString("senhavol"));
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
    
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
    
            ResultSet rs = ps.executeQuery();
    
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
    

    private int getCodVoluntario(String cpf) {
        int codvol = -1;
        String query = "SELECT CODVOL FROM VOLUNTARIOS WHERE CPFVOL = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, cpf);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                codvol = rs.getInt("CODVOL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codvol;
    }


    public void deletarVoluntario(voluntarios voluntario) {
        String queryUpdateEstado = "UPDATE voluntarios SET estadovol = 'OFF' WHERE codvol = ?";
        String queryUpdateCredenciais = "UPDATE voluntarios SET emailvol = '', senhavol = '' WHERE codvol = ?";
    
        try (Connection connection = Conexao.getConexao();
             PreparedStatement psUpdateEstado = connection.prepareStatement(queryUpdateEstado);
             PreparedStatement psUpdateCredenciais = connection.prepareStatement(queryUpdateCredenciais)) {
    
            connection.setAutoCommit(false);
            
            int codvol = getCodVoluntario(voluntario.getCpfvol());
            if (codvol != -1) {
                psUpdateEstado.setInt(1, voluntario.getCodvol());
                psUpdateEstado.executeUpdate();
    
                psUpdateCredenciais.setInt(1, voluntario.getCodvol());
                psUpdateCredenciais.executeUpdate();
    
                connection.commit();
            } else {
                System.out.println("Voluntário não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    public void deletarFuncionario(funcionarios fun) {
        String deleteFuncSql = "DELETE FROM FUNCIONARIOS WHERE CPFFUNC = ?";
    
        Connection connection = null;
        PreparedStatement psDeleteFunc = null;
    
        try {
            connection = Conexao.getConexao();
            connection.setAutoCommit(false);
    
            // Excluir o funcionário
            psDeleteFunc = connection.prepareStatement(deleteFuncSql);
            psDeleteFunc.setString(1, fun.getCpffunc());
            int rowsDeletedFunc = psDeleteFunc.executeUpdate();
            System.out.println("Rows deleted from FUNCIONARIOS: " + rowsDeletedFunc);
    
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
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
                dvol.setEstadovol(rs.getString("ESTADOVOL"));
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

    public void inserirRastreamento(rastreamento rastreamento) throws SQLException {
        String sql = "INSERT INTO RASTREAMENTO (TIPORESIDUO, CODRESIDUO, CODVOL, CODPONTOCOLETA, QUANTCOLETADA, DATAHORA_COLETA) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, rastreamento.getTiporesiduo());
            ps.setInt(2, rastreamento.getCodresiduo());
            ps.setInt(3, rastreamento.getCodvol());
            ps.setInt(4, rastreamento.getCodpontocoleta());
            ps.setString(5, rastreamento.getQuantcoletada());
            ps.setTimestamp(6, Timestamp.valueOf(rastreamento.getDatahora_coleta())); // Convertendo LocalDateTime para Timestamp
    
            ps.executeUpdate();
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