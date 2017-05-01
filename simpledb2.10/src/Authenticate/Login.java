package Authenticate;
import java.util.*;import java.applet.*;import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import simpledb.remote.RemoteDriver;
import simpledb.remote.RemoteDriverImpl;
import simpledb.server.*;


public class Login extends Applet implements ActionListener{
	
	//private JPanel pane = null; // 
	//private JPanel p = null;
	    JTextField textUser;
	    JPasswordField textPass;
	    JButton b1,b2,b3;
	    JLabel l1,l2,error;
	    Window window;
	    Dimension screenSize;
	    JPanel gui;
	    Random r = new Random();
	 
	    public void init(){
	    
	        textUser=new JTextField("",10);
	        textUser.setBackground(Color.white);
	        //myWindow.add(textUser);
	        
	        textPass =new JPasswordField("",10);
	        textPass.setBackground(Color.white);
	        
	      //  authen = new JLabel("Authentication", SwingConstants.CENTER);
	        l1 = new JLabel ("username");
	        l2 = new JLabel ("password");
	        b1=new JButton("confirm"); 
	        b2=new JButton("clear");
	        b3 = new JButton("New User");
	        b1.addActionListener(this);
	        b2.addActionListener(this);
	      //  b3.addActionListener(this);
	       
	        add(l1);
	        add(textUser);
	        add(l2);
	        add(textPass);
	        add(b1);
	        add(b2);
	       // add(b3);
	        
	       
	        //pane.add(error);
	    }
	    
	    public void actionPerformed(ActionEvent e){
	        if(e.getSource()==b1){
	            String username = textUser.getText();
	            String myPass=String.valueOf(textPass.getPassword());
	            System.out.println(username);
	            System.out.println(myPass);
	            System.out.println(username.getClass().getName());
	            System.out.println(myPass.getClass().getName());
	            boolean re = true;
	            re = pairs.searchPairs(username,myPass);
	            if(!re){
	            	
		            //feedback.append("Bon Voyage!\n");
		            errorApplet.infoBox("Invalid Username or password !", "Error Message");
		            textUser.setText(null);
		            textPass.setText(null);
	            	
	            }else{
	            	  errorApplet.infoBox("Success Message", "Database has been initiated for you!");
	            	  String[]args = {"studentdb"};
	            	  try {
	            		  
						Startup.main(args);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	 
	                  
	               }
	        }
	           
	           
	        

	        
	        else if(e.getSource()==b2){
	            textUser.setText(null);
	            textPass.setText(null);
	        }
	       
	    }	
}
	  
	    

	

