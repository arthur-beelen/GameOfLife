import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class World {
	static public ArrayList<Tile> tiles; //Array of all tiles
	int dimension;
	JFrame frame;
	JLabel timeLabel;
	JLabel averageTimeLabel;
	long averageTime;
	JLabel stepsLabel;
	int steps;
	
	JRadioButton r1;
	JRadioButton r2;
	JRadioButton r3;
	
	enum Performance_Mode {
		FOR_LOOP,
		STREAM,
		PARALLELSTREAM
	}
	
	
	public World(int dimension) {
		this.dimension = dimension;
		
		frame = new JFrame("Game Of Life");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(960,1080);
		frame.setVisible(true);
		
		
		tiles = new ArrayList<Tile>();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(dimension, dimension));
		fillButtons(buttonPanel, dimension);
		frame.getContentPane().add(buttonPanel);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(1, 5));
		JButton startButton = new JButton("Next");
		controlPanel.add(startButton);
		
		
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(3, 1));
		r1 = new JRadioButton("For loop (1T)");
		r2 = new JRadioButton("Stream (1T)");
		r3 = new JRadioButton("ParallelStream (MT)");
		ButtonGroup bg = new ButtonGroup();
		bg.add(r1); bg.add(r2); bg.add(r3);
		radioPanel.add(r1); radioPanel.add(r2); radioPanel.add(r3);
		r1.setSelected(true);
		controlPanel.add(radioPanel);
		
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
		Performance_Mode pMode;
		if(r1.isSelected())
			pMode = Performance_Mode.FOR_LOOP;
		else if(r2.isSelected())
			pMode = Performance_Mode.STREAM;
		else 
			pMode = Performance_Mode.PARALLELSTREAM;
		
		long timeStart = System.nanoTime();
		long timed;
		
		//650 탎 90x90
		if(pMode == Performance_Mode.FOR_LOOP) {
			timeStart = System.nanoTime();
			for(int x = 0; x < dimension; x++) {
				for(int y = 0; y < dimension; y++) {
					newStateOfTile(tiles.get(getIndex(x, y)));
				}
			}
			timed = System.nanoTime() - timeStart;
		}
		//650 탎 90x90
		else if(pMode == Performance_Mode.STREAM) {
			timeStart = System.nanoTime();
			tiles.stream().forEach(tile -> newStateOfTile(tile));
			timed = System.nanoTime() - timeStart;
		}
		
		//150 탎 90x90
		else {
			timeStart = System.nanoTime();
			tiles.parallelStream().forEach(tile -> newStateOfTile(tile));
			timed = System.nanoTime() - timeStart;
		}
		
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
			if(neighboursAlive < 2 || neighboursAlive > 3) {
				tile.setAliveNext(false);
			}
			else {
				tile.setAliveNext(true);
			}
		}
		else {
			if(neighboursAlive == 3)
				tile.setAliveNext(true);
		}
	}
	
	private void applyNewStates() {
		tiles.parallelStream()
						.filter(tile -> tile.isAliveNext() != tile.isAlive())
						.forEach(tile -> tile.toggleAlive());
	}
}
