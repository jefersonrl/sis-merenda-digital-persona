package br.com.eteczonaleste.view;

import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entity.Funcionarios;
import br.com.eteczonaleste.entityManager.AlunosManager;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

public class CadastrarAlunos extends JFrame{
	
	JLabel lblRA = new JLabel("RA:");
	JLabel lblNome = new JLabel("Nome:");
	JLabel lblEndereco = new JLabel("Endereço:");
	JLabel lblNumero = new JLabel("Número:");
	JLabel lblBairro = new JLabel("Bairro:");
	JLabel lblCep = new JLabel("Cep:");
	JLabel lblCidade = new JLabel("Cidade:");
	JLabel lblEstado = new JLabel("Estado:");
	JLabel lblTelefone = new JLabel("Telefone:");
	JLabel lblCelular = new JLabel("Celular:");
	JLabel lblEmail = new JLabel("Email:");
	
	JTextField txtRA = new JTextField();
	JTextField txtNome = new JTextField();
	JTextField txtEndereco = new JTextField();
	JTextField txtNumero = new JTextField();
	JTextField txtBairro = new JTextField();
	JTextField txtCep = new JTextField();
	JTextField txtCidade = new JTextField();
	JTextField txtEstado = new JTextField();
	JTextField txtTelefone = new JTextField();
	JTextField txtCelular = new JTextField();
	JTextField txtEmail = new JTextField();
	
	JButton btnCadastrar = new JButton("Cadastrar");
	JButton btnCancelar = new JButton("Cancelar");
	
	public CadastrarAlunos(){
		super("Cadastro de Alunos");
		
		Container paine = this.getContentPane();
		paine.setLayout(null);
		
		lblRA.setBounds(20, 20, 100, 20);
		txtRA.setBounds(120, 20, 100, 20);
		
		lblNome.setBounds(20, 60, 100, 20);
		txtNome.setBounds(120, 60, 250, 20);
		
		lblEndereco.setBounds(20, 100, 100, 20);
		txtEndereco.setBounds(120, 100, 250, 20);

		lblNumero.setBounds(20, 140, 100, 20);
		txtNumero.setBounds(120, 140, 250, 20);
		
		lblBairro.setBounds(20, 180, 100, 20);
		txtBairro.setBounds(120, 180, 250, 20);
		
		lblCep.setBounds(20, 220, 100, 20);
		txtCep.setBounds(120, 220, 250, 20);
		
		lblCidade.setBounds(20, 260, 100, 20);
		txtCidade.setBounds(120, 260, 250, 20);
		
		lblEstado.setBounds(20, 300, 100, 20);
		txtEstado.setBounds(120, 300, 50, 20);
		
		lblTelefone.setBounds(400, 20, 100, 20);
		txtTelefone.setBounds(500, 20, 200, 20);
		
		lblCelular.setBounds(400, 60, 100, 20);
		txtCelular.setBounds(500, 60, 200, 20);
		
		lblEmail.setBounds(400, 100, 100, 20);
		txtEmail.setBounds(500, 100, 270, 20);
		
		btnCadastrar.setBounds(220, 350, 200, 50);
		btnCadastrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AlunosManager alunosManager = new AlunosManager();
				Alunos alunos = new Alunos();
                                alunos.setRa(Integer.parseInt(txtRA.getText()));
				alunos.setNome(txtNome.getText());
				alunos.setEndereco(txtEndereco.getText() + " " + txtNumero.getText());
				alunos.setBairro(txtBairro.getText());
				alunos.setCep(txtCep.getText());
				alunos.setCidade(txtCidade.getText());
				alunos.setEstado(txtEstado.getText());
				alunos.setTelefone(txtTelefone.getText());
				alunos.setCelular(txtCelular.getText());
				alunos.setEmail(txtEmail.getText());
				
				alunosManager.insert(alunos);
                                
                                JOptionPane.showMessageDialog(null, "Dados cadastrados com sucesso!");
                                
                                //Limpando os campos
                                txtRA.setText(null);
				txtNome.setText(null);
				txtEndereco.setText(null);
				txtNumero.setText(null);
				txtBairro.setText(null);
				txtCep.setText(null);
				txtCidade.setText(null);
				txtEstado.setText(null);
				txtTelefone.setText(null);
				txtCelular.setText(null);
				txtEmail.setText(null);
			}
		});
		
		btnCancelar.setBounds(430, 350, 180, 50);
		btnCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txtRA.setText(null);
				txtNome.setText(null);
				txtEndereco.setText(null);
				txtNumero.setText(null);
				txtBairro.setText(null);
				txtCep.setText(null);
				txtCidade.setText(null);
				txtEstado.setText(null);
				txtTelefone.setText(null);
				txtCelular.setText(null);
				txtEmail.setText(null);
			}
		});
		
		paine.add(lblRA);
		paine.add(txtRA);
		paine.add(lblNome);
		paine.add(txtNome);
		paine.add(lblEndereco);
		paine.add(txtEndereco);
		paine.add(lblNumero);
		paine.add(txtNumero);
		paine.add(lblBairro);
		paine.add(txtBairro);
		paine.add(lblCep);
		paine.add(txtCep);
		paine.add(lblCidade);
		paine.add(txtCidade);
		paine.add(lblEstado);
		paine.add(txtEstado);
		paine.add(lblTelefone);
		paine.add(txtTelefone);
		paine.add(lblCelular);
		paine.add(txtCelular);
		paine.add(lblEmail);
		paine.add(txtEmail);
		paine.add(btnCadastrar);
		paine.add(btnCadastrar);
		paine.add(btnCancelar);
		
		this.setSize(800,450);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
                this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens\\icones-bio.gif"));
	}
	public static void main(String args[]){
		CadastrarAlunos cadastrarAlunos = new CadastrarAlunos();
	}
}
