import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import DAO.ecobancodao;
import entity.residuos;

public class residuosvolun {
    private JFrame frame;
    private JComboBox<String> comboBox;
    private JLabel label;

    public residuosvolun() {
        frame = new JFrame("Seleção de Resíduos Voluntários");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        label = new JLabel("Selecione o tipo de resíduo:");
        label.setBounds(20, 20, 200, 30);
        frame.add(label);

        String[] opcoes = {"Metal", "Plástico", "Papel", "Resíduo Orgânico", "Químico"};
        comboBox = new JComboBox<>(opcoes);
        comboBox.setBounds(20, 60, 150, 30);
        frame.add(comboBox);

        JButton button = new JButton("Selecionar");
        button.setBounds(180, 60, 100, 30);
        button.addActionListener(e -> {
            String tipoResiduo = (String) comboBox.getSelectedItem();
            JOptionPane.showMessageDialog(frame, "Você selecionou: " + tipoResiduo);
            
        });
        frame.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            String tiporesiduo;
            String categoria;
            String descricao;
            String descarte;
            ecobancodao banco = new ecobancodao();
            residuos residuo= banco.buscaResiduos(tiporesiduo, categoria, descricao, descarte);}});

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(residuosvolun::new);
    }
}
