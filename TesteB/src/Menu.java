import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
    public Menu() {
        setTitle("ECOMAP");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(7, 1, 0, 10));

        JLabel titleJLabel = new JLabel("Bem-vindo(a) ao ECOMAP");
        titleJLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
        titleJLabel.setHorizontalAlignment(JLabel.CENTER);
        painel.add(titleJLabel);

        JLabel logintxt = new JLabel("Se você já possui uma conta, clique aqui!");
        logintxt.setHorizontalAlignment(JLabel.CENTER);
        painel.add(logintxt);

        JButton login = new JButton("Login");
        login.setFont(new Font("Helvetica", Font.BOLD, 14));
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Main().setVisible(true);
            }
        });
        painel.add(login);

        JLabel cadstxt = new JLabel("Caso não tenha ainda uma conta, cadastre-se!");
        cadstxt.setHorizontalAlignment(JLabel.CENTER);
        painel.add(cadstxt);

        JButton cadastroVoluntario = new JButton("Cadastrar-se como voluntário");
        cadastroVoluntario.setFont(new Font("Helvetica", Font.BOLD, 14));
        cadastroVoluntario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CadastroVoluntarioGUI().setVisible(true);
            }
        });
        painel.add(cadastroVoluntario);

        JButton cadastroFuncionario = new JButton("Cadastrar ponto de coleta");
        cadastroFuncionario.setFont(new Font("Helvetica", Font.BOLD, 14));
        cadastroFuncionario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CadastroPontoColetaGUI().setVisible(true);
            }
        });
        
        painel.add(cadastroFuncionario);

        JButton sair = new JButton("Sair");
        sair.setFont(new Font("Helvetica", Font.BOLD, 14));
        sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int escolha = JOptionPane.showConfirmDialog(Menu.this, "Tem certeza que deseja sair do ECOMAP?", "Sair", JOptionPane.YES_NO_OPTION);
                if (escolha == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        painel.add(sair);

        add(painel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Menu();
    }
}