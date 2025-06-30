package com.playwright.tests;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.utils.ConfigReader;
import org.testng.annotations.*;


public class EmailToTicketTest {
    Playwright playwright;
    Browser browser;
    Page page;

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
        String url = ConfigReader.get("instance.url");
        String user = ConfigReader.get("instance.user");
        String pass = ConfigReader.get("instance.pass");
        String company = ConfigReader.get("company.name");
        String provider = ConfigReader.get("mailbox.provider");
        String authType = ConfigReader.get("auth.type");
        String status = ConfigReader.get("status");
        page.navigate(url);
        page.reload();
        page.fill("input[name='user_email']", user);
        // Step 3: Wait for NEXT button to become enabled
        Locator nextButton = page.locator("text=NEXT");
        nextButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        // Step 4: Click the button
        nextButton.click();
        page.fill("#username", user);
        page.fill("#password", pass);
        // Click the Sign In button
        page.click("#kc-login");
        // Wait for full load
        page.waitForLoadState(LoadState.LOAD);
        page.click("a[title='Application Menu']");
        page.fill(".form-control", "Mailbox");
        page.click("text=Mailbox Configuration");
        page.waitForLoadState(LoadState.LOAD);
        String expectedMailbox = ConfigReader.get("mailbox.email");
        page.fill("input[id='search']",expectedMailbox);
        Locator row0 = page.locator("div#row-0");
        row0.waitFor();  // Wait for row to appear

        Locator mailboxCell = row0.locator("div[data-column-id='2']");
        String mailbox = mailboxCell.innerText().trim();

        if (!mailbox.isEmpty()) {
            row0.click();
            page.click("a[title='Edit']");
        } else {
            page.waitForSelector("a[title='Mailbox Actions']");
            page.click("a[title='Mailbox Actions']");
        }
        // Clear Company field
        Locator companyInput = page.locator("input[placeholder='Please choose...']");
        companyInput.fill("");  // clear it
//        companyInput.press("Backspace");  // clear autofill hint if any
        //fill
        page.fill("input[placeholder='Please choose...']", company);
        page.click("a[id='-item-0']");

// Clear Mailbox field
        page.fill("input[name='MAILBOX_ID']", "");  // clear
//        page.press("input[name='MAILBOX_ID']", "Backspace");  // optional fallback
        page.fill("input[name='MAILBOX_ID']", expectedMailbox);
// Reset Dropdowns by selecting first <option> (assumed to be "Select" or blank)
        page.selectOption("select[name='MAILBOX_PROVIDER']", "");  // reset
        page.selectOption("select[name='MAILBOX_PROVIDER']", provider);
        page.selectOption("select[name='AUTH_TYPE']", "");         // reset
        page.selectOption("select[name='AUTH_TYPE']", authType);
        page.selectOption("select[name='STATUS']", "");            // reset
        page.selectOption("select[name='STATUS']", status);
        page.click("a[title='Save']");
        page.click("text=OK");

        // Config mailbox
        page.click("text=Settings");
        page.fill("#email", ConfigReader.get("mailbox.email"));
        page.fill("#password", ConfigReader.get("mailbox.password"));
        page.click("text=Save");

        // Simulate email (or use EmailSender.sendTestMail(...) here)

        // Navigate to tickets
        page.click("text=Tickets");
        page.fill("#search", "Test Subject");
        page.press("#search", "Enter");

        // Assertion logic here
        assert page.locator(".ticket-id").isVisible();
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
