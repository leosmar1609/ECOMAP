import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import conexao.*;

public class ExcluirConta extends JFrame {
    private String cpfUsuarioLogado;

    public ExcluirConta(String cpf) {
        this.cpfUsuarioLogado = cpf;
        String basePath = System.getProperty("user.dir");
        setTitle("Excluir Conta");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51));
        topPanel.setBounds(0, 0, 800, 150);
        topPanel.setLayout(null);
        add(topPanel);

        String imagePath = basePath + File.separator + "images" + File.separator + "logoc.png";
        ImageIcon logoIcon = new ImageIcon(imagePath);
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(30, 25, 100, 100);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Olá, novamente!");
        titleLabel.setBounds(140, 30, 600, 30);
        titleLabel.setForeground(new Color(0xD3B88C));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Exclua aqui a sua conta!");
        subtitleLabel.setBounds(140, 70, 600, 30);
        subtitleLabel.setForeground(new Color(0xD3B88C));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitleLabel);

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

        JButton btnConfirmar = createButton("Confirmar", 200, 150, 120, 30);
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirConta(cpfUsuarioLogado);
                new Menu();
                dispose();
            }
        });
        centerPanel.add(btnConfirmar);

        JButton btnCancelar = createButton("Cancelar", 350, 150, 120, 30);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Main().setVisible(true);
            }
        });
        centerPanel.add(btnCancelar);

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 102, 51));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 102, 51));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        button.setFont(new Font("Helvetica", Font.BOLD, 14));
        button.setBounds(x, y, width, height);
        button.setForeground(new Color(246, 234, 215));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    private void excluirConta(String cpfUsuarioLogado) {
        Connection conexao = null;
        try {
            conexao = Conexao.getConexao();
            conexao.setAutoCommit(false);

            int codvol = getCodVoluntario(cpfUsuarioLogado);
            if (codvol != -1) {
                String deleteVoluntario = "UPDATE voluntarios SET estadovol = 'OFF', emailvol = '', senhavol = '' WHERE codvol = ?";
                try (PreparedStatement stmtVoluntario = conexao.prepareStatement(deleteVoluntario)) {
                    stmtVoluntario.setInt(1, codvol);
                    stmtVoluntario.executeUpdate();
                }
            } else {
                int codpc = getCodPc(cpfUsuarioLogado);
                String deleteras = "DELETE FROM RASTREAMENTO WHERE CODPONTOCOLETA = ?";
                String deleteFuncionario = "DELETE FROM FUNCIONARIOS WHERE CPFFUNC = ?";
                String deletepc = "DELETE FROM PONTOSDECOLETA WHERE CODPONTOCOLETA = ?";
                try (PreparedStatement stmtras = conexao.prepareStatement(deleteras);
                     PreparedStatement stmtFuncionario = conexao.prepareStatement(deleteFuncionario);
                     PreparedStatement stmtpc = conexao.prepareStatement(deletepc)) {

                    stmtras.setInt(1, codpc);
                    stmtras.executeUpdate();

                    stmtFuncionario.setString(1, cpfUsuarioLogado);
                    stmtFuncionario.executeUpdate();

                    stmtpc.setInt(1, codpc);
                    stmtpc.executeUpdate();
                }
            }
            conexao.commit();
            JOptionPane.showMessageDialog(null, "Conta excluída com sucesso!");
        } catch (SQLException e) {
            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir a conta.");
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

    private int getCodPc(String cpfUsuarioLogado) {
        int codvol = -1;
        String query = "SELECT CODPONTOCOLETA FROM FUNCIONARIOS WHERE CPFFUNC = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, cpfUsuarioLogado);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                codvol = rs.getInt("CODPONTOCOLETA");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codvol;
    }

    // public static void main(String[] args) {
    //     String cpfUsuarioLogado = args.length > 0 ? args[0] : "";
    //     new ExcluirConta(cpfUsuarioLogado);
    // }
}