package br.com.eteczonaleste.view;

import br.com.eteczonaleste.entity.Funcionarios;
import br.com.eteczonaleste.entityManager.FuncionariosManager;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CadastrarFuncionarios extends JFrame {

    JLabel lblNome = new JLabel("Nome:");
    JLabel lblUsuario = new JLabel("Usuário:");
    JLabel lblSenha = new JLabel("Senha:");
    JLabel lblConfirmeSenha = new JLabel("Confirmação de Senha:");

    JTextField txtNome = new JTextField("");
    JTextField txtUsuario = new JTextField("");
    JPasswordField txtSenha = new JPasswordField("");
    JPasswordField txtConfirmeSenha = new JPasswordField("");

    JButton btnCadastrar = new JButton("Cadastrar");
    JButton btnCancelar = new JButton("Cancelar");

    public CadastrarFuncionarios() {
        super("Cadastro de Funcionários");

        Container paine = this.getContentPane();
        paine.setLayout(null);

        lblNome.setBounds(20, 20, 100, 20);
        txtNome.setBounds(120, 20, 250, 20);

        lblUsuario.setBounds(20, 60, 100, 20);
        txtUsuario.setBounds(120, 60, 250, 20);

        lblSenha.setBounds(20, 100, 100, 20);
        txtSenha.setBounds(120, 100, 250, 20);

        lblConfirmeSenha.setBounds(20, 140, 160, 20);
        txtConfirmeSenha.setBounds(170, 140, 200, 20);

        btnCadastrar.setBounds(100, 190, 200, 40);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                FuncionariosManager funcionariosManager = new FuncionariosManager();
                Funcionarios funcionarios = new Funcionarios();

                funcionarios.setNome(txtNome.getText());
                funcionarios.setUsuario(txtUsuario.getText());
                funcionarios.setSenha(txtSenha.getText());

                funcionariosManager.insert(funcionarios);

                JOptionPane.showMessageDialog(
                        null, "Dados cadastrados com sucesso!");

                txtNome.setText(
                        null);
                txtUsuario.setText(
                        null);
                txtSenha.setText(
                        null);
                txtConfirmeSenha.setText(
                        null);
            }
        });

        btnCancelar.setBounds(
                100, 190, 200, 40);
        btnCancelar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                txtNome.setText(null);
                txtUsuario.setText(null);
                txtSenha.setText(null);
                txtConfirmeSenha.setText(null);
            }
        }
        );

        paine.add(lblNome);
        paine.add(txtNome);
        paine.add(lblUsuario);
        paine.add(txtUsuario);
        paine.add(lblSenha);
        paine.add(txtSenha);
        paine.add(lblConfirmeSenha);
        paine.add(txtConfirmeSenha);
        paine.add(btnCadastrar);

        this.setSize(410, 300);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens\\icones-bio.gif"));
    }
}
