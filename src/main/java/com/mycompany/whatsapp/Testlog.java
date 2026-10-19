/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.whatsapp;

import java.util.regex.Pattern;

/**
 *
 * @author uhone
 */
public class Testlog {
    private String username;
    private String password;
    private String cellphonenumber;

    public Testlog(String username, String password, String cellphonenumber) {
        this.username = username;
        this.password = password;
        this.cellphonenumber = cellphonenumber;
    }
    /** Username must contain an underscore and be no more than five characters. */
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    /** At least 8 characters, a capital letter, a number and a special character. */
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) {
            return false;
        }
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        return hasCapital && hasNumber && hasSpecial;
    }
    public boolean checkCellPhoneNumber() {
        return Pattern.matches("^\\+\\d{1,3}\\d{1,10}$", cellphonenumber);
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        return "User has been registered successfully.";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    public String returnLoginStatus(boolean loginStatus, String firstName, String lastName) {
        if (loginStatus) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
}
