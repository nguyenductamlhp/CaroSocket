package interfaces;

import dataimpl.CaroException;

public interface Game {
        public void addPlayer(Player player) throws CaroException;
        public void saveStep();
        public Player getTheWinner();
        public void commit();
        public boolean isCommit();
        public void addJudgment(Judgment judgment) throws CaroException;
        
        public void pawnUnder(Pawn pawn);
        
        public boolean checkReady();
}