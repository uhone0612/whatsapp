/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.whatsapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class loginTestTest {
    
    public loginTestTest() {
    }
    
    
    /**
     * Test of checkUserName method, of class loginTest.
     */
    @Test
    public void testCheckUserNameValid() {
        System.out.println("checkUserName - correctly formatted");
        loginTest instance = new loginTest("Kyl_1","","");
        boolean expResult = true;
        boolean result = instance.checkUserName();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCheckUserNameInvalid() {
        System.out.println("checkUserName - incorrectly formatted");
        loginTest instance = new loginTest("kyle!!!!!","","");
        boolean expResult = false;
        boolean result = instance.checkUserName();
        assertEquals(expResult, result);
    }

    /**
     * Test of checkPasswordComplexity method, of class loginTest.
     */
    @Test
    public void testCheckPasswordComplexityValid() {
        System.out.println("checkPasswordComplexity - correctly formatted");
        String password = "Ch&&sec@ke99!";
        loginTest instance = new loginTest("Ch&&sec@ke99!","","");
        boolean expResult = true;
        boolean result = instance.checkPasswordComplexity();
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testCheckPasswordComplexityInvalid() {
        System.out.println("checkPasswordComplexity - incorrectly formatted");
        String password = "password";
        loginTest instance = new loginTest("password","","");
        boolean expResult = true;
        boolean result = instance.checkPasswordComplexity();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of checkCellPhoneNumber method, of class loginTest.
     */
    @Test
    public void testCheckCellPhoneNumberValid() {
        System.out.println("checkCellPhoneNumber");
        String cell = "+27838968976";
        loginTest instance = new loginTest("+27838968976","","");
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber();
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testCheckCellPhoneNumberInvalid() {
        System.out.println("checkCellPhoneNumber");
        String cell = "08966553";
        loginTest instance = new loginTest("08966553","","");
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber();
        assertEquals(expResult, result);
    }
    /**
     * Test of registerUser method, of class loginTest.
     */
    @Test
    public void testRegisterUsercheckUserName() {
        System.out.println("checkUserName");
        loginTest instance = new loginTest("Uhoooo2oo2","Uh@1234ma!","+27646282282"); //bad username
        String expResult = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        String result = instance.registerUser();
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testRegisterUsercheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");
        loginTest instance = new loginTest("Uhl_1","password","+27646282282"); //valid username + bad password
        String expResult = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        String result = instance.registerUser();
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testRegisterUsercheckPhoneNumber() {
        System.out.println("checkPhoneNumber");
        loginTest instance = new loginTest("Uh_1","Uh@1234ma!","646282282"); // valid username + valid password + bad PhoneNumber
        String expResult = "Cell phone number incorrectly formatted or does not contain an international code; please correct the number and try again.";
        String result = instance.registerUser();
        assertEquals(expResult, result);
    }    

    /**
     * Test of loginUser method, of class loginTest.
     */
    @Test
    public void testLoginUserValid() {
        System.out.println("loginUser");
        String enteredUsername = "Uh_1";
        String enteredPassword = "Uh@1234ma!";
        loginTest instance = new loginTest("Uh_1","Uh@1234ma!","");
        boolean expResult = true;
        boolean result = instance.loginUser(enteredUsername, enteredPassword);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of returnLoginStatus method, of class loginTest.
     */
    @Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        boolean loginStatus = true;
        String firstName = "uhone";
        String lastName = "mathoma";
        loginTest instance = new loginTest("uhone","mathoma","" );
        String expResult = "Welcome uhone, mathoma it is great to see you again.";
        String result = instance.returnLoginStatus(loginStatus, firstName, lastName);
        assertEquals(expResult, result);
     
    }
    
}
