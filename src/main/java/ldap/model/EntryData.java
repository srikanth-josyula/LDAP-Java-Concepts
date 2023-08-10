package main.java.ldap.model;

import java.util.Map;

public class EntryData {
    private String dn;
    private Map<String, String> attributes;

    public EntryData(String dn, Map<String, String> attributes) {
        this.dn = dn;
        this.attributes = attributes;
    }

    public String getDn() {
        return dn;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
