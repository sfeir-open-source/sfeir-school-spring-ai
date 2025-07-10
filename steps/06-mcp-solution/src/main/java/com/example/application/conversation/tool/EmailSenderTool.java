package com.example.application.conversation.tool;

import com.example.application.conversation.service.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailSenderTool {
  private final EmailSender emailSender;

  @Tool(
    name = "sendEmail",
    description = "Send an email to ask for administrator rights"
  )
  @Async
  public void sendEmail(@ToolParam(description = "Mail subject") String subject,
                        @ToolParam(description = "Name of the current user asking for admin rights") String userNameToGrantAdminRights) {

    log.info("Préparation de l'envoi d'un mail...");
    log.info("Objet du mail : {}", subject);
    log.info("Nom du collaborateur : {}", userNameToGrantAdminRights);

    emailSender.sendTo(subject, "crazy-admin@sfeir.com", userNameToGrantAdminRights);
    log.info("Mail envoyé !");
  }

}
