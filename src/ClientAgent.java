import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import moses.member.Agent;
import moses.member.Member;
import moses.member.Receiver;
import moses.util.Const;


public class ClientAgent implements Agent {
	
	private final String identity;
	private final String secret;

	private Member member;
	
	private Receiver receiver;
	
	public ClientAgent(String identity, String secret, String lawFile, String controller, int controllerPort) throws IOException{
		this.identity = identity;
		this.secret = secret;
		
		FileInputStream law_stream  = new FileInputStream(lawFile);
		byte[] law_byte = new byte[law_stream.available()];
		law_stream.read(law_byte);
		String law_content = new String(law_byte);
		law_stream.close();
		
		this.member = new Member(law_content, Const.IMM_LAW, controller, controllerPort, this.identity);
		this.member.adopt(this.secret, "Arguments??"); //TODO if needed add arguments

        this.receiver = new Receiver(this, this.member);
        this.receiver.start();
	}

    public void processRequest(Member member, String message, String destination) {
        System.out.println("Sent to " + destination + ": " + message);
    }

    public void processReply(Member member, String reply) {
        System.out.println("Received: " + reply);
    }

    public void processReply(Member member, byte[] breply) {
        System.out.println("Received: " + breply);
    }

    public void processReply(Member member, Object oreply) {
        System.out.println("Received: " + oreply);
    }

	public void logout() {
		
		//TODO Logout somehow?? just disconnect??
		
	}

}
