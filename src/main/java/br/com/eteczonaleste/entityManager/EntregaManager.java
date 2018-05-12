package br.com.eteczonaleste.entityManager;

import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entity.Entregas;
import br.com.eteczonaleste.entity.Funcionarios;
import br.com.eteczonaleste.view.Principal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

	public EntregaManager() {
	}

	public void insert(Entregas entrega) {
		try {
			Principal.em.getTransaction().begin();
			Principal.em.persist(entrega);
			Principal.registraEntregaNoArquivoDeEntregasDoDia(entrega);
			Principal.em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verificaSeOAlunoJaAlmocou(Alunos nra) {
		System.out.println(nra+"public boolean verificaSeOAlunoJaAlmocou(Alunos "+nra.getRa()+") {...");
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date dta = new Date();
//		java.sql.Date sqlDate = new java.sql.Date(dta.getTime());

//		Query entregaPesq = Principal.em.createQuery(
//				"SELECT count(e) FROM Entregas e WHERE e.ra = :nra AND e.data_retirada = :data", Entregas.class);
//		entregaPesq.setParameter("data", sqlDate);
//		entregaPesq.setParameter("nra", nra);
		// ID, DATA_RETIRADA, HORA_RETIRADA, ra, ID_FUNCIONARIOS_ID
//		int qtdeAlmocos = Integer.parseInt(entregaPesq.getResultList().get(0).toString());
//		boolean result = qtdeAlmocos == 0 ? false : true;
//		System.out.println("Ja amocou ? " + result);
		
		boolean result = false;
		String line;
		System.out.println("antes de entrar no try");
		try {
			
			System.out.println("entrou no try...");	
			FileReader fr = new FileReader(Principal.arqDeEntregasDoDia);			
			BufferedReader br = new BufferedReader(fr);
			
			while ((line = br.readLine())!= null) {
				System.out.println("entrou no while..");
				System.out.println("line = br.readLine() -> "+line);
				String lineNo = line.split(";")[2];
				System.out.println("linha lida = "+line+" "+lineNo);
				String strRa = line.split(";")[0];
				System.out.println("strRa="+strRa);
				int intRa = Integer.parseInt(strRa);
				System.out.println("intRa="+intRa);
				int alunoRa = nra.getRa();
				System.out.println("alunoRa="+alunoRa);
			    if(intRa == alunoRa) {
			    	System.out.println(intRa+"=="+alunoRa+":True");
			    	return true; 
			    }else {
			    	System.out.println(intRa+"=="+alunoRa+":False");
			    	result = false;
			    }			    
			}
		} catch (Exception e) {
			System.out.println("} catch (IOException e) {....");			
			e.printStackTrace();
		}	
		
		System.out.println("retornando false porque nem analisou o arquivo");
		return result;
	}

	public List<Entregas> findSelectAll(String data) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dta = null;

		try {
			dta = formatter.parse(data);
		} catch (ParseException ex) {
			Logger.getLogger(EntregaManager.class.getName()).log(Level.SEVERE, null, ex);
		}

		Query entregaPesq = Principal.em.createQuery("SELECT e FROM Entregas e WHERE e.data_retirada = :data",
				Entregas.class);
		entregaPesq.setParameter("data", dta);

		// ID, DATA_RETIRADA, HORA_RETIRADA, ra, ID_FUNCIONARIOS_ID

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

		Query entregaPesq = (Query) Principal.em
				.createQuery("SELECT COUNT(a) FROM Entregas a WHERE a.data_retirada = :data").setParameter("data", dta);

		return (Long) entregaPesq.getSingleResult();
	}

	public void delete(Entregas entrega) {
		Principal.em.getTransaction().begin();
		Principal.em.remove(entrega);
		Principal.em.getTransaction().commit();
	}

	public Collection<Entregas> execJpql(String jpql, Map<String, Object> parameters) {
		List<Entregas> resultList = new ArrayList<Entregas>();

		Query qry = (Query) Principal.em.createQuery(jpql);

		if (!parameters.isEmpty()) {
			Iterator<Map.Entry<String, Object>> iterator = parameters.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> next = iterator.next();
				qry.setParameter(next.getKey(), next.getValue());
			}
		}

		resultList = qry.getResultList();
		return resultList;
	}
}// class
