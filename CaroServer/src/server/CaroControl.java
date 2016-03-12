package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;

import dataimpl.CaroException;
import dataimpl.CaroGame;
import dataimpl.CaroPlayer;
import dataimpl.OPawn;
import dataimpl.XPawn;

public class CaroControl implements Runnable {

        HashMap<String,CaroGame> caroGames = new HashMap<String,CaroGame>();
        
        Socket socket;
        BufferedReader reader;
        BufferedWriter writer;
        
        public CaroControl(Socket socket){
                this.socket = socket;
                try {
                        socket.setSendBufferSize(100);
                } catch (SocketException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } 
        }
        public void run() {
                try {
                        InputStream is = socket.getInputStream();
                        
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        while(true){
                                String status;
                                String content = reader.readLine();
                                Message message = Message.parserMessage(content);
                                switch(message.action){
                                case Message.ACTION_CREATE:
                                        status = createCaroGame(message);
                                        break;
                                case Message.ACTION_JOIN:
                                        status = joinCaroGame(message);
                                        break;
                                case Message.ACTION_START:
                                        status = startCaroGame(message);
                                        break;
                                case Message.ACTION_UNDERPIECE:
                                        status = underPawnCaroGame(message);
                                        break;
                                case Message.ACTION_GIVEUP:
                                        status = giveUpCaroGame(message);
                                        break;
                                case Message.ACTION_SUEPEACE:
                                        status = suePeaceCaroGame(message);
                                        break;
                                case Message.ACTION_TAKEBACK:
                                        status = tackBackCaroGame(message);
                                        break;
                                case Message.ACTION_LEAVE:
                                        status = leaveCaroGame(message);
                                        break;
                                case Message.ACTION_TALK:
                                        status = talkInCaroGame(message);
                                        break;
                                case Message.ACTION_LISTGAME:
                                        status = listCaroGame(message);
                                        break;
                                default:
                                        status = "7fff";
                                        break;
                                }
                                writer.write(status);
                                writer.write("\n");
                                writer.flush();
                        }
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
        private String talkInCaroGame(Message message) {
                return null;
        }
        
        private String listCaroGame(Message message) {
                Iterator keys = caroGames.keySet().iterator();
                String result = "7000:";
                while(keys.hasNext()){
                        result = result + keys.next();
                }
                return result;
        }

        private String leaveCaroGame(Message message) {
                return null;
        }
        private String tackBackCaroGame(Message message) {
                if(!caroGames.containsKey(message.name)){
                        return CaroStatus.ERROR_GAMENOTEXIST;
                }
                CaroGame CaroGame = caroGames.get(message.name);
                CaroPlayer player = CaroGame.getCaroPlayerByName(message.attributes[0]);
                if(player != null){
                        player.takeBack();
                }else{
                        return CaroStatus.ERROR_PLAYERNOTEXIST;
                }
                return CaroStatus.STATUS_SUCCESS;
        }
        private String suePeaceCaroGame(Message message) {
                //角色名
                if(!caroGames.containsKey(message.name)){
                        return CaroStatus.ERROR_GAMENOTEXIST;
                }
                CaroGame CaroGame = caroGames.get(message.name);
                CaroPlayer player = CaroGame.getCaroPlayerByName(message.attributes[0]);
                if(player != null){
                        player.sueForPeace();
                }else{
                        return CaroStatus.ERROR_PLAYERNOTEXIST;
                }
                return CaroStatus.STATUS_SUCCESS;
        }
        private String giveUpCaroGame(Message message) {
                //角色名
                if(!caroGames.containsKey(message.name)){
                        return CaroStatus.ERROR_GAMENOTEXIST;
                }
                CaroGame CaroGame = caroGames.get(message.name);
                CaroPlayer player = CaroGame.getCaroPlayerByName(message.attributes[0]);
                if(player != null){
                        player.giveUp();
                }else{
                        return CaroStatus.ERROR_PLAYERNOTEXIST;
                }
                return CaroStatus.STATUS_SUCCESS;
        }
        private String underPawnCaroGame(Message message) {
                // TODO Auto-generated method stub
                
                if(!caroGames.containsKey(message.name)){
                        return CaroStatus.ERROR_GAMENOTEXIST;
                }
                CaroGame CaroGame = caroGames.get(message.name);
                CaroPlayer player = CaroGame.getCaroPlayerByName(message.attributes[0]);
                if(player != null){
                        if(player == CaroGame.getFirstPlayer()){
                                if(CaroGame.getpawnCount()%2 == 0){
                                        player.pawnUnder(new XPawn(Integer.parseInt(message.attributes[1]),Integer.parseInt(message.attributes[2])), CaroGame);
                                        CaroPlayer player2 = CaroGame.getSecordPlayer();
                                        if(CaroGame.isCommit()){
                                                try {
                                                        player2.getWriter().write(CaroStatus.STATUS_SUCCESS+":lose\n");
                                                } catch (IOException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                }
                                                return CaroStatus.STATUS_SUCCESS+":win";
                                        }
                                        try {
                                                player2.getWriter().write(CaroStatus.STATUS_SUCCESS+":draw;"+message.attributes[1]+";"+message.attributes[2]+"\n");
                                        } catch (IOException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                        }
                                        return CaroStatus.STATUS_SUCCESS+":wait";
                                }else{
                                        return CaroStatus.UNDERPIECEERROR_NOTYOURTURN;
                                }
                                        
                        }else{
                                if(CaroGame.getpawnCount() % 2 == 1){
                                        player.pawnUnder(new OPawn(Integer.parseInt(message.attributes[1]),Integer.parseInt(message.attributes[2])), CaroGame);
                                        CaroPlayer player1 = CaroGame.getFirstPlayer();
                                        if(CaroGame.isCommit()){
                                                try {
                                                        player1.getWriter().write(CaroStatus.STATUS_SUCCESS+":lose\n");
                                                } catch (IOException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                }
                                                return CaroStatus.STATUS_SUCCESS+":win";
                                        }
                                        
                                        try {
                                                player1.getWriter().write(CaroStatus.STATUS_SUCCESS+":draw;"+message.attributes[1]+";"+message.attributes[2]+"\n");
                                        } catch (IOException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                        }
                                        return CaroStatus.STATUS_SUCCESS+":wait";
                                }else{
                                        return CaroStatus.UNDERPIECEERROR_NOTYOURTURN;
                                }
                        }
                }else{
                        return CaroStatus.ERROR_PLAYERNOTEXIST;
                }
        }
        private String startCaroGame(Message message) {
                if(!caroGames.containsKey(message.name)){
                        return CaroStatus.ERROR_GAMENOTEXIST;
                }

                CaroGame CaroGame = caroGames.get(message.name);
                CaroPlayer player = CaroGame.getCaroPlayerByName(message.attributes[0]);
                if(player != null){
                        player.setReady();
                        while(!CaroGame.checkReady()){
                        }
                }else{
                        return CaroStatus.ERROR_PLAYERNOTEXIST;
                }
                if(player.equals(CaroGame.getFirstPlayer())){
                        CaroPlayer player2 = CaroGame.getSecordPlayer();
                        try {
                                player2.getWriter().write(CaroStatus.STATUS_SUCCESS+":wait\n");
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                        return CaroStatus.STATUS_SUCCESS+":underpiece";
                }else{
                        return CaroStatus.STATUS_SUCCESS+":wait";
                }
        }
        private String joinCaroGame(Message message) {
                        // TODO Auto-generated method stub
                if(!caroGames.containsKey(message.name)){
                        return CaroStatus.ERROR_GAMENOTEXIST;
                }
                //名称
                CaroGame CaroGame = caroGames.get(message.name);
                CaroPlayer CaroPlayer = new CaroPlayer(message.attributes[0]);
                try {
                        CaroGame.addPlayer(CaroPlayer);
                } catch (CaroException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return CaroStatus.ERROR_JOINERROR+":"+CaroPlayer.getYellow();
                }
                
                return CaroStatus.STATUS_SUCCESS;
        }
        private String createCaroGame(Message message) {
                        if(caroGames.containsKey(message.name)){
                                return CaroStatus.ERROR_GAMEEXIST;
                        }
                        
                        CaroGame CaroGame = new CaroGame(message.name);
                        caroGames.put(message.name, CaroGame);
                        return CaroStatus.STATUS_SUCCESS;
        }


        static class Message{
                String client;
                String name;
                int action;
                String[] attributes;
        
                public static final int ACTION_CREATE = 0;
                public static final int ACTION_JOIN = 2;
                public static final int ACTION_START = 1;
                public static final int ACTION_UNDERPIECE = 3;
                public static final int ACTION_GIVEUP=4;
                public static final int ACTION_SUEPEACE =5;
                public static final int ACTION_TAKEBACK =6;
                public static final int ACTION_LEAVE =7;
                public static final int ACTION_TALK =8;
                public static final int ACTION_LISTGAME = 99;
                
                public Message(){
                }
                public void init(String content){
                        String[] cons = content.split(":");
                        if(cons.length != 4){
                                //error
                        }
                        client = cons[0];
                        name = cons[1];
                        action = Integer.parseInt(cons[2]);
                        attributes = cons[3].split(";");
                }
                public static Message parserMessage(String content){
                        Message mess = new Message();
                        mess.init(content);
                        return mess;
                }
        }

}
