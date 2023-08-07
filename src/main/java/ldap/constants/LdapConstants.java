package main.java.ldap.constants;

public class LdapConstants {
    public static final String HOST = "ldap.forumsys.com";
    public static final int PORT = 389;
    public static final String BIND_DN = "cn=read-only-admin,dc=example,dc=com";
    public static final String ADMIN_PASSWORD = "password";
    public static final String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    public static final String SIMPLE_AUTHENTICATION = "simple";
    public static final String LDAP_URL = "ldap://" + HOST + ":" + PORT;
}
