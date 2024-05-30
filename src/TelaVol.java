import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaVol extends JFrame {
    private String cpfUsuarioLogado;

    public TelaVol(String cpf) {
        this.cpfUsuarioLogado = cpf;

        setTitle("Tela de Voluntário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51)); // Cor verde escuro
        topPanel.setPreferredSize(new Dimension(800, 150));
        topPanel.setLayout(null);
        add(topPanel, BorderLayout.NORTH);

        // Adicionando o logo
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(30, 25, 100, 100);
        topPanel.add(logo);

        // Adicionando título
        JLabel titleLabel = new JLabel("Oi, voluntário!");
        titleLabel.setBounds(325, 40, 400, 30);
        titleLabel.setForeground(new Color(246, 234, 215));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Bem vindo(a) a sua área");
        subtitleLabel.setBounds(305, 70, 400, 30);
        subtitleLabel.setForeground(new Color(246, 234, 215));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        topPanel.add(subtitleLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        add(mainPanel, BorderLayout.CENTER);

        // Botão Coletar
        JLabel coletarLabel = new JLabel("Colete seus resíduos");
        coletarLabel.setBounds(107, 100, 200, 25);
        coletarLabel.setForeground(new Color(0xD3B88C));
        coletarLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        mainPanel.add(coletarLabel);

        JButton coletarButton = createButton("COLETAR", 100, 130, 150, 40);
        coletarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ColetaResiduos(cpfUsuarioLogado).setVisible(true);
            }
        });
        mainPanel.add(coletarButton);

        // Separador
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBounds(400, 0, 3, 450);
        separator.setForeground(new Color(0, 102, 51));
        mainPanel.add(separator);

        // Botão Alterar
        JLabel alterarLabel = new JLabel("Altere seus dados");
        alterarLabel.setBounds(560, 100, 200, 25);
        alterarLabel.setForeground(new Color(0xD3B88C));
        alterarLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        mainPanel.add(alterarLabel);

        JButton alterarButton = createButton("ALTERAR", 540, 130, 150, 40);
        alterarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AlterarDadosUsuario(cpfUsuarioLogado).setVisible(true);
            }
        });
        mainPanel.add(alterarButton);

        // Botão Excluir
        JLabel excluirLabel = new JLabel("Exclua sua conta");
        excluirLabel.setBounds(560, 180, 200, 25);
        excluirLabel.setForeground(new Color(0xD3B88C));
        excluirLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        mainPanel.add(excluirLabel);

        JButton excluirButton = createButton("EXCLUIR", 540, 210, 150, 40);
        excluirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(TelaVol.this, "Tem certeza que deseja excluir sua conta?",
                        "Excluir Conta", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new ExcluirConta(cpfUsuarioLogado);
                    dispose();
                }
            }
        });
        mainPanel.add(excluirButton);

        // Botão Sair
        JButton sairButton = createButton("VOLTAR", 30, 360, 100, 40);
        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int escolha = JOptionPane.showConfirmDialog(TelaVol.this, "Tem certeza que deseja sair do ECOMAP?",
                        "Sair", JOptionPane.YES_NO_OPTION);
                if (escolha == JOptionPane.YES_OPTION) {
                    dispose();
                    new Menu().setVisible(true);
                }
            }
        });
        mainPanel.add(sairButton);

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

    public static void main(String[] args) {
        new TelaVol("CPF_TESTE");
    }
}