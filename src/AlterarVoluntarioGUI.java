import DAO.ecobancodao;
import entity.voluntarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterarVoluntarioGUI extends JFrame {
    private JTextField cpfField;
    private JButton verificarButton;
    private JComboBox<String> campoComboBox;
    private JTextField novoValorField;
    private JButton salvarButton;
    private ecobancodao dao;
    private voluntarios voluntarioAtual;

    public AlterarVoluntarioGUI() {
        setTitle("Alterar Dados do Voluntário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        // Main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255)); // White background

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(18, 70, 22)); // Green background
        headerPanel.setPreferredSize(new Dimension(800, 120));
        headerPanel.setLayout(new BorderLayout());

        // Load logo image
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png"); // Change this path to your logo image
        Image logoImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel adminLabel = new JLabel("Oi, administrador!");
        adminLabel.setForeground(new Color(216, 193, 162)); // Light brown color
        adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 20));

        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(adminLabel, BorderLayout.CENTER);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2));
        contentPanel.setBackground(new Color(255, 255, 255)); // White background

        // Left panel for verifying CPF
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel cpfLabel = new JLabel("Verifique o CPF");
        cpfLabel.setForeground(new Color(216, 193, 162));
        cpfLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cpfLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpfField = new JTextField(20);
        verificarButton = new JButton("VERIFICAR CPF");
        verificarButton.setBackground(new Color(18, 70, 22));
        verificarButton.setForeground(new Color(255, 255, 255));
        verificarButton.setBorderPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        leftPanel.add(cpfLabel, gbc);
        gbc.gridy = 1;
        leftPanel.add(cpfField, gbc);
        gbc.gridy = 2;
        leftPanel.add(verificarButton, gbc);

        // Right panel for changing field and saving
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(new Color(255, 255, 255));

        JLabel campoLabel = new JLabel("Escolha o campo a ser alterado");
        campoLabel.setForeground(new Color(216, 193, 162));
        campoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        campoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String[] campos = {"Nome", "Telefone", "Email", "Senha"};
        campoComboBox = new JComboBox<>(campos);
        novoValorField = new JTextField(20);
        salvarButton = new JButton("SALVAR");
        salvarButton.setBackground(new Color(18, 70, 22));
        salvarButton.setForeground(new Color(255, 255, 255));
        salvarButton.setBorderPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(campoLabel, gbc);
        gbc.gridy = 1;
        rightPanel.add(campoComboBox, gbc);
        gbc.gridy = 2;
        rightPanel.add(novoValorField, gbc);
        gbc.gridy = 3;
        rightPanel.add(salvarButton, gbc);

        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpfvol = cpfField.getText();
                voluntarioAtual = dao.pesquisarUsuario(cpfvol);

                if (voluntarioAtual != null) {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "CPF encontrado. Selecione o campo a ser alterado.");
                } else {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "CPF não encontrado.");
                }
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (voluntarioAtual == null) {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "Verifique um CPF válido primeiro.");
                    return;
                }

                String campoSelecionado = (String) campoComboBox.getSelectedItem();
                String novoValor = novoValorField.getText();

                switch (campoSelecionado) {
                    case "Nome":
                        voluntarioAtual.setNomevol(novoValor);
                        break;
                    case "Telefone":
                        voluntarioAtual.setFonevol(novoValor);
                        break;
                    case "Email":
                        voluntarioAtual.setEmailvol(novoValor);
                        break;
                    case "Senha":
                        voluntarioAtual.setSenhavol(novoValor);
                        break;
                }

                voluntarios updatedVoluntario = dao.alterarDadosVol(
                        voluntarioAtual.getCpfvol(),
                        voluntarioAtual.getNomevol(),
                        voluntarioAtual.getFonevol(),
                        voluntarioAtual.getEmailvol(),
                        voluntarioAtual.getSenhavol()
                );

                if (updatedVoluntario != null) {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "Voluntário atualizado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "Erro ao atualizar os dados do voluntário.");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlterarVoluntarioGUI();
            }
        });
    }
}
