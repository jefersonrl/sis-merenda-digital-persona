package br.com.eteczonaleste.view;

import br.com.eteczonaleste.entity.Funcionarios;
import br.com.eteczonaleste.entityManager.FuncionariosManager;
import java.awt.Container;
import java.awt.Toolkit;
import java.util.Collection;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RelatorioFuncionarios extends JFrame{
    
    private DefaultTableModel modelo = new DefaultTableModel();
    private JTable tabela;
    
    public RelatorioFuncionarios(){
        super("Relatório de Funcionários");
        
        Container paine = this.getContentPane();
        paine.setLayout(null);

        tabela = new JTable(modelo);
        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        modelo.addColumn("Usuário");
        
        FuncionariosManager funcionariosManager = new FuncionariosManager();
        Funcionarios funcionarios = new Funcionarios();
        modelo.setNumRows(0);

        Collection<Funcionarios> funcionariosPesq = funcionariosManager.findSelectAll();
        for (Funcionarios e : funcionariosPesq) {
            modelo.addRow(new Object[]{e.getId(), e.getNome(), e.getUsuario()});
        }

        JScrollPane barraRolagem = new JScrollPane(tabela);
        paine.add(barraRolagem);
        barraRolagem.setBounds(20, 20, 760, 380);

        this.setSize(800, 450);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens\\icones-bio.gif"));
    }
    
}
