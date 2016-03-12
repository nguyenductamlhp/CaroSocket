/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package offical.caro.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


    //1- ket noi thanh cong
	//2- ten nguoi choi
	//3- gui lai ten
	//4- gui toa do
	//5-gui nguoi thang cuoc

class ListenServer extends Thread{
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	String playerName;
	Pos p = new Pos();
	
	ListenServer(Socket skt) throws IOException {
        this.socket = skt;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  socket.getOutputStream())), true);
        start();
    }
	 @Override
     public void run() {
         do {
             try {
                 String S = in.readLine();
                 String[] s = S.split(",");
                 switch (s[0]) {
				case "2":
					playerName = S.substring(1);
					break;
				
				case "4":
					p.x = Integer.parseInt(s[1]);
					p.y = Integer.parseInt(s[2]);
				default:
					break;
				} 
           

             } catch (IOException e) {
                 //System.out.println("There're some error");
             }
         }while (true);
 
     }
	 public void SendMessage(String msg) throws IOException {
		 out.println(msg);
		 out.flush();
	 }

}