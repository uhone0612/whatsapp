/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.whatsapp;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class WhatsApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //registration
        System.out.println("enter username: ");
        String username = input.nextLine();
        
        System.out.println("enter password: ");
        String password = input.nextLine();
        
        System.out.println("enter your south african cell number: ");
        String cellphonenumber = input.nextLine();
        
        System.out.println("enter first name: ");
        String firstname = input.nextLine();
        
        System.out.println("enter last name: ");
        String lastname = input.nextLine();
        
        loginTest user = new loginTest(username, password, cellphonenumber);
        
            String registrationmessage = user.registerUser();
        System.out.println(registrationmessage);
        //login
        if (registrationmessage.equals("user has been registered successfully.")){
            System.out.println("enter username to login: ");
            String enteredusername = input.nextLine();
            
            System.out.println("enter password to login: ");
            String enteredpassword = input.nextLine();
            
            boolean loginStatus = user.loginUser(enteredusername, enteredpassword);
            System.out.println(user.returnLoginStatus(loginStatus, firstname, lastname));
        }
        
        input.close();
    }
}
