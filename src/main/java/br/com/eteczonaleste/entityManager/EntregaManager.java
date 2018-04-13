package br.com.eteczonaleste.entityManager;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entity.Entregas;
import br.com.eteczonaleste.entity.Funcionarios;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntregaManager {

    EntityManagerFactory emf;
    EntityManager em;

    public EntregaManager() {
        emf = Persistence.createEntityManagerFactory("e211PU");
        em = emf.createEntityManager();
    }

    public void insert(Entregas entrega) {
        em.getTransaction().begin();
        em.persist(entrega);
        em.flush();
        em.getTransaction().commit();
        emf.close();
    }
    
    public List verificaSeOAlunoJaAlmocou(Alunos nra){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dta = new Date();
        java.sql.Date sqlDate = new java.sql.Date(dta.getTime());
                
        Query entregaPesq = em.createQuery("SELECT e FROM Entregas e WHERE e.ra = :nra AND e.data_retirada = :data", Entregas.class);
               entregaPesq.setParameter("data", sqlDate);
               entregaPesq.setParameter("nra", nra);

        //ID, DATA_RETIRADA, HORA_RETIRADA, ra, ID_FUNCIONARIOS_ID
        
        return entregaPesq.getResultList();
    }

    public List<Entregas> findSelectAll(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dta = null;

        try {
            dta = formatter.parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(EntregaManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        Query entregaPesq = em.createQuery("SELECT e FROM Entregas e WHERE e.data_retirada = :data", Entregas.class);
                entregaPesq.setParameter("data", dta);

        //ID, DATA_RETIRADA, HORA_RETIRADA, ra, ID_FUNCIONARIOS_ID
        
        return entregaPesq.getResultList();
    }

    public long findSelectTotal(String data) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dta = null;

        try {
            dta = formatter.parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(EntregaManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        Query entregaPesq = (Query) em.createQuery("SELECT COUNT(a) FROM Entregas a WHERE a.data_retirada = :data")
                .setParameter("data", dta);

        return (Long) entregaPesq.getSingleResult();
    }

    public void delete(Entregas entrega) {
        em.getTransaction().begin();
        em.remove(entrega);
        em.getTransaction().commit();
        emf.close();
    }

    public Collection<Entregas> execJpql(String jpql, Map<String,Object> parameters){
        List<Entregas> resultList = new ArrayList<Entregas>();
        
        Query qry = (Query) em.createQuery(jpql);
        
        if(!parameters.isEmpty()){
            Iterator<Map.Entry<String, Object>> iterator = parameters.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                qry.setParameter(next.getKey(), next.getValue());
            }
        }
        
        resultList = qry.getResultList();
        return resultList;
    }
}//class
