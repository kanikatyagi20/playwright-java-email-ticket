package com.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class EmailToTicketTestPage {
    Page page;

    ExcelReader excelReader = new ExcelReader();
    Map<String, String> instanceData;

    // 🔄 NEW: Load instance data once, and reuse in all methods
    public void loadInstanceData(String instanceName) {
        List<Map<String, String>> rows = excelReader.getDetailsForInstance(instanceName);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("No Excel data found for instance: " + instanceName);
        }
        this.instanceData = rows.get(0);  // assuming single row match
    }

    public void Login() {
        if (instanceData == null) {
            throw new IllegalStateException("Instance data not loaded. Call loadInstanceData() first.");
        }

        // Get values from Excel
        String url = instanceData.get("url");
        String user = instanceData.get("username");
        String pass = instanceData.get("password");

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
    }

    public void MailBoxConfiguration() {
        if (instanceData == null) {
            throw new IllegalStateException("Instance data not loaded. Call loadInstanceData() first.");
        }

        // Get values from Excel
        String company = instanceData.get("company_name");
        String provider = instanceData.get("mailbox_provider");
        String authType = instanceData.get("auth_type");
        String status = instanceData.get("status");

        page.click("a[title='Application Menu']");
        page.fill(".form-control", "Mailbox");
        page.click("text=Mailbox Configuration");
        page.waitForLoadState(LoadState.LOAD);

        // Read mailbox email from Excel (you can also fall back to ConfigReader if needed)
        String expectedMailbox = instanceData.get("mailbox_email");

        page.fill("input[id='search']", expectedMailbox);
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

        // Fill company field
        page.fill("input[placeholder='Please choose...']", company);
        page.click("a[id='-item-0']");

        // Clear Mailbox field
        page.fill("input[name='MAILBOX_ID']", "");  // clear
//        page.press("input[name='MAILBOX_ID']", "Backspace");  // optional fallback

        // Fill mailbox field
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
        page.fill("#email", instanceData.get("mailbox_email"));
        page.fill("#password", instanceData.get("mailbox_password"));
        page.click("text=Save");
    }
    //add credtion method
}
