package ve.com.digitel.bssint.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;

import ve.com.digitel.scratch.to.Usuario;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.util.Base64;

/**
 * Permite establecer conexion con el servidor LDAP
 *
 */
public class ConexionLDAP {

	public static boolean autenticarUsuario(String host, int port, String dn,
			Usuario usuario) throws Exception {
		
		boolean flag = false;
		LDAPConnection conn = new LDAPConnection();
		String nombre = "";

		try {
			// connect to the server LDAP
			conn.connect(host, port);

			// authenticate to the server
			try {

				conn.bind(LDAPConnection.LDAP_V3, usuario.getLogin() + dn,
						usuario.getClave().getBytes("UTF8"));

				// Search for attributes
				int searchScope = LDAPConnection.SCOPE_SUB;
				int ldapVersion = LDAPConnection.LDAP_V3;
				// String searchBase =
				// "OU=Usuarios de Aplicaciones,DC=digitelcorp,DC=com,DC=ve";
				String searchBase = "DC=digitelcorp,DC=com,DC=ve";
				String searchFilter = "(&(objectcategory=CN=Person,CN=Schema,CN=Configuration,DC=digitelcorp,DC=com,DC=ve)(userPrincipalName="
						+ usuario.getLogin() + dn + "))";
				String[] attr = { "name" };

				com.novell.ldap.LDAPSearchResults searchResults = conn.search(
						searchBase, searchScope, searchFilter, attr, // return
																		// all
																		// attributes
						false); // return attrs and values

				// Iterate attributes
				while (searchResults.hasMore()) {
					LDAPEntry nextEntry = null;
					try {
						nextEntry = searchResults.next();
					} catch (LDAPException e) {
						continue;
					}

					LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
					Iterator allAttributes = attributeSet.iterator();

					while (allAttributes.hasNext()) {
						LDAPAttribute attribute = (LDAPAttribute) allAttributes
								.next();
						String attributeName = attribute.getName();

						Enumeration allValues = attribute.getStringValues();

						if (allValues != null) {
							while (allValues.hasMoreElements()) {
								String value = (String) allValues.nextElement();
								if (Base64.isLDIFSafe(value)) {
									if (attributeName.equalsIgnoreCase("name"))
										nombre = value;
								} else {
									// base64 encode and then print out
									value = Base64.encode(value.getBytes());
									if (attributeName.equalsIgnoreCase("name"))
										nombre = value;

								}
							}
						}
					}
				}

				if (nombre != "")
					flag = true;

			} catch (UnsupportedEncodingException u) {
				throw new LDAPException("UTF8 Invalid Encoding",
						LDAPException.LOCAL_ERROR, (String) null, u);
			}

			// disconnect with the server
			conn.disconnect();
		} catch (LDAPException e) {
			e.printStackTrace();

			if (e.getMessage().indexOf("Invalid Credentials") > -1)
				return false;
			else
				throw new Exception("LDAP Exception: " + e.getMessage());
		} finally {

			usuario.setNombre(nombre.toString());
		}
		if(nombre=="")
			flag = false;
			
		return flag;
	}
}
