package main.java.ldap;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import main.java.ldap.operations.LdapSearchOperations;
import main.java.ldap.util.LdapConnectionUtil;

public class MainApp {
	public static void main(String[] args) {
		DirContext context = null;
		try {
			if (LdapConnectionUtil.testConnection()) {
				System.out.println("LDAP connection successful!");

				context = LdapConnectionUtil.createConnection();

				// Perform the search operation
				LdapSearchOperations.searchByUsername(context, "jo");

			} else {
				System.out.println("LDAP connection failed.");
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}