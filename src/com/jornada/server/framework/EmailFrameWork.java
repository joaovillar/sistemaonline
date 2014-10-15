package com.jornada.server.framework;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.jornada.ConfigJornada;
import com.jornada.server.classes.CursoServer;
import com.jornada.server.classes.DocumentoServer;
import com.jornada.server.classes.UsuarioServer;
import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Documento;
import com.jornada.shared.classes.RelDocumentoUsuario;
import com.jornada.shared.classes.Usuario;

public class EmailFrameWork {
    
//    private static final String username = "paisonline.ci.suporte@gmail.com";
//    private static final String password = "eddygdnqlocydttl";
  public static final String USER_NAME = ConfigJornada.getProperty("config.email.user");
  public static final String PASSWORD = ConfigJornada.getProperty("config.email.password");
  public static final int MAX_LIMIT_SEND_APP = Integer.parseInt(ConfigJornada.getProperty("config.email.max.send.app"));
  public static final int MAX_LIMIT_SEND_WEB = Integer.parseInt(ConfigJornada.getProperty("config.email.max.send.web"));
    

  public void sendOcorrenciaPorEmail(ArrayList<Integer> userList, String subject, String content) {

        ArrayList<Usuario> listUsuario = new ArrayList<Usuario>(); 
        for(int i = 0;i<userList.size();i++){
            Usuario usuario = UsuarioServer.getUsuarioPeloId(userList.get(i));
            listUsuario.add(usuario);
        }
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });
        
        
        String aux = content;
        for (Usuario user : listUsuario) {
            try {
                
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(USER_NAME));
                
//                content = content + "<br><a href='suporte/doc/rematricula.jsp?user="+user.getPrimeiroNome() + " "+ user.getSobreNome()+"' target='_blank'>clique aqui para iniciar o download</a>.";
                content = content.replace("?user=parameter", "?user="+user.getPrimeiroNome() + " "+ user.getSobreNome()+"&cpf="+user.getCpf()+"&rg="+user.getRg());

                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
                message.setSubject(subject);
                message.setContent(content, "text/html; charset=utf-8");
                Transport.send(message);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            
            content = aux;

        }
        
    }

  public String sendMailByUserIdParaDocumentos(int idCurso, int idDocumento, ArrayList<Integer> userList, String url) {

        
        String strStatus="success";
        ArrayList<Usuario> listUsuario = new ArrayList<Usuario>(); 
        for(int i = 0;i<userList.size();i++){
            Usuario usuario = UsuarioServer.getUsuarioPeloId(userList.get(i));
            listUsuario.add(usuario);
        }
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });
        Documento documento = DocumentoServer.getDocumento(idDocumento);
        Curso curso = CursoServer.getCurso(idCurso);
//        String folder = ConfigJornada.getProperty("config.download.doc");
        for (Usuario pai : listUsuario) {
            try {

                RelDocumentoUsuario relDocUser = DocumentoServer.getRelDocumentoUsuarios(idCurso, idDocumento, pai.getIdUsuario()).get(0);    
                
                if (relDocUser.isEmailSent() == false) {
                    if(pai.getEmail()==null && pai.getEmail().isEmpty())
                    {
                        
                    }else{
                        ArrayList<Usuario> filhos = UsuarioServer.getFilhoDoPaiPorCurso(pai, idCurso);
                        
                        for (int i = 0; i < filhos.size(); i++) {

                            Usuario filho = filhos.get(i);
                            String subject = documento.getEmailSubject();
                            String content = documento.getEmailContent();
                            String nomeAluno = filho.getPrimeiroNome() + " " + filho.getSobreNome();
                            
                            String strEnderecoWord = DocumentoServer.criarDocumentoFilho(filho, url, documento.getNomeFisicoDocumento());

                            content = content.replace("nome_pai", pai.getPrimeiroNome() + " " + pai.getSobreNome());
                            content = content.replace("nome_aluno", nomeAluno);
                            content = content.replace("nome_documento", documento.getNomeDocumento());
                            content = content.replace("link_documento", strEnderecoWord);

                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(USER_NAME));

                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pai.getEmail()));
                            message.setSubject(subject);
                            message.setContent(content, "text/html; charset=utf-8");
                            Transport.send(message);
                            DocumentoServer.setEmailSent(idCurso, idDocumento, pai.getIdUsuario(), true);
                            strStatus="success";
                            System.out.println("Deu certo");
                        }
                    }
                }
               

            } catch (MessagingException e) {
                System.out.println("deu tudo errado");
                strStatus = "Curso:"+curso.getNome()+" Pai:"+pai.getPrimeiroNome()+" "+ pai.getSobreNome() + "Erro :"+e.getMessage();
                throw new RuntimeException(e);
            }
        }
        return strStatus;
    }
    
  public String sendMailByUserIdParaDocumentosSemCurso(int idCurso, int idDocumento, ArrayList<Integer> userList, String url) {

        
        String strStatus="success";
        ArrayList<Usuario> listUsuario = new ArrayList<Usuario>(); 
        for(int i = 0;i<userList.size();i++){
            Usuario usuario = UsuarioServer.getUsuarioPeloId(userList.get(i));
            listUsuario.add(usuario);
        }
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });
        Documento documento = DocumentoServer.getDocumento(idDocumento);
//        Curso curso1 = CursoServer.getCurso(idCurso);
//        String folder = ConfigJornada.getProperty("config.download.doc");
        for (Usuario pai : listUsuario) {
            try {

                RelDocumentoUsuario relDocUser = DocumentoServer.getRelDocumentoUsuariosSemCurso(idDocumento, pai.getIdUsuario()).get(0);    
                
                if (relDocUser.isEmailSent() == false) {
                    if(pai.getEmail()==null && pai.getEmail().isEmpty())
                    {
                        
                    }else{
                        ArrayList<Usuario> filhos = UsuarioServer.getFilhoDoPaiPorCurso(pai, idCurso);
                        
                        for (int i = 0; i < filhos.size(); i++) {

                            Usuario filho = filhos.get(i);
                            String subject = documento.getEmailSubject();
                            String content = documento.getEmailContent();
                            String nomeAluno = filho.getPrimeiroNome() + " " + filho.getSobreNome();
                            
                            String strEnderecoWord = DocumentoServer.criarDocumentoFilho(filho, url, documento.getNomeFisicoDocumento());

                            content = content.replace("nome_pai", pai.getPrimeiroNome() + " " + pai.getSobreNome());
                            content = content.replace("nome_aluno", nomeAluno);
                            content = content.replace("nome_documento", documento.getNomeDocumento());
                            content = content.replace("link_documento", strEnderecoWord);

                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(USER_NAME));

                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pai.getEmail()));
                            message.setSubject(subject);
                            message.setContent(content, "text/html; charset=utf-8");
                            Transport.send(message);
                            DocumentoServer.setEmailSent(idDocumento, pai.getIdUsuario(), true);
                            strStatus="success";
                            System.out.println("Envio de Email Deu certo usuario:"+pai.getPrimeiroNome() +" "+pai.getSobreNome());
                        }
                    }
                }
               

            } catch (MessagingException e) {
                System.out.println("Envio de email deu errado");
//                strStatus = "Curso:"+curso.getNome()+" Pai:"+pai.getPrimeiroNome()+" "+ pai.getSobreNome() + "Erro :"+e.getMessage();
                throw new RuntimeException(e);
            }
        }
        return strStatus;
    }
    
  public String sendDocumentosPorEmailParaOsPaisDosAlunos(int idCurso, int idDocumento, ArrayList<Integer> userList, String url) {

        
        String strStatus="success";
        ArrayList<Usuario> listUsuario = new ArrayList<Usuario>(); 
        for(int i = 0;i<userList.size();i++){
            Usuario usuario = UsuarioServer.getUsuarioPeloId(userList.get(i));
            listUsuario.add(usuario);
        }
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });
        Documento documento = DocumentoServer.getDocumento(idDocumento);
        Curso curso = CursoServer.getCurso(idCurso);

        for (Usuario aluno : listUsuario) {
            try {
                
                RelDocumentoUsuario relDocUser = DocumentoServer.getRelDocumentoUsuariosSemCurso(idDocumento, aluno.getIdUsuario()).get(0);
                
                
                if (relDocUser.isEmailSent() == false) {

                    ArrayList<Usuario> pais = UsuarioServer.getTodosOsPaisDoAluno(aluno.getIdUsuario());

                    boolean enviouParaPai=false;
                    for (int i = 0; i < pais.size(); i++) {
                        Usuario pai = pais.get(i);
                        if (pai.getEmail() != null && !pai.getEmail().isEmpty()) {

                            String subject = documento.getEmailSubject();
                            String content = documento.getEmailContent();
                            String nomePai = pai.getPrimeiroNome() + " " + pai.getSobreNome();

                            String strEnderecoWord = DocumentoServer.criarDocumentoFilho(aluno, url, documento.getNomeFisicoDocumento());

                            content = content.replace("nome_aluno", aluno.getPrimeiroNome() + " " + aluno.getSobreNome());
                            content = content.replace("nome_pai", nomePai);
                            content = content.replace("nome_documento", documento.getNomeDocumento());
                            content = content.replace("link_documento", strEnderecoWord);

                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(USER_NAME));

                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pai.getEmail()));
                            message.setSubject(subject);
                            message.setContent(content, "text/html; charset=utf-8");
                            Transport.send(message);
                            strStatus = "success";
                            System.out.println("Deu certo");
                            enviouParaPai=true;
                        }
                    }
                    if(enviouParaPai){
                        DocumentoServer.setEmailSent(idCurso, idDocumento, aluno.getIdUsuario(), true);
                    }

                }               

            } catch (MessagingException e) {
                System.out.println("deu tudo errado");
                strStatus = "Curso:"+curso.getNome()+" Pai:"+aluno.getPrimeiroNome()+" "+ aluno.getSobreNome() + "Erro :"+e.getMessage();
                throw new RuntimeException(e);
            }
        }
        return strStatus;
    }
  
  public String sendDocumentosPorEmailParaOsPaisDosAlunosSemCurso(int idCurso, int idDocumento, ArrayList<Integer> userList, String url) {

      
      String strStatus="success";
      ArrayList<Usuario> listUsuario = new ArrayList<Usuario>(); 
      for(int i = 0;i<userList.size();i++){
          Usuario usuario = UsuarioServer.getUsuarioPeloId(userList.get(i));
          listUsuario.add(usuario);
      }
      
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");

      Session session = Session.getInstance(props, new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(USER_NAME, PASSWORD);
          }
      });
      Documento documento = DocumentoServer.getDocumento(idDocumento);
//      Curso curso = CursoServer.getCurso(idCurso);

      for (Usuario aluno : listUsuario) {
          try {
              
              RelDocumentoUsuario relDocUser = DocumentoServer.getRelDocumentoUsuariosSemCurso(idDocumento, aluno.getIdUsuario()).get(0);
              
              
              if (relDocUser.isEmailSent() == false) {

                  ArrayList<Usuario> pais = UsuarioServer.getTodosOsPaisDoAluno(aluno.getIdUsuario());

                  boolean enviouParaPai=false;
                  for (int i = 0; i < pais.size(); i++) {
                      Usuario pai = pais.get(i);
                      if (pai.getEmail() != null && !pai.getEmail().isEmpty()) {

                          String subject = documento.getEmailSubject();
                          String content = documento.getEmailContent();
                          String nomePai = pai.getPrimeiroNome() + " " + pai.getSobreNome();

                          String strEnderecoWord = DocumentoServer.criarDocumentoFilho(aluno, url, documento.getNomeFisicoDocumento());

                          content = content.replace("nome_aluno", aluno.getPrimeiroNome() + " " + aluno.getSobreNome());
                          content = content.replace("nome_pai", nomePai);
                          content = content.replace("nome_documento", documento.getNomeDocumento());
                          content = content.replace("link_documento", strEnderecoWord);

                          Message message = new MimeMessage(session);
                          message.setFrom(new InternetAddress(USER_NAME));

                          message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pai.getEmail()));
                          message.setSubject(subject);
                          message.setContent(content, "text/html; charset=utf-8");
                          Transport.send(message);
                          strStatus = "success";
                          System.out.println("Deu certo");
                          enviouParaPai=true;
                      }
                  }
                  if(enviouParaPai){
                      DocumentoServer.setEmailSent(idDocumento, aluno.getIdUsuario(), true);
                  }

              }               

          } catch (MessagingException e) {
              System.out.println("deu tudo errado");
//              strStatus = "Curso:"+curso.getNome()+" Pai:"+aluno.getPrimeiroNome()+" "+ aluno.getSobreNome() + "Erro :"+e.getMessage();
              throw new RuntimeException(e);
          }
      }
      return strStatus;
  }  
    

    public String sendEmailAlunosPaisProfessores(ArrayList<Integer> listUser, String subject, String content, String strFileAddress, String strFileName) {
        String status = "";

        ArrayList<Usuario> listUsuario = new ArrayList<Usuario>();
        ArrayList<String> listEmails = new ArrayList<String>();
        ArrayList<String> listEmailsSplit = new ArrayList<String>();

        for (int i = 0; i < listUser.size(); i++) {
            Usuario usuario = UsuarioServer.getUsuarioPeloId(listUser.get(i));
            listUsuario.add(usuario);
            if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
                listEmails.add(usuario.getEmail());
            }
        }

        listEmailsSplit = MpUtilServer.getMaxTestCaseQueryRowsAllowed(listEmails, MAX_LIMIT_SEND_APP);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });
        
        try {

            for (int i = 0; i < listEmailsSplit.size(); i++) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(USER_NAME));
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(listEmailsSplit.get(i)));
                message.setSubject(subject);               
                
             // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();

                // Now set the actual message
//                messageBodyPart.setText(content);
                messageBodyPart.setContent(content, "text/html; charset=utf-8");


                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment
                String fileNameAddress = strFileAddress;
                if (fileNameAddress != null && !fileNameAddress.isEmpty()) {
                    
                    File f = new File(fileNameAddress);
                    if(!f.exists()){
                        String strDestinationAddress = ConfigJornada.getProperty("config.download") + ConfigJornada.getProperty("config.download.image"); 
                        fileNameAddress = strDestinationAddress+fileNameAddress;
                    }
                    messageBodyPart = new MimeBodyPart();                    
                    DataSource source = new FileDataSource(fileNameAddress);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(strFileName);
                    multipart.addBodyPart(messageBodyPart);
                }

                // Send the complete message parts
                message.setContent(multipart);    
                
                
                Transport.send(message);
                status = "success";

            }
        } catch (MessagingException e) {
            status=null;
            System.out.println("deu tudo errado");
            throw new RuntimeException(e);            
        }

        return status;
    }
}
