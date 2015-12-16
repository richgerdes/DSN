import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.security.*; 

import moses.member.*;
import moses.security.*;
import moses.controlState.*;
import moses.util.*;

public class Client implements Agent{
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
    public void run (String[] args) throws Exception { 
        if(args.length < 2) {
            System.out.println("> java Client controller_name controller_port");
            return;
        }
        
        String controller_name = args[0];
        String controller_port = args[1];
        String law_path = "./echo.law";
        String agent_name = "";
        String agent_secret = "";
        

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please Log in.");
        while(agent_name.equals("")){
    		System.out.print("username: ");
        	agent_name = in.readLine();
        	if(agent_name.equals(""))
        		System.out.println("Invalid Username.");
        }
        
        while(agent_secret.equals("")){
    		System.out.print("password: ");
    		agent_secret = in.readLine();
        	if(agent_secret.equals(""))
        		System.out.println("Invalid Password.");
        }

        FileInputStream law_stream  = new FileInputStream(law_path);
        byte[] law_byte = new byte[law_stream.available()];
        law_stream.read(law_byte);
        String law_content = new String(law_byte);


        Member member = new Member(law_content, Const.IMM_LAW, controller_name, Integer.parseInt(controller_port), agent_name);

        member.adopt(agent_secret,"someargument");

        new Receiver(this, member).start();

        System.out.println("Login Complete.");
        System.out.println("Type exit to quit.");
        
        System.out.print("> ");
        
        while (true) {
            try{
                String raw_command  = in.readLine();
                if (raw_command.equals("exit")) {
                    member.close();
                    System.out.println("Logging off...");
                    System.exit(0);
                }

                if(!raw_command.equals(""))
                	member.send_lg(raw_command, member.longName);
                
                System.out.print("> ");

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void processRequest(Member member, String message, String destination) {
        System.out.println("\nSent to " + destination + ": " + message +"\n>");
    }

    public void processReply(Member member, String reply) {
        System.out.println("\n" + format.format(new Date()) + ": " + reply +"\n>");
    }

    public void processReply(Member member, byte[] breply) {
        System.out.println("\n" + format.format(new Date()) + ": " + breply +"\n>");
    }

    public void processReply(Member member, Object oreply) {
        System.out.println("\n" + format.format(new Date()) + ": " + oreply +"\n>");
    }

    public static void main (String[] args) throws Exception {
        Client agent = new Client();
        String[] clientArgs = {"172.31.150.117","9000"};
        agent.run(clientArgs);
    }
}