import javax.swing.JButton;

public class Tile {
	private JButton button;
	private boolean alive;
	
	public Tile(JButton button) {
		this.button = button;
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
		
	}

	
	
	
}
