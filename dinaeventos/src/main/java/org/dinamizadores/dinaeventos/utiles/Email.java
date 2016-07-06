package org.dinamizadores.dinaeventos.utiles;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.dinamizadores.dinaeventos.dto.EmailBasico;
import org.dinamizadores.dinaeventos.dto.entradasCompleta;

import freemarker.cache.ClassTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;


public class Email {
	
	public void enviarEmail(List<entradasCompleta> listaentradas, EmailBasico datosEmail)  {
        
		ConfiguracionBasico configuracion = new ConfiguracionBasico();
		
		configuracion.setServerSMTP("smtp.gmail.com");
		configuracion.setUserSMTP("lozanozapata@gmail.com");
		configuracion.setPasswordSMTP("Zapata123");
		configuracion.setEmailSMTP("lozanozapata@gmail.com");
	
		 
        //configuracion = gestorAdmin.getConfiguracionSistema(auditoria);
 
        Session session = getSession(configuracion);
        String pie;
 
        if (datosEmail.getNombreUsuario() != null) {
            pie = "Enviado por el usuario " + datosEmail.getNombreUsuario() + " con fecha " ;
        } else {
            pie = "Enviado con fecha ";
 
        }
            Message message = new MimeMessage(session);
 
            // TODO: poner los valores de la BD antes de poner en PRO
            //message.setFrom(new InternetAddress("eudatox@sender.eutox.com"));
            //message.setFrom(new InternetAddress("rauldel@irtic.uv.es"));
            try {
				message.setFrom(new InternetAddress(configuracion.getEmailSMTP()));
			
 
            message.setSubject(datosEmail.getTitulo());
            message.setHeader("Content-Type", "text/html");
 
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(datosEmail.getMailReceptor()));
            //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(datosEmail.getMailReceptor()));
 
            if (!"".equalsIgnoreCase(datosEmail.getMailSegundoReceptor())) {
                // message.addRecipient(type, address);
                //message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("rauldel@irtic.uv.es"));
                //message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(datosEmail.getMailSegundoReceptor()));
            }
 
            //if (configuracion.getEmailsCopiaOculta().contains(";")) {
              //  for (String mail : configuracion.getEmailsCopiaOculta().split(";")) {
                //    message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(mail));
                //}
            //} else {
                //message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(configuracion.getEmailsCopiaOculta()));
                //message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse("esangar6@uv.es"));
            //}
 
            BodyPart body = new MimeBodyPart();
 
            Configuration cfg;
            cfg = new Configuration(Configuration.VERSION_2_3_22);
 
            cfg.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));
            Template template;
			template = cfg.getTemplate("mailTemplate.htm");
			
 
            Map<String, String> rootMap = new HashMap<String, String>();
            rootMap.put("Titulo", "prueba");
            //rootMap.put("Texto", datosEmail.getTexto());
            rootMap.put("Texto", "prueba");
            rootMap.put("Lineas", "");
            rootMap.put("Pie", pie);
            //rootMap.put("imgAsBase64", getImagenLogo(datosEmail.getIdEnte()));
            Writer out = new StringWriter();
            template.process(rootMap, out);
 
            body.setContent(out.toString(), "text/html; charset=utf-8");
 
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
 
            message.setContent(multipart, "text/html; charset=utf-8");
 
            
 
            	for (entradasCompleta archivo : listaentradas){
	                // create the second message part with the attachment
            		File aux = new File ("entrada-" + archivo.getUsuario().getDni() + ".pdf"); 
	                MimeBodyPart attachment = new MimeBodyPart();
	                //ByteArrayDataSource ds = new ByteArrayDataSource(arrayInputStream, "application/pdf");
	                attachment.setDataHandler(new DataHandler(new FileDataSource(aux)));
	                attachment.setFileName("entrada-" + archivo.getUsuario().getDni() + ".pdf");
	                multipart.addBodyPart(attachment);
            	
            }
 
            Transport.send(message);
 
            } catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedTemplateNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
    }
	
	private Session getSession(ConfiguracionBasico configuracion) {

 
        // TODO: poner los valores de la BD antes de poner en PRO
 
        final String username = configuracion.getUserSMTP();
        //final String username = "alertas@eutox.com";
        //final String username = "rauldel@irtic.uv.es";
        final String password = configuracion.getPasswordSMTP();
        //final String password = "L10-l20!";
        //final String password = "iuzomasi";
 
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.smtp.ssl.trust", "irtic.uv.es");
        //props.put("mail.smtp.ssl.trust", "contacts.eutox.com");
        //props.put("mail.smtp.ssl.trust", configuracion.getServerSMTP());
        //props.put("mail.smtp.host", "contacts.eutox.com");
        //props.put("mail.smtp.host", "irtic.uv.es");
        props.put("mail.smtp.host", configuracion.getServerSMTP());
        props.put("mail.smtp.port", "587");
        //props.put("mail.smtp.port", "25");
 
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
 
 
        return session;
    }

}
