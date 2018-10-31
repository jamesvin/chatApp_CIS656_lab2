package edu.gvsu.cis.cis656.lab2.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import edu.gvsu.cis.cis656.lab2.PresenceService;
import edu.gvsu.cis.cis656.lab2.RegistrationInfo;
	

public class PresenceServiceImpl implements PresenceService {
	
	private Vector<RegistrationInfo> registeredUsers = new Vector<RegistrationInfo>();

	 static String name = "PresenceService";
	 
	 static Registry registry;
	 
	@Override
	public boolean register(RegistrationInfo reg) {
		boolean isUserAlreadyReg = false;
		
		if(registeredUsers.size() > 0) {
			for(RegistrationInfo user : registeredUsers) {
				if(user.getUserName().equals(reg.getUserName()))
					isUserAlreadyReg = true;
			}
		}
		if(!isUserAlreadyReg) {
			registeredUsers.add(reg);
			
		}
		return isUserAlreadyReg;
	}

	@Override
	public boolean updateRegistrationInfo(RegistrationInfo reg) throws RemoteException {
		for(RegistrationInfo user : registeredUsers) {
			if(user.getUserName().equals(reg.getUserName()))
				user.setStatus(reg.getStatus());
		}
		return false;
	}

	@Override
	public void unregister(String userName) throws RemoteException {
		RegistrationInfo toBeDelUser = null;
		for(RegistrationInfo user : registeredUsers) {
			if(user.getUserName().equals(userName))
				toBeDelUser = user;
		}
		registeredUsers.remove(toBeDelUser);
	}

	@Override
	public RegistrationInfo lookup(String name) throws RemoteException {
	
		for(RegistrationInfo user : registeredUsers) {
			if(user.getUserName().equals(name))
				return user;
		}
		return null;
	}

	@Override
	public Vector<RegistrationInfo> listRegisteredUsers() throws RemoteException {

		return registeredUsers;
	}
	
	 public static void main(String[] args) { 
	        if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
	        try {
	            //String name = "PresenceService";
	            PresenceService engine = new PresenceServiceImpl();
	            int port= 0;
	            if(args.length >0) {
	            	port = Integer.parseInt(args[0]);
	            }
	            PresenceService stub =
	                    (PresenceService) UnicastRemoteObject.exportObject(engine, port);
	            registry = LocateRegistry.getRegistry();
	            registry.rebind(name, stub);
	            System.out.println("Server has started!!");
	        } catch (Exception e) {
	            System.err.println("PresenceService, exception:");
	            e.printStackTrace();
	        }
	 }

	public Vector<RegistrationInfo> getRegisteredUsers() {
		return registeredUsers;
	}

	public void setRegisteredUsers(Vector<RegistrationInfo> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}
	 
	 

}
