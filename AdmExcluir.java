import DAO.ecobancodao;
import entity.funcionarios;
import entity.voluntarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdmExcluir extends JFrame {
    private JTextField cpfField;
    private JButton searchButton;
    private JButton deleteButton;

    private ecobancodao dao;

    public AdmExcluir() {
        setTitle("Excluir Usuário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51)); // Cor verde escuro
        topPanel.setPreferredSize(new Dimension(800, 150));
        topPanel.setLayout(null);
        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // Usar layout nulo para posicionamento absoluto
        mainPanel.setBackground(new Color(255, 255, 255)); // Fundo branco

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Ryan\\Downloads\\logo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(30, 25, 100, 100);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Excluir o usuário");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setForeground(new Color(246, 234, 215));
        titleLabel.setBounds(280, 30, 500, 50);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Exclua o usuário desejado aqui");
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(246, 234, 215));
        subtitleLabel.setBounds(250, 80, 500, 30);
        topPanel.add(subtitleLabel);

        JLabel cpfLabel = new JLabel("CPF do usuário desejado");
        cpfLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        cpfLabel.setForeground(new Color(0, 102, 51));
        cpfLabel.setBounds(320, 100, 200, 30);
        mainPanel.add(cpfLabel);

        cpfField = new JTextField();
        cpfField.setFont(new Font("Helvetica", Font.PLAIN, 14));
        cpfField.setBounds(300, 130, 200, 30);
        mainPanel.add(cpfField);

        searchButton = createButton("Pesquisar", 195, 180, 150, 30);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                pesquisarUsuario(cpf);
            }
        });
        mainPanel.add(searchButton);

        deleteButton = createButton("Excluir", 450, 180, 150, 30);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();

                if (cpf.isEmpty()) {
                    JOptionPane.showMessageDialog(AdmExcluir.this, "Preencha o campo CPF.");
                    return;
                }

                int option = JOptionPane.showConfirmDialog(AdmExcluir.this, "Tem certeza que deseja excluir o usuário?",
                        "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    voluntarios voluntario = dao.pesquisarUsuario(cpf);
                    funcionarios funcionario = dao.pesquisarFuncionario(cpf);

                    if (voluntario != null) {
                        dao.deletarVoluntario(voluntario);
                        JOptionPane.showMessageDialog(AdmExcluir.this, "Voluntário excluído com sucesso.");
                    } else if (funcionario != null) {
                        dao.deletarFuncionario(funcionario);
                        JOptionPane.showMessageDialog(AdmExcluir.this, "Funcionário excluído com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(AdmExcluir.this, "Usuário não encontrado.");
                    }
                }
            }
        });
        mainPanel.add(deleteButton);

        JButton backButton = createButton("Voltar", 30, 520, 100, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Adicione aqui a lógica para voltar para a tela anterior
            }
        });
        mainPanel.add(backButton);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 102, 51)); // Cor de fundo do botão
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 102, 51)); // Cor da borda do botão
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        button.setFont(new Font("Helvetica", Font.BOLD, 14));
        button.setBounds(x, y, width, height);
        button.setForeground(new Color(246, 234, 215)); // Cor do texto
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    public void pesquisarUsuario(String cpf) {
        voluntarios voluntario = dao.pesquisarUsuario(cpf);
        funcionarios funcionario = dao.pesquisarFuncionario(cpf);

        if (voluntario != null || funcionario != null) {
            JOptionPane.showMessageDialog(this, "Usuário encontrado. Você pode excluí-lo agora.");
        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdmExcluir();
            }
        });
    }
}
