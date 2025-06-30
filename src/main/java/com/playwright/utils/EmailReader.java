package com.playwright.utils;

import jakarta.mail.*;
import jakarta.mail.search.SubjectTerm;

import java.util.*;

public class EmailReader {

    public static List<Map<String, String>> readEmailBySubject(String targetSubject) {
        final String username = "xsm432@gmail.com";
        final String password = "ameiumexjbsmidta";

        List<Map<String, String>> emailList = new ArrayList<>();

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.enable", "true");

        try {
            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(new SubjectTerm(targetSubject));
            System.out.println("🔍 Found " + messages.length + " email(s) with subject: " + targetSubject);

            for (Message message : messages) {
                Map<String, String> emailMap = new LinkedHashMap<>();

                emailMap.put("from", getAddressString(message.getFrom()));
                emailMap.put("to", getAddressString(message.getRecipients(Message.RecipientType.TO)));
                emailMap.put("cc", getAddressString(message.getRecipients(Message.RecipientType.CC)));
                emailMap.put("subject", message.getSubject());
                emailMap.put("sentDate", String.valueOf(message.getSentDate()));
                emailMap.put("content", getTextFromMessage(message));

                emailList.add(emailMap);
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to read emails: " + e.getMessage());
        }

        return emailList;
    }

    private static String getAddressString(Address[] addresses) {
        if (addresses == null) return "";
        List<String> list = new ArrayList<>();
        for (Address addr : addresses) {
            list.add(addr.toString());
        }
        return String.join(", ", list);
    }

    private static String getTextFromMessage(Message message) throws Exception {
        Object content = message.getContent();
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart part = multipart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) continue;

                if (part.isMimeType("text/plain")) {
                    return (String) part.getContent();
                } else if (part.isMimeType("text/html")) {
                    return (String) part.getContent(); // optionally strip HTML
                }
            }
        }
        return "⚠️ Unable to extract content.";
    }

    // For Verification
    public static void main(String[] args) {
        String subject = "Incident REQ000002925012 / INC000002561169 has been logged";
        List<Map<String, String>> emails = readEmailBySubject(subject);
        System.out.println("To: " + emails.get(0).get("to"));
    }
}
