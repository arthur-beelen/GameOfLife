import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class World {
	ArrayList<Tile> tiles; //Array of all tiles
	int dimension;
	JFrame frame;
	JLabel timeLabel;
	JLabel averageTimeLabel;
	long averageTime;
	JLabel stepsLabel;
	int steps;
	
	
	
	public World(JFrame frame, int dimension) {
		this.dimension = dimension;
		this.frame = frame;
		
		tiles = new ArrayList<Tile>();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(dimension, dimension));
		fillButtons(buttonPanel, dimension);
		frame.getContentPane().add(buttonPanel);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(1, 4));
		JButton startButton = new JButton("Next");
		controlPanel.add(startButton);
		timeLabel = new JLabel("Time = ");
		averageTimeLabel = new JLabel("Avg. time = ");
		stepsLabel = new JLabel("Steps = ");
		controlPanel.add(timeLabel);
		controlPanel.add(averageTimeLabel);
		controlPanel.add(stepsLabel);
		frame.add(controlPanel, BorderLayout.SOUTH);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		
	}
	
	private void fillButtons(JPanel buttonPanel, int dimension) {
		for(int x = 0; x < dimension; x++) {
			for(int y = 0; y < dimension; y++) {
				
				tiles.add(new Tile(x, y));
				buttonPanel.add(tiles.get(tiles.size()-1).button);
			}
		}
	}
	
	private void start() {
		final long timeStart = System.nanoTime();
		
		//500-800 탎
		/*
		 * 
		for(int x = 0; x < dimension; x++) {
			for(int y = 0; y < dimension; y++) {
				newStateOfTile(tiles.get(getIndex(x, y)));
			}
		}
		*/
		//500-800 탎
		//tiles.stream().forEach(tile -> newStateOfTile(tile));
		
		//150-250 탎 (~400 탎 for a lot of life in the game)
		tiles.parallelStream().forEach(tile -> newStateOfTile(tile));
		
		final long timed = System.nanoTime() - timeStart;
		++steps;
		timeLabel.setText("Time = " + timed/1000 + "탎");
		averageTime = (long)(((double)steps-1.0d)/(double)steps * averageTime + 1.0d/(double)steps * timed);
		averageTimeLabel.setText("Avg. time = " + averageTime/1000 + " 탎");
		stepsLabel.setText("Steps = " + steps);
		applyNewStates();
	}
	
	private int getIndex(int x, int y) {
		return x*dimension + y;
	}
	
	private void newStateOfTile(Tile tile) {
		int neighboursAlive = 0;
		for(int i = tile.x-1; i <= tile.x+1; i++) {
			for(int j = tile.y-1; j <= tile.y+1; j++) {
				if(i >= 0 && i < dimension && j >= 0 && j < dimension) {
					if(i != tile.x || j != tile.y)
						if(tiles.get(getIndex(i, j)).isAlive())
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
				tile.setAliveNext(false);
			}
			else {
				System.out.println("Stays alive.");
				tile.setAliveNext(true);
			}
		}
		else {
			if(neighboursAlive == 3)
				tile.setAliveNext(true);
		}
	}
	
	private void applyNewStates() {
		for(int x = 0; x < dimension; x++) {
			for(int y = 0; y < dimension; y++) {
				int index = getIndex(x, y);
				if(tiles.get(index).isAliveNext() != tiles.get(index).isAlive())
					tiles.get(index).toggleAlive();
			}
		}
	}
}
