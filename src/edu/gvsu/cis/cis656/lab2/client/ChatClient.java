
package edu.gvsu.cis.cis656.lab2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

import edu.gvsu.cis.cis656.lab2.ClientService;
import edu.gvsu.cis.cis656.lab2.PresenceService;
import edu.gvsu.cis.cis656.lab2.RegistrationInfo;


public class ChatClient extends UnicastRemoteObject implements Runnable,Serializable{
	
	protected ChatClient() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String x;
	
		public static void main(String args[]){
		x=args[1];
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        try {
            
        	String name = "PresenceService";
            Registry registry = LocateRegistry.getRegistry(System.getProperty("localhost"));
            PresenceService ps = (PresenceService) registry.lookup(name);
            
            if(args.length > 1) {
	            String[] serverDetails = args[1].split(":");
	            RegistrationInfo temp = ps.lookup(args[0]);
            //if(args[0].equals((ps.lookup(args[0]).getUserName())))
	                       	if(temp!=null && args[0].equals(temp.getUserName()))
            {
            	System.out.println("The username already exists");
            	System.exit(1);
            }
            else
            {
            	ps.register(new RegistrationInfo(args[0],serverDetails[0], Integer.parseInt(serverDetails[1]),true));
            	System.out.println("New user registered - Welcome " + args[0]);
                boolean connected=true;
            	Scanner input = new Scanner(System.in);
            	Thread t = new Thread(new ChatClient());
            	t.start();
            	//t.join();
                while(connected)
                {
                	String a = input.next();
                	if(a.equals("friends")){
                		Vector<RegistrationInfo> users = ps.listRegisteredUsers();
            			
            			for(RegistrationInfo user : users) {
            				System.out.println("Name: " + user.getUserName() + " Status: " + (user.getStatus()?"Available":"Not Available"));
            			}
                	}
                	else if(a.equals("talk")){
            			synchronized(ChatClient.class){
                		String talkTo = input.next();
                		String text = input.nextLine();
                		int clientPort;
                		String clientHost;
                		boolean clientStatus;
                		
                		if(ps.lookup(talkTo)!=null){
                		clientPort = ps.lookup(talkTo).getPort();
                		clientHost = ps.lookup(talkTo).getHost();
                		clientStatus = ps.lookup(talkTo).getStatus();
                		
                		if(clientStatus==true){
                		Socket cs = new Socket(clientHost,clientPort);
                		PrintStream os = new PrintStream(cs.getOutputStream());
                		os.println("Received message from " + args[0] + ": " + text);
                		cs.close();
                		}
                		else System.out.println("User Unavailable");
                		}
                		else System.out.println("Try again, This user does not exist");
                	}}
                	else if(a.equals("broadcast")){
            			synchronized(ChatClient.class){

                		String text = input.nextLine();
                		Vector<RegistrationInfo> users = ps.listRegisteredUsers();
                    	RegistrationInfo currentUser = ps.lookup(args[0]);

            			for(RegistrationInfo user : users) {
            				if(user.getStatus() && !user.getUserName().equals(currentUser.getUserName())){
            					
            					Socket cs = new Socket(user.getHost(),user.getPort());
                        		PrintStream os = new PrintStream(cs.getOutputStream());
                        		os.println("Broadcast message from " + args[0] + " : " + text);
                        		cs.close();
                    	}}}
                	}
                      	else if(a.equals("busy")){
                    	RegistrationInfo currentUser = ps.lookup(args[0]);
                    	if(currentUser.getStatus()==true){
                    		currentUser.setStatus(false);
                    		ps.updateRegistrationInfo(currentUser);
                    	}
                    	else System.out.println("Your status is already unavailable");
                	}
                	else if(a.equals("available")){
                    	RegistrationInfo currentUser = ps.lookup(args[0]);
                    	if(currentUser.getStatus()==false){
                    		currentUser.setStatus(true);
                    		ps.updateRegistrationInfo(currentUser);
                    	}
                    	else System.out.println("You are already online");
                	}
                	
                	else if(a.equals("exit")){
                		System.out.println("Exiting program");
                    	RegistrationInfo currentUser = ps.lookup(args[0]);
                		ps.unregister(currentUser.getUserName());
                		System.exit(1);
               		 	connected = false;

                	}
                	
                	else{
                		System.out.println("Bad input, try again");
                	}
            }	
            }
            
        }
            else {
            	System.out.println("Try again, bad arguments");
            	System.exit(1);
            }
        }catch (Exception e) {
            System.err.println("ChatClient exception:");
            e.printStackTrace();
        }
        
		}         
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
            while(true){
            	synchronized(this){
			try { 
            	String[] socketServerDetails = x.split(":");
            	ServerSocket ss = new ServerSocket(Integer.parseInt(socketServerDetails[1]));
            	Socket s = ss.accept();
            	InputStreamReader ir = new InputStreamReader(s.getInputStream());
        		BufferedReader br = new BufferedReader(ir);
        		String str = br.readLine();
        		System.out.println(str); 
        		s.close();
	        	ss.close();
            } catch (IOException e) { 

                e.printStackTrace(); 
            }}} 
         
    } 
		
}

    
    