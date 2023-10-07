/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fire;




import java.sql.Connection ;
import javax.swing.JOptionPane;

import java.sql.DriverManager;
import java.sql.SQLException;
//import com.mysql.cj.jdbc.Driver; 
import java.sql.Connection;

import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
//import java.sql.Statement;

//import java.sql.ResultSet;
import java.util.Date;
//import com.mysql.cj.xdevapi.Result;
//import com.mysql.cj.xdevapi.Statement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author mohammedhamdy32
 */
public class db_connection {
    
    public static Connection Connecdb()
    {
        try 
        {
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost/school_mangment_system3","root","");
            System.out.println("connected");
            return conn;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
    
}