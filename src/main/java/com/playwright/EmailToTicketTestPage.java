package com.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class EmailToTicketTestPage {
    private final Page page;
    private static Map<String, String> instanceData;

    private Locator inputUserEmail;
    private Locator nextButton;
    private Locator inputUsername;
    private Locator inputPassword;
    private Locator loginButton;
    private Locator appMenuButton;
    private Locator searchInput;
    private Locator mailboxRow0;
    private Locator editButton;
    private Locator createButton;
    private Locator companyInput;
    private Locator mailboxInput;
    private Locator providerDropdown;
    private Locator authTypeDropdown;
    private Locator statusDropdown;
    private Locator saveButton;
    private Locator okButton;
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
    private Locator applicationMenuSearch;
    private Locator mailBoxConfigurationMenu;
    private Locator mailboxActions;
    private Locator mailToTicketTab;
    private Locator surveyTab;
    private Locator approvalTab;
    private Locator mailboxDropdown;
    private Locator serviceInput;
    private Locator whiteListedDomains;

    private Locator alwaysException;
    private Locator neverException;
    private Locator impact;
    private Locator urgency;
    private Locator userVerificationType;
    private Locator selectGuestUser;
    private Locator searchedCompanySelect;
    private Locator guestAndOfferingSelect;
    private Locator closeButton;

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
        noRecordsText = page.locator("text=There are no records to display");
        editButton = page.locator("a[title='Edit']");
        createButton = page.locator("a[class='myBt plus fillBtn']");
        companyInput = page.locator("input[placeholder='Please choose...']");
        mailboxInput = page.locator("input[name='MAILBOX_ID']");
        providerDropdown = page.locator("select[name='MAILBOX_PROVIDER']");
        authTypeDropdown = page.locator("select[name='AUTH_TYPE']");
        statusDropdown = page.locator("select[name='STATUS']");
        saveButton = page.locator("a[title='Save']");
        closeButton = page.locator("a[title='Close']");
        okButton = page.locator("//button[contains(@class, 'swal2-confirm') and normalize-space(text())='OK']");
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
        applicationMenuSearch = page.locator(".form-control");
        mailBoxConfigurationMenu = page.locator("text=Mailbox Configuration");
        mailboxActions = page.locator("a[title='Mailbox Actions'][href='/systemConfig']");
        mailToTicketTab = page.locator("button[title='Mail to Ticket']");
        surveyTab = page.locator("button[title='Survey']");
        approvalTab = page.locator("button[title='Approval']");
        mailboxDropdown = page.locator("select[name='MAILBOX_ID']");
        serviceInput = page.locator("//label[contains(text(), 'Service')]/following::input[1]");
        whiteListedDomains = page.locator("//label[contains(text(), 'Whitelisted Domains')]/following::input[1]");
        alwaysException = page.locator("//label[contains(text(), 'Always Process Exception')]/following::input[1]");
        neverException = page.locator("//label[contains(text(), 'Never Process Exception')]/following::input[1]");
        impact = page.locator("select[name='impact']");
        urgency = page.locator("select[name='urgency']");
        userVerificationType = page.locator("select[name='ACTIONS']");
        selectGuestUser = page.locator("//label[contains(text(), 'Guest User')]/following::input[1]");
        searchedCompanySelect = page.locator("a[id='-item-0']");
        guestAndOfferingSelect = page.locator("li[id='react-autowhatever-1--item-0']");
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
        String url = instanceData.get(InstanceConfigKeys.URL.getValue());
        String user = instanceData.get(InstanceConfigKeys.USERNAME.getValue());
        String pass = instanceData.get(InstanceConfigKeys.PASSWORD.getValue());

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

    public void navigateToMailboxConfiguration() {
        appMenuButton.click();
        applicationMenuSearch.fill(InstanceConfigKeys.MailBox_CONFIGURATION.getValue());
        mailBoxConfigurationMenu.click();
        page.waitForLoadState(LoadState.LOAD);
    }

    public void searchMailbox(String expectedMailbox) {
        page.waitForLoadState(LoadState.LOAD);
        searchInput.fill(expectedMailbox);
        if (noRecordsText.count() > 0) {
            createButton.waitFor();
            createButton.click();
        } else {
            page.waitForLoadState(LoadState.LOAD);
            mailboxRow0.click();
            editButton.waitFor();
            editButton.click();
        }
    }

    public void mailBoxConfiguration(String expectedMailbox, String company) {
        if (instanceData == null) throw new IllegalStateException("Instance data not loaded.");
        String vendor = instanceData.get(InstanceConfigKeys.VENDOR.getValue());
        String authType = instanceData.get(InstanceConfigKeys.AUTH_TYPE.getValue());
        String status = instanceData.get(InstanceConfigKeys.STATUS.getValue());

        //Validating if Mailbox already exists
        searchMailbox(expectedMailbox);

        //Create or Edit Mailbox Configuration
        companyInput.fill(company);
        searchedCompanySelect.click();
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
        applicationId.fill(instanceData.get(InstanceConfigKeys.APPLICATION_ID.getValue()));
        clientId.fill(instanceData.get(InstanceConfigKeys.TENANT_ID.getValue()));
        secretKey.fill(instanceData.get(InstanceConfigKeys.SECRET_KEY.getValue()));

        // Fill vendor-specific fields
        switch (vendor.toLowerCase()) {
            case "outlook" -> {
                objectId.fill(instanceData.get(InstanceConfigKeys.OBJECT_ID.getValue()));
                displayName.fill(instanceData.get(InstanceConfigKeys.DISPLAY_NAME.getValue()));
            }
            case "gmail" -> refreshToken.fill(instanceData.get(InstanceConfigKeys.SX_INBOUND.getValue()));
            default -> throw new IllegalArgumentException("Unsupported vendor: " + vendor);
        }
        saveCredentials.click();
        page.waitForLoadState(LoadState.LOAD);
        closeButton.click();
        page.waitForLoadState(LoadState.LOAD);
    }

    public void emailToTicketConfiguration() {

        //navigating to Mailbox Configuration page
        navigateToMailboxConfiguration();

        String mailbox = instanceData.get(InstanceConfigKeys.MAILBOX_EMAIL.getValue());
        String company = instanceData.get(InstanceConfigKeys.COMPANY_NAME.getValue());

        //Configuring Mailbox and its Credentials
        mailBoxConfiguration(mailbox, company);

        //navigating to Mailbox Actions
        mailboxActions.click();
        mailToTicketTab.click();
        searchMailbox(mailbox);

        page.waitForLoadState(LoadState.LOAD);

        //Filling details for Email to Ticket
        companyInput.fill(company);
        searchedCompanySelect.click();
        mailboxDropdown.selectOption(mailbox);
        serviceInput.fill(instanceData.get(InstanceConfigKeys.OFFERING_NAME.getValue()));
        guestAndOfferingSelect.click();

        //Filling Mailbox Filters
        whiteListedDomains.fill(instanceData.get(InstanceConfigKeys.WHITELISTED_DOMAINS.getValue()));
        alwaysException.fill(instanceData.get(InstanceConfigKeys.ALWAYS_PROCESS_EXCEPTION.getValue()));
        neverException.fill(instanceData.get(InstanceConfigKeys.NEVER_PROCESS_EXCEPTION.getValue()));

        //Filling Defaults values
        impact.selectOption(instanceData.get(InstanceConfigKeys.DEFAULT_IMPACT.getValue()));
        urgency.selectOption(instanceData.get(InstanceConfigKeys.DEFAULT_URGENCY.getValue()));

        //Filling User Verification details
        userVerificationType.selectOption(instanceData.get(InstanceConfigKeys.USER_VERIFICATION_TYPE.getValue()));

        if (instanceData.get(InstanceConfigKeys.USER_VERIFICATION_TYPE.getValue()).equalsIgnoreCase("Create with Guest Account")) {
            selectGuestUser.fill(instanceData.get(InstanceConfigKeys.GUEST_USER_EMAIL.getValue()));
            page.waitForLoadState(LoadState.LOAD);
            guestAndOfferingSelect.click();
        }
        statusDropdown.selectOption(instanceData.get(InstanceConfigKeys.ACTION_STATUS.getValue()));
        saveButton.click();
        okButton.click();
        page.waitForLoadState(LoadState.LOAD);
    }

    public void surveyApprovalConfiguation(String mailBoxAction) {

    }
}
