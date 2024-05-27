import DAO.ecobancodao;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import conexao.*;

public class ExcluirConta extends JFrame {
    private String cpfUsuarioLogado;

    public ExcluirConta(String cpf) {
        this.cpfUsuarioLogado = cpf;
        setTitle("Excluir Conta");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblConfirmacao = new JLabel("Tem certeza que deseja excluir sua conta?");
        lblConfirmacao.setBounds(20, 20, 250, 25);
        add(lblConfirmacao);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(100, 100, 100, 25);
        add(btnConfirmar);

        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirConta(cpfUsuarioLogado);
                new Menu();
                dispose();
            }
        });

        setVisible(true);
    }

    private void excluirConta(String cpfUsuarioLogado) {
        ecobancodao banco = new ecobancodao();
        Connection conexao = Conexao.getConexao();
        
        try {
            conexao.setAutoCommit(false);

            int codvol = getCodVoluntario(cpfUsuarioLogado);
            if (codvol != -1) {
                
                String deleteRastreamento = "DELETE FROM RASTREAMENTO WHERE CODVOL = ?";
                try (PreparedStatement stmtRastreamento = conexao.prepareStatement(deleteRastreamento)) {
                    stmtRastreamento.setInt(1, codvol);
                    stmtRastreamento.executeUpdate();
                }

                String deleteVoluntario = "DELETE FROM VOLUNTARIOS WHERE CODVOL = ?";
                try (PreparedStatement stmtVoluntario = conexao.prepareStatement(deleteVoluntario)) {
                    stmtVoluntario.setInt(1, codvol);
                    stmtVoluntario.executeUpdate();
                }
            } else {
                
                String deleteFuncionario = "DELETE FROM FUNCIONARIOS WHERE CPFFUNC = ?";
                try (PreparedStatement stmtFuncionario = conexao.prepareStatement(deleteFuncionario)) {
                    stmtFuncionario.setString(1, cpfUsuarioLogado);
                    stmtFuncionario.executeUpdate();
                }
            }

            conexao.commit();
            JOptionPane.showMessageDialog(null, "Conta excluída com sucesso!");
        } catch (SQLException e) {
            try {
                conexao.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir a conta.");
        } finally {
            try {
                conexao.setAutoCommit(true);
                conexao.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
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

    public static void main(String[] args) {
        String cpfUsuarioLogado = args.length > 0 ? args[0] : "";
        new ExcluirConta(cpfUsuarioLogado);
    }
}