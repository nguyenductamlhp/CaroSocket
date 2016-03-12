package offical.caro.data;

public class Player {

	private String Username;
	private String Password;
	private Pawn plPawn;
	
	
	public Player() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public Pawn getPlPawn() {
		return plPawn;
	}

	public void setPlPawn(Pawn plPawn) {
		this.plPawn = plPawn;
	}
	
	

}
