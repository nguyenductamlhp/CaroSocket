package dataimpl;

import java.util.ArrayList;

import interfaces.Board;
import interfaces.Pawn;

public class CaroBoard implements Board{

        private static CaroBoard caroBoard;
        
        private CaroBoard(){
        }
        
        public static CaroBoard getCaroBoard(){
                if(caroBoard == null){
                        caroBoard = new CaroBoard();
                        caroBoard.draw(null);
                }
                return caroBoard;
        }

        public void clear() {
                
        }

        public void draw(ArrayList<Pawn> cps) {
                
        }

        public void drawAdd(Pawn cp) {
                
        }

        public void drawRemove(Pawn cp) {
                
        }

}