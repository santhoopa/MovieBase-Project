/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author MYPC
 */
public class games1 {
    static private int game_id;
    static private String game_name;
    static private String category;
    static private int innitial_qty;
    static private int current_qty;
    static private double rental_fee;

    public static int getGame_id() {
        return game_id;
    }

    public static void setGame_id(int game_id) {
        games1.game_id = game_id;
    }

    public static String getGame_name() {
        return game_name;
    }

    public static void setGame_name(String game_name) {
        games1.game_name = game_name;
    }

    public static String getCategory() {
        return category;
    }

    public static void setCategory(String category) {
        games1.category = category;
    }

    public static int getInnitial_qty() {
        return innitial_qty;
    }

    public static void setInnitial_qty(int innitial_qty) {
        games1.innitial_qty = innitial_qty;
    }

    public static int getCurrent_qty() {
        return current_qty;
    }

    public static void setCurrent_qty(int current_qty) {
        games1.current_qty = current_qty;
    }

    public static double getRental_fee() {
        return rental_fee;
    }

    public static void setRental_fee(double rental_fee) {
        games1.rental_fee = rental_fee;
    }
    
    public static void add_games(int id,String n,String c,int q,double f)
    {
        game_id=id;
        game_name=n;
        category=c;
        innitial_qty=q;
        current_qty=q;
        rental_fee=f;
        String availability="available";
        try{
        String qu="insert into games values ("+game_id+",'"+game_name+"','"+category+"',"+innitial_qty+","+current_qty+","+rental_fee+",'"+availability+"');";
        int x=dbcon.sendquery(qu);
        if(x==1)
            JOptionPane.showMessageDialog(null, "Successfully added");
        else
                JOptionPane.showMessageDialog(null, "Adding failed");

        }catch(Exception e)
        {
        JOptionPane.showMessageDialog(null, "Exception occured while entering data");
        }
    
    }
    
    public static ResultSet view_all_game_details()
    {    ResultSet r=null;
         try{
         String query="select * from games where availability='available';";
         r=dbcon.getData(query);
         
         return r;
         }
         catch(Exception e)
         {
            JOptionPane.showMessageDialog(null, "Exception Occured");
            return r;
         }
    
    }
    
     public static ResultSet view_individual_game_details(String name)
    {    ResultSet r=null;
         try{
         String query="select * from games where game_name "+name+" AND availability='available';";
         r=dbcon.getData(query);
        
             return r;
         }
         catch(Exception e)
         {
            JOptionPane.showMessageDialog(null, "Exception Occured when viewing individual details");
            return r;
         }
    
    }
     
     public static void remove_game(String name)
    {  
        try{
        String q="UPDATE games set availability='REMOVED' where game_name='"+name+"' AND innitial_qty=current_qty AND availability='available';";
        int x=dbcon.sendquery(q);
        if(x==0){
         JOptionPane.showMessageDialog(null,"Failed to remove the Game");
         }
         else
         {
           JOptionPane.showMessageDialog(null, "Successfully Removed");
         }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Exception Occured when removing");

        }
    }
     
     
     public static void update_game_qty(String id,String quantity_added)
     {
         try{
         int i=Integer.parseInt(id);
         int q_a=Integer.parseInt(quantity_added);
         String q="update  games set innitial_qty=innitial_qty+"+q_a+",current_qty=current_qty+"+q_a+" where game_id="+i+" AND availability='available';";
         int x=dbcon.sendquery(q);
         if(x==1){
         JOptionPane.showMessageDialog(null, "Successfully Updated");
         }
         else
         {
           JOptionPane.showMessageDialog(null, "Failed to Update");
         }
         
         
         }catch(Exception e){JOptionPane.showMessageDialog(null, "Exception Occured when updating");}
     }
     
     public static void update_game_fee(String id,String new_fee)
     {
         try{
         int i=Integer.parseInt(id);
         double f=Double.parseDouble(new_fee);
         String q="update  games set rental_fee="+f+" where game_id="+i+" AND availability='available';";
         int x=dbcon.sendquery(q);
         if(x==1){
         JOptionPane.showMessageDialog(null, "Successfully Updated");
         }
         else
         {
           JOptionPane.showMessageDialog(null, "Failed to Update");
         }
         
         
         }catch(Exception e){JOptionPane.showMessageDialog(null, "Exception Occured when updating");}
     }
     
     public static void rent_out_game(int invoice_number,String date_of_rent,String nic,int game_id,double innitial_fee,double discount,double final_fee,String due_date)
     {
       try
       {  
       String q="INSERT INTO  invoice values("+invoice_number+",'"+date_of_rent+"','"+nic+"',"+game_id+","+innitial_fee+","+discount+","+final_fee+",'"+due_date+"','NOT RETURNED');";  
       String que="UPDATE customer SET rented_gameid='"+game_id+"' WHERE nic='"+nic+"' AND availability='available'; ";
       String query2="UPDATE games SET current_qty=current_qty-1 WHERE game_id="+game_id+" ;";
       int x=dbcon.sendquery(q);
       dbcon.sendquery(que);
       dbcon.sendquery(query2);
       if(x==1){
         JOptionPane.showMessageDialog(null, "Successfully Rented");
         }
         else
         {
           JOptionPane.showMessageDialog(null, "Failed");
         }
       }catch(Exception e){JOptionPane.showMessageDialog(null, "Error Occured when placng the invoice \n Try Again");}
     }
     
     public static ResultSet view_rented_game_using_combobox(String nic)
     {
         ResultSet rs=null;
     try
     {String q="select c.nic,c.name,i.invoice_no,i.game_id,g.game_name,i.invoice_date,i.due_date,i.final_fee from invoice i,customer c,games g where i.customer_nic=c.nic AND i.game_id=g.game_id AND i.status='NOT RETURNED' AND i.customer_nic='"+nic+"';";
     rs=dbcon.getData(q);
     return rs;
     }catch(Exception e){JOptionPane.showMessageDialog(null, "Error");  return rs;}
     }
     
     public static void receive_rented_out_game(String nic,int game_Id)
     {  int x=0;
        int y=0;
        int z=0;
        try{
         String q1="update invoice set status='RETURNED' where customer_nic='"+nic+"' AND status='NOT RETURNED';";
         String q2="UPDATE games SET current_qty=(current_qty+1) WHERE game_id="+game_Id+";";
         String q3="UPDATE customer SET rented_gameid='null' WHERE nic='"+nic+"';";
         x=dbcon.sendquery(q1);
         y=dbcon.sendquery(q2);
         z=dbcon.sendquery(q3);
        }catch(Exception e){JOptionPane.showMessageDialog(null, "Error");}
        if((x+y+z)==3)
            JOptionPane.showMessageDialog(null, "Successfull");
     }
}
