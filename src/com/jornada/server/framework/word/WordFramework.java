package com.jornada.server.framework.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;

import com.jornada.ConfigJornada;

public class WordFramework {

 private XWPFDocument document = null; 
    
    public WordFramework() { 
    } 
    
    public final void openFile(String filename) throws IOException { 
        File file = null; 
        FileInputStream fis = null; 
        try { 
            file = new File(filename); 
            fis = new FileInputStream(file); 
            this.document = new XWPFDocument(fis); 
        } 
        finally { 
            try { 
                if(fis != null) { 
                    fis.close(); 
                    fis = null; 
                } 
            } 
            catch(IOException ioEx) { 
                // Swallow this exception. It would have occured onyl 
                // when releasing the file handle and should not pose 
                // problems to later processing. 
            } 
        } 
    } 
    
    public final void saveAs(String filename) throws IOException { 
        File file = null; 
        FileOutputStream fos = null; 
        try { 
            file = new File(filename); 
            fos = new FileOutputStream(file); 
            this.document.write(fos); 
        } 
        finally { 
            if(fos != null) { 
                fos.close(); 
               fos = null; 
            } 
        } 
    } 
    
    public final void insertAtBookmark(String bookmarkName, String bookmarkValue) { 
        List<XWPFParagraph> paraList = null; 
        Iterator<XWPFParagraph> paraIter = null; 
        XWPFParagraph para = null; 
        List<CTBookmark> bookmarkList = null; 
        Iterator<CTBookmark> bookmarkIter = null; 
        CTBookmark bookmark = null; 
        XWPFRun run = null; 
        
        paraList = this.document.getParagraphs(); 
        paraIter = paraList.iterator(); 
            
        while(paraIter.hasNext()) { 
            para = paraIter.next(); 
                
            bookmarkList = para.getCTP().getBookmarkStartList(); 
            bookmarkIter = bookmarkList.iterator(); 
                
            while(bookmarkIter.hasNext()) { 
                bookmark = bookmarkIter.next(); 
                if(bookmark.getName().equals(bookmarkName)) { 
                    run = para.createRun(); 
                    run.setText(bookmarkValue); 
                    para.getCTP().getDomNode().insertBefore(run.getCTR().getDomNode(), bookmark.getDomNode()); 
                } 
            } 
        } 
    } 
    
    public String createWordParaContratoAluno(String nomeFisicoDoc, String nomeAluno, String cursoAluno,
            String nomeRespFinan, String cpfRespFinan, String rgRespFinan, String enderecoRespFinan,
            String nomeRespAcad, String cpfRespAcad, String rgRespAcad, String enderecoRespAcad) {
        
        String fileName="";
        try { 

            WordFramework wordContract = new WordFramework(); 
            
            String enderecoFisicoTemplate = ConfigJornada.getProperty("config.download")+ ConfigJornada.getProperty("config.doc");
            wordContract.openFile(enderecoFisicoTemplate+nomeFisicoDoc); 
            
            int intAluno=4;
            int intRespFinanc=5;
            int intRespAcad=2;
            int intCurso=2;
            
            for (int i = 0; i < intAluno; i++){
                wordContract.insertAtBookmark("aluno_"+(i+1), getTextForContract(nomeAluno));
            }                        
            for (int i = 0; i < intRespFinanc; i++){
                wordContract.insertAtBookmark("responsavel_financeiro_"+(i+1), getTextForContract(nomeRespFinan));                 
            }
            for (int i = 0; i < intRespAcad; i++){
                wordContract.insertAtBookmark("responsavel_academico_"+(i+1), getTextForContract(nomeRespAcad));                 
            }
            for (int i = 0; i < intCurso; i++){
                wordContract.insertAtBookmark("aluno_curso_"+(i+1), getTextForContract(cursoAluno));                 
            }            
            
            wordContract.insertAtBookmark("responsavel_financeiro_rg", getTextForContract(rgRespFinan)); 
            wordContract.insertAtBookmark("responsavel_financeiro_cpf", getTextForContract(cpfRespFinan)); 
            wordContract.insertAtBookmark("responsavel_financeiro_endereco", getTextForContract(enderecoRespFinan)); 
            
            wordContract.insertAtBookmark("responsavel_academico_rg", getTextForContract(rgRespAcad)); 
            wordContract.insertAtBookmark("responsavel_academico_cpf", getTextForContract(cpfRespAcad)); 
            wordContract.insertAtBookmark("responsavel_academico_endereco", getTextForContract(enderecoRespAcad)); 
            
            String enderecoFisicoFile = ConfigJornada.getProperty("config.download")+ ConfigJornada.getProperty("config.download.doc");
            String nomeFile = "Contrato_" + nomeAluno + ".docx";
            wordContract.saveAs(enderecoFisicoFile+nomeFile);
            fileName=ConfigJornada.getProperty("config.download.doc")+nomeFile;
        } 
        catch(Exception ex) { 
            fileName="erro";
            System.out.println("Caught a: " + ex.getClass().getName()); 
            System.out.println("Message: " + ex.getMessage()); 
            System.out.println("Stacktrace follows:....."); 
            ex.printStackTrace(System.out); 
        } 
        return fileName;
    } 
    
    
    
    private static String getTextForContract(String strText) {

        String testTruncate;

        if (strText != null && !strText.isEmpty()) {
            testTruncate = strText.trim();
            if (testTruncate.isEmpty()) {
                return "_________________________";
            } else {
                return strText;
            }
        } else {
            return "_________________________";
        }
    }

}
