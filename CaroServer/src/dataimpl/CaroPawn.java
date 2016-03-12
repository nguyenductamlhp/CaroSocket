package dataimpl;

import interfaces.Pawn;

public abstract class CaroPawn implements Pawn{
        public PawnLocate location;
        public abstract void disPlay();
        public CaroPawn(int x,int y){
                this.location = new PawnLocate(x,y);
        }
        public PawnLocate getLocation() {
                return location;
        }
        public void setLocation(PawnLocate location) {
                this.location = location;
        }
        
}