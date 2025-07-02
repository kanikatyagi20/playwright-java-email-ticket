package com.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.utils.ExcelReader;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.util.StringUtil;

import java.util.List;
import java.util.Map;

public class EmailToTicketTestPage {
    private final Page page;
    private final ExcelReader excelReader = new ExcelReader();
    private static Map<String, String> instanceData;

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
    private Locator mailboxConfigCreateButton;
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
    private Locator editCredentials;
    private Locator noRecordsText;
    private Locator popUpText;
    private Locator addCredentials;
    private Locator refreshToken;
    private Locator applicationId;
    private Locator clientId;
    private Locator secretKey;
    private Locator displayName;
    private Locator objectId;
    private Locator saveCredentials;

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
        noRecordsText = page.locator("text=There are no records to display");
        mailboxEditButton = page.locator("a[title='Edit']");
        mailboxConfigCreateButton = page.locator("a[title='Mailbox Actions'][href='/mailboxConfig']");
        companyInput = page.locator("input[placeholder='Please choose...']");
        mailboxInput = page.locator("input[name='MAILBOX_ID']");
        providerDropdown = page.locator("select[name='MAILBOX_PROVIDER']");
        authTypeDropdown = page.locator("select[name='AUTH_TYPE']");
        statusDropdown = page.locator("select[name='STATUS']");
        saveButton = page.locator("a[title='Save']");
        okButton = page.locator("//button[contains(@class, 'swal2-confirm') and normalize-space(text())='OK']");
        settingsTab = page.locator("text=Settings");
        mailboxEmailInput = page.locator("#email");
        mailboxPasswordInput = page.locator("#password");
        mailboxSaveButton = page.locator("text=Save");
        editCredentials = page.locator("button[title='Edit']");
        popUpText = page.locator("h2[class='swal2-title']");
        addCredentials = page.locator("button[title='Add Credentials']");
        refreshToken = page.locator("input[name='SX_INBOUND']");
        applicationId = page.locator("input[name='APPLICATION_ID']");
        clientId = page.locator("input[name='TENANT_ID']");
        secretKey = page.locator("input[name='SECRET_KEY']");
        objectId = page.locator("input[name='OBJECT_ID']");
        displayName = page.locator("input[name='DISPLAY_NAME']");
        saveCredentials = page.locator("text=Save");
    }

    public static void loadInstanceData(String instanceName) {
        List<Map<String, String>> rows = ExcelReader.getDetailsForInstance(instanceName);
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("No Excel data found for instance: " + instanceName);
        }
        instanceData = rows.get(0);
    }

    public void login() {
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

    public void searchMailbox(String expectedMailbox) {
        searchInput.fill(expectedMailbox);

        if (noRecordsText.count() > 0) {
            mailboxConfigCreateButton.waitFor();
            mailboxConfigCreateButton.click();
        } else {
            mailboxRow0.click();
            mailboxEditButton.click();
        }
    }

    public void mailBoxConfiguration() {
        if (instanceData == null) throw new IllegalStateException("Instance data not loaded.");

        String company = instanceData.get("company_name");
        String vendor = instanceData.get("vendor");
        String authType = instanceData.get("auth_type");
        String status = instanceData.get("status");
        String expectedMailbox = instanceData.get("mailbox_email");

        appMenuButton.click();
        page.fill(".form-control", "Mailbox");
        page.click("text=Mailbox Configuration");
        page.waitForLoadState(LoadState.LOAD);

        searchMailbox(expectedMailbox);

        companyInput.fill(company);
        page.click("a[id='-item-0']");
        mailboxInput.fill(expectedMailbox);
        providerDropdown.selectOption(vendor);
        authTypeDropdown.selectOption(authType);
        statusDropdown.selectOption(status);

        saveButton.click();

        String popUpMessage = popUpText.innerText().trim();
        okButton.click();

        switch (popUpMessage) {
            case "No changes detected in the provided payload" -> addEditCredentials(vendor);
            case "Record saved successfully" -> {
                searchMailbox(expectedMailbox);
                addEditCredentials(vendor);
            }
            case "Mailbox already exists for a different company" ->
                    System.out.println("✅ Mailbox configuration exist for different company.. provide correct info");
        }
    }

    public void addEditCredentials(String vendor) {
        if (instanceData == null || instanceData.isEmpty()) {
            throw new IllegalStateException("Instance data is not loaded. Call loadInstanceData() first.");
        }
        page.waitForLoadState(LoadState.LOAD);

        // Click on Add or Edit button
        (addCredentials.count() > 0 ? addCredentials : editCredentials).click();
        page.waitForLoadState(LoadState.LOAD);

        // Fill common credentials
        applicationId.fill(instanceData.get("application_Id"));
        clientId.fill(instanceData.get("tenant_id"));
        secretKey.fill(instanceData.get("secret_key"));

        // Fill vendor-specific fields
        switch (vendor.toLowerCase()) {
            case "outlook" -> {
                objectId.fill(instanceData.get("object_id"));
                displayName.fill(instanceData.get("display_name"));
            }
            case "gmail" -> refreshToken.fill(instanceData.get("sx_inbound"));
            default -> throw new IllegalArgumentException("Unsupported vendor: " + vendor);
        }

        saveCredentials.click();
    }
}
