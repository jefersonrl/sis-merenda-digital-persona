/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eteczonaleste.Util;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entity.Entregas;
import br.com.eteczonaleste.entity.Funcionarios;
import br.com.eteczonaleste.entityManager.EntregaManager;
import br.com.eteczonaleste.entityManager.FuncionariosManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 *
 * @author wagner
 */
public class Util {

    public static Collection<Alunos> createAlunosCadastradosCollection() {
        Alunos aluno1 = new Alunos();
        aluno1.setNome("NomeDoAluno1");
        Alunos aluno2 = new Alunos();
        aluno2.setNome("NomeDoAluno2");
        List<Alunos> alunosCollections = new ArrayList<Alunos>();
        alunosCollections.add(aluno1);
        alunosCollections.add(aluno2);
        return alunosCollections;
    }

    public void insereUmUsuario() {
        Funcionarios funcWagner = new Funcionarios();
        funcWagner.setNome("Wagner França Marques");
        funcWagner.setSenha("1234");
        funcWagner.setUsuario("wagner");
        FuncionariosManager funcManager = new FuncionariosManager();
        funcManager.insert(funcWagner);
    }

    public void registraEntregas() {
        Alunos aluno = new Alunos();
        aluno.setNome("alunoEntrega1");

        Funcionarios funcionario = new Funcionarios();
        funcionario.setNome("funcionarioEntrega1");

        Entregas entrega = new Entregas();
        entrega.setData_retirada(new Date());
        entrega.setHora_retirada(new Date());
        entrega.setRa(aluno);
        entrega.setId_funcionarios(funcionario);

        EntregaManager entregaManager = new EntregaManager();
        entregaManager.insert(entrega);

    }

    public Util() {
    }

    public static Collection createBeanCollection2() {
        return createBeanCollection();
    }

    public static Collection<Entregas> createBeanCollection() {
        Funcionarios funcionario = new Funcionarios();
        funcionario.setNome("funcionarioEntrega1");

        Funcionarios funcWagner = new Funcionarios();
        funcWagner.setNome("Wagner França Marques");
        funcWagner.setSenha("1234");
        funcWagner.setUsuario("wagner");
        //FuncionariosManager funcManager = new FuncionariosManager();
        //funcManager.insert(funcWagner);

        Alunos aluno = new Alunos();
        aluno.setNome("alunoEntrega1");

        Alunos aluno2 = new Alunos();
        aluno2.setNome("alunoEntrega2");

        Entregas entrega = new Entregas();
        entrega.setData_retirada(new Date());
        entrega.setHora_retirada(new Date());
        entrega.setRa(aluno);
        entrega.setId_funcionarios(funcionario);

        Entregas entrega2 = new Entregas();
        entrega2.setData_retirada(new Date());
        entrega2.setHora_retirada(new Date());
        entrega2.setRa(aluno2);
        entrega2.setId_funcionarios(funcWagner);

        Collection<Entregas> entregaCollection = new ArrayList();

        entregaCollection.add(entrega);
        entregaCollection.add(entrega2);

        for (Entregas e : entregaCollection) {
            System.out.println(e.getRa().getNome());
            System.out.println(e.getId_funcionarios().getNome());
        }
        
        return entregaCollection;
    }
    
    public static List<Entregas> findSelectAll() {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("e211PU");
        EntityManager em = emf.createEntityManager();
        
        
        Query entregaPesq = em.createQuery("SELECT e FROM Entregas e", Entregas.class);
        //entregaPesq.setParameter("data", cal2016Set5.getTime());

        //ID, DATA_RETIRADA, HORA_RETIRADA, ra, ID_FUNCIONARIOS_ID
        List resultList = entregaPesq.getResultList();
        return resultList;
    }
    public static void main(String[] args) {
        //new Util().registraEntregas();
        findSelectAll();
    }
}
