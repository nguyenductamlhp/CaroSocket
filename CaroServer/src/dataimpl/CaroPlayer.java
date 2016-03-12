package dataimpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import interfaces.Board;
import interfaces.Game;
import interfaces.Pawn;
import interfaces.Player;

public class CaroPlayer implements Player {

        public static final String XPAWN = "X";
        public static final String OPAWN = "O";
        String Name;
        String yellow;
        private boolean isReady;
        
        Socket socket;
        BufferedReader reader;
        BufferedWriter writer;
        
        public CaroPlayer(String name){
                this.Name = name;
        }
        
        public void sueForPeace() {
                
        }

        public void giveUp() {
                
        }

        public void takeBack() {
                
        }

        public void pawnsUnder(CaroPawn pawn, Game game) {
                game.pawnUnder(pawn);
                CaroGame caroGame = (CaroGame)game;
                if(caroGame.getCaroJudgment().checkGame(caroGame)){
                	caroGame.commit();
                	caroGame.setWinner(this);
                }
        }
        public String getName() {
                return Name;
        }

        public void setName(String name) {
                this.Name = name;
        }

        public String getYellow() {
                return yellow;
        }

        public void setYellow(String yellow) {
                this.yellow = yellow;
        }

        public void setReady() {
                // TODO Auto-generated method stub
                isReady = true;
        }
        public boolean isReady(){
                return isReady;
        }

        public Socket getSocket() {
                return socket;
        }

        public void setSocket(Socket socket) {
                this.socket = socket;
                try {
                        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        public BufferedReader getReader() {
                return reader;
        }

        public BufferedWriter getWriter() {
                return writer;
        }

		@Override
		public void pawnUnder(Pawn pawn, Game game) {
			// TODO Auto-generated method stub
			
		}

}
