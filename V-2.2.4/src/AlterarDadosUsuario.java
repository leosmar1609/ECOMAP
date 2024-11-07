import DAO.ecobancodao;
import entity.voluntarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterarDadosUsuario extends JFrame {
    private JTextField cpfField;
    private JButton verificarButton;
    private JComboBox<String> campoComboBox;
    private JTextField novoValorField;
    private JButton salvarButton;
    private ecobancodao dao;
    private voluntarios voluntarioAtual;
    private String cpfUsuarioLogado;

    public AlterarDadosUsuario(String cpf) {
        this.cpfUsuarioLogado = cpf;
        setTitle("Alterar Dados do Voluntário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));

        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(new Color(0, 102, 51));
        headerPanel.setPreferredSize(new Dimension(800, 120));

        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(logoImage));
        logoLabel.setBounds(10, 0, 180, 120);
        headerPanel.add(logoLabel);

        JLabel adminLabel = new JLabel("Olá novamente, voluntário!");
        adminLabel.setForeground(new Color(246, 234, 215));
        adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminLabel.setFont(new Font("Helvética", Font.BOLD, 20));
        adminLabel.setBounds(200, 20, 400, 30);
        headerPanel.add(adminLabel);

        JLabel subLabel = new JLabel("Altere aqui os seus dados!");
        subLabel.setForeground(new Color(246, 234, 215));
        subLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subLabel.setFont(new Font("Helvética", Font.PLAIN, 16));
        subLabel.setBounds(200, 60, 400, 30);
        headerPanel.add(subLabel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2));
        contentPanel.setBackground(new Color(255, 255, 255));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        gbcLeft.insets = new Insets(10, 10, 10, 10);

        JLabel cpfLabel = new JLabel("Verifique o CPF");
        cpfLabel.setForeground(new Color(0, 102, 51));
        cpfLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cpfLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpfField = new JTextField(cpf);
        cpfField.setEditable(false);
        verificarButton = createRoundedButton("VERIFICAR CPF");

        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.gridwidth = 2;
        leftPanel.add(cpfLabel, gbcLeft);
        gbcLeft.gridy = 1;
        leftPanel.add(cpfField, gbcLeft);
        gbcLeft.gridy = 2;
        leftPanel.add(verificarButton, gbcLeft);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.fill = GridBagConstraints.HORIZONTAL;
        gbcRight.insets = new Insets(10, 10, 10, 10);

        JLabel campoLabel = new JLabel("Escolha o campo a ser alterado");
        campoLabel.setForeground(new Color(0, 102, 51));
        campoLabel.setFont(new Font("Helvética", Font.PLAIN, 14));
        campoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String[] campos = { "Nome", "Telefone", "Email", "Senha" };
        campoComboBox = new JComboBox<>(campos);
        novoValorField = new JTextField(20);
        salvarButton = createRoundedButton("SALVAR");

        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.gridwidth = 2;
        rightPanel.add(campoLabel, gbcRight);
        gbcRight.gridy = 1;
        rightPanel.add(campoComboBox, gbcRight);
        gbcRight.gridy = 2;
        rightPanel.add(novoValorField, gbcRight);
        gbcRight.gridy = 3;
        rightPanel.add(salvarButton, gbcRight);

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
                    JOptionPane.showMessageDialog(AlterarDadosUsuario.this,
                            "CPF encontrado. Selecione o campo a ser alterado.");
                } else {
                    JOptionPane.showMessageDialog(AlterarDadosUsuario.this, "CPF não encontrado.");
                }
            }
        });
        JButton voltarButton = createRoundedButton("VOLTAR");
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaVol(cpfUsuarioLogado).setVisible(true);
            }
        });

        mainPanel.add(voltarButton, BorderLayout.SOUTH);

        add(mainPanel);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (voluntarioAtual == null) {
                    JOptionPane.showMessageDialog(AlterarDadosUsuario.this, "Verifique um CPF válido primeiro.");
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
                        voluntarioAtual.getSenhavol());

                if (updatedVoluntario != null) {
                    JOptionPane.showMessageDialog(AlterarDadosUsuario.this, "Voluntário atualizado com sucesso.");
                    dispose();
                    new TelaVol(cpfUsuarioLogado).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(AlterarDadosUsuario.this,
                            "Erro ao atualizar os dados do voluntário.");
                }
            }
        });

        setVisible(true);
    }

    private JButton createRoundedButton(String text) {
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
                g2.setColor(new Color(18, 70, 22));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        button.setFont(new Font("Helvética", Font.BOLD, 14));
        button.setForeground(new Color(246, 234, 215));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlterarDadosUsuario("CPF_TESTE");
            }
        });
    }
}