package com.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.utils.EmailReader;
import com.playwright.utils.EmailSender;
import com.playwright.utils.ExtentReportManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EmailToTicketTestPage {
    private final Page page;

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
    private Locator workItemBoard;
    private Locator incidentTab;
    private Locator allButton;
    private Locator summarySearchBox;
    private Locator searchIcon;
    private Locator incidentFirstRow;
    private Locator ticketNumber;
    private Locator globalSearchBar;
    private Locator globalSearchIcon;
    private Locator issueDescription;
    private Locator issueSummary;
    private Locator ticketCompany;
    private Locator ticketConsumer;
    private Locator ticketOffering;
    private Locator ticketUrgency;
    private Locator ticketImpact;
    private Locator reportedThrough;
    private Locator attachmentLink;
    private Locator watcherLink;
    private Locator attachmentNameTable;
    private Locator watcherDetails;
    private Locator ticketStatus;
    private Locator workItemIdSearchBox;
    private Locator editIncidentBtn;
    private Locator incidentBreadCrumbs;


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
        applicationMenuSearch = page.locator(".modulePopupSerch input.form-control");
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
        workItemBoard = page.locator("text=Work Item Board");
        incidentTab = page.locator("a[title=Incident]");
        allButton = page.locator("button[title=All]");
        summarySearchBox = page.locator("//th[div[text()='Summary']]//input[@placeholder='Search here']");
        workItemIdSearchBox = page.locator("//th[div[text()='Work Item ID']]//input[@placeholder='Search here']");
        searchIcon = page.locator("a[title='search']");
        incidentFirstRow = page.locator("td[class='deskReqNum']");
        ticketNumber = page.locator("div[class='rPageHeading']");
        globalSearchBar = page.locator("input[placeholder='Search here...']");
        globalSearchIcon = page.locator("a[title='Search']");
        issueSummary = page.locator("textarea[name='issueDescription']");
        issueDescription = page.locator("div[class='ql-editor']");
        ticketCompany = page.locator("input[name='companyName']");
        ticketConsumer = page.locator("//label[contains(text(), 'Consumer')]/following::input[1]");
        ticketOffering = page.locator("//label[contains(text(), 'Which Service is impacted?')]/following::input[1]");
        ticketUrgency = page.locator("select[name='urgencyMode']");
        ticketImpact = page.locator("select[name='criticality']");
        reportedThrough = page.locator("select[name='reportedThrough']");
        attachmentLink = page.locator("li[title='Attachment']");
        watcherLink = page.locator("li[title='Watcher Details']");
        attachmentNameTable = page.locator("//table[contains(@class, 'wrpAttTab')]//tbody/tr/td[1]/div[1]");
        watcherDetails = page.locator("//table[contains(@class, 'table-hover')]//tbody/tr/td[1]");
        ticketStatus = page.locator("button[title='Statuses']");
        editIncidentBtn = page.locator("a[title='Edit']");
        incidentBreadCrumbs = page.locator("div[class='lnk']");

    }


    //Login method
    public void login(Map<String, String> loginDetails) {
        String url = loginDetails.get(InstanceConfigKeys.URL.getValue());
        String user = loginDetails.get(InstanceConfigKeys.USERNAME.getValue());
        String pass = loginDetails.get(InstanceConfigKeys.PASSWORD.getValue());

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
        ExtentReportManager.getTest().pass("User is Login into the Application");
    }

    //Navigating method starts

    public void navigateToMailboxConfiguration() {
        appMenuButton.click();
        page.waitForLoadState(LoadState.LOAD);
        applicationMenuSearch.fill(InstanceConfigKeys.MailBox_CONFIGURATION.getValue());
        page.waitForLoadState(LoadState.LOAD);
        mailBoxConfigurationMenu.click();
        page.waitForLoadState(LoadState.LOAD);
        ExtentReportManager.getTest().pass("Navigated to Mailbox Configuration page");
    }

    public void navigateToIncidentListPage() {
        appMenuButton.click();
        page.waitForLoadState(LoadState.LOAD);
        applicationMenuSearch.fill(InstanceConfigKeys.Work_Item_Board.getValue());
        page.waitForLoadState(LoadState.LOAD);
        workItemBoard.click();
        page.waitForLoadState(LoadState.LOAD);
        incidentTab.click();
        page.waitForLoadState(LoadState.LOAD);
        allButton.click();
        page.waitForLoadState(LoadState.LOAD);
        ExtentReportManager.getTest().pass("Navigated to Incident List page");
    }

    public boolean searchAndEditIncidentTicket(String ticketNumber) {
        navigateToIncidentListPage();
        workItemIdSearchBox.fill(ticketNumber);
        searchIcon.click();
        page.waitForLoadState(LoadState.LOAD);
        incidentFirstRow.click();
        page.waitForLoadState(LoadState.LOAD);
        editIncidentBtn.click();
        incidentBreadCrumbs.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        incidentBreadCrumbs.innerText();
        boolean result = incidentBreadCrumbs.innerText().contains(ticketNumber);
        if (result) {
            ExtentReportManager.getTest().pass("Incident Ticket " + ticketNumber + " is opened for editing");
        } else {
            ExtentReportManager.getTest().fail("Incident Ticket " + ticketNumber + " not found or could not be opened");
        }
        return result;
    }

    //Mailbox Configuration
    public void searchMailbox(String expectedMailbox) {
        page.waitForLoadState(LoadState.LOAD);
        searchInput.fill(expectedMailbox);
        if (noRecordsText.count() > 0) {
            createButton.waitFor();
            createButton.click();
            ExtentReportManager.getTest().pass("No Mailbox found, creating new Mailbox: " + expectedMailbox);
        } else {
            page.waitForLoadState(LoadState.LOAD);
            mailboxRow0.click();
            editButton.waitFor();
            editButton.click();
            ExtentReportManager.getTest().pass("Editing existing Mailbox: " + expectedMailbox);
        }
    }

    public void mailBoxConfiguration(String expectedMailbox, String company, Map<String, String> mailBoxDetails) {
        String vendor = mailBoxDetails.get(InstanceConfigKeys.VENDOR.getValue());
        String authType = mailBoxDetails.get(InstanceConfigKeys.AUTH_TYPE.getValue());
        String status = mailBoxDetails.get(InstanceConfigKeys.STATUS.getValue());

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
            case "No changes detected in the provided payload" -> addEditCredentials(vendor, mailBoxDetails);
            case "Record saved successfully" -> {
                searchMailbox(expectedMailbox);
                addEditCredentials(vendor, mailBoxDetails);
            }
            case "Mailbox already exists for a different company" ->
                    ExtentReportManager.getTest().fail("Mailbox already exists for a different company: " + expectedMailbox);
        }
    }

    public void addEditCredentials(String vendor, Map<String, String> credentialsDetails) {
        page.waitForLoadState(LoadState.LOAD);

        // Click on Add or Edit button
        (addCredentials.count() > 0 ? addCredentials : editCredentials).click();
        page.waitForLoadState(LoadState.LOAD);

        // Fill common credentials
        applicationId.fill(credentialsDetails.get(InstanceConfigKeys.APPLICATION_ID.getValue()));
        clientId.fill(credentialsDetails.get(InstanceConfigKeys.TENANT_ID.getValue()));
        secretKey.fill(credentialsDetails.get(InstanceConfigKeys.SECRET_KEY.getValue()));

        // Fill vendor-specific fields
        switch (vendor.toLowerCase()) {
            case "outlook" -> {
                objectId.fill(credentialsDetails.get(InstanceConfigKeys.OBJECT_ID.getValue()));
                displayName.fill(credentialsDetails.get(InstanceConfigKeys.DISPLAY_NAME.getValue()));
            }
            case "gmail" -> refreshToken.fill(credentialsDetails.get(InstanceConfigKeys.SX_INBOUND.getValue()));
            default -> throw new IllegalArgumentException("Unsupported vendor: " + vendor);
        }
        saveCredentials.click();
        page.waitForLoadState(LoadState.LOAD);
        closeButton.click();
        page.waitForLoadState(LoadState.LOAD);
        ExtentReportManager.getTest().pass("Credentials for " + vendor + " Mailbox are configured successfully");
    }

    public void emailToTicketConfiguration(Map<String, String> mailboxDetails) {

        //navigating to Mailbox Configuration page
        navigateToMailboxConfiguration();

        String mailbox = mailboxDetails.get(InstanceConfigKeys.MAILBOX_EMAIL.getValue());
        String company = mailboxDetails.get(InstanceConfigKeys.COMPANY_NAME.getValue());

        //Configuring Mailbox and its Credentials
        mailBoxConfiguration(mailbox, company, mailboxDetails);

        //navigating to Mailbox Actions
        mailboxActions.click();
        mailToTicketTab.click();
        searchMailbox(mailbox);

        page.waitForLoadState(LoadState.LOAD);

        //Filling details for Email to Ticket
        companyInput.fill(company);
        searchedCompanySelect.click();
        mailboxDropdown.selectOption(mailbox);
        serviceInput.fill(mailboxDetails.get(InstanceConfigKeys.OFFERING_NAME.getValue()));
        guestAndOfferingSelect.click();

        //Filling Mailbox Filters
        whiteListedDomains.fill(mailboxDetails.get(InstanceConfigKeys.WHITELISTED_DOMAINS.getValue()));
        alwaysException.fill(mailboxDetails.get(InstanceConfigKeys.ALWAYS_PROCESS_EXCEPTION.getValue()));
        neverException.fill(mailboxDetails.get(InstanceConfigKeys.NEVER_PROCESS_EXCEPTION.getValue()));

        //Filling Defaults values
        impact.selectOption(mailboxDetails.get(InstanceConfigKeys.DEFAULT_IMPACT.getValue()));
        urgency.selectOption(mailboxDetails.get(InstanceConfigKeys.DEFAULT_URGENCY.getValue()));

        //Filling User Verification details
        userVerificationType.selectOption(mailboxDetails.get(InstanceConfigKeys.USER_VERIFICATION_TYPE.getValue()));

        if (mailboxDetails.get(InstanceConfigKeys.USER_VERIFICATION_TYPE.getValue()).equalsIgnoreCase("Create with Guest Account")) {
            selectGuestUser.fill(mailboxDetails.get(InstanceConfigKeys.GUEST_USER_EMAIL.getValue()));
            page.waitForLoadState(LoadState.LOAD);
            guestAndOfferingSelect.click();
        }
        statusDropdown.selectOption(mailboxDetails.get(InstanceConfigKeys.ACTION_STATUS.getValue()));
        saveButton.click();
        okButton.click();
        page.waitForLoadState(LoadState.LOAD);
        ExtentReportManager.getTest().pass("Email to Ticket Configuration is done successfully for Mailbox: " + mailbox);
    }

    public void surveyApprovalConfiguation(String mailBoxAction) {

    }

    //Email related Methods
    public String sentEmailAndGetTicketNumber(String subject, String body, String attachment, String mailBoxId) {

        EmailSender.sendEmail(mailBoxId, subject, body, attachment);
        ExtentReportManager.getTest().pass("Email sent with subject: " + subject + " to mailbox: " + mailBoxId);

        navigateToIncidentListPage();

        long startTime = System.currentTimeMillis();
        long timeout = 5 * 60 * 1000; // 5 minutes
        long interval = 10 * 1000;    // 10 seconds

        while (System.currentTimeMillis() - startTime < timeout) {
            summarySearchBox.fill("");
            summarySearchBox.fill(subject);
            searchIcon.click();
            page.waitForTimeout(7000);
            if (incidentFirstRow.count() > 0) {
                incidentFirstRow.first().click();
                page.waitForLoadState(LoadState.LOAD);
                ExtentReportManager.getTest().pass("Incident Ticket found for subject: " + subject);
                return ticketNumber.innerText().trim();
            }

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted during sleep", e);
            }
        }
        ExtentReportManager.getTest().fail("Incident Ticket not found for subject: " + subject + " within the timeout period");
        return "No ticket number found";
    }

    public List<Map<String, String>> validateNotificationsReceived(String subject) {
        long startTime = System.currentTimeMillis();
        long timeout = 5 * 60 * 1000;
        long interval = 10 * 1000;

        while (System.currentTimeMillis() - startTime < timeout) {
            List<Map<String, String>> emails = EmailReader.readEmailBySubject(subject);
            if (emails != null && !emails.isEmpty()) {
                ExtentReportManager.getTest().pass("Email found with subject: " + subject);
                return emails;
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted during sleep", e);
            }
        }
        ExtentReportManager.getTest().fail("Email not found with subject: " + subject + " within the timeout period");
        return Collections.emptyList();
    }

    public static Map<String, String> subjectAndBodyGenerator(String mailAction, String reqNumber, String itemId, Integer rating, String approvalAction, String waterMarkId) {
        Map<String, String> subjectAndBody = new HashMap<>();
        String subject;
        String body;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String randomId = UUID.randomUUID().toString().substring(0, 20);

        String comment = "Comment for " + mailAction + "  " + randomId + "  " + timestamp;

        switch (mailAction.toLowerCase()) {
            case "survey":
                subject = String.format("Feedback Rating #%d: Service Request: Feedback %s / %s", rating, reqNumber, itemId);
                body = String.format("Rating: %d star\n\nFeedback Comment: %s\n\nRef: %s\n\nId: %s", rating, comment, itemId, waterMarkId);
                break;

            case "approval":
                subject = String.format("%s-%s", approvalAction, itemId);
                body = String.format("%s\n\nId: %s", comment, waterMarkId);
                break;

            case "incident":
            default:
                subject = "Testing Ticket - " + randomId + " " + timestamp;
                body = "Test description for ticket: " + randomId + " at " + timestamp;
                break;
        }
        subjectAndBody.put("subject", subject);
        subjectAndBody.put("body", body);
        return subjectAndBody;
    }

    //Incident Ticket Methods
    public Map<String, String> getAllTicketDetails(String ticketNumber) {
        searchAndEditIncidentTicket(ticketNumber);

        String summary = issueSummary.inputValue();
        String additionalInfo = issueDescription.innerText().trim();
        String company = ticketCompany.inputValue();
        String consumer = ticketConsumer.getAttribute("placeholder");
        String offering = ticketOffering.getAttribute("placeholder");
        String urgency = ticketUrgency.locator("option:checked").textContent();
        String impact = ticketImpact.locator("option:checked").textContent();
        String through = reportedThrough.locator("option:checked").textContent();
        String status = ticketStatus.innerText().trim();
        String watcherDetailsText = getAllWatcherDetails();
        String attachmentDetails = getAllAttachedFiles();

        Map<String, String> ticketDetails = new HashMap<>();
        ticketDetails.put(InstanceConfigKeys.SUMMARY.getValue(), summary);
        ticketDetails.put(InstanceConfigKeys.DESCRIPTION.getValue(), additionalInfo);
        ticketDetails.put(InstanceConfigKeys.TICKET_COMPANY_NAME.getValue(), company);
        ticketDetails.put(InstanceConfigKeys.COSNUMER_NAME.getValue(), consumer);
        ticketDetails.put(InstanceConfigKeys.TICKET_OFFERING.getValue(), offering);
        ticketDetails.put(InstanceConfigKeys.TICKET_URGENCY.getValue(), urgency);
        ticketDetails.put(InstanceConfigKeys.TICKET_IMPACT.getValue(), impact);
        ticketDetails.put(InstanceConfigKeys.REPORTING_THROUGH.getValue(), through);
        ticketDetails.put(InstanceConfigKeys.TICKET_STATUS.getValue(), status);
        ticketDetails.put(InstanceConfigKeys.WATCHERDETAILS.getValue(), watcherDetailsText);
        ticketDetails.put(InstanceConfigKeys.ATTACHMENT_NAME.getValue(), attachmentDetails);

        ExtentReportManager.getTest().pass("Ticket details retrieved successfully for ticket number: " + ticketNumber);
        return ticketDetails;
    }

    public String getAllAttachedFiles() {
        attachmentLink.click();
        page.waitForTimeout(5000);
        List<String> attachmentNames = new ArrayList<>();
        int count = attachmentNameTable.count();
        for (int i = 1; i < count; i++) {
            String name = attachmentNameTable.nth(i).innerText().trim();
            attachmentNames.add(name);
        }
        String allAttachments = String.join(", ", attachmentNames);
        return allAttachments;
    }

    public String getAllWatcherDetails() {
        watcherLink.click();
        page.waitForTimeout(5000);
        List<String> watcheres = new ArrayList<>();
        int count = watcherDetails.count();
        for (int i = 1; i < count; i++) {
            String watcher = watcherDetails.nth(i).innerText().trim();
            if (!watcher.isEmpty()) {
                watcheres.add(watcher);
            }
        }
        return String.join(", ", watcheres);
    }
}
