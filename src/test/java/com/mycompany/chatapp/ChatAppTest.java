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

/**
 *
 * @author Student
 */
public class ChatAppTest {
    
    private RegAndLogFeature app; 
    
    
    @BeforeEach
    public void setUp() {
        app = new RegAndLogFeature();   
    }
    @Test
    public void testCheckUserName_Valid(){
        assertTrue(app.checkUserName("johndoe"));
    }
    
    @Test
    public void testCheckCellPhoneNumber_wrongLength(){
        assertFalse(app.checkCellPhoneNumber("072257514"));
    }

    
}
