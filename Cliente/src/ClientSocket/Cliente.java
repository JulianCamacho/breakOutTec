package ClientSocket;
import java.io.*;
import java.net.*;

public class Cliente extends Thread{ 
	String sentence;
	String jsonReceived;
	
	
	public Cliente(String sentence) {
		super();
		this.sentence = sentence;
	}

	public void run(){

        String modifiedSentence;

        BufferedReader inFromUser =
          new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket;
		try {
			clientSocket = new Socket("localhost", 8080);
		

	        DataOutputStream outToServer =
	          new DataOutputStream(clientSocket.getOutputStream());
	
	        BufferedReader inFromServer =
	          new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        
	        try {
	        	while(true) {
	                	esperar(3);
	                	
		                System.out.println("SENTENCE: "+sentence);
		                
		                outToServer.writeBytes('/' + sentence +'\n');
		                
		                modifiedSentence = inFromServer.readLine();
		                jsonReceived = modifiedSentence;
		                
		                System.out.println("FROM SERVER: "+jsonReceived+"\n");
	            }
	        	
	        }catch (SocketException socketException){
	            System.out.println("Socket exception: " + socketException);
	        }
	        clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void esperar(int segundos){
        try {
            Thread.sleep(segundos * 1000);
         } catch (Exception e) {
            System.out.println(e);
         }
    }

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getJsonReceived() {
		return jsonReceived;
	}

	public void setJsonReceived(String jsonReceived) {
		this.jsonReceived = jsonReceived;
	}
	
	
}