package main.java.ldap.operations;

import java.io.IOException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;

public class LdapSearchOperations {

	public static void searchByUsername(DirContext context, String username) {
		try {
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			String searchBase = "dc=example,dc=com"; // Update with your base DN
			String searchFilter = "(uid=" + username + "*)";

			NamingEnumeration<SearchResult> results = context.search(searchBase, searchFilter, searchControls);
			while (results.hasMore()) {
				SearchResult result = results.next();
				System.out.println("DN: " + result.getName());
				System.out.println("CN: " + result.getAttributes().get("cn").get());
				System.out.println("UID: " + result.getAttributes().get("uid").get());
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static SearchResult searchWithANDOperator(DirContext context, String baseDN, String searchFilter,
			String[] attributes) throws NamingException {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		return context.search(baseDN, searchFilter, searchControls).next();
	}

	public static NamingEnumeration<SearchResult> searchWithFilters(DirContext context, String baseDn,
			String searchFilter, String[] attributes) throws NamingException {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setReturningAttributes(attributes);

		return context.search(baseDn, searchFilter, searchControls);
	}

	public static NamingEnumeration<SearchResult> searchWithNegationFilters(DirContext context, String baseDn,
			String negationFilter, String[] attributes) throws NamingException {
		String finalFilter = "(&" + negationFilter + ")";
		return searchWithFilters(context, baseDn, finalFilter, attributes);
	}

	public static NamingEnumeration<SearchResult> searchWithORFilter(DirContext context, String baseDn,
			String[] orFilters, String[] attributes) throws NamingException {
		StringBuilder orFilter = new StringBuilder("(|");
		for (String filter : orFilters) {
			orFilter.append(filter);
		}
		orFilter.append(")");

		return searchWithFilters(context, baseDn, orFilter.toString(), attributes);
	}

	public static NamingEnumeration<SearchResult> searchWithResultPagination(LdapContext context, String baseDn,
			String searchFilter, String[] attributes, int pageSize) throws NamingException {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setReturningAttributes(attributes);

		Control[] controls;
		try {
			controls = new Control[] { new PagedResultsControl(pageSize, Control.NONCRITICAL) };
			context.setRequestControls(controls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return context.search(baseDn, searchFilter, searchControls);
	}

	public static NamingEnumeration<SearchResult> searchEntriesWithAttributePresent(LdapContext context, String baseDn,
			String attribute) throws NamingException {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		String searchFilter = "(" + attribute + "=*)";
		return context.search(baseDn, searchFilter, searchControls);
	}
}
