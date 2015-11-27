import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;

import moses.member.Member;
import moses.member.Receiver;
import moses.security.LGICert;
import moses.security.Secu;
import moses.security.certCreation;
import moses.util.Const;


public class Client {
	 public void run (String[] args) throws Exception { 
		 
		 
			if(args.length < 2) {
				System.out.println("> java Client controller_name controller_port");
				return;
			}
			
			String controller_name = args[0];
			int controller_port = Integer.parseInt(args[1]);
			String law_path = "/the/law/isStatic.law";
			
			Console console = System.console();
			
			if(console == null){
				System.out.println("FAULT: Could not detect console... Exiting...");
				System.exit(-1);
			}
			
			
			
			System.out.println("Please Enter your username: ");
			String identity = new String(System.console().readPassword());

			while(identity == null || identity.equals("")){
				System.out.println("Invalid username. Please enter your username: ");
				identity = new String(System.console().readPassword());
			}
			
			
			System.out.println("Please enter your password: ");
			String secret = new String(System.console().readPassword());

			while(secret == null || secret.equals("")){
				System.out.println("Invalid password. Please enter your password: ");
				secret = new String(System.console().readPassword());
			}
			
			ClientAgent agent = new ClientAgent(identity, secret, law_path, controller_name, controller_port );

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("> ");
			try{
				String raw = null;
				while ((raw  = in.readLine()) != null) {
					if (raw.equals("logout")) {
						agent.logout();
						System.out.println("Signing... Out");
					   	break;
					}
					
					String[] cmd = raw.split(" ", 3);
					
					switch(cmd[0]){
					case "set":
						if(!(cmd[1].equals("private") || cmd[1].equals("public")){
							System.out.println("Invalid visibility type: " + cmd[1]);
							break;
						}
					}


					System.out.print("> ");
				}

			} catch(Exception e) {
				e.printStackTrace();
			}
		}
}
