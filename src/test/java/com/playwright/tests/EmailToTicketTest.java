package com.playwright.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.EmailToTicketTestPage;
import com.playwright.utils.ConfigReader;
import com.playwright.utils.EmailSender;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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
    public void testEmailCreatesTicket() {
        EmailToTicketTestPage.loadInstanceData("TestingProd");
        emailToTicketTestPage = new EmailToTicketTestPage(page);
        emailToTicketTestPage.login();
        emailToTicketTestPage.emailToTicketConfiguration();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);

        String randomId = UUID.randomUUID().toString().substring(0, 20);

        String subjectForEmail = "Testing Ticket - " + randomId + timestamp;
        String emailBody = "Test description for ticket: " + randomId + " at " + timestamp;
        String attachmentPath = "src/test/resources/JIRA.png";
        String ticketNumber = emailToTicketTestPage.sentEmailAndGetTicketNumber(subjectForEmail, emailBody,attachmentPath);
        String[] parts = ticketNumber.split("\n");
        String[] ticketParts = parts[0].split("-");
        String reqNumber = ticketParts[0];
        String incNumber = ticketParts[1];

        String ticketSubmitMail = "Incident " + reqNumber + " / " + incNumber + " has been logged";

        List<Map<String, String>> mailReceived = emailToTicketTestPage.validateNotificationsReceived(ticketSubmitMail);
        System.out.println("From: " + mailReceived.get(0).get("from"));
        System.out.println("To: " + mailReceived.get(0).get("to"));
        System.out.println("CC: " + mailReceived.get(0).get("cc"));
        System.out.println("Subject: " + mailReceived.get(0).get("subject"));
        System.out.println("Sent Date: " + mailReceived.get(0).get("sentDate"));
        System.out.println("Content: " + mailReceived.get(0).get("content"));
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
