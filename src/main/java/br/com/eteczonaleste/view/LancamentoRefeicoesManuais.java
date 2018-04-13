package br.com.eteczonaleste.view;

import com.towel.swing.calendar.CalendarView;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entity.Entregas;
import br.com.eteczonaleste.entityManager.AlunosManager;
import br.com.eteczonaleste.entityManager.EntregaManager;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class LancamentoRefeicoesManuais extends JFrame{
	
	private JLabel lblNome = new JLabel("Nome:");
	private JLabel lblQuantidade = new JLabel("Quantidade:");
        private JLabel lblData = new JLabel("Data:");
        
        private JComboBox cboNome = new JComboBox();
	private JComboBox cboQuantidade = new JComboBox();
        private final CalendarView txtData = new CalendarView();
        
	private JButton btnCadastrar = new JButton("Cadastrar");
	private JButton btnCancelar = new JButton("Cancelar");
	
	public LancamentoRefeicoesManuais(){
		super("Lançamento de Refeições Manuais");
		
		Container paine = this.getContentPane();
		paine.setLayout(null);
		
		lblNome.setBounds(20, 20, 100, 20);
		cboNome.setBounds(120, 20, 400, 20);
                
                lblQuantidade.setBounds(20, 60, 100, 20);
		cboQuantidade.setBounds(120, 60, 250, 20);
		
		lblData.setBounds(20, 95, 100, 35);
		txtData.setBounds(76, 95, 250, 35);
                
                cboNome.addItem("");
                
                final AlunosManager alunosManager = new AlunosManager();
                Collection<Alunos> alunosPesq = alunosManager.findSelectAll();
                for (Alunos e : alunosPesq) {
                    cboNome.addItem(e.getNome());
                }
                
                cboQuantidade.addItem("");
                cboQuantidade.addItem(1);
		
		btnCadastrar.setBounds(100, 180, 200, 50);
		btnCadastrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                            final EntregaManager entregarManager = new EntregaManager();
                            Entregas entregas = new Entregas();
                            
                            Alunos alunosPesqNome = alunosManager.findByName(cboNome.getSelectedItem().toString());
                         
                            entregas.setRa(alunosPesqNome);  
                            
                            Calendar selectedDate = txtData.getSelectedDate();
                                                                        
                            entregas.setHora_retirada(selectedDate.getTime());
                            entregas.setData_retirada(selectedDate.getTime());
                            
                            //Inserindo os dados na entidade
                            try{
                                entregarManager.insert(entregas);
                                
                                //JOptioinPane com tempo determinado para encerrar
                                JOptionPane meuJOPane = new JOptionPane("Refeição inserida com sucesso!");
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
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
			}
		});
		
		btnCancelar.setBounds(350, 180, 180, 50);
		btnCancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		
		paine.add(lblNome);
		paine.add(cboNome);
                
                paine.add(lblQuantidade);
		paine.add(cboQuantidade);
                
                paine.add(lblData);
		paine.add(txtData);
                
		paine.add(btnCadastrar);
		paine.add(btnCancelar);
		
		this.setSize(600,300);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
                this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens\\icones-bio.gif"));
	}
	public static void main(String args[]){
            String asdf= "sdf";
            LancamentoRefeicoesManuais lancamentoRefeicoes = new LancamentoRefeicoesManuais();
	}
}
