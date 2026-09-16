/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;




/**
 *
 * @author Student
 */
public class ChatAppTest {
    
    private RegAndLogFeature app; 
    private PrintStream originalout;
    
    
    @BeforeEach
    public void setUp() {
        app = new RegAndLogFeature();   
    }//checkUserName
    @Test
    public void testCheckUserName_Valid(){
        assertTrue(app.checkUserName("Thabani"));
    }
    @Test
    public void testCheckUserName_Invalid(){
        assertFalse(app.checkUserName("Tha"));
    }
    @Test
    public void testCheckUserName_BoundaryLenth(){
        //exactly 5 characters should be valid
        assertTrue(app.checkUserName("Thabani"));
    }
    //Check PasswordComplexity
    @Test
    public void testCheckPasswordComplexity_Valid(){
        assertTrue(app.checkPasswordComplexity("Ogxmark21"));
    }
    @Test
    public void testCheckPasswordComplexity_Invalid_Too_Short(){
        assertFalse(app.checkPasswordComplexity("Mark1"));
    }
    @Test
    public void testCheckPasswordComplexity_Invalid_NoUpperCase(){
        assertFalse(app.checkPasswordComplexity("ogxmark21"));
    }
    
    //check CellPhoneNumber
    @Test
    public void testCheckCellPhoneNumber_Valid(){
        assertTrue(app.checkCellPhoneNumber("0722575148"));
    }
    @Test
    public void testCheckCellPhoneNumber_wrongLength(){
        assertFalse(app.checkCellPhoneNumber("072257514"));
    }
    @Test
    public void testCheckCellPhoneNumber_DoesNotStartWithZero(){
        assertFalse(app.checkCellPhoneNumber("722575148"));
    }
    @Test
    public void testCheckCellPhoneNumber_ContainsNonDigit(){
        assertFalse(app.checkCellPhoneNumber("0722575tha"));
    }
    //RegisterUser
    @Test
    public void testCheckRegisterUser_Success(){
        String simulatedInput = "Thabani\nOgxmark21\n0722575148\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        assertTrue(app.registerUser(scanner));
    }
    public void testCheckRegisterUser_Failure_InvalidInput(){
        String simulatedInput = "Tha\nOgxmark21\n0722575148\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        assertFalse(app.registerUser(scanner));
        
    }
    //loginUser
    @Test
    public void testloginUser_Success(){
        String regInput = "Thabani\nOgxmark21\n0722575148\n";
        app.registerUser(new Scanner(new ByteArrayInputStream(regInput.getBytes())));
        
        ByteArrayOutputStream outContent= new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        String loginInput = "Thabani\nOgxmark21\n";
        app.loginUser(new Scanner(new ByteArrayInputStream(loginInput.getBytes())));
        System.setOut(originalout);
        assertTrue(toString().contains("nice to see you again"));
    }
    @Test
    public void testloginUser_WrongPassword(){
        String regInput = "Thabani\nOgxmark21\n0722575148\n";
        app.registerUser(new Scanner(new ByteArrayInputStream(regInput.getBytes())));
        
        ByteArrayOutputStream outContent= new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        String loginInput = "Thabani\nWrongmark1\n0722575148\n";
        app.loginUser(new Scanner(new ByteArrayInputStream(loginInput.getBytes())));
        System.setOut(originalout);
        assertTrue(toString().contains("incorrect"));
        
        
    }

    private void assertTrue(boolean contains) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    

    
}
