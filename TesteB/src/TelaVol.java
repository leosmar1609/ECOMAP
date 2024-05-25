import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaVol extends JFrame {

    public TelaVol(){
        setTitle("Tela de Voluntário");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(10, 1, 0, 10));

        JLabel titleJLabel = new JLabel("Bem-vindo(a), ");
        titleJLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
        titleJLabel.setHorizontalAlignment(JLabel.CENTER);
        painel.add(titleJLabel);

        JLabel txt = new JLabel("Para coletar os seus resíduos clique aqui!");
        txt.setHorizontalAlignment(JLabel.CENTER);
        painel.add(txt);

        JButton login = new JButton("Coletar");
        login.setFont(new Font("Helvetica", Font.BOLD, 14));
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ColetaResiduos().setVisible(true);
            }
        });
        painel.add(login);

        JLabel txt2 = new JLabel("Para alterar os dados da sua conta clique aqui!");
        txt2.setHorizontalAlignment(JLabel.CENTER);
        painel.add(txt2);

        JButton alterar = new JButton("Alterar");
        alterar.setFont(new Font("Helvetica", Font.BOLD, 14));
        alterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        painel.add(alterar);

        JLabel txt3 = new JLabel("Para excluir a sua conta clique aqui!");
        txt3.setHorizontalAlignment(JLabel.CENTER);
        painel.add(txt3);

        JButton excluir = new JButton("Excluir");
        excluir.setFont(new Font("Helvetica", Font.BOLD, 14));
        excluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(TelaVol.this, "Tem certeza que deseja excluir sua conta?", "Excluir Conta", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new AdmExcluir();
                    dispose();
                }
            }
        });
        painel.add(excluir);

        JButton sair = new JButton("Sair");
        sair.setFont(new Font("Helvetica", Font.BOLD, 14));
        sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int escolha = JOptionPane.showConfirmDialog(TelaVol.this, "Tem certeza que deseja sair do ECOMAP?", "Sair", JOptionPane.YES_NO_OPTION);
                if (escolha == JOptionPane.YES_OPTION) {
                    dispose();
                    new Menu().setVisible(true);
                }
            }
        });
        painel.add(sair);

        add(painel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaVol();
    }
}
