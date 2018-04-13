package br.com.eteczonaleste.view;

import br.com.eteczonaleste.entity.Funcionarios;
import br.com.eteczonaleste.entityManager.FuncionariosManager;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Autenticacao extends JFrame {

    JLabel lblUsuario = new JLabel("Usuário:");
    JLabel lblSenha = new JLabel("Senha:");

    JTextField txtUsuario = new JTextField();
    JPasswordField txtSenha = new JPasswordField();

    JButton btnAcesso = new JButton("Acessar");

    ImageIcon icon = new ImageIcon("imagens/lock.png");
    JLabel label = new JLabel(icon);

    public Autenticacao() {
        super("Sistema de Controle - 211 - ETEC Zona Leste");

        Container paine = this.getContentPane();
        paine.setLayout(null);

        lblUsuario.setBounds(20, 20, 100, 20);
        txtUsuario.setBounds(120, 20, 240, 20);

        lblSenha.setBounds(20, 60, 100, 20);
        txtSenha.setBounds(120, 60, 240, 20);

        btnAcesso.setBounds(160, 100, 100, 50);
        btnAcesso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FuncionariosManager funcionariosManager = new FuncionariosManager();
                Funcionarios funcionarios = new Funcionarios();

                try {
                    funcionarios = funcionariosManager.findUserAndPassword(txtUsuario.getText(), txtSenha.getText());
                } catch (Exception w) {
                    w.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Usuário ou senha incorreto(s)!");
                }

                if (funcionarios.getId() != 0) {
                    //JOptioinPane com tempo determinado para encerrar
                    JOptionPane meuJOPane = new JOptionPane("Dados confirmados!");
                    final JDialog dialog = meuJOPane.createDialog(null, "Bem-vindo ao Sistema de Controle");
                    dialog.setModal(true);
                    Timer timer = new Timer(2 * 1000, new ActionListener() {
                        public void actionPerformed(ActionEvent ev) {
                            dialog.dispose();
                        }
                    });
                    timer.start();
                    dialog.setVisible(true);
                    timer.stop();
                    
                    Principal principal = new Principal();
                    dispose();
                }
            }
        }
        );

        getRootPane().setDefaultButton(btnAcesso);

        paine.add(lblUsuario);

        paine.add(txtUsuario);

        paine.add(lblSenha);

        paine.add(txtSenha);

        paine.add(btnAcesso);

        paine.add(label);
        label.setBounds(320, 90, 70, 70);

        this.setSize(
                400, 200);

        this.setVisible(
                true);

        this.setResizable(
                false);

        this.setLocationRelativeTo(
                null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens\\icones-bio.gif"));
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub        
        Autenticacao autenticacao = new Autenticacao();
    }
}
