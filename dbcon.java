/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author MYPC
 */
public class dbcon {
            static String path = "jdbc:mysql://localhost/project1";
            static String username ="root";
            static String password ="775870322";
            static Connection con;
            public static void getConnection() throws Exception 
            {
                con=DriverManager.getConnection(path,username,password);
            }
            
             public static int sendquery(String query) throws Exception 
             {//create a connetion for insert,delete & update
                if (con == null) 
                {
                    getConnection();
                }
                Statement s = con.createStatement();
                int x=s.executeUpdate(query);
                 return x;
            
             }   
             
             public static ResultSet getData(String query) throws Exception
             {//craete a connection for search
                 if (con == null) 
                 {
                    getConnection();
                 }
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(query);
                return rs;

              }

}
