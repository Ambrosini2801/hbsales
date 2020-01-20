package br.com.hbsis.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {

    private final JavaMailSender mailSender;

    @Autowired
    public MailConfig(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMailSave() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Produto");
        message.setText("O seu produto está a cmainho!");
        message.setTo("vanessa.ambrosini.silva@gmail.com");
        message.setFrom("vanessa.ambrosini.silva@gmail.com");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}