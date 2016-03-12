package dataimpl;

import interfaces.Game;
import interfaces.Judgment;

public class CaroJudgment implements Judgment {
        boolean isX = false;
        CaroGame caroGame ;
        CaroPawn pawn;
        @Override
        
        
        public boolean checkGame(Game game) {
                
                caroGame = (CaroGame)game;
                pawn = (CaroPawn) caroGame.getLastPawn();
                if(pawn instanceof OPawn){
                        isX = true;
                }
                
                if(checkXAxis()){
                        return true;
                }
                
                if(checkYAxis()){
                        return true;
                }
                if(checkRightUpAxis()){
                        return true;
                }
                if(checkLeftUpAxis()){
                        return true;
                }
                return false;
        }
        private boolean checkLeftUpAxis() {
            int findCount = 1;
            PawnLocate lastLocation = pawn.getLocation();
            int lastX = lastLocation.getX();
            int lastY = lastLocation.getY();
            int X = lastX;
            int Y = lastY;
            while(true){
                    X--;
                    Y++;
                    if(X<0)
                            break;
                    if(this.isX){
                            if(caroGame.getxPawnByLocation(new PawnLocate(X,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }else{
                            if(caroGame.getoPawnByLocation(new PawnLocate(X,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }
            }
            X = lastX;
            Y = lastY;
            while(true){
                    X++;
                    Y--;
                    if(Y<0)
                            break;
                    if(this.isX){
                            if(caroGame.getxPawnByLocation(new PawnLocate(X,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }else{
                            if(caroGame.getoPawnByLocation(new PawnLocate(X,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }
            }
            if(findCount >=5)
                    return true;
            return false;
    }
        private boolean checkRightUpAxis() {
            int findCount = 1;
            PawnLocate lastLocation = pawn.getLocation();
            int lastX = lastLocation.getX();
            int lastY = lastLocation.getY();
            int X = lastX;
            int Y = lastY;
            while(true){
                    X++;
                    Y++;
                    if(this.isX){
                            if(caroGame.getxPawnByLocation(new PawnLocate(X,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }else{
                            if(caroGame.getoPawnByLocation(new PawnLocate(X,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }
            }
            X = lastX;
            Y = lastY;
            while(true){
                    X--;
                    Y--;
                    if(X<0||Y<0)
                            break;
                    if(this.isX){
                            if(caroGame.getxPawnByLocation(new PawnLocate(X,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }else{
                            if(caroGame.getoPawnByLocation(new PawnLocate(X,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }
            }
            if(findCount >=5)
                    return true;
            return false;
    }

    private boolean checkYAxis() {
            int findCount = 1;
            PawnLocate lastLocation = pawn.getLocation();
            int lastX = lastLocation.getX();
            int lastY = lastLocation.getY();
            int X = lastX;
            while(true){
                    X ++;
                    if(this.isX){
                            if(caroGame.getxPawnByLocation(new PawnLocate(X,lastY))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }else{
                            if(caroGame.getoPawnByLocation(new PawnLocate(X,lastY))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }
            }
            X = lastX;
            while(true){
                    X --;
                    if(X<0)
                            break;
                    if(this.isX){
                            if(caroGame.getxPawnByLocation(new PawnLocate(X,lastY))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }else{
                            if(caroGame.getoPawnByLocation(new PawnLocate(X,lastY))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }
            }
            if(findCount >=5)
                    return true;
            return false;
    }

    private boolean checkXAxis() {
            int findCount = 1;
            PawnLocate lastLocation = pawn.getLocation();
            int lastX = lastLocation.getX();
            int lastY = lastLocation.getY();
            int Y = lastY;
            while(true){
                    Y ++;
                    if(this.isX){
                            if(caroGame.getxPawnByLocation(new PawnLocate(lastX,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }else{
                            if(caroGame.getxPawnByLocation(new PawnLocate(lastX,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }
            }
            Y = lastY;
            while(true){
                    Y --;
                    if(Y<0)
                            break;
                    if(this.isX){
                            if(caroGame.getxPawnByLocation(new PawnLocate(lastX,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }else{
                            if(caroGame.getxPawnByLocation(new PawnLocate(lastX,Y))!=null){
                                    findCount++;
                                    if(findCount >=5)
                                            return true;
                            }else{
                                    break;
                            }
                    }
            }
            if(findCount >=5)
                    return true;
            return false;
    }

}
