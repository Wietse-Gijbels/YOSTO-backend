package com.yosto.yostobackend.email;

import java.io.IOException;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public void sendTextEmail(String to1, String subject1, String content1) throws IOException {
        Email from = new Email("ucllyosto@gmail.com");
        Email to = new Email(to1);
        Content content = new Content("text/plain", content1);
        Mail mail = new Mail(from, subject1, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        logger.info(response.getBody());
    }

    public void sendHtmlEmail(String to1, String subject1, String geschenkCategorieNaam, String geschenkCode) throws IOException {
        Email from = new Email("ucllyosto@gmail.com");
        Email to = new Email(to1);

        String emailContent = String.format(
                "<html>" +
                        "<body>" +
                        "<h1>Gefeliciteerd met uw nieuwe geschenk!</h1>" +
                        "<p>Beste,</p>" +
                        "<p>Uw reward ligt klaar in uw mailbox! Hier zijn de details van uw gekozen geschenk:</p>" +
                        "<ul>" +
                        "<li><strong>Geschenk:</strong> %s</li>" +
                        "<li><strong>Code:</strong> %s</li>" +
                        "</ul>" +
                        "<p>Geniet ervan en blijf doorsparen voor uw volgende geschenken!</p>" +
                        "<p>Met vriendelijke groet,<br/>Het Yosto-team</p>" +
                        "</body>" +
                        "</html>",
                geschenkCategorieNaam,
                geschenkCode
        );

        Content content = new Content("text/html", emailContent);
        Mail mail = new Mail(from, subject1, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        logger.info(response.getBody());
    }
}
