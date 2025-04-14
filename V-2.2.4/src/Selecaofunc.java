import javax.swing.*;
import conexao.Conexao;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Selecaofunc extends JFrame {
    private JComboBox<String> cbFuncionarios;
    private JButton btnProseguir;

    public Selecaofunc() {
        String basePath = System.getProperty("user.dir");
        setTitle("Selecione o funcionário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
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

        JLabel titleLabel = new JLabel("Seleção");
        titleLabel.setBounds(140, 30, 600, 30);
        titleLabel.setForeground(new Color(0xD3B88C));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Selecione o funcionário");
        subtitleLabel.setBounds(140, 70, 600, 30);
        subtitleLabel.setForeground(new Color(0xD3B88C));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitleLabel);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblFuncionario = new JLabel("Selecione o Funcionário:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(lblFuncionario, gbc);

        cbFuncionarios = new JComboBox<>(getFuncionarios().toArray(new String[0]));
        gbc.gridx = 1;
        painelPrincipal.add(cbFuncionarios, gbc);

        btnProseguir = new JButton("Prosseguir");
        gbc.gridx = 1;
        gbc.gridy = 1;
        painelPrincipal.add(btnProseguir, gbc);

        btnProseguir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String funcionarioSelecionado = (String) cbFuncionarios.getSelectedItem();
                if (funcionarioSelecionado != null) {
                    new RastreamentoADM(funcionarioSelecionado).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione um funcionário.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        painelPrincipal.setBounds(150, 180, 500, 200);
        add(painelPrincipal);
    }

    private ArrayList<String> getFuncionarios() {
        ArrayList<String> funcionarios = new ArrayList<>();
        String sql = "SELECT nomefunc FROM funcionarios";

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                funcionarios.add(rs.getString("nomefunc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao buscar funcionários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return funcionarios;
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             new Selecaofunc().setVisible(true);
    //         }
    //     });
    // }
}
