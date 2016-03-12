package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import dataimpl.*;

public class CaroService {

        public static final int port = 1234;
                
        @SuppressWarnings("resource")
		public static void main(String[] args){
                ServerSocket serverSocket = null;
                try {
                        serverSocket = new ServerSocket(port);
                } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }
                while(true){
                        try {
                                
                                Socket socket = serverSocket.accept();
                                new Thread(new CaroControl(socket)).start();
                        } catch (IOException e) {
                                
                                e.printStackTrace();
                        }
                }
        }
}