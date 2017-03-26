package Authenticate;
import java.util.*;import java.applet.*;import java.awt.*;
import javax.swing.*;
import java.awt.event.*;



public class Login extends Applet implements ActionListener{
	
	//private JPanel pane = null; // 
	//private JPanel p = null;
	    JTextField textUser;
	    JPasswordField textPass;
	    JButton b1,b2;
	    JLabel l1,l2,error;
	    
	    public void init(){
	        setSize(250,150); 
	        textUser=new JTextField("",10);
	        textUser.setBackground(Color.white);
	        textPass =new JPasswordField("",10);
	        textPass.setBackground(Color.white);
	        
	      //  authen = new JLabel("Authentication", SwingConstants.CENTER);
	        l1 = new JLabel ("username");
	        l2 = new JLabel ("password");
	        b1=new JButton("confirm"); 
	        b2=new JButton("clear");
	        b1.addActionListener(this);
	        b2.addActionListener(this);
	       
	        add(l1);
	        add(textUser);
	        add(l2);
	        add(textPass);
	        add(b1);
	        add(b2);
	        
	       
	        //pane.add(error);
	    }
	    public void actionPerformed(ActionEvent e){
	        if(e.getSource()==b1){
	            String username = textUser.getText();
	            String myPass=String.valueOf(textPass.getPassword());
	            boolean re = true;
	            re = pairs.searchPairs(username,myPass);
	            if(!re){
	            	
		            //feedback.append("Bon Voyage!\n");
		            errorApplet.infoBox("Invalid Username or password !", "Error Message");
		            textUser.setText(null);
		            textPass.setText(null);
	            	
	            }
	           
	        }
	        else if(e.getSource()==b2){
	            textUser.setText(null);
	            textPass.setText(null);
	        }
	    }	    
	    
	}


