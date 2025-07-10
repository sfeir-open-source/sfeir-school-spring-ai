package com.example.application.conversation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {
  private final JavaMailSender emailSender;

  public void sendTo(String subject, String to, String name) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("you@sfeir.com");
    message.setTo(to);
    message.setSubject(subject);
    message.setText("Bonjour, pourriez-vous accorder les droits administrateur à " + name + " ?\n\nMerci d'avance !");
    emailSender.send(message);
  }
}
