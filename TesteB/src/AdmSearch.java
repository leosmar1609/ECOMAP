import DAO.ecobancodao;
import entity.funcionarios;
import entity.voluntarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdmSearch extends JFrame {
    private JTextField cpfvolField;
    private JTextField cpffuncField;
    private JButton searchVolButton;
    private JButton searchFuncButton;

    private ecobancodao dao;

    public AdmSearch() {
        setTitle("Pesquisar Usuário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51));
        topPanel.setPreferredSize(new Dimension(800, 115));
        topPanel.setLayout(null);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(200, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(10, -20, 180, 180);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Pesquisar o Usuário", SwingConstants.CENTER);
        titleLabel.setForeground(new Color(246, 234, 215));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setBounds(0, 20, 800, 30);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Pesquise o usuário desejado aqui!", SwingConstants.CENTER);
        subtitleLabel.setForeground(new Color(246, 234, 215));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setBounds(0, 60, 800, 30);
        topPanel.add(subtitleLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JPanel volPanel = new JPanel();
        volPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel cpfVolLabel = new JLabel("CPF do voluntário desejado");
        cpfVolLabel.setForeground(new Color(0, 102, 51));
        cpfVolLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        cpfvolField = new JTextField(15);
        searchVolButton = createButton("PESQUISAR VOLUNTÁRIO", 0, 0, 200, 50);

        gbc.gridx = 0;
        gbc.gridy = 0;
        volPanel.add(cpfVolLabel, gbc);

        gbc.gridy = 1;
        volPanel.add(cpfvolField, gbc);

        gbc.gridy = 2;
        volPanel.add(searchVolButton, gbc);

        JPanel funcPanel = new JPanel();
        funcPanel.setLayout(new GridBagLayout());

        JLabel cpfFuncLabel = new JLabel("CPF do funcionário desejado");
        cpfFuncLabel.setForeground(new Color(0, 102, 51));
        cpfFuncLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        cpffuncField = new JTextField(15);
        searchFuncButton = createButton("PESQUISAR FUNCIONÁRIO", 0, 0, 200, 50);

        gbc.gridx = 0;
        gbc.gridy = 0;
        funcPanel.add(cpfFuncLabel, gbc);

        gbc.gridy = 1;
        funcPanel.add(cpffuncField, gbc);

        gbc.gridy = 2;
        funcPanel.add(searchFuncButton, gbc);

        centerPanel.add(volPanel);
        centerPanel.add(funcPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton backButton = createButton("VOLTAR", 0, 0, 200, 50);
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        searchVolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpfvol = cpfvolField.getText();
                pesquisarVoluntario(cpfvol);
            }
        });

        searchFuncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpffunc = cpffuncField.getText();
                pesquisarFuncionario(cpffunc);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdmMainGUI();
                dispose(); 
            }
        });

        add(mainPanel);
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
        button.setForeground(new Color(246, 234, 215));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBounds(x, y, width, height);
        return button;
    }

    public void pesquisarVoluntario(String cpfvol) {
        voluntarios voluntario = dao.pesquisarUsuario(cpfvol);
        if (voluntario != null) {
            JOptionPane.showMessageDialog(this, "Voluntário encontrado:\nCPF: " + voluntario.getCpfvol() +
                    "\nNome: " + voluntario.getNomevol() +
                    "\nTelefone: " + voluntario.getFonevol() +
                    "\nEmail: " + voluntario.getEmailvol());
        } else {
            JOptionPane.showMessageDialog(this, "Voluntário não encontrado.");
        }
    }

    public void pesquisarFuncionario(String cpffunc) {
        funcionarios funcionario = dao.pesquisarFuncionario(cpffunc);
        if (funcionario != null) {
            JOptionPane.showMessageDialog(this, "Funcionário encontrado:\nCPF: " + funcionario.getCpffunc() +
                    "\nNome: " + funcionario.getNomefunc() +
                    "\nTelefone: " + funcionario.getFonefunc() +
                    "\nEmail: " + funcionario.getEmailfunc());
        } else {
            JOptionPane.showMessageDialog(this, "Funcionário não encontrado.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdmSearch();
            }
        });
    }
}