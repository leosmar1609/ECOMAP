import javax.swing.*;
import java.awt.event.*;
import DAO.ecobancodao;
import entity.voluntarios;
import entity.funcionarios;
import entity.administradores;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main extends JFrame {
    private int tentativas = 0;
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

        JButton loginBotao = new JButton("Login");
        loginBotao.setBounds(150, 150, 100, 25);
        add(loginBotao);

        JButton sairBotao = new JButton("Sair");
        sairBotao.setBounds(270, 150, 50, 25);
        add(sairBotao);

        loginBotao.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String email = userField.getText();
                String senha = new String(senhaField.getPassword());

                String emailRegex = "^[a-z]{3,}[._]?[a-z0-9]+@gmail\\.com$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(email);

                if (!matcher.matches()) {
                    JOptionPane.showMessageDialog(null, "Email inválido. Insira o email cadastrado");
                    return;
                }

                ecobancodao banco = new ecobancodao();

                voluntarios usuarioVoluntario = banco.buscarUsuario(email, senha);
                funcionarios usuarioFuncionario = banco.buscarFuncionario(email, senha);
                administradores usuarioAdministrador = banco.buscarAdministrador(email, senha);

                if (usuarioVoluntario != null) {
                    JOptionPane.showMessageDialog(null, "Bem vindo(a) Voluntário: " + usuarioVoluntario.getNomevol() + "!");
                    new TelaVol();
                    dispose();
                } else if (usuarioFuncionario != null) {
                    JOptionPane.showMessageDialog(null, "Bem vindo(a) Funcionário: " + usuarioFuncionario.getNomefunc() + "!");
                } else if (usuarioAdministrador != null) {
                    dispose();
                    new AdmGUI();
                } else {
                    tentativas++;
                    if (tentativas >= 3) {
                        JOptionPane.showMessageDialog(null, "Número máximo de tentativas atingido. A aplicação será fechada.");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário e senha inválidos ou usuário desconhecido. Tentativas restantes: " + (3 - tentativas));
                    }
                }
            }
        });

        sairBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Menu();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
