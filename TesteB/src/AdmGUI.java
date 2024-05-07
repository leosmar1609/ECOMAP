import javax.swing.*;
import java.awt.event.*;

public class AdmGUI extends JFrame {
    public AdmGUI() {
        setTitle("Tela de Administração");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton rastreamentoBotao = new JButton("Rastreamento");
        rastreamentoBotao.setBounds(100, 30, 200, 50);
        add(rastreamentoBotao);
        
        JButton administrarBotao = new JButton("Gerenciar Funcionários");
        administrarBotao.setBounds(100, 100, 200, 50);
        add(administrarBotao);

        JButton gerenciarResiduosBotao = new JButton("Gerenciar Resíduos");
        gerenciarResiduosBotao.setBounds(100, 170, 200, 50);
        add(gerenciarResiduosBotao);

        rastreamentoBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ok");
            }
        });

        administrarBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ok");
            }
        });

        gerenciarResiduosBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GerenciarResiduos();
            }
        });

        setVisible(true);
    }
}
