import DAO.ecobancodao;
import entity.funcionarios;
import entity.voluntarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdmSearch extends JFrame {
    private JTextField cpfvolField;
    private JTextField cpffuncField;
    private JButton searchVolButton;
    private JButton searchFuncButton;

    private ecobancodao dao;

    public AdmSearch() {
        setTitle("Pesquisar Usuário");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JLabel cpfVolLabel = new JLabel("CPF Voluntário");
        cpfvolField = new JTextField(15);
        searchVolButton = new JButton("Pesquisar Voluntário");
        
        JLabel cpfFuncLabel = new JLabel("CPF Funcionário");
        cpffuncField = new JTextField(15);
        searchFuncButton = new JButton("Pesquisar Funcionário");

        searchVolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpfvol = cpfvolField.getText();
                pesquisarVoluntario(cpfvol);
            }
        });

        searchFuncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpffunc = cpffuncField.getText();
                pesquisarFuncionario(cpffunc);
            }
        });

        
        mainPanel.add(cpfVolLabel);
        mainPanel.add(cpfvolField);
        mainPanel.add(searchVolButton);
        mainPanel.add(Box.createRigidArea(new Dimension(20000, 0)));
        mainPanel.add(cpfFuncLabel);
        mainPanel.add(cpffuncField);
        mainPanel.add(searchFuncButton);
        mainPanel.add(Box.createRigidArea(new Dimension(20000, 0)));

        add(mainPanel);
        setVisible(true);
    }

    public void pesquisarVoluntario(String cpfvol) {
        voluntarios voluntario = dao.pesquisarUsuario(cpfvol);
        if (voluntario != null) {
            JOptionPane.showMessageDialog(this, "Voluntário encontrado:\nCPF: " + voluntario.getCpfvol() +
                    "\nNome: " + voluntario.getNomevol() +
                    "\nTelefone: " + voluntario.getFonevol() +
                    "\nEmail: " + voluntario.getEmailvol());
        } else {
            JOptionPane.showMessageDialog(this, "Voluntário não encontrado.");
        }
    }

    public void pesquisarFuncionario(String cpffunc) {
        funcionarios funcionario = dao.pesquisarFuncionario(cpffunc);
        if (funcionario != null) {
            JOptionPane.showMessageDialog(this, "Funcionário encontrado:\nCPF: " + funcionario.getCpffunc() +
                    "\nNome: " + funcionario.getNomefunc() +
                    "\nTelefone: " + funcionario.getFonefunc() +
                    "\nEmail: " + funcionario.getEmailfunc());
        } else {
            JOptionPane.showMessageDialog(this, "Funcionário não encontrado.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdmSearch();
            }
        });
    }
}