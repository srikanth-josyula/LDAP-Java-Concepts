package main.java.ldap.operations;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

public class LdapDeleteOperations {
	public static void deleteEntry(DirContext context, String entryDn) throws NamingException {
		context.addToEnvironment(Context.REFERRAL, "follow");
		context.destroySubcontext(entryDn);
	}

	public static void recursiveDelete(DirContext context, String entryDn) throws NamingException {
		context.addToEnvironment(Context.REFERRAL, "follow");
		context.unbind(entryDn);
	}

	public static void deleteMultipleEntries(DirContext context, String[] entryDns) throws NamingException {
		context.addToEnvironment(Context.REFERRAL, "follow");
		for (String entryDn : entryDns) {
			context.destroySubcontext(entryDn);
		}
	}

	public static void deleteEntrySilently(DirContext context, String entryDn) {
		try {
			deleteEntry(context, entryDn);
			System.out.println("Entry deleted successfully!");
		} catch (NamingException e) {
			System.err.println("Error deleting entry: " + e.getMessage());
		}
	}
}
