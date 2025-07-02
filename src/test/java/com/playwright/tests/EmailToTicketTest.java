package com.playwright.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.EmailToTicketTestPage;
import com.playwright.utils.ConfigReader;
import org.testng.annotations.*;

import java.io.IOException;


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

        // email sender
        //store subject
        //subject - search - WIB-inc
        // store INC ticket
        //INC - email me search
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
