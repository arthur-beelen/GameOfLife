import java.util.ArrayList;

import javax.swing.*;

public class Main {
	public static void main(String args[]){
       JFrame frame = new JFrame("Game Of Life");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(1920,1080);
       frame.setResizable(false);
       JButton button1 = new JButton("Button 1");
       JButton button2 = new JButton("Button 2");
       frame.getContentPane().add(button1); // Adds Button to content pane of frame
       frame.getContentPane().add(button2);
       frame.setVisible(true);
       
       World world = new World(frame);
	}
}
