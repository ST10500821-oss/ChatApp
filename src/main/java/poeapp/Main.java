package poeapp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Step 1: Registration process
        System.out.println("=== Registration ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        
        // Use the firstName and lastName during registration
        Login login = new Login();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter cellphone (+countrycode): ");
        String cellPhone = scanner.nextLine();

        // Register the user
        String registerResult = login.registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println(registerResult);

        // Step 2: Login process
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();

        // Show login status
        String loginStatus = login.returnLoginStatus(loginUser, loginPass);
        System.out.println(loginStatus);

        scanner.close();
    }
}


//o