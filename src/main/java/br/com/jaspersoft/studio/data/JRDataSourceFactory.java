package br.com.jaspersoft.studio.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import java.util.Map;

import com.ibm.icu.util.GregorianCalendar;

import br.com.eteczonaleste.entity.Entregas;
import br.com.eteczonaleste.entityManager.EntregaManager;

public class JRDataSourceFactory {
	
	 public static java.util.Collection generateCollection(){
		Date dtaInicial = new GregorianCalendar(2018,3,1).getTime();
		Date dtaFinal = new GregorianCalendar(2018,3,15).getTime();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("DTA_INICIAL", dtaInicial);
        params.put("DTA_FINAL", dtaFinal);
        
        EntregaManager entregaManager = new EntregaManager();
        Collection<Entregas> colectionDeEntregas = entregaManager.execJpql("SELECT e FROM Entregas e where e.data_retirada between :DTA_INICIAL and :DTA_FINAL", params);		
		return colectionDeEntregas;
	}	
}
