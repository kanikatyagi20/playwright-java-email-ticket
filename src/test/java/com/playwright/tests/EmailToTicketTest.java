package com.playwright.tests;

import com.microsoft.playwright.*;
import com.playwright.EmailToTicketTestPage;
import com.playwright.InstanceConfigKeys;
import com.playwright.utils.ExcelReader;
import com.playwright.utils.ExtentReportManager;
import org.testng.annotations.*;

import java.util.List;
import java.util.Map;


public class EmailToTicketTest {
    Playwright playwright;
    Browser browser;
    Page page;
    EmailToTicketTestPage emailToTicketTestPage;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        page = browser.newPage();  // ✅ this.page is now initialized properly

    }

    @Test
    public void verifyTicketCreationForExistingUserWithAttachments() {
        ExtentReportManager.initReport();
        Map<String, String> mailboxDetails = ExcelReader.getMailBoxDetails("TestingProd");
        emailToTicketTestPage = new EmailToTicketTestPage(page);

        //Login into Application
        emailToTicketTestPage.login(mailboxDetails);

        //Doing Mailbox configuration
        emailToTicketTestPage.emailToTicketConfiguration(mailboxDetails);
        //Sending email to the configured mailbox for ticket creation
        Map<String, String> mailToSent = EmailToTicketTestPage.subjectAndBodyGenerator("incident", null, null, null, null, null);
        String subjectForEmail = mailToSent.get("subject");
        String emailBody = mailToSent.get("body");
        String attachmentPath = "src/test/resources/JIRA.png";
        String ticketNumber = emailToTicketTestPage.sentEmailAndGetTicketNumber(subjectForEmail, emailBody, attachmentPath, mailboxDetails.get(InstanceConfigKeys.MAILBOX_EMAIL.getValue()));
        String[] parts = ticketNumber.split("\n");
        String[] ticketParts = parts[0].split("-");
        String reqNumber = ticketParts[0];
        String incNumber = ticketParts[1];
        String ticketSubmitMail = "Incident " + reqNumber + " / " + incNumber + " has been logged";
        List<Map<String, String>> mailReceived = emailToTicketTestPage.validateNotificationsReceived(ticketSubmitMail);
        ExtentReportManager.getTest().pass("Email is recieved with ticket number for the: " + ticketNumber);

        // Validating the received email
        System.out.println("From: " + mailReceived.get(0).get("from"));
        System.out.println("To: " + mailReceived.get(0).get("to"));
        System.out.println("CC: " + mailReceived.get(0).get("cc"));
        System.out.println("Subject: " + mailReceived.get(0).get("subject"));
        System.out.println("Sent Date: " + mailReceived.get(0).get("sentDate"));
        System.out.println("Content: " + mailReceived.get(0).get("content"));

        ExtentReportManager.flushReport();
    }


    @AfterMethod
    public void cleanup() {
        // Optional: Delete or deactivate test ticket via UI
        try {
            if (page.isVisible(".delete-button")) {
                page.click(".delete-button");
                page.click("text=Confirm");
            }
        } catch (Exception e) {
            System.out.println("No ticket to delete or cleanup failed: " + e.getMessage());
        }

        // Always close browser and playwright instance
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
