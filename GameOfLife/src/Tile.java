import java.awt.Color;
import java.awt.event.*;

import javax.swing.JButton;

public class Tile {
	public JButton button;
	private boolean alive;
	public int x, y;
	
	public Tile(int x, int y) {
		this.x = x; this.y = y;
		
		button = new JButton("0");
		button.setBackground(Color.RED);
		setAlive(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAlive();
			}
		});
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		System.out.println("Tile " + x + "x" + y + ": " + alive);
		this.alive = alive;
		if(alive) {
			button.setText("1");
			button.setBackground(Color.GREEN);
			
		}
		else {
			button.setText("0");
			button.setBackground(Color.RED);
		}
		button.revalidate();
		button.repaint();
	}

	private void toggleAlive() {
		setAlive(!alive);
	}
}
