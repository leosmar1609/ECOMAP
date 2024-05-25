import javax.swing.*;
import java.awt.event.*;

public class AdmMainGUI extends JFrame {
    public AdmMainGUI() {
        setTitle("Tela de Administração");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        JButton PesquisarBotao = new JButton("Pesquisar Usuário");
        PesquisarBotao.setBounds(100, 30, 200, 50);
        add(PesquisarBotao);

        JButton AlterarBotao = new JButton("Alterar Voluntário");
        AlterarBotao.setBounds(100, 100, 200, 50);
        add(AlterarBotao);

        JButton AlterarfuncBotao = new JButton("Alterar Funcionário");
        AlterarfuncBotao.setBounds(100, 170, 200, 50);
        add(AlterarfuncBotao);

        JButton ExcluirBotao = new JButton("Excluir Usuário");
        ExcluirBotao.setBounds(100, 240, 200, 50);
        add(ExcluirBotao);
        
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
            public void actionPerformed(ActionEvent e){
                new AdmExcluir();
                dispose();
            }
        });

    }

    public static void main(String[] args){
        new AdmMainGUI();
    }
}