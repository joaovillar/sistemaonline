package com.jornada.server.framework;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jornada.server.classes.UsuarioServer;
import com.jornada.shared.classes.Usuario;

public class EmailFrameWork {

    public void sendMailByUserId(ArrayList<Integer> userList, String subject, String content) {

        final String username = "xxxxx@xxxx";
        final String password = "xxxxxx";

        //ArrayList<String> emailList = EmailServer.getEmailListByUserId(userList);
        ArrayList<Usuario> listUsuario = new ArrayList<Usuario>(); 
        for(int i = 0;i<userList.size();i++){
            Usuario usuario = UsuarioServer.getUsuarioPeloId(userList.get(i));
            listUsuario.add(usuario);
        }

//        String emails = "";
//        boolean first = true;

        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        
        String aux = content;
        for (Usuario user : listUsuario) {
            try {
                
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                
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
}
