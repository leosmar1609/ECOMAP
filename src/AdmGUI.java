import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdmGUI extends JFrame {

    public AdmGUI() {
        setTitle("Tela de Administração");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51)); // Cor verde escuro
        topPanel.setBounds(0, 0, 800, 150);
        topPanel.setLayout(null);
        add(topPanel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        // Redimensiona a imagem para 150x100 pixels
        Image logoImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(30, 30, 150, 100);
        topPanel.add(logo);

        JLabel saudacao = new JLabel("Oi, administrador!");
        saudacao.setBounds(200, 30, 400, 30);
        saudacao.setForeground(new Color(0xD3B88C)); // Cor bege
        saudacao.setFont(new Font("Helvetica", Font.BOLD, 24));
        saudacao.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(saudacao);

        JLabel subtitulo = new JLabel("Bem-vindo(a) à sua área");
        subtitulo.setBounds(200, 70, 400, 30);
        subtitulo.setForeground(new Color(0xD3B88C)); // Cor bege
        subtitulo.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitulo);

        JButton rastreamentoButton = createButton("RASTREAMENTO", 300, 200, 200, 50);
        rastreamentoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RastreamentoGUI().setVisible(true);
            }
        });
        add(rastreamentoButton);

        JButton administrarBotao = createButton("GERENCIAR USUÁRIOS", 300, 280, 200, 50);
        administrarBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdmMainGUI();
                dispose();
            }
        });
        add(administrarBotao);

        JButton gerenciarResiduosBotao = createButton("GERENCIAR RESÍDUOS", 300, 360, 200, 50);
        gerenciarResiduosBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GerenciarResiduos();
                dispose();
            }
        });
        add(gerenciarResiduosBotao);

        JButton voltarBotao = createButton("VOLTAR", 670, 520, 100, 30);
        voltarBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(voltarBotao);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AdmGUI();
    }

    // Método para criar botões estilizados
    private static JButton createButton(String text, int x, int y, int width, int height) {
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
}