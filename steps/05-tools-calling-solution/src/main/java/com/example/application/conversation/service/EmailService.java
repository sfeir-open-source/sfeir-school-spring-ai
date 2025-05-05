package com.example.application.conversation.service;


import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.ai.tool.annotation.Tool;

import java.util.*;

public class EmailService {

  private final Session session;

  public EmailService() {

    Properties prop = new Properties();
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
    prop.put("mail.smtp.port", "25");
    prop.put("mail.smtp.ssl.trust", "sandbox.smtp.mailtrap.io");

    session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("username", "password");
      }
    });
  }


  @Tool(description = "Send email to ask admin administrator rights")
  Boolean sendEmail() throws MessagingException {
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress("from@gmail.com"));
    message.setRecipients(
      Message.RecipientType.TO, InternetAddress.parse("to@gmail.com"));
    message.setSubject("Mail Subject");

    String msg = "Pouvez-vous m'accorder les droits d'administrateur sur mon poste local svp ?";

    MimeBodyPart mimeBodyPart = new MimeBodyPart();
    mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(mimeBodyPart);

    message.setContent(multipart);

    Transport.send(message);

    return true;

  }

}
