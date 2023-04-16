package tetris;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;

public class tetris extends JFrame {

	 Board board;

  public tetris() {
	  setSize(800,790);	  
	  setTitle("Tetris 1.0V");
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
	  setLocationRelativeTo(null);
	  board = new Board();
	  //adding panel
	  add(board);
	  //adding keylistner to the panel
	  addKeyListener(board);
	  
	  setVisible(true);
  }

}