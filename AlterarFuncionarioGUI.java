import DAO.ecobancodao;
import entity.funcionarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterarFuncionarioGUI extends JFrame {
    private JTextField cpfField;
    private JButton verificarButton;
    private JComboBox<String> campoComboBox;
    private JTextField novoValorField;
    private JButton salvarButton;
    private JButton voltarButton;
    private ecobancodao dao;
    private funcionarios funcionarioAtual;

    public AlterarFuncionarioGUI() {
        setTitle("Alterar Dados do Funcionário");
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
        headerPanel.setBackground(new Color(0, 102, 51)); // Green background
        headerPanel.setPreferredSize(new Dimension(800, 120));
        headerPanel.setLayout(new BorderLayout());

        // Load logo image
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Ryan\\Downloads\\logo.png"); // Change this path to your logo image
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel adminLabel = new JLabel("Oi, administrador!");
        adminLabel.setForeground(new Color(216, 193, 162)); // Light brown color
        adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminLabel.setFont(new Font("Helvética", Font.BOLD, 20));

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
        cpfLabel.setForeground(new Color(0, 102, 51));
        cpfLabel.setFont(new Font("Helvética", Font.PLAIN, 14));
        cpfLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpfField = new JTextField(20);
        verificarButton = createButton("VERIFICAR CPF");

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
        campoLabel.setForeground(new Color(0, 102, 51));
        campoLabel.setFont(new Font("Helvética", Font.PLAIN, 14));
        campoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String[] campos = {"Nome", "Telefone", "Email", "Senha"};
        campoComboBox = new JComboBox<>(campos);
        novoValorField = new JTextField(20);
        salvarButton = createButton("SALVAR");

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

        // Bottom panel for the "Voltar" button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 255, 255));
        voltarButton = createButton("VOLTAR");
        bottomPanel.add(voltarButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpffunc = cpfField.getText();
                funcionarioAtual = dao.pesquisarFuncionario(cpffunc);

                if (funcionarioAtual != null) {
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "CPF encontrado. Selecione o campo a ser alterado.");
                } else {
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "CPF não encontrado.");
                }
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (funcionarioAtual == null) {
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "Verifique um CPF válido primeiro.");
                    return;
                }

                String campoSelecionado = (String) campoComboBox.getSelectedItem();
                String novoValor = novoValorField.getText();

                switch (campoSelecionado) {
                    case "Nome":
                        funcionarioAtual.setNomefunc(novoValor);
                        break;
                    case "Telefone":
                        funcionarioAtual.setFonefunc(novoValor);
                        break;
                    case "Email":
                        funcionarioAtual.setEmailfunc(novoValor);
                        break;
                    case "Senha":
                        funcionarioAtual.setSenhafunc(novoValor);
                        break;
                }

                funcionarios updatedFuncionario = dao.alterarDadosFunc(
                        funcionarioAtual.getCpffunc(),
                        funcionarioAtual.getNomefunc(),
                        funcionarioAtual.getFonefunc(),
                        funcionarioAtual.getEmailfunc(),
                        funcionarioAtual.getSenhafunc()
                );

                if (updatedFuncionario != null) {
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "Funcionário atualizado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "Erro ao atualizar os dados do funcionário.");
                }
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdmMainGUI(); // Assuming AdmMainGUI is the main admin GUI class
            }
        });

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 102,51)); // Button background color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 102, 51)); // Button border color
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        button.setFont(new Font("Helvetica", Font.BOLD, 14));
        button.setForeground(new Color(255, 255, 255)); // Text color
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlterarFuncionarioGUI();
            }
        });
    }
}
