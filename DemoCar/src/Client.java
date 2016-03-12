	
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Client {
		public static JFrame f ;
	    JButton[][] button ;
	    static boolean flat = false ;
	    
	    boolean winner;
	    
	    
	    JPanel p;
	    int xx, yy, x, y;
	    {
	    	x = 10;
	    	y = 10;
	    }
	    
	    
	    int [][]matrix;
	    int [][]matrixnote;
	    
	    //Client Socket
	    Socket socket ;
	    OutputStream os ;
	    InputStream is ;
	    ObjectOutputStream oos ;
	    ObjectInputStream ios ;
	    
	    //menu bar
	    MenuBar menubar ;
	    
	    class NewGame implements ActionListener {
	    	  @Override
	            public void actionPerformed(ActionEvent e) {
	                newgame();
	                try{
	                    oos.writeObject("newgame,123");
	                }catch(IOException ie){
	                    //
	                }
	            }
	    
	    }
	    /**
	     * 
	     */
	    public void newgame() {
	        for(int i = 0; i < x; i++)
	            for(int j = 0; j < y; j++){
	                button[i][j].setIcon(null);
	                
	                matrix[i][j] = 0;
	                matrixnote[i][j] = 0;
	            } 
	            setEnableButton(true);
	            
	    }
	    public void setEnableButton(boolean b){
	        for (int i = 0; i < x; i++) {
	            for (int j = 0; j < y; j++) {
	                if (matrixnote[i][j] == 0) {
	                	button[i][j].setEnabled(b);
	                }
	            }
	        }
	    }
	    public void setVisiblePanel(JPanel pHienthi){
			f.add(pHienthi);
			pHienthi.setVisible(true);
			pHienthi.updateUI();
	    }
	    
	    public void initMatrix() {
			for (int i = 0; i < x; i++) {
		            for (int j = 0; j < y; j++) {
		            	matrix[i][j] = 0;
		            }
			}
	    }

	    /**
	     * 
	     * Kiem tra trow thua
	     */
	    
	    public int checkCol() {
	        int win = 0, collum = 0;
	        boolean check=false;
	        for (int j=0;j<y;j++) {
	            for (int i=0;i<x;i++) {
	            	if (check) {
	                    if (matrix[i][j]==1){
	                    	collum++;
	                    	if (collum>4){
	                            win=1;
	                            break;
	                    	}
	                    	continue;
	                    }else {
	                    	check=false;
	                    	collum=0;
	                    }
	            	}
	            	if (matrix[i][j]==1){
	                    check=true;
	                    collum++;
	            	}else{
	                    check = false ;
	                }
	            }
	            collum=0;
	        }
	        return win;
	    }
	    
	    public int checkRow(){
			int win = 0, row = 0, n = 0, k = 0;
			boolean check = false;
			
			for (int i = 0; i < x; i++) {
		            for (int j = 0;j < y; j++) {
		            	if (check) {
		            		if (matrix[i][j] == 1) {
		            			row++;
		            			if (row > 4) {
		            				win = 1;
		            				break;
		            			}
		            			continue;
		            		}else {
		            			check = false;
		            			row = 0;
		            		}
		            	}
		            	if (matrix[i][j] == 1) {
		                    check=true;
		                    row++;
		            	}else{
		                    check = false ;
		                }
		            }
		            row=0;
				}
				return win;
	    }
	    
	    public int checkWN_ES(){
			int win = 0, wn_es = 0,n = 0,k = 0;
			boolean check = false;
			
			for (int i = x -1; i >= 0; i--) {
				for (int j = 0; j < y; j++) {                            
					if (check) {
						if (matrix[n - j][ j ] == 1){
							wn_es++;
		                    if(wn_es > 4){
		                    	win = 1;
		                        break;
		                    }
		                    continue;
		                    }else {
		                    	check = false;
		                    	wn_es = 0;
		                    }
					}
				if (matrix[i][j] == 1) {
					n = i + j;
					check = true;
					wn_es++;
				}else {
					check = false ;
				}
			}
				wn_es =0;
		        check = false ;
			}
	        return win;
	    }
	    
	    public int checkWS_EN() {
			int win = 0, ws_en = 0, n = 0;
			boolean check = false;
			for (int i = 0; i < x; i++) {
		        for (int j = y-1;j >= 0; j--){
					if (check){
						if (matrix[n - j - 2 * ws_en][j] == 1){
							ws_en++;
			               
			                if (ws_en > 4) {
			                	win=1;
			                    break;
			                }
			                continue;
						}else {
							check = false;
							ws_en = 0;
						}
					}
					if (matrix[i][j] == 1){
			            n = i + j;
			            check = true;
			            ws_en++;
					}else{
			            check = false ;
					}
		        }
		        n = 0;
			    ws_en = 0 ;
			    check = false ;
			}
			return win;
	    }
	    
	    
	    public Client(){
	    	
	    	matrix = new int[x][y];
	        matrixnote = new int[x][y];
	    	
	        f = new JFrame();
	        f.setTitle(JOptionPane.showInputDialog("Nhap ten nguoi choi: "));
	        f.setSize(700, 500);
	        x=10;y=10;
	        f.getContentPane().setLayout(null);
	        f.getContentPane().setBackground(Color.GREEN);
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setVisible(true);
	        f.setResizable(false);
	        //
	        
	        menubar = new MenuBar();
	        p = new JPanel();
	        p.setBounds(10, 30, 400, 400);
	        p.setLayout(new GridLayout(x,y));
	        f.add(p);

	        //menu bar game
	        f.setMenuBar(menubar);
	        Menu game = new Menu("Game");
	        menubar.add(game);
	        MenuItem newItem = new MenuItem("New Game");
	        game.add(newItem);
	        MenuItem exit = new MenuItem("Exit");
	        game.add(exit);
	        game.addSeparator();
	        newItem.addActionListener(new NewGame());
	        exit.addActionListener(new ActionListener(){

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0);
	            }           
	        });
	        
	        ///Game Caro
	        button=new JButton[x][y];
	        for (int i = 0; i < x; i++){
	            for(int j = 0; j < y; j++){
	                final int a = i,b = j;
	        
	                button[a][b]= new JButton();
	                button[a][b].setBackground(Color.ORANGE);
	                button[a][b].setBounds(i * 40, j * 40, 40, 40);
	                button[a][b].addActionListener(new ActionListener(){
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        flat = true ;//server da click
	                        
	                        matrixnote[a][b] = 1;
	                        button[a][b].setEnabled(false);
	                        if(Server.flat) {
	                            setEnableButton(true);
	                        }
	                        else { 
	                            setEnableButton(false);
	                        }
	                        button[a][b].setIcon(new ImageIcon("C:\\Users\\NguyenDuc\\Pictures\\X11-icon.png"));
	                        try{
	                            oos.writeObject("caro," + a + ","+b);
	                        }catch(IOException ie){
	                            ie.printStackTrace();
	                        }
	                    }
	                });
	                
	                p.add(button[a][b]);
	            }
	        }
	        ///main
	        initMatrix();
	        try{
	            socket = new Socket("localhost",1234);
	            System.out.println("Da ket noi toi server!");
	            os= socket.getOutputStream();
	            oos= new ObjectOutputStream(os);
	            is= socket.getInputStream();
	            ios= new ObjectInputStream(is);
	            
	            System.out.println("Doc tu socket");
	            while(true){
	                String stream = ios.readObject().toString();
	                String[] message = stream.split(",");
	                if(message[0].equals("caro")) {
	                    caro(message[1],message[2]);
	                    if(winner == false)
	                        setEnableButton(true);
	                }else if(message[0].equals("newgame")){
	                    newgame();
	                }else if(message[0].equals("checkwin")){
	                   
	                }
	            }
	        }catch(Exception ie){
	            ie.printStackTrace();
	        }finally{
	            try {
	            	if (socket != null)
	            		socket.close();
	            	System.exit(0);
	            } catch (IOException ex) {
	                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	                JOptionPane.showMessageDialog(null, "Error connect");
	                
	            }
	        }      
	    }
	    public void caro(String x,String y){
	        xx=Integer.parseInt(x);
	        yy=Integer.parseInt(y);
	    
	        matrix[xx][yy]=1;
	        matrixnote[xx][yy]=1;
	        button[xx][yy].setEnabled(false);
	        button[xx][yy].setIcon(new ImageIcon("C:\\Users\\NguyenDuc\\Pictures\\Letter-O-blue-icon.png"));
	        
	        winner = (checkRow()==1||checkCol()==1||checkWN_ES()==1||checkWS_EN()==1);
	        
	        if(checkRow()==1 || checkCol()==1 || checkWN_ES()==1 || checkWS_EN()==1) {
	            setEnableButton(false);
	            
	            try {
	                oos.writeObject("checkwin,123");
	            } catch (IOException ex) {

	            }
	            Object[]options={"Đồng ý","Hủy bỏ"};
	            int m=JOptionPane.showConfirmDialog(f,"Bạn thua! Bạn có muốn chơi lại?","THÔNG BÁO",JOptionPane.YES_NO_OPTION);
	            if (m==JOptionPane.YES_OPTION){
	                
	                setVisiblePanel(p);
	                newgame();
	                try{
	                    oos.writeObject("newgame,123");
	                }catch(IOException ie){
	                    //
	                }
	            }else if(m==JOptionPane.NO_OPTION){
	             
	            }
	        }
	    }
	  public static void main(String[] args) {
	        new Client();
	    }
	    
}
