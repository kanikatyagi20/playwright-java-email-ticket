package com.playwright.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String to, String subject, String body, String attachmentPath) {

        final String username = "xsm432@gmail.com";
        final String password = "ameiumexjbsmidta";

        // SMTP server configuration
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create session with authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Create message part for the email body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Create multipart object and add body part to it
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Add attachment if provided
            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(new File(attachmentPath));
                multipart.addBodyPart(attachmentPart);
            }

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("✅ Email sent successfully to " + to);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }
}
