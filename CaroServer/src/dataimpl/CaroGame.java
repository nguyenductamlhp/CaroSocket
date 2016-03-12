package dataimpl;

import java.util.ArrayList;

import interfaces.Game;
import interfaces.Judgment;
import interfaces.Pawn;
import interfaces.Player;

import interfaces.Player;

public class CaroGame implements Game {


        String name;
        
        ArrayList<Pawn> oPawn;
        ArrayList<Pawn> xPawn;
        CaroPawn caroPawn;
        
        
        CaroBoard caroBoard;
        CaroPlayer firstPlayer;
        CaroPlayer secordPlayer;
        CaroPlayer winner;
        boolean isXFirst;
        ArrayList<CaroPlayer> lookers;
        
        int pawnCount;
        boolean isCommit;
        
        CaroJudgment caroJudgment;
        
        private static CaroGame caroGame;
        
        public CaroGame(String name){
                this.name = name;
                this.init();
        }
        
        public void init(){
                if(oPawn == null)
                        oPawn = new ArrayList<Pawn>();
                if(xPawn == null)
                        xPawn = new ArrayList<Pawn>();
                caroBoard = CaroBoard.getCaroBoard();
                caroJudgment = new CaroJudgment();
                
                lookers = new ArrayList<CaroPlayer>();
        }
        

        @Override
        public void addPlayer(Player gp) throws CaroException{
                CaroPlayer gcaroPawn = (CaroPlayer)gp;
                if(this.firstPlayer != null){
                        this.secordPlayer = gcaroPawn;
                        gcaroPawn.setYellow("O");
                }else{
                        this.firstPlayer = gcaroPawn;
                        gcaroPawn.setYellow("X");
                }
                this.isXFirst = true;
        }
        public void saveStep() {

        }

        public Player getTheWinner() {
                return winner;
        }

        public void commit() {
                isCommit = true;
                pawnCount = 0;         
            
        }

        public boolean isCommit() {
                return isCommit;
        }

        public void addJudgment(Judgment judgment)throws CaroException {
                if(this.caroJudgment == null){
                        this.caroJudgment = (CaroJudgment) judgment;
                }
                CaroException.throwIt("the judgment is existed");
        }
        
        @Override    	
        public void pawnUnder(Pawn caroPawn) {
                if(caroPawn instanceof XPawn){
                        this.xPawn.add(caroPawn);
                }else{
                        this.oPawn.add(caroPawn);
                }
                this.caroBoard.drawAdd(caroPawn);
                caroPawn = caroPawn;
        
        }

        public Pawn getLastPawn(){
            return (Pawn) caroPawn;
        }
		public Pawn getoPawnByLocation(PawnLocate pl){
			return null;
		}
		public Pawn getxPawnByLocation(PawnLocate pl){
			return null;
		}
		public CaroPlayer getFirstPlayer() {
			return firstPlayer;
		}
		public void setFirstPlayer(CaroPlayer firstPlayer) {
			this.firstPlayer = firstPlayer;
		}
		public CaroPlayer getCaroPlayerByName(String name){
			if(this.firstPlayer.getName().equals(name)){
				return this.firstPlayer;
		    }else if(this.secordPlayer.getName().equals(name)){
		    	return this.secordPlayer;
		    }
		    return null;
		}
		    public boolean checkReady() {
		        // TODO Auto-generated method stub
		        if((firstPlayer!=null && this.firstPlayer.isReady()) && secordPlayer != null&& this.secordPlayer.isReady())
		                return true;
		        return false;
		}
		
		public CaroPlayer getSecordPlayer() {
		        return secordPlayer;
		}
		
		public void setSecordPlayer(CaroPlayer secordPlayer) {
		        this.secordPlayer = secordPlayer;
		}

	public int getpawnCount() {
	        return pawnCount;
	}
	
	public void setpawnCount(int pawnCount) {
	        this.pawnCount = pawnCount;
	}
	
	public CaroPlayer getWinner() {
	        return winner;
	}
	
	public void setWinner(CaroPlayer winner) {
	        this.winner = winner;
	}
	
	public CaroJudgment getCaroJudgment() {
	        return caroJudgment;
	}
	
	public void setCaroJudgment(CaroJudgment caroJudgment) {
	        this.caroJudgment = caroJudgment;
	}


	


}