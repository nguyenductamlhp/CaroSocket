package interfaces;

import java.util.ArrayList;

public interface Board {
        public void draw(ArrayList<Pawn> cps);
        public void clear();

        public void drawAdd(Pawn cp);
        public void drawRemove(Pawn cp);
}
