package br.com.eteczonaleste.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entityManager.AlunosManager;

public class Principal extends JFrame {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("e211PU");
    static public EntityManager em = emf.createEntityManager();

    private JMenu menuCadastrar = new JMenu("Cadastrar");
    //private JMenu menuEditar = new JMenu("Editar");
    private JMenu menuRelatorio = new JMenu("Relatório");
    private JMenu menuEntrega = new JMenu("Entrega");

    private JMenuItem menuItemCadastrarRefeicaoManual = new JMenuItem("Refeição");
    private JMenuItem menuItemCadastrarAluno = new JMenuItem("Aluno");
    private JMenuItem menuItemCadastrarBiometria = new JMenuItem("Biometria");
    private JMenuItem menuItemCadastrarFuncionario = new JMenuItem("Funcionário");
    private JMenuItem menuItemRelatorioFuncionario = new JMenuItem("Funcionários");
    private JMenuItem menuItemRelatorioAluno = new JMenuItem("Alunos");
    private JMenuItem menuItemRelatorioEntrega = new JMenuItem("Entrega");

    private JMenuItem menuItemSobre = new JMenuItem("Sobre");
    private JMenuItem menuItemFechar = new JMenuItem("Fechar");

    private JMenuItem menuItemEntrega = new JMenuItem("Merenda Escolar");

    private JMenuBar menuBar = new JMenuBar();

    ImageIcon icon = new ImageIcon("imagens/leituras.png");
    JLabel label = new JLabel(icon);
    
    public Principal() {
        super("Sistema de Controle v2.0 - 211 - ETEC Zona Leste");

        //adicona os JMenuItem no JMenu
        menuCadastrar.add(menuItemCadastrarAluno);
        //menuCadastrar.add(menuItemCadastrarBiometria);
        menuCadastrar.add(menuItemCadastrarFuncionario);
        menuCadastrar.add(menuItemCadastrarRefeicaoManual);
        menuCadastrar.add(menuItemSobre);
        menuCadastrar.addSeparator();
        menuCadastrar.add(menuItemFechar);

        menuRelatorio.add(menuItemRelatorioAluno);
        menuRelatorio.add(menuItemRelatorioFuncionario);
        menuRelatorio.add(menuItemRelatorioEntrega);

        menuEntrega.add(menuItemEntrega);

        //adiciona o JMenu no JMenuBar
        menuBar.add(menuCadastrar);
        menuBar.add(menuRelatorio);
        menuBar.add(menuEntrega);

        this.setJMenuBar(menuBar);

        menuItemEntrega.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {                    
                    //Alterar
                    //Runtime.getRuntime().exec("C:\\biometria\\EnrollmentSample CS.exe");
                	MainForm main = new MainForm();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        menuItemCadastrarBiometria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    AlunosManager alunosManager = new AlunosManager();
                    Alunos aluno = new Alunos();
                    
                    int rm = Integer.parseInt(JOptionPane.showInputDialog("Digite o RM do Aluno a ser cadastrado"));
                    System.out.println(rm);
                    aluno = alunosManager.findByRm(rm);
                    
                    JOptionPane.showMessageDialog(null, "Aluno(a) cadastrado(a) na tabela verifica");
                    
                    //Alterar
                    //Runtime.getRuntime().exec("cmd.exe cd C:\\GrFinger\\Captura\\bin\\ &  /c 'start captura.exe'");
                    //Runtime.getRuntime().exec("c:\\GrFinger\\Captura\\bin\\captura.exe");
                    
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        menuItemCadastrarRefeicaoManual.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LancamentoRefeicoesManuais lancamento = new LancamentoRefeicoesManuais();
            }
        });

        menuItemRelatorioEntrega.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RelatorioEntregas relatorioEntrega = new RelatorioEntregas();
            }
        });

        menuItemRelatorioAluno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RelatorioAlunos relatorioAlunos = new RelatorioAlunos();
            }
        });

        menuItemRelatorioFuncionario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RelatorioFuncionarios relatorioFuncionarios = new RelatorioFuncionarios();
            }
        });

        menuItemCadastrarAluno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastrarAlunos cadastrarAlunos = new CadastrarAlunos();
            }
        });

        menuItemCadastrarFuncionario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastrarFuncionarios cadastrarFuncionarios = new CadastrarFuncionarios();
            }
        });

        menuItemSobre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sobre sobre = new Sobre();
            }
        });

        menuItemFechar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon("imagens\\icones-bio.gif");
                int i = JOptionPane.showConfirmDialog(null, "Você deseja encerrar essa aplicação?", "Sistema de Controle v2.0", JOptionPane.YES_NO_OPTION, 1, null);
                if (i == 0) {
                    System.exit(0);
                }
            }
        });

        Container paine = this.getContentPane();
        paine.setLayout(new BorderLayout());

        paine.add(label, BorderLayout.CENTER);
        //label.setBounds(350, 150, 668, 503);
 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens\\icones-bio.gif"));
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().getSize());
        this.setVisible(true);
        this.setResizable(false);
        //this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    public static void main(String args[]){
        Principal principal = new Principal();
    }
}
