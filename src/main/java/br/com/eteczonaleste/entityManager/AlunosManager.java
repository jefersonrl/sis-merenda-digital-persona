package br.com.eteczonaleste.entityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.com.eteczonaleste.entity.Alunos;
import java.util.Collection;
import javax.persistence.Query;

public class AlunosManager {

    EntityManagerFactory emf;
    EntityManager em;

    public AlunosManager() {
        emf = Persistence.createEntityManagerFactory("e211PU");
        em = emf.createEntityManager();
    }

    public void insert(Alunos alunos) {
        em.getTransaction().begin();
        em.persist(alunos);
        em.getTransaction().commit();
        emf.close();
    }
    
    public void atualizar(Alunos alunos) {
        em.getTransaction().begin();
        em.persist(alunos);
        em.getTransaction().commit();
        emf.close();
    }

    public Alunos findSelectDigital(String digital) {
        Alunos alunoPesq = (Alunos) em.createQuery("SELECT a FROM Alunos a WHERE a.template=:maoesquerdapolegar")
                .setParameter("maoesquerdapolegar", digital)
                .getSingleResult();
        return alunoPesq;
    }
    
    public Alunos findByRm(int rm) {
        Alunos alunosPesq = (Alunos) em.createQuery("SELECT a FROM Alunos a WHERE a.ra=:regm")
                .setParameter("regm", rm)
                .getSingleResult();
        return alunosPesq;
    }
    
    public Alunos findByName(String nome) {
        Alunos alunosPesq = (Alunos) em.createQuery("SELECT a FROM Alunos a WHERE a.nome=:nome")
                .setParameter("nome", nome)
                .getSingleResult();
        return alunosPesq;
    }
    
    public Collection<Alunos> findSelectAll() {
        Query alunoPesq = em.createQuery("SELECT s FROM Alunos s");        
        return (Collection<Alunos>) alunoPesq.getResultList();
    }
    
    public Collection<Alunos> findAlunosSoQueEstaoEstudando() {
        Query alunoPesq = em.createQuery("SELECT s FROM Alunos s where s.nome like 'alice%' ");        
        return (Collection<Alunos>) alunoPesq.getResultList();
    }

    public void delete(AlunosManager alunos) {
        em.getTransaction().begin();
        em.remove(alunos);
        em.getTransaction().commit();
        emf.close();
    }

}
