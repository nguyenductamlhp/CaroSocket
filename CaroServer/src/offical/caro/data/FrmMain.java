package offical.caro.data;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmMain extends JFrame {

	Board board;
	ListenServer listener1, listener2;
	Pawn pawn;
	private JPanel contentPane;

	
	/**
	 * 
	 */
	public boolean CheckStringInRange(String S){
        char[] temp = S.toCharArray();

        for(int i=0;i<temp.length;i++){
            if(temp[0]==' '){
//                JOptionPane.showMessageDialog(null, "Nhập lại tên vì có khoảng trắng ở đầu");
               return false;
            }
            if(temp[i]==' '){
                continue;
            }
            if((temp[i] < '0' || temp[i] > '9') && (temp[i] < 'a' || temp[i] > 'z') &&( temp[i] < 'A' || temp[i] > 'Z'))
                return false;
        }
        return true;
    }
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain frame = new FrmMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmMain() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					pawn = new Pawn();
					
					ServerSocket serverSocket;
					board = new Board();
					serverSocket = new ServerSocket(1234);
					Socket soc1 = serverSocket.accept();
					listener1 = new ListenServer(soc1);
					pawn.Statetus = Pawn.X;
					board.player1.setUsername(listener1.playerName);
					board.player1.setPlPawn(pawn);
					
					Socket soc2 = serverSocket.accept();
					listener2 = new ListenServer(soc2);
					pawn.Statetus = Pawn.O;
					while (!CheckStringInRange(listener2.playerName) || listener1.playerName.equals(listener2.playerName)) {
						listener2.SendMessage("3");
						
					}
					board.player2.setUsername(listener2.playerName);
					board.player2.setPlPawn(pawn);
					boolean isX = true;
					do {
						int x = listener1.p.x;
						int y = listener1.p.y;
						
						if (board.Find5Block(Pawn.X, x, y)) {
							listener1.SendMessage("X thang");
							listener2.SendMessage("X thang");
							break;
						}
						else if (board.Find5Block(Pawn.O, x, y)) {
							listener1.SendMessage("X thang");
							listener2.SendMessage("X thang");
							break;
						}
						
					} while (true);
					
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		});
		btnStart.setBounds(98, 51, 89, 23);
		contentPane.add(btnStart);
	}
}

