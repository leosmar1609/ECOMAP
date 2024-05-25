import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DAO.ecobancodao;
import entity.residuos;

public class GerenciarResiduos extends JFrame {
    private JTextField tipoField;
    private JTextField categoriaField;
    private JTextField descricaoField;
    private JTextField descarteField;

    public GerenciarResiduos(){
        setTitle("Adicionar Residuos");  
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JLabel tipoLabel = new JLabel("Tipo de Resíduo:");
        tipoField = new JTextField();
        add(tipoLabel);
        add(tipoField);

        JLabel categoriaLabel = new JLabel("Categoria:");
        categoriaField = new JTextField();
        add(categoriaLabel);
        add(categoriaField);

        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoField = new JTextField();
        add(descricaoLabel);
        add(descricaoField);

        JLabel descarteLabel = new JLabel("Descarte:");
        descarteField = new JTextField();
        add(descarteLabel);
        add(descarteField);

        JButton cadastrarBotao = new JButton("Cadastrar Resíduo");
        add(cadastrarBotao);

        cadastrarBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tipoResiduo = tipoField.getText();
                String categoria = categoriaField.getText();
                String descricao = descricaoField.getText();
                String descarte = descarteField.getText();

                residuos novoResiduo = new residuos(tipoResiduo, categoria, descricao, descarte);
                ecobancodao banco = new ecobancodao();
                banco.cadResiduos(novoResiduo);
                JOptionPane.showMessageDialog(null, "Resíduo cadastrado com sucesso!");

                // Limpar os campos após cadastrar
                tipoField.setText("");
                categoriaField.setText("");
                descricaoField.setText("");
                descarteField.setText("");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new GerenciarResiduos();
    }
}
