import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import conexao.*;

public class ExcluirConta extends JFrame {
    private String cpfUsuarioLogado;

    public ExcluirConta(String cpf) {
        this.cpfUsuarioLogado = cpf;
        setTitle("Excluir Conta");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Painel superior para o título e logo
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51));
        topPanel.setBounds(0, 0, 800, 150);
        topPanel.setLayout(null);
        add(topPanel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(20, 25, 100, 100);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Excluir Conta");
        titleLabel.setBounds(140, 30, 600, 30);
        titleLabel.setForeground(new Color(0xD3B88C));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Acesse o ECOMAP");
        subtitleLabel.setBounds(140, 70, 600, 30);
        subtitleLabel.setForeground(new Color(0xD3B88C));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitleLabel);

        // Painel para a mensagem de confirmação e botões
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(50, 200, 700, 300);
        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel);

        JLabel lblConfirmacao = new JLabel("Tem certeza que deseja excluir sua conta?");
        lblConfirmacao.setBounds(200, 50, 300, 25);
        lblConfirmacao.setHorizontalAlignment(SwingConstants.CENTER);
        lblConfirmacao.setFont(new Font("Helvetica", Font.PLAIN, 16));
        centerPanel.add(lblConfirmacao);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(200, 150, 120, 30);
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirConta(cpfUsuarioLogado);
                new Menu();
                dispose();
            }
        });
        btnConfirmar.setBackground(new Color(0, 102, 51));
        btnConfirmar.setForeground(Color.WHITE);
        centerPanel.add(btnConfirmar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(350, 150, 120, 30);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancelar.setBackground(new Color(0, 102, 51));
        btnCancelar.setForeground(Color.WHITE);
        centerPanel.add(btnCancelar);

        setVisible(true);
    }

    private void excluirConta(String cpfUsuarioLogado) {
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
