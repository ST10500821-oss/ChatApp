package poeapp;

public class Login {

    private String username;
    private String password;
    private String cellPhone;
    private String firstName;
    private String lastName;

    

    //  initialize with username, password, and phone number (useful for Registration)
    

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5 ;
    }

    public boolean checkPasswordComplexity(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$");
    }

    public boolean checkCellPhoneNumber(String cellPhone) {
        return cellPhone.matches("^\\+\\d{10,13}$");
    }

    public String registerUser(String username, String password, String cellPhone, String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "Username invalid: must contain underscore and be 5-15 characters.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password invalid: must be 8+ chars, include uppercase, number, and special character.";
        }
        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cellphone invalid: must include international code (e.g., +27...).";
        }

        // Store user details after successful validation
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
        this.firstName = firstName;
        this.lastName = lastName;

        return "User registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        return this.username != null && this.username.equals(username) &&
               this.password.equals(password);
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}