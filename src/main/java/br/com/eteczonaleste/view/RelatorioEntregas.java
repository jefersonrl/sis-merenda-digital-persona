package br.com.eteczonaleste.view;

import br.com.eteczonaleste.entity.Funcionarios;
import br.com.eteczonaleste.entityManager.EntregaManager;
import br.com.eteczonaleste.entity.Entregas;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.towel.swing.calendar.CalendarView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class RelatorioEntregas extends JFrame {

    private DefaultTableModel modelo = new DefaultTableModel();
    private JTable tabela;
    private JLabel lblData_retirada = new JLabel("Data:");
    private JButton btnBuscar = new JButton("Buscar");
    private JButton btnRelatorio = new JButton("Visualizar Relatorio");
    private JButton btnRelEntreDatas = new JButton("Entregas Entre Datas");
    private JButton btnRelTotaisEntreDatas = new JButton("Totais Entre Datas");
    
    private final CalendarView txtData_retirada = new CalendarView();
    
    private Collection<Entregas> entregaPesq;
    
    public RelatorioEntregas() {
        super("Relatório de Entregas");

        Container paine = this.getContentPane();
        paine.setLayout(null);

        paine.add(lblData_retirada);
        lblData_retirada.setBounds(20, 20, 100, 20);

        paine.add(txtData_retirada);
        txtData_retirada.setBounds(120, 20, 180, 60);
        
        paine.add(btnBuscar);
        btnBuscar.setBounds(300, 25, 100, 25);

        paine.add(btnRelatorio);       
        btnRelatorio.setBounds(410, 25, 150, 25);

        paine.add(btnRelEntreDatas);
        btnRelEntreDatas.setBounds(570, 25 , 200, 25);
        
        paine.add(btnRelTotaisEntreDatas);
        btnRelTotaisEntreDatas.setBounds(780, 25, 220, 25);
        
        tabela = new JTable(modelo);
        modelo.addColumn("Id");
        modelo.addColumn("Ra");
        modelo.addColumn("Data Retirada");

        final EntregaManager entregaManager = new EntregaManager();
        Funcionarios funcionarios = new Funcionarios();
        modelo.setNumRows(0);
        
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent j) {  
                
                try {
                    Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(txtData_retirada.getText());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String parsedDate = formatter.format(initDate);
                    int i = 0;
                    List<Entregas> findSelectAll = entregaManager.findSelectAll(parsedDate);
                    RelatorioEntregas.this.entregaPesq = findSelectAll;
                    
                    
                    for (Entregas e : findSelectAll) {                       
                        if(e.getRa() != null){
                            i++;
                            modelo.addRow(new Object[]{e.getId(), e.getRa(), e.getData_retirada()});                            
                        }else{
                            System.out.println(e.getId());
                        }                                                
                    }
                    JOptionPane.showMessageDialog(null, "Total: " + entregaManager.findSelectTotal(parsedDate));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        btnRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {                               
                try {
                    new RelatorioFactory().gerarRelatorioDeEntregas(entregaPesq);
                } catch (IOException ex) {
                    Logger.getLogger(RelatorioEntregas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnRelEntreDatas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    new RelatorioFactory().gerarRelatorioEntregasEntreDatas();
                } catch (IOException ex) {
                    Logger.getLogger(RelatorioEntregas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnRelTotaisEntreDatas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    new RelatorioFactory().gerarRelatorioEntregasTOTAISEntreDatas();
                } catch (IOException ex) {
                    Logger.getLogger(RelatorioEntregas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JScrollPane barraRolagem = new JScrollPane(tabela);

        paine.add(barraRolagem);
        barraRolagem.setBounds(20, 80, 980, 330);

        this.setSize(1024, 450);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens\\icones-bio.gif"));
    }
    
    
    public static void main(String args[]){
        new RelatorioEntregas();
    }
}
