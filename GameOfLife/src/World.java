import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

public class World {
	Tile[][] tiles; //Array of all tiles
	int dimension;
	JFrame frame;
	JPanel buttonPanel;
	
	
	public World(JFrame frame, int dimension) {
		this.dimension = dimension;
		this.frame = frame;
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(dimension, dimension));
		fillButtons(buttonPanel, dimension);
		frame.getContentPane().add(buttonPanel);
		
		JButton startButton = new JButton("Next");
		frame.add(startButton, BorderLayout.SOUTH);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
	}
	
	private void fillButtons(JPanel buttonPanel, int dimension) {
		tiles = new Tile[dimension][dimension];
		for(int x = 0; x < dimension; x++) {
			for(int y = 0; y < dimension; y++) {
				tiles[x][y] = new Tile(x, y);
				buttonPanel.add(tiles[x][y].button);
			}
		}
	}
	
	private void start() {
			boolean[][] states = new boolean[dimension][dimension];
			for(int x = 0; x < dimension; x++) {
				for(int y = 0; y < dimension; y++) {
					states[x][y] = newStateOfTile(tiles[x][y]);
				}
			}
			applyNewStates(states);
	}
	
	private boolean newStateOfTile(Tile tile) {
		int neighboursAlive = 0;
		for(int i = tile.x-1; i <= tile.x+1; i++) {
			for(int j = tile.y-1; j <= tile.y+1; j++) {
				if(i >= 0 && i < dimension && j >= 0 && j < dimension) {
					if(i != tile.x || j != tile.y)
						if(tiles[i][j].isAlive())
							neighboursAlive++;
				}
			}
		}
		
		if(tile.isAlive()) {
			System.out.println("Number of neighbours: " + neighboursAlive);
		}
		
		
		if(tile.isAlive()) {
			if(neighboursAlive < 2 || neighboursAlive > 3) {
				System.out.println("Dies.");
				return false;
			}
			else {
				System.out.println("Stays alive.");
				return true;
			}
		}
		else {
			if(neighboursAlive == 3)
				return true;
		}
		return false;
	}
	
	private void applyNewStates(boolean[][] states) {
		for(int x = 0; x < dimension; x++) {
			for(int y = 0; y < dimension; y++) {
				if(states[x][y] != tiles[x][y].isAlive())
					tiles[x][y].setAlive(states[x][y]);
			}
		}
	}
}
