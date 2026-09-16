/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.whatsapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class TestTest {
    
    public TestTest() {
    }  
 //1. username is good it has _ and <=5 chars
    @Test
    public void testcheckUserNameValid() {
        System.out.println("checkUserName - correctly formatted");
        String username = "Uh_1";
        login instance = new login();
        boolean expResult = true;
        boolean result = instance.checkUserName(username);
        assertEquals(expResult, result);
    }
    
    //2. username is not good it has no underscore
    @Test
    public void testcheckUserNameInvalid(){
        System.out.println("checkUserName - incorrectly formatted");
        String username = "Uho111";
        login instance = new login();
        boolean expResult = false;
        boolean result = instance.checkUserName(username);
        assertEquals(expResult, result);
    }
    
    //password is correct
    @Test
    public void testcheckPasswordComplexityValid(){
        System.out.println("checkPasswordComplexity - correctly formatted");
        String password = "Uh@1234ma!";
        login instance = new login();
        boolean expResult = true;
        boolean result = instance.checkPasswordComplexity(password);
        assertEquals(expResult, result);
    }
    
}
