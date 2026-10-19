
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
public class Login {
    private String username, password, cellNumber, firstName, lastName;

    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean checkUserName(String user) {
        return user.contains("_") && user.length() <= 5;
    }

    public boolean checkPasswordComplexity(String pass) {
        return pass.length() >= 8
                && pass.matches(".*[A-Z].*")
                && pass.matches(".*\\d.*")
                && pass.matches(".*[^A-Za-z0-9].*");
    }
    // Regex: '+' then country code (1-3 digits) then up to 10 digits.
    // TODO: add your Harvard reference for the regex source you researched here.
    public boolean checkCellPhoneNumber(String cell) {
        return Pattern.matches("^\\+\\d{1,3}\\d{1,10}$", cell);
    }

    public String registerUser(String user, String pass, String cell) {
        if (!checkUserName(user)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(pass)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cell)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        username = user;
        password = pass;
        cellNumber = cell;
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }
    public boolean loginUser(String user, String pass) {
        return user.equals(username) && pass.equals(password);
    }

    public String returnLoginStatus(String user, String pass) {
        if (loginUser(user, pass)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
    
}
