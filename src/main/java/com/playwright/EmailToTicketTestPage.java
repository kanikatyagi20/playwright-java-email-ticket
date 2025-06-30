package com.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class EmailToTicketTestPage {
    private final Page page;
    private final ExcelReader excelReader = new ExcelReader();
    private Map<String, String> instanceData;

    private Locator inputUserEmail;
    private Locator nextButton;
    private Locator inputUsername;
    private Locator inputPassword;
    private Locator loginButton;
    private Locator appMenuButton;
    private Locator searchInput;
    private Locator mailboxRow0;
    private Locator mailboxCell;
    private Locator mailboxEditButton;
    private Locator mailboxActionsButton;
    private Locator companyInput;
    private Locator mailboxInput;
    private Locator providerDropdown;
    private Locator authTypeDropdown;
    private Locator statusDropdown;
    private Locator saveButton;
    private Locator okButton;
    private Locator settingsTab;
    private Locator mailboxEmailInput;
    private Locator mailboxPasswordInput;
    private Locator mailboxSaveButton;

    public EmailToTicketTestPage(Page page) {
        this.page = page;
        initializeLocators();
    }

    private void initializeLocators() {
        inputUserEmail = page.locator("input[name='user_email']");
        nextButton = page.locator("text=NEXT");
        inputUsername = page.locator("#username");
        inputPassword = page.locator("#password");
        loginButton = page.locator("#kc-login");
        appMenuButton = page.locator("a[title='Application Menu']");
        searchInput = page.locator("input[id='search']");
        mailboxRow0 = page.locator("div#row-0");
        mailboxCell = mailboxRow0.locator("div[data-column-id='2']");
        mailboxEditButton = page.locator("a[title='Edit']");
        mailboxActionsButton = page.locator("a[title='Mailbox Actions']");
        companyInput = page.locator("input[placeholder='Please choose...']");
        mailboxInput = page.locator("input[name='MAILBOX_ID']");
        providerDropdown = page.locator("select[name='MAILBOX_PROVIDER']");
        authTypeDropdown = page.locator("select[name='AUTH_TYPE']");
        statusDropdown = page.locator("select[name='STATUS']");
        saveButton = page.locator("a[title='Save']");
        okButton = page.locator("text=OK");
        settingsTab = page.locator("text=Settings");
        mailboxEmailInput = page.locator("#email");
        mailboxPasswordInput = page.locator("#password");
        mailboxSaveButton = page.locator("text=Save");
    }

    public void loadInstanceData(String instanceName) {
        List<Map<String, String>> rows = excelReader.getDetailsForInstance(instanceName);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("No Excel data found for instance: " + instanceName);
        }
        this.instanceData = rows.get(0);
    }

    public void Login() {
        if (instanceData == null) throw new IllegalStateException("Instance data not loaded.");

        String url = instanceData.get("url");
        String user = instanceData.get("username");
        String pass = instanceData.get("password");

        page.navigate(url);
        page.reload();

        if (inputUserEmail.count() > 0) {
            inputUserEmail.fill(user);
        }

        nextButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        nextButton.click();

        inputUsername.fill(user);
        inputPassword.fill(pass);
        loginButton.click();

        page.waitForLoadState(LoadState.LOAD);
    }

    public void MailBoxConfiguration() {
        if (instanceData == null) throw new IllegalStateException("Instance data not loaded.");

        String company = instanceData.get("company_name");
        String provider = instanceData.get("mailbox_provider");
        String authType = instanceData.get("auth_type");
        String status = instanceData.get("status");
        String expectedMailbox = instanceData.get("mailbox_email");

        appMenuButton.click();
        page.fill(".form-control", "Mailbox");
        page.click("text=Mailbox Configuration");
        page.waitForLoadState(LoadState.LOAD);

        searchInput.fill(expectedMailbox);
        mailboxRow0.waitFor();
        String mailbox = mailboxCell.innerText().trim();

        if (!mailbox.isEmpty()) {
            mailboxRow0.click();
            mailboxEditButton.click();
        } else {
            mailboxActionsButton.waitFor();
            mailboxActionsButton.click();
        }

        companyInput.fill("");
        companyInput.fill(company);
        page.click("a[id='-item-0']");

        mailboxInput.fill("");
        mailboxInput.fill(expectedMailbox);

        providerDropdown.selectOption("");
        providerDropdown.selectOption(provider);
        authTypeDropdown.selectOption("");
        authTypeDropdown.selectOption(authType);
        statusDropdown.selectOption("");
        statusDropdown.selectOption(status);

        saveButton.click();
        okButton.click();

        settingsTab.click();
        mailboxEmailInput.fill(instanceData.get("mailbox_email"));
        mailboxPasswordInput.fill(instanceData.get("mailbox_password"));
        mailboxSaveButton.click();
    }
}
