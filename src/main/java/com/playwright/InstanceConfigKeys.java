package com.playwright;

import java.util.HashMap;
import java.util.Map;

public enum InstanceConfigKeys {
    MailBox_CONFIGURATION("Mailbox Configuration"),
    Work_Item_Board("Work Item Board"),
    INSTANCE_NAME("instanceName"),
    URL("url"),
    USERNAME("username"),
    PASSWORD("password"),
    COMPANY_NAME("companyName"),
    MAILBOX_EMAIL("mailboxEmail"),
    VENDOR("vendor"),
    AUTH_TYPE("authType"),
    STATUS("status"),
    SX_INBOUND("sxInbound"),
    APPLICATION_ID("applicationId"),
    TENANT_ID("tenantId"),
    SECRET_KEY("secretKey"),
    DISPLAY_NAME("displayName"),
    OBJECT_ID("objectId"),
    OFFERING_NAME("offeringName"),
    WHITELISTED_DOMAINS("whitelistedDomains"),
    ALWAYS_PROCESS_EXCEPTION("alwaysProcessException"),
    NEVER_PROCESS_EXCEPTION("neverProcessException"),
    DEFAULT_IMPACT("defaultImpact"),
    DEFAULT_URGENCY("defaultUrgency"),
    USER_VERIFICATION_TYPE("userVerificationType"),
    GUEST_USER_EMAIL("guestUserEmail"),
    GUEST_USER_NAME("guestUserName"),
    ACTION_STATUS("actionStatus");

    private final String value;

    InstanceConfigKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
