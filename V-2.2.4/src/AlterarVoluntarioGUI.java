<<<<<<< HEAD
import DAO.ecobancodao;
import entity.voluntarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AlterarVoluntarioGUI extends JFrame {
    private JTextField cpfField;
    private JButton verificarButton;
    private JComboBox<String> campoComboBox;
    private JTextField novoValorField;
    private JButton salvarButton, voltarButton;
    private ecobancodao dao;
    private voluntarios voluntarioAtual;

    public AlterarVoluntarioGUI() {
        String basePath = System.getProperty("user.dir");
        setTitle("Alterar Dados do Voluntário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 51));
        headerPanel.setPreferredSize(new Dimension(100, 150));

        JLabel logoLabel = new JLabel();
        String imagePath = basePath + File.separator + "images" + File.separator + "logoc.png";
        ImageIcon logoIcon = new ImageIcon(imagePath);
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);

        JLabel adminLabel = new JLabel("Oi, administrador!");
        adminLabel.setForeground(new Color(216, 193, 162));
        adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminLabel.setFont(new Font("Helvetica", Font.BOLD, 20));

        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(adminLabel, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2));
        contentPanel.setBackground(new Color(255, 255, 255));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel cpfLabel = new JLabel("Verifique o CPF");
        cpfLabel.setForeground(new Color(0, 102, 51));
        cpfLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        cpfLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpfField = new JTextField(20);
        verificarButton = createButton("VERIFICAR CPF", 0, 0, 150, 30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        leftPanel.add(cpfLabel, gbc);
        gbc.gridy = 1;
        leftPanel.add(cpfField, gbc);
        gbc.gridy = 2;
        leftPanel.add(verificarButton, gbc);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(new Color(255, 255, 255));

        JLabel campoLabel = new JLabel("Escolha o campo a ser alterado");
        campoLabel.setForeground(new Color(0, 102, 51));
        campoLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        campoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String[] campos = {"Nome", "Telefone", "Email", "Senha"};
        campoComboBox = new JComboBox<>(campos);
        novoValorField = new JTextField(20);
        salvarButton = createButton("SALVAR", 0, 0, 150, 30);
        voltarButton = createButton("VOLTAR", 0, 0, 150, 30);

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
        gbc.gridy = 4;
        rightPanel.add(voltarButton, gbc);

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

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdmMainGUI();
            }
        });

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
        button.setOpaque(false);
        return button;
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             new AlterarVoluntarioGUI();
    //         }
    //     });
    // }
=======
import DAO.ecobancodao;
import entity.voluntarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AlterarVoluntarioGUI extends JFrame {
    private JTextField cpfField;
    private JButton verificarButton;
    private JComboBox<String> campoComboBox;
    private JTextField novoValorField;
    private JButton salvarButton, voltarButton;
    private ecobancodao dao;
    private voluntarios voluntarioAtual;

    public AlterarVoluntarioGUI() {
        String basePath = System.getProperty("user.dir");
        setTitle("Alterar Dados do Voluntário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 51));
        headerPanel.setPreferredSize(new Dimension(100, 150));

        JLabel logoLabel = new JLabel();
        String imagePath = basePath + File.separator + "images" + File.separator + "logoc.png";
        ImageIcon logoIcon = new ImageIcon(imagePath);
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);

        JLabel adminLabel = new JLabel("Oi, administrador!");
        adminLabel.setForeground(new Color(216, 193, 162));
        adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminLabel.setFont(new Font("Helvetica", Font.BOLD, 20));

        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(adminLabel, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2));
        contentPanel.setBackground(new Color(255, 255, 255));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel cpfLabel = new JLabel("Verifique o CPF");
        cpfLabel.setForeground(new Color(0, 102, 51));
        cpfLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        cpfLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpfField = new JTextField(20);
        verificarButton = createButton("VERIFICAR CPF", 0, 0, 150, 30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        leftPanel.add(cpfLabel, gbc);
        gbc.gridy = 1;
        leftPanel.add(cpfField, gbc);
        gbc.gridy = 2;
        leftPanel.add(verificarButton, gbc);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(new Color(255, 255, 255));

        JLabel campoLabel = new JLabel("Escolha o campo a ser alterado");
        campoLabel.setForeground(new Color(0, 102, 51));
        campoLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        campoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String[] campos = {"Nome", "Telefone", "Email", "Senha"};
        campoComboBox = new JComboBox<>(campos);
        novoValorField = new JTextField(20);
        salvarButton = createButton("SALVAR", 0, 0, 150, 30);
        voltarButton = createButton("VOLTAR", 0, 0, 150, 30);

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
        gbc.gridy = 4;
        rightPanel.add(voltarButton, gbc);

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

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdmMainGUI();
            }
        });

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
        button.setOpaque(false);
        return button;
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             new AlterarVoluntarioGUI();
    //         }
    //     });
    // }
>>>>>>> 61b75dfac6d30311dee39921307db1f08f788225
}