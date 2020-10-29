package cliente;

import java.io.*;
import java.net.*;

public class Cliente {

	public static void main(String[] args) throws IOException{

		String sentence;
        String modifiedSentence;

        BufferedReader inFromUser =
          new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 8080);

        DataOutputStream outToServer =
          new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer =
          new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        try {
        	while(true) {
	               	sentence = inFromUser.readLine();
	                System.out.println("SENTENCE: "+sentence);
	                System.out.println("SENTENCE equals: "+sentence.equals("/exit"));
	                if(sentence.equals("/exit")) {
	                	clientSocket.close();
	                	break;
	                }
	                outToServer.writeBytes(" /" + sentence + '\0'+'\n');
	                
	                modifiedSentence = inFromServer.readLine()+"}";
	                System.out.println("FROM SERVER: " + modifiedSentence+"\n");
	        		
                }
        	
        }catch (SocketException socketException){
            System.out.println("Socket exception: " + socketException);
        }
        clientSocket.close();
	}

}