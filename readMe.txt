Task 1 - Compute Engine
-----------------------
java -cp C:\Users\vinee\Documents\eclipse\workspace\HW_trial3\simple-server.jar -Djava.rmi.server.codebase=file:/C:/Users/vinee/Documents/eclipse/workspace/HW_trial3/simple-server.jar -Djava.rmi.server.hostname=localhost -Djava.security.policy=file:/C:/Users/vinee/Documents/eclipse/workspace/HW_trial3/policy engine.ComputeEngine


C:\Users\vinee\Documents\eclipse\workspace\HW_trial3>java -cp C:\Users\vinee\Documents\eclipse\workspace\HW_trial3\simple-client.jar -Djava.rmi.server.codebase=file:/C:/Users/vinee/Documents/eclipse/workspace/HW_trial3/simple-client.jar -Djava.rmi.server.hostname=localhost -Djava.security.policy=file:/C:/Users/vinee/Documents/eclipse/workspace/HW_trial3/policy client.ComputePi localhost

Task 2-Chat Application
-----------------------
java -cp C:\Users\vinee\Documents\eclipse\workspace\ChatApp\simple-server.jar -Djava.rmi.server.codebase=file:/C:/Users/vinee/Documents/eclipse/workspace/ChatApp/simple-server.jar -Djava.rmi.server.hostname=localhost -Djava.security.policy=file:/C:/Users/vinee/Documents/eclipse/workspace/ChatApp/policy edu.gvsu.cis.cis656.lab2.impl.PresenceServiceImpl

java -cp C:\Users\vinee\Documents\eclipse\workspace\ChatApp\simple-client.jar -Djava.rmi.server.codebase=file:/C:/Users/vinee/Documents/eclipse/workspace/ChatApp/simple-client.jar -Djava.rmi.server.hostname=localhost -Djava.security.policy=file:/C:/Users/vinee/Documents/eclipse/workspace/ChatApp/policy edu.gvsu.cis.cis656.lab2.client.ChatClient alice localhost:9999

java -cp C:\Users\vinee\Documents\eclipse\workspace\ChatApp\simple-client.jar -Djava.rmi.server.codebase=file:/C:/Users/vinee/Documents/eclipse/workspace/ChatApp/simple-server.jar -Djava.rmi.server.hostname=localhost -Djava.security.policy=file:/C:/Users/vinee/Documents/eclipse/workspace/ChatApp/policy edu.gvsu.cis.cis656.lab2.client.ChatClient bob localhost:7777

java -cp C:\Users\vinee\Documents\eclipse\workspace\ChatApp\simple-client.jar -Djava.rmi.server.codebase=file:/C:/Users/vinee/Documents/eclipse/workspace/ChatApp/simple-server.jar -Djava.rmi.server.hostname=localhost -Djava.security.policy=file:/C:/Users/vinee/Documents/eclipse/workspace/ChatApp/policy edu.gvsu.cis.cis656.lab2.client.ChatClient charlie localhost:5555 