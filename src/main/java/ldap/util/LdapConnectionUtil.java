package main.java.ldap.util;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import main.java.ldap.constants.LdapConstants;

public class LdapConnectionUtil {

    public static boolean testConnection() {
        DirContext context = null;

        try {
            context = createConnection();
            return true;
        } catch (NamingException e) {
            return false;
        } finally {
            closeConnection(context);
        }
    }

    public static boolean authenticateUser(String username, String password) {
        DirContext context = null;

        try {
            context = createAuthenticationConnection(username, password);
            return true;
        } catch (NamingException e) {
            return false;
        } finally {
            closeConnection(context);
        }
    }

    public static DirContext createConnection() throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, LdapConstants.LDAP_FACTORY);
        env.put(Context.PROVIDER_URL, LdapConstants.LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, LdapConstants.SIMPLE_AUTHENTICATION);
        env.put(Context.SECURITY_PRINCIPAL, LdapConstants.BIND_DN);
        env.put(Context.SECURITY_CREDENTIALS, LdapConstants.ADMIN_PASSWORD);

        return new InitialDirContext(env);
    }

    public static DirContext createAuthenticationConnection(String username, String password) throws NamingException {
        String userDn = "uid=" + username + "," + LdapConstants.BIND_DN;

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, LdapConstants.LDAP_FACTORY);
        env.put(Context.PROVIDER_URL, LdapConstants.LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, LdapConstants.SIMPLE_AUTHENTICATION);
        env.put(Context.SECURITY_PRINCIPAL, userDn);
        env.put(Context.SECURITY_CREDENTIALS, password);

        return new InitialDirContext(env);
    }

    public static void closeConnection(DirContext context) {
        try {
            if (context != null) {
                context.close();
            }
        } catch (NamingException e) {
            // Handle the exception or log it
        }
    }
}
