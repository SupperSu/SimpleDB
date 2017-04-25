package Authenticate;
import java.util.Scanner;

public class newAut {
	public static void main(String args[]){

        Scanner scanner = new Scanner(System.in);
        //int eid,sid;
        String username,password;
        System.out.println("Enter username: ");
        //eid=scanner.nextInt();
        username = scanner.nextLine();
        scanner.nextLine(); //This is needed to pick up the new line
        System.out.println("Enter password: ");
        password = scanner.nextLine();
        //System.out.println("Enter SupervisiorId:");
        //sid=(scanner.nextInt());
        boolean ans = true;
        ans = pairs.searchPairs(username, password);
        if(ans){
        System.out.println("access");
        }else{
        	System.out.println("fail");
        }
}
}
	