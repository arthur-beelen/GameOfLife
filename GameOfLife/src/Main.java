import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

public class Main {
	public static void main(String args[]){
		JFrame launcherFrame = new JFrame("Launcher Game Of Life");
		launcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridLayout(1, 2));
		JLabel dimensionLabel = new JLabel("Dimension: ");
		JTextField dimensionField = new JTextField();
		settingsPanel.add(dimensionLabel); 
		settingsPanel.add(dimensionField);
		dimensionField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dimensionField.setText(dimensionField.getText().replace("\\D", ""));
			}
		});
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				World world = new World(Integer.parseInt(dimensionField.getText()));
			}
		});
		mainPanel.add(settingsPanel);
		mainPanel.add(startButton);
		
		launcherFrame.add(mainPanel);
		launcherFrame.setSize(450, 120);
		launcherFrame.setVisible(true);
		

	}
	
	
}
