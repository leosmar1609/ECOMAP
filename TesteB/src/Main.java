import javax.swing.*;
import java.awt.event.*;
import DAO.ecobancodao;
import entity.voluntarios;
import entity.funcionarios;
import entity.administradores;

public class Main extends JFrame {
    public Main() {
        setTitle("Login ECOMAP");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Email:");
        userLabel.setBounds(50, 50, 80, 25);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(140, 50, 200, 25);
        add(userField);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(50, 100, 80, 25);
        add(senhaLabel);

        JPasswordField senhaField = new JPasswordField();
        senhaField.setBounds(140, 100, 200, 25);
        add(senhaField);

        JButton loginBotão = new JButton("Login");
        loginBotão.setBounds(150, 150, 100, 25);
        add(loginBotão);

        JButton sairBotão = new JButton("Sair");
        sairBotão.setBounds(270, 150, 100, 25);
        add(sairBotão);

        loginBotão.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = userField.getText();
                String senha = new String(senhaField.getPassword());

                ecobancodao banco = new ecobancodao();

                voluntarios usuarioVoluntario = banco.buscarUsuario(email, senha);
                funcionarios usuarioFuncionario = banco.buscarFuncionario(email, senha);
                administradores usuarioAdministrador = banco.buscarAdministrador(email, senha);

                if (usuarioVoluntario != null) {
                    JOptionPane.showMessageDialog(null, "Bem vindo(a) Voluntário: " + usuarioVoluntario.getNomevol() + "!");
                } else if (usuarioFuncionario != null) {
                    JOptionPane.showMessageDialog(null, "Bem vindo(a) Funcionário: " + usuarioFuncionario.getNomefunc() + "!");
                } else if (usuarioAdministrador != null) {
                    dispose();
                    new AdmGUI();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário e senha inválidos ou usuário desconhecido");
                }
            }
        });

        sairBotão.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int escolha = JOptionPane.showConfirmDialog(Main.this, "Tem certeza que deseja sair da tela de login?", "Sair", JOptionPane.YES_NO_OPTION);

                if (escolha == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
