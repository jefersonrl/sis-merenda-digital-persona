package br.com.eteczonaleste.entityManager;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entity.Funcionarios;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Collection;
import javax.persistence.Query;

public class FuncionariosManager {

    EntityManagerFactory emf;
    EntityManager em;

    public FuncionariosManager() {
        emf = Persistence.createEntityManagerFactory("e211PU");
        em = emf.createEntityManager();
    }

    public void insert(Funcionarios funcionarios) {
        em.getTransaction().begin();
        em.merge(funcionarios);
        em.getTransaction().commit();
        emf.close();
    }

    public Funcionarios findUserAndPassword(String usuario, String senha) {
        Funcionarios funcionarioPesq = (Funcionarios) em.createQuery("SELECT f FROM Funcionarios f WHERE f.usuario=:usuario AND f.senha=:senha")
                .setParameter("usuario", usuario)
                .setParameter("senha", senha)
                .getSingleResult();
        return funcionarioPesq;
    }

    public Collection<Funcionarios> findSelectAll() {
        Query funcionarioPesq = em.createQuery("SELECT t FROM Funcionarios t");

        return (Collection<Funcionarios>) funcionarioPesq.getResultList();
    }

    public void delete(Funcionarios funcionarios) {
        em.getTransaction().begin();
        em.remove(funcionarios);
        em.getTransaction().commit();
        emf.close();
    }

}
