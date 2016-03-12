

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class Server {
	public static JFrame f;
	JButton[][] bt;
	static boolean flat = false;
	boolean winner;
	
	// module ban co
	JPanel p;
	int xx, yy, x, y;
	int[][] matrix;
	int[][] matrixnote;
	
	// Server Socket
	ServerSocket serversocket;
	Socket socket;
	OutputStream os;
	InputStream is;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	// menu bar
	MenuBar menubar;

	/**
	 * 
	 */


	public void initMatrix() {
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				matrix[i][j] = 0;
			}
	}

	// Kiểm tr thắng thua
	public int checkRow() {
		int win = 0, row = 0;// n = 0, k = 0;
		boolean check = false;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (check) {
					if (matrix[i][j] == 1) {
						row++;
						if (row > 4) {
							win = 1;
							break;
						}
						continue;
					} else {
						check = false;
						row = 0;
					}
				}
				if (matrix[i][j] == 1) {
					check = true;
					row++;
				} else {
					check = false;
				}
			}
			row = 0;
		}
		return win;
	}

	public int checkCol() {
		int win = 0, collum = 0;
		boolean check = false;
		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				if (check) {
					if (matrix[i][j] == 1) {
						collum++;
						if (collum > 4) {
							win = 1;
							break;
						}
						continue;
					} else {
						check = false;
						collum = 0;
					}
				}
				if (matrix[i][j] == 1) {
					check = true;
					collum++;
				} else {
					check = false;
				}
			}
			collum = 0;
		}
		return win;
	}

	public int chechWN_ES() {
		int win = 0, wn_es = 0, n = 0, k = 0;
		boolean check = false;
		for (int i = x - 1; i >= 0; i--) {
			for (int j = 0; j < y; j++) {
				if (check) {
					if (matrix[n - j][j] == 1) {
						wn_es++;
						if (wn_es > 4) {
							win = 1;
							break;
						}
						continue;
					} else {
						check = false;
						wn_es = 0;
					}
				}
				if (matrix[i][j] == 1) {
					n = i + j;
					check = true;
					wn_es++;
				} else {
					check = false;
				}
			}
			wn_es = 0;
			check = false;
		}
		return win;
	}

	public int checkWS_EN() {
		int win = 0, ws_en = 0, n = 0;
		boolean check = false;
		for (int i = 0; i < x; i++) {
			for (int j = y - 1; j >= 0; j--) {
				if (check) {
					if (matrix[n - j - 2 * ws_en][j] == 1) {
						ws_en++;
						if (ws_en > 4) {
							win = 1;
							break;
						}
						continue;
					} else {
						check = false;
						ws_en = 0;
					}
				}
				if (matrix[i][j] == 1) {
					n = i + j;
					check = true;
					ws_en++;
				} else {
					check = false;
				}
			}
			n = 0;
			ws_en = 0;
			check = false;
		}
		return win;
	}

	public void newgame() {
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				bt[i][j].setIcon(null);
				matrix[i][j] = 0;
				matrixnote[i][j] = 0;
			}
		setEnableButton(true);
		
	}

	public void setEnableButton(boolean b) {
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				if (matrixnote[i][j] == 0)
					bt[i][j].setEnabled(b);
			}
	}

	public void setVisiblePanel(JPanel pHienthi) {
		f.add(pHienthi);
		pHienthi.setVisible(true);
		pHienthi.updateUI();
	}
	// ///
	public Server() throws IOException {
		f = new JFrame();
		f.setTitle(JOptionPane.showInputDialog("Nhap ten nguoi choi: "));
		f.setSize(700, 500);
		x = 10;
		y = 10;
		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(Color.green);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setResizable(false);
		//
		matrix = new int[x][y];
		matrixnote = new int[x][y];
		menubar = new MenuBar();
		p = new JPanel();
		p.setBounds(10, 30, 400, 400);
		p.setLayout(new GridLayout(x, y));
		f.add(p);
		
		// menu bar game
		f.setMenuBar(menubar);// tao menubar cho frame
		Menu game = new Menu("Game");
		menubar.add(game);
		MenuItem newItem = new MenuItem("New Game");
		game.add(newItem);
		MenuItem exit = new MenuItem("Exit");
		game.add(exit);
		game.addSeparator();
		newItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newgame();
				try {
					oos.writeObject("newgame,123");
				} catch (IOException ie) {
					ie.printStackTrace();
				}
			}

		});
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// /Game Caro
		bt = new JButton[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				final int a = i, b = j;
				bt[a][b] = new JButton();
				bt[a][b].setBackground(Color.ORANGE);
				bt[a][b].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						flat = true;
						matrixnote[a][b] = 1;
						bt[a][b].setEnabled(false);
						if (Client.flat) {
							setEnableButton(true);
						}
						else {
							setEnableButton(false);
						}
						bt[a][b].setIcon(new ImageIcon("C:\\Users\\NguyenDuc\\Pictures\\Letter-O-blue-icon.png"));
						try {
							oos.writeObject("caro," + a + "," + b);
						} catch (IOException ie) {
							ie.printStackTrace();
						}
					}

				});
				p.add(bt[a][b]);
			}
		}
		// /main
		initMatrix();
		try {
			serversocket = new ServerSocket(1234);
			System.out.println("Đợi kết nối ...");
			socket = serversocket.accept();
			System.out.println("Đã kết nối!");
			os = socket.getOutputStream();
			is = socket.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
			while (true) {
				String stream = ois.readObject().toString();
				String[] data = stream.split(",");
				if (data[0].equals("caro")) {
					
					caro(data[1], data[2]);
					if (winner == false)
					setEnableButton(true);
				} else if (data[0].equals("newgame")) {
					newgame();
				} else if (data[0].equals("checkwin")) {
					
				}
				
			}
		} catch (Exception ie) {
			 ie.printStackTrace();
		} finally {
			socket.close();
			serversocket.close();
		}
		
	}

	public void caro(String x, String y) {
		xx = Integer.parseInt(x);
		yy = Integer.parseInt(y);
		// danh dau vi tri danh
		matrix[xx][yy] = 1;
		matrixnote[xx][yy] = 1;
		bt[xx][yy].setEnabled(false);
		bt[xx][yy].setIcon(new ImageIcon("C:\\Users\\NguyenDuc\\Pictures\\X11-icon.png"));
		// Kiem tra trow hay chua
		winner = (checkRow() == 1 || checkCol() == 1 || chechWN_ES() == 1 || checkWS_EN() == 1);
		if (checkRow() == 1 || checkCol() == 1 || chechWN_ES() == 1 || checkWS_EN() == 1) {
			setEnableButton(false);
			
			try {
				oos.writeObject("checkwin,123");
			} catch (IOException ex) {

			}
			Object[] options = { "Có", "Không" };
			int m = JOptionPane.showConfirmDialog(f, "Bạn thua.Bạn có muốn chơi lại không?", "Thông báo",JOptionPane.YES_NO_OPTION);
			if (m == JOptionPane.YES_OPTION) {
				
				setVisiblePanel(p);
				newgame();
				try {
					oos.writeObject("newgame,123");
				} catch (IOException ie) {

				}
			} else if (m == JOptionPane.NO_OPTION) {
				
			}
		}
	}


	public static void main(String[] args) throws IOException {
		// TODO code application logic here
		new Server();
	}

}
