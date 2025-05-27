package com.example.application.conversation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService  {

  Logger logger = LoggerFactory.getLogger(EmailService.class);

  private final JavaMailSender emailSender;

  public EmailService(JavaMailSender emailSender){
    this.emailSender = emailSender;
  }

  @Tool(
    name = "sendEmail",
    description = "Send email to ask administrator rights"
  )
  @Async
  public void sendEmail(@ToolParam(description = "objet du mail") String subject,
                        @ToolParam(description = "nom du collaborateur") String name) {

    logger.info("objet du mail : {}", subject);
    logger.info("nom du collaborateur : {}", name);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("claudy.focan@sfeir.com");
    message.setTo("sylvain.josse85@gmail.com");
    message.setSubject(subject);
    message.setText("Bonjour, pourriez-vous accorder les droits administrateur à " + name + " ?\n\nMerci d'avance !");
    emailSender.send(message);

  }

}
