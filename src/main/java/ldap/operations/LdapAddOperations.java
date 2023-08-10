package main.java.ldap.operations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

import main.java.ldap.model.EntryData;

public class LdapAddOperations {
	public static void addEntryFromLDIF(DirContext context, String ldifFilePath) throws NamingException, IOException {
		String ldifContent = new String(Files.readAllBytes(Paths.get(ldifFilePath)));
		context.addToEnvironment(javax.naming.Context.REFERRAL, "follow");

		BasicAttributes attributes = new BasicAttributes();
		BasicAttribute uidAttribute = new BasicAttribute("uid", "newuser");
		attributes.put(uidAttribute);

		context.createSubcontext("uid=newuser,ou=Users,dc=example,dc=com", attributes);

		// context.createSubcontext("uid=newuser,ou=Users,dc=example,dc=com",
		// ldifContent);
	}

	public static void addEntryWithAttributes(DirContext context, String dn, Map<String, String> attributes)
			throws NamingException {
		BasicAttributes basicAttributes = new BasicAttributes();
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			basicAttributes.put(new BasicAttribute(entry.getKey(), entry.getValue()));
		}

		context.createSubcontext(dn, basicAttributes);
	}

	public static void batchAddEntries(DirContext context, List<EntryData> entryDataList) throws NamingException {
		for (EntryData entryData : entryDataList) {
			addEntryWithAttributes(context, entryData.getDn(), entryData.getAttributes());
		}
	}

}
