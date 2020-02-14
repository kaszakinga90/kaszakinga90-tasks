package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    @Autowired
    private ScheduledMailService scheduledMailService;

    /*
    public void send(final Mail mail) {
        LOGGER.info("Email preparation has started");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has been send.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }
     */

    public void send(final Mail mail, final boolean infoMail) {
        LOGGER.info("Email preparation has started");
        try {
            if (infoMail)
                javaMailSender.send(mimeInfoMessage(mail));
            else
                javaMailSender.send(createMimeScheduledMessage(mail));
            LOGGER.info("Email has been send.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

 //   private MimeMessagePreparator createMimeMessage(final Mail mail) {
    private MimeMessagePreparator createMimeScheduledMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private MimeMessagePreparator mimeInfoMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(scheduledMailService.scheduledMail(mail.getMessage()), true);
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        Optional.ofNullable(mail.getToCc()).ifPresent(mailMessage::setCc);
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
