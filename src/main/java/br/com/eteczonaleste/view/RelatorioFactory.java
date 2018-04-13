/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eteczonaleste.view;

import br.com.eteczonaleste.Util.DTOEntregasTotais;
import br.com.eteczonaleste.entity.Entregas;
import br.com.eteczonaleste.entity.Alunos;
import br.com.eteczonaleste.entityManager.EntregaManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author jefersonr
 */
public class RelatorioFactory {

    private InputStream getFileInMetaInf(String fileName) {
        System.out.println("private File getFileInMetaInf(String " + fileName + "){...");
        //File devFile = new File("src/META-INF/"+fileName);   
        File inJarFile = new File("META-INF/" + fileName);
        System.out.println("inJarFile = " + inJarFile);
        System.out.println("inJarFile.exists() = " + inJarFile.exists());
        InputStream input = getClass().getResourceAsStream("/META-INF/" + fileName);
        return input;
    }

    private void gerarRelatorioWithInputStreamJasper(
            Collection<?> entityCollection, 
            InputStream jasperFileAsInputStream, 
            Map<String, Object> params) {
        
        try {
            //File jasperFile = new File(jasperFileName);   
            //FileInputStream jasperFileInputStream = null;        
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entityCollection,false);        

            //report params
            //LogoCPS         
            InputStream fileLogoCPS = getFileInMetaInf("LogoCPS.png");        
            BufferedImage bufferedImgLogoCPS =  ImageIO.read(fileLogoCPS);
            Map<String, Object> relParams = null;
            if(null == params){
                relParams  = new HashMap<String,Object>();
            }else{  
                relParams = params;
            }
                
            JasperPrint impressao = null;        
            //jasperFileInputStream = jasperFileAsInputStream;
                        
            relParams.put("logo", bufferedImgLogoCPS);
            
            //o groovy do relatorio nao parseia direito a data inicial e final
            //entao vamos dar uma modificada no pattern da data inicial e final
            //aqui, logo antes de enviar pro rel.
            //String dtaPattern = "yyyy-MM-dd'T'HH:mm:ss";
            //Object dtaInical = (Date)relParams.get("DTA_INICIAL");
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dtaPattern);
            //String format = simpleDateFormat.format(dtaInical);
            
            impressao = JasperFillManager.fillReport(jasperFileAsInputStream, relParams, ds);
            JasperViewer viewer = new JasperViewer(impressao, false);
            
            viewer.setVisible(true);            
            
        } catch (Exception e) {
           e.printStackTrace();
        }
    }//private void gerarRelatorioWithInputStreamJasper(
    
    public void gerarRelatorio(Collection<?> entityCollection, String jasperFileName,Map<String, Object> relParams) throws IOException{
        File jasperFile = new File(jasperFileName);   
        FileInputStream jasperFileInputStream = null;
        
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(entityCollection,false);
        
        //report params
        //LogoCPS 
        
        InputStream fileLogoCPS = getFileInMetaInf("LogoCPS.png");        
        BufferedImage bufferedImgLogoCPS = ImageIO.read(fileLogoCPS);
        
        
        Map<String, Object> params;
        if(null == relParams){
            params = new HashMap<String,Object>();
        }else{
            params = relParams;
        }
        
        
        JasperPrint impressao = null;
        try {                                   
            jasperFileInputStream = new FileInputStream(jasperFile);
            
            params.put("logo", bufferedImgLogoCPS);
            
            impressao = JasperFillManager.fillReport(jasperFileInputStream, params, ds);
            JasperViewer viewer = new JasperViewer(impressao, false);
            
            viewer.setVisible(true);            
            
        } catch (Exception e) {
           e.printStackTrace();
        }
    }   
    
    
    public void gerarRelatorioEntregasTOTAISEntreDatas() throws IOException {
        //RelatorioEntregasTOTAISEntreDatas.jrxml
        
        
        Collection<DTOEntregasTotais> collDtoEntregasTotais = new ArrayList<DTOEntregasTotais>();
        
        String strDtaInicial = null; 
        String strDtaFinal = null;        
        Date dtaInicial = null;
        Date dtaFinal = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        try {            
            
            do{
                strDtaInicial = JOptionPane.showInputDialog("Digite a DATA INICIAL");
                strDtaFinal = JOptionPane.showInputDialog("Digite a DATA FINAL");        
                dtaInicial = simpleDateFormat.parse(strDtaInicial);                
                dtaFinal = simpleDateFormat.parse(strDtaFinal);                
                if (dtaFinal.before(dtaInicial))
                    JOptionPane.showMessageDialog(null, "DATA FINAL deve ser posterior a INICIAL");
            }while (dtaFinal.before(dtaInicial));

                
        } catch (ParseException ex) {
            Logger.getLogger(RelatorioFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("DTA_INICIAL", dtaInicial);
        params.put("DTA_FINAL", dtaFinal);
        
        EntregaManager entregaManager = new EntregaManager();
        Collection<?> results = entregaManager.execJpql("SELECT e.data_retirada, COUNT(e) FROM Entregas e where e.data_retirada between :DTA_INICIAL and :DTA_FINAL group by e.data_retirada", params);
        
        Iterator<?> iterator = results.iterator();
        DTOEntregasTotais dtoEntregasTotais = null;
        while(iterator.hasNext()){
            dtoEntregasTotais = new DTOEntregasTotais();
            Object[] row = (Object[]) iterator.next();
            
            for (int i = 0; i < row.length; i++) {
                System.out.println(row[i]);
                if(row[i] instanceof Date){
                    dtoEntregasTotais.setData_retirada((Date)row[i]);
                }else if (row[i] instanceof Long){
                    dtoEntregasTotais.setQtde((Long)row[i]);
                }
            }            
        collDtoEntregasTotais.add(dtoEntregasTotais);
        }
        
        
        gerarRelatorioWithInputStreamJasper(
                collDtoEntregasTotais, getFileInMetaInf("RelatorioEntregasTOTAISEntreDatas.jrxml.jasper"),params);        
    }

    
    public void gerarRelatorioEntregasEntreDatas() throws IOException {
       
        String strDtaInicial = null; 
        String strDtaFinal = null;        
        Date dtaInicial = null;
        Date dtaFinal = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        try {            
            
            do{
                strDtaInicial = JOptionPane.showInputDialog("Digite a DATA INICIAL");
                strDtaFinal = JOptionPane.showInputDialog("Digite a DATA FINAL");        
                dtaInicial = simpleDateFormat.parse(strDtaInicial);                
                dtaFinal = simpleDateFormat.parse(strDtaFinal);
                
                if (dtaFinal.before(dtaInicial))
                    JOptionPane.showMessageDialog(null, "DATA FINAL deve ser posterior a INICIAL");
            }while (dtaFinal.before(dtaInicial));

                
        } catch (ParseException ex) {
            Logger.getLogger(RelatorioFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("DTA_INICIAL", dtaInicial);
        params.put("DTA_FINAL", dtaFinal);
        
        EntregaManager entregaManager = new EntregaManager();
        Collection<Entregas> colectionDeEntregas = entregaManager.execJpql("SELECT e FROM Entregas e where e.data_retirada between :DTA_INICIAL and :DTA_FINAL", params);
       
//        gerarRelatorio(colectionDeEntregas, getFileInMetaInf(
//                "RelatorioEntregasEntreDatas.jrxml.jasper").getCanonicalPath(),
//                params);        
        
          gerarRelatorioWithInputStreamJasper(colectionDeEntregas, getFileInMetaInf(
                  "RelatorioEntregasEntreDatas.jrxml.jasper"),
                   params);        

    }       
    public void gerarRelatorioDeEntregas(Collection<Entregas> colectionDeEntregas) throws IOException {
        gerarRelatorioWithInputStreamJasper(colectionDeEntregas, getFileInMetaInf(
                "RelatorioEntregas.jasper"),null);        
    }
    public void gerarRelatorioAlunosCadastrados(Collection<Alunos> colectionDeAlunos) throws IOException {
        //gerarRelatorio(colectionDeAlunos, getFileInMetaInf(
        //        "RelatorioAlunosCadastrados.jrxml.jasper").getAbsolutePath(),null);        
    }
    
    public static void compilarJrxml(String jrxmlFileName){
        File jrxmlFile = new File(jrxmlFileName);
        if(jrxmlFile.exists()){
        try {   
            JasperCompileManager.compileReportToFile(
                    jrxmlFile.getAbsolutePath(),
                    jrxmlFile.getName()+".jasper");
        } catch (JRException ex) {
            Logger.getLogger(RelatorioFactory.class.getName()).log(Level.SEVERE, null, ex);
        }    
        }else{
            System.out.println("arquivo "+ jrxmlFileName +" nao encontrado");
        }                
    }

    public static void compilarJrxmlRelatorioEntregasTotaisEntreDatas(){
        compilarJrxml("src/META-INF/RelatorioEntregasTOTAISEntreDatas.jrxml");        
    }
     
    public static void compilarJrxmlRelatorioEntregasEntreDatas(){
        compilarJrxml("src/META-INF/RelatorioEntregasEntreDatas.jrxml");                
    }
            
    public static void compilarJrxmlRelatorioEntregas(){
        compilarJrxml("src/META-INF/RelatorioEntregas.jrxml");                
    }
    
    public static void compilarJrxmlRelatorioEntregasDoDia(){
        compilarJrxml("RelatorioEntregasDoDia.jrxml");                
    }
    
    public static void compilarJrxmlMensal(){
        //totais de cada dia durante um periodo escolhido 
        compilarJrxml("RelatorioEntregas.jrxml");                
    }

    public static void compilarJrxmlAlimentacaoPorAluno(){
        //Dado RM ou nome do aluno mostra as alimentacoes dele 
        compilarJrxml("AlimentacaoPorAluno.jrxml");                
    }
    
    public static void compilarJrxmlAlunosCadastrados(){
        //Dado RM ou nome do aluno mostra as alimentacoes dele 
        compilarJrxml("RelatorioAlunosCadastrados.jrxml");                
    }
        
    public static void compilarJrxmlFuncionariosCadastrados(){
        //Dado RM ou nome do aluno mostra as alimentacoes dele 
        compilarJrxml("FuncionariosCadastrados.jrxml");                
    }
     
    public static void main(String args[]) throws IOException{        
        //RelatorioFactory.compilarJrxmlRelatorioEntregasTotaisEntreDatas();
        //RelatorioFactory.compilarJrxmlRelatorioEntregasEntreDatas();
        //RelatorioFactory.compilarJrxmlRelatorioEntregas();
        //RelatorioFactory.compilarJrxmlAlunosCadastrados();
        
        //RelatorioFactory relatorioFactory = new RelatorioFactory();
        
        //relatorioFactory.gerarRelatorioDeEntregas(Util.Util.createBeanCollection());
        //relatorioFactory.gerarRelatorioAlunosCadastrados(Util.Util.createAlunosCadastradosCollection());        
        //relatorioFactory.gerarRelatorioEntregasEntreDatas();
        //relatorioFactory.gerarRelatorioEntregasTOTAISEntreDatas();
    } 
}
