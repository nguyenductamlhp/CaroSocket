package offical.caro.data;

public class Board {

	int[][] Field;
	Player player1, player2;
	boolean isXGo;
	
	//////////
	static final int D_UP        = 0;
    static final int D_UPRIGHT    = 1;
    static final int D_RIGHT    = 2;
    static final int D_DOWNRIGHT= 3;
    static final int D_DOWN        = 4;
    static final int D_DOWNLEFT    = 5;
    static final int D_LEFT        = 6;
    static final int D_UPLEFT    = 7;
    
    static final int NOT5        = 0;
    static final int OK5        = 1;
    static final int ILL_CHOUREN= 2;
    static final int ILL_33        = 3;
    static final int ILL_44        = 4;
    static final int ILL_NOT    = 5;
    
    int Dx[] = new int[8];
    int Dy[] = new int[8];
    {
	    Dx[0] =  0;  Dy[0] = -1;
	    Dx[1] =  1;  Dy[1] = -1;
	    Dx[2] =  1;  Dy[2] =  0;
	    Dx[3] =  1;  Dy[3] =  1;
	    Dx[4] =  0;  Dy[4] =  1;
	    Dx[5] = -1;  Dy[5] =  1;
	    Dx[6] = -1;  Dy[6] =  0;
	    Dx[7] = -1;  Dy[7] = -1;
    }
	
	
	public Board() {
		Field = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Field[i][j] = Pawn.EMPTY; 
			}
		}
		player1 = new Player();
		player2 = new Player();
		isXGo = true;
	}
	
	public int GetSequence(int chr,int x,int y,int direction) {
        int num = 0;
        int dx = Dx[direction];
        int dy = Dy[direction];

        
        while(Field[x][y] == chr) {
            num++;
            x += dx; y += dy;
            if( x < 0 || x >= 8 || y < 0 || y >= 8 ) break;
            if(Field[x][y] == Pawn.EMPTY) {
               
                break;
            }
        }
        return num;
    }
    
    
    public boolean Find5Block(int color,int x,int y) {

        int max,a;

        max = GetSequence(color,x,y,D_UP) + GetSequence(color,x,y,D_DOWN) - 1 ;
        a =GetSequence(color,x,y,D_LEFT) + GetSequence(color,x,y,D_RIGHT) - 1 ;
        max = Math.max(max,a);
        a = GetSequence(color,x,y,D_UPLEFT) + GetSequence(color,x,y,D_DOWNRIGHT) -1 ;
        max = Math.max(max,a);
        a = GetSequence(color,x,y,D_UPRIGHT) + GetSequence(color,x,y,D_DOWNLEFT) - 1 ;
        max = Math.max(max,a);

        if( max >= 5)
            return true;

        return false;
    }
    
    public void putPawn(Pawn p, int x, int y) {
    	if (x < 8 && y < 8 && ((p.Statetus >= 0) && (p.Statetus <= 3))) {
    		Field[x][y] = p.Statetus;
    	}
    	
    }

    public int[][] getBoard() {
    	return Field;
    }

	public void Print() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.println(Field[i][j] = Pawn.EMPTY); 
			}
		}
	}
}
