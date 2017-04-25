package Authenticate;
import java.util.*;import java.applet.*;import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import simpledb.remote.RemoteDriver;
import simpledb.remote.RemoteDriverImpl;
import simpledb.server.*;


public class CenterTry{
}

//extends Applet implements ActionListener{

	 /* //private JPanel pane = null; // 
	//private JPanel p = null;
	  
	 
	    public void init(){
	    	JFrame myWindow = new JFrame("HELLO");
	    	myWindow.setSize(500,500);
	    	myWindow.setLocation(500, 350);
	    	
	    	
	    	// add a new text field
	    	 myWindow.setLayout(new BorderLayout());
	    	 JPanel mainPanel = new JPanel(new GridBagLayout());
	       JTextField textUser= new JTextField(20);
	        myWindow.add(textUser);
	        JLabel l1 = new JLabel("UserName");
	        l1.setText("User Name:");
	        JLabel l2 = new JLabel("Password: ");
	        myWindow.add(l1);
	        myWindow.add(l2);
	        myWindow.setVisible(true);
	    
	        JLabel label1 = new JLabel("Test");
	    	 JPanel panel = new JPanel(new GridLayout(0, 2));
	         panel.add(new JLabel("User: "));
	         JTextField name = new JTextField(20);
	         panel.add(name);
	         panel.add(new JLabel("Password: "));
	         JTextField Password = new JTextField(15);
	         panel.add(Password);
	         panel.setBorder(BorderFactory.createTitledBorder("Enter your username and password please."));
	         mainPanel.add(panel);
	         myWindow.add(mainPanel);
	         myWindow.pack();
	         myWindow.setVisible(true);
	  
	       
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
	    }*/	

	  
	    

	

