package br.com.eteczonaleste.entityManager;

import java.util.Collection;

import javax.persistence.Query;

import br.com.eteczonaleste.entity.Alunos;

public class AlunosManager {


    public AlunosManager() {    
    }

    public void insert(Alunos alunos) {
    	br.com.eteczonaleste.view.Principal.em.getTransaction().begin();
    	br.com.eteczonaleste.view.Principal.em.persist(alunos);
    	br.com.eteczonaleste.view.Principal.em.getTransaction().commit();        
    }
    
    public void atualizar(Alunos alunos) {
    	br.com.eteczonaleste.view.Principal.em.getTransaction().begin();
    	br.com.eteczonaleste.view.Principal.em.persist(alunos);
    	br.com.eteczonaleste.view.Principal.em.getTransaction().commit();        
    }

    public Alunos findSelectDigital(String digital) {
        Alunos alunoPesq = (Alunos) br.com.eteczonaleste.view.Principal.em.createQuery("SELECT a FROM Alunos a WHERE a.template=:maoesquerdapolegar")
                .setParameter("maoesquerdapolegar", digital)
                .getSingleResult();
        return alunoPesq;
    }
    
    public Alunos findByRm(int rm) {
        Alunos alunosPesq = (Alunos) br.com.eteczonaleste.view.Principal.em.createQuery("SELECT a FROM Alunos a WHERE a.ra=:regm")
                .setParameter("regm", rm)
                .getSingleResult();
        return alunosPesq;
    }
    
    public Alunos findByName(String nome) {
        Alunos alunosPesq = (Alunos) br.com.eteczonaleste.view.Principal.em.createQuery("SELECT a FROM Alunos a WHERE a.nome=:nome")
                .setParameter("nome", nome)
                .getSingleResult();
        return alunosPesq;
    }
    
    public Collection<Alunos> findSelectAll() {
        Query alunoPesq = br.com.eteczonaleste.view.Principal.em.createQuery("SELECT s FROM Alunos s");        
        return (Collection<Alunos>) alunoPesq.getResultList();
    }
    
    public Collection<Alunos> findAlunosSoQueEstaoEstudando() {
        Query alunoPesq = br.com.eteczonaleste.view.Principal.em.createQuery("SELECT s FROM Alunos s where s.nome like 'alice%' ");        
        return (Collection<Alunos>) alunoPesq.getResultList();
    }

    public void delete(AlunosManager alunos) {
    	br.com.eteczonaleste.view.Principal.em.getTransaction().begin();
    	br.com.eteczonaleste.view.Principal.em.remove(alunos);
    	br.com.eteczonaleste.view.Principal.em.getTransaction().commit();    	
    }

}
