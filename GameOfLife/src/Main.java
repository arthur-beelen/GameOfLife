import java.util.ArrayList;

import javax.swing.*;

public class Main {
	public static void main(String args[]){
       JFrame frame = new JFrame("Game Of Life");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(960,1080);
       World world = new World(frame, 90);
       frame.setVisible(true);
	}
}
