package com.playwright.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailSender {
    public static void sendTestMail(String to, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.testmail.com");
        props.put("mail.smtp.port", "25");

        Session session = Session.getDefaultInstance(props);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("sender@testmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);
    }
}
