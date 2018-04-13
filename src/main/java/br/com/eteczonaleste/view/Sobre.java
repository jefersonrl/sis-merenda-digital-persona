package br.com.eteczonaleste.view;

import java.awt.Container;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Sobre extends JFrame{
    JLabel lblInformacoes = new JLabel("Desenvolvido para a Escola Técnica Estadual da Zona Leste");
    JLabel lbldesenvolvedor = new JLabel("Desenvolvedor/Programador: Jeferson Roberto de Lima");
    JLabel lblprogramador = new JLabel("Programador: Wagner França");
    
    JLabel lblcontato = new JLabel("Contato: jefersonrl@icloud.com / (11) 98660-0042");
    
    public Sobre(){
        super("Controle de Merenda v2.0");
        
        Container paine = this.getContentPane();
        paine.setLayout(null);
        
        lblInformacoes.setBounds(20,20,380,20);
        paine.add(lblInformacoes);
        
        lbldesenvolvedor.setBounds(20,60,380,20);
        paine.add(lbldesenvolvedor);
                
        lblprogramador.setBounds(20,80,380,20);
        paine.add(lblprogramador);
        
        lblcontato.setBounds(20,110,380,20);
        paine.add(lblcontato);
        
        this.setSize(400, 200);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens\\icones-bio.gif"));
    }
}
