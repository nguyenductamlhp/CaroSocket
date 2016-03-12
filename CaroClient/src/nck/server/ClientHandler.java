/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nck.server;

import static nck.client.LoginForm.socket;
import static nck.client.LoginForm.user;
import nck.common.GPos;
import nck.common.KMessage;
import nck.common.Room;
import nck.common.Users;
//import nck.database.DataFunc;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nguyen Cao Ky
 */
public class ClientHandler extends Thread {
        public Room room = null;
    
        private Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;

        public Users user;
        
        Boolean execute = true;
        
        ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            
            execute = true;
        }

        void Send(String strSend) throws IOException
        {
            //Users temp = new Users(1, strSend, "123456", 100);
            //outputStream.writeObject(temp);
        }
        
        void Guilai() throws IOException, InterruptedException {
            //Users temp = new Users(1, "Server gui lai", "123456", 100);
            //outputStream.writeObject(temp);
            //Thread.sleep(1000);
        }

        void ReceiveMessage(KMessage msg) throws IOException {
            
            switch (msg.getType()) {
                case 0: {
                    Users temp = (Users)msg.getObject();
//                    DataFunc df = new DataFunc();
//                    user = df.checkLogin(temp.getUsername(), temp.getPassword());
                    if(user != null)
                    {
//                        Boolean flag = true;
//                        // Kiem tra coi da co ai dang nhap hay chua
//                        if (Main.client1 != null && Main.client1.user.getUsername().equalsIgnoreCase(user.getUsername())) {
//                        	user = null;
//                        }
//                        
                        if (user!=null)
                            System.out.println("Server: Xin chao " + user.getUsername());
                    }
                    SendMessage(0, user);
                    break;
                }
                case 1: 
//                    Users temp = (Users)msg.getObject();
////                    DataFunc df = new DataFunc();
//                    boolean succ;
////                    succ = df.checkAva(df.getId(temp.getUsername()));
//                    if (succ == true) {
//                        SendMessage(1, temp.getUsername() + " is Available");
//                        return;
//                    }
//
////                    succ = df.register(temp.getUsername(), temp.getPassword());
//                    if (succ == true) {
//                        SendMessage(1, "Register succesfull");
//                    }
//                    
//                    break;
//                }
               
                case 20: // Join room
                {
                    this.room = Main.room;
                    if (room.add(this) == false) //full
                    {
                        
                        
                        SendMessage(22, room);
                    }
                    else
                        SendMessage(20, null);
                    
                    break;
                }
                
                case 28:
                {
                    if (room.client1!=null && room.client2!=null)
                    {
                        Users[] arrUser = new Users[2];
                        arrUser[0] = room.client1.user;
                        arrUser[1] = room.client2.user;
                        room.client1.SendMessage(34, arrUser);
                        room.client2.SendMessage(34, arrUser);
                        room.client2.SendMessage(36, null);
                    }
                    break;
                }
                case 30: // Lay ban co
                {
                    GPos gPos = (GPos)msg.getObject();
                    if (gPos!=null)
                        room.put(this, gPos);
                    
                    if (room != null) {
                    	ClientHandler cli = new ClientHandler(socket);
                    	room.client1.SendMessage(30, room.pieceses);
                    	room.client2.SendMessage(30, room.pieceses);
                        
                    	room.client1.SendMessage(30, room.pieceses);
                    	room.client2.SendMessage(30, room.pieceses);
                    }
        
                    break;
                }
                
                case 39: //Exit room
                    if (room!=null)
                    {
                        room.clientExit(this);
                    }
                    break;
                
                case 40: //Chat
                {
                    if (room!=null)
                    {
                        // Gui cho 2 client
                        if (room.client1!=this)
                            room.client1.SendMessage(msg);
                        if (room.client2!=this)
                            room.client2.SendMessage(msg);

//                        for (ClientHandler cli : room.lstClientView) {
//                            if (cli!=this)
//                            {
//                                cli.SendMessage(msg);
//                            }
                        room.client1.SendMessage(msg);
                        room.client1.SendMessage(msg);
                    }
                }
                    break;
                
                case 41: //View
//                {
//                    room = Main.lstRoom.get(Integer.parseInt(msg.getObject().toString()));
//                    room.lstClientView.add(this);
//                    SendMessage(20, null);
//                    break;
//                }
                	break;
            }
        }
        

        public void SendMessage(int ty, Object obj) throws IOException {
            KMessage temp = new KMessage(ty, obj);
            SendMessage(temp);
        }
                
        public void SendMessage(KMessage msg) throws IOException {
            outputStream.reset();
            outputStream.writeObject(msg);
        }
        
        public Boolean closeClient() throws Throwable
        {
            
            
            if (room!=null) // Thong bao thoat room
            {
//                try {
//                    room..remove(this);
//                } catch (Exception e) {
//                    
//                }
                
                room.clientExit(this);
            }
            
            Main.client1.remove(this);
            try {
                this.socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Client Exit");
            execute = false;
            
            
            return true;
        }
        
        @Override
        public void run() {
            
            while (execute) {
                
                try {
                    Object o = inputStream.readObject();
                    if (o != null) {
                        ReceiveMessage((KMessage)o);
                    }
                    //Guilai();
                } catch (IOException e) {
                    try {
                        closeClient();
                    } catch (Throwable ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }


    }