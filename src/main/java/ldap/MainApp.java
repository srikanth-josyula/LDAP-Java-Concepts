package main.java.ldap;

import java.util.Scanner;
import main.java.ldap.util.LdapConnectionUtil;

public class MainApp {
    public static void main(String[] args) {
        if (LdapConnectionUtil.testConnection()) {
            System.out.println("LDAP connection successful!");
            System.out.println("Check if you have access to LDAP");
            
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            scanner.close();

            if (LdapConnectionUtil.authenticateUser(username, password)) {
                System.out.println("Authentication successful.");
                // You can start a user session or perform other actions here.
            } else {
                System.out.println("Authentication failed.");
            }
        } else {
            System.out.println("LDAP connection failed.");
        }
    }
}