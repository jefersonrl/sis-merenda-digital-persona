package br.com.eteczonaleste.view;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entityManager.AlunosManager;
import java.awt.Container;
import java.awt.Toolkit;
import java.util.Collection;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RelatorioAlunos extends JFrame {

    private DefaultTableModel modelo = new DefaultTableModel();
    private JTable tabela;

    public RelatorioAlunos() {
        super("Relatório de Alunos");

        Container paine = this.getContentPane();
        paine.setLayout(null);

        tabela = new JTable(modelo);
        modelo.addColumn("RA");
        modelo.addColumn("Nome");

        AlunosManager alunosManager = new AlunosManager();
        Alunos alunos = new Alunos();
        modelo.setNumRows(0);

        Collection<Alunos> alunosPesq = alunosManager.findSelectAll();
        for (Alunos e : alunosPesq) {
            modelo.addRow(new Object[]{ e.getRa(), e.getNome()});
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
