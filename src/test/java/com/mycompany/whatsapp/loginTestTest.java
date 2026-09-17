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
        loginTest instance = new loginTest("Uh_1","","");
        boolean expResult = true;
        boolean result = instance.checkUserName();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCheckUserNameInvalid() {
        System.out.println("checkUserName - incorrectly formatted");
        loginTest instance = new loginTest("Uho111","","");
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
        String password = "Uh@1234ma!";
        loginTest instance = new loginTest("Uh@1234ma!","","");
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
    public void testCheckCellPhoneNumber() {
        System.out.println("checkCellPhoneNumber");
        String cell = "+27646282282";
        loginTest instance = new loginTest("+27646282282","","");
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of registerUser method, of class loginTest.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        loginTest instance = null;
        String expResult = "";
        String result = instance.registerUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loginUser method, of class loginTest.
     */
    @Test
    public void testLoginUser() {
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
