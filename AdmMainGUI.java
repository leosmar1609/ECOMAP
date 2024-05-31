import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdmMainGUI extends JFrame {
    public AdmMainGUI() {
        setTitle("Administrador");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Painel superior para o título e logo
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51));
        topPanel.setBounds(0, 0, 500, 150);
        topPanel.setLayout(null);
        add(topPanel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Ryan\\Downloads\\logo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(20, 25, 100, 100);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Administrador");
        titleLabel.setBounds(100, 50, 300, 30);
        titleLabel.setForeground(new Color(0xD3B88C));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel);

        // Botões
        JButton PesquisarBotao = createButton("Pesquisar Usuário", 150, 180, 200, 50);
        add(PesquisarBotao);

        JButton AlterarBotao = createButton("Alterar Voluntário", 150, 250, 200, 50);
        add(AlterarBotao);

        JButton AlterarfuncBotao = createButton("Alterar Funcionário", 150, 320, 200, 50);
        add(AlterarfuncBotao);

        JButton ExcluirBotao = createButton("Excluir Usuário", 150, 390, 200, 50);
        add(ExcluirBotao);

        JButton cadastroFuncionario = createButton("Cadastrar ponto de coleta", 150, 460, 200, 50);
        add(cadastroFuncionario);
        JButton voltar= createButton("Voltar", 350, 520, 120, 30);
        add(voltar);

        cadastroFuncionario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CadastroPontoColetaGUI();
                dispose();
            }
        });

        PesquisarBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdmSearch();
                dispose();
            }
        });

        AlterarBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AlterarVoluntarioGUI();
                dispose();
            }
        });

        AlterarfuncBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AlterarFuncionarioGUI();
                dispose();
            }
        });

        ExcluirBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdmExcluir();
                dispose();
            }
        });

        
       voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdmGUI().setVisible(true);
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
        return button;
    }

    public static void main(String[] args) {
        new AdmMainGUI();
    }
}
