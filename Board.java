package tetris;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.*;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener {
	
	
	
	public static int  play=0;
	public static int pause=1;
	public static int end=2;
	
	private int state = play;
	
	//timer to refresh to screen
	private Timer looper;
	// cordinate of the object
	private int x=4 , y=0;
	// the board
	private Color[][] board = new Color[25][20];

	//refresh speed
	public static int FPS=60;
	public static int delay = FPS/100;
	private int normal=500;
	private static int delaytimemov=500;
	private int fast =50;
	private long bt;
	
	
	private int dx=0;
	private boolean colision = false;
	
	// colors used on the shapes
	private Color[]colors = {
			Color.red,Color.green,Color.yellow,Color.BLUE,Color.ORANGE,Color.PINK,Color.lightGray 
	};
	// array of shapes
	private Shape [] shapes = new Shape[7];
	// the current shape
	private Shape current;
	private Random rand;
	
	public void setcurrent()
	{
		rand= new Random();
		current=shapes[rand.nextInt(shapes.length)];
		current.reset();
		endit();
	}
	
	//check if the game is over
	
	private void endit()
	{
		int [][] cords = current.getcords();
		for ( int row =0 ; row < cords.length ; row++)
		{
			
			for (int col=0 ; col < cords[0].length;col++)
			{
				
				
				if (cords[row][col ]!= 0)
				{
					if (board[row+current.gety()][col + current.getx()]!=null)
					{
						state=end;
					}
				}
			}
		}
	}
	
	public Color[][] getboard()
	{
		return board;
	}
	public Board()
	{
		
		shapes[0]= new Shape(new int [][] {{1,1,1,1}},this,colors[0]);//line horizontal
		shapes[1]= new Shape(new int [][] {{1,1,1},{0,1,0}},this,colors[1]);//T block
		shapes[2]= new Shape(new int [][]{{1,1,1},{1,0,0}},this,colors[2]);//L block
		shapes[3]= new Shape(new int [][]{{1,1,1},{0,0,1}},this,colors[3]);// reverse L block
		shapes[4]= new Shape(new int [][] {{0,1,1},{1,1,0}},this,colors[4]);// squigelly
		shapes[5]= new Shape(new int [][] {{1,1,0},{0,1,1}},this,colors[5]);// reverse squigelly
		shapes[6]= new Shape(new int [][]{{1,1},{1,1}},this,colors[6]);// squere
		current = shapes[0];// set current objet to line (initiation)
		
		
		looper = new Timer(delay, new ActionListener() {

			
			@Override
			
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();// draw agin
			}
			
		});
		looper.start();
	}
	private void update()
	{	
		if (state== play)
		current.update();
		
	}
	
	@Override
	protected void paintComponent( Graphics g )
	{
		super.paintComponent(g);
		//background
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		current.render(g);
		//color shpes 
		for (int row=0 ; row <board.length ; row++)
		{
			for (int col=0;col <board[row].length;col++)
			{
				if (board[row][col] != null) {
					g.setColor(board[row][col]);
					g.fillRect(col * 30, row *30, 30, 30);
				}
			}
		}
		
		//board
		g.setColor(Color.white);
		for (int row=0 ; row <26 ; row++) {
			
			g.drawLine(0, 30 * row, 600, 30 * row);
		}
		
		for (int col=0 ; col <21 ; col++) {
			int j=10;
			g.drawLine(col* 30 ,0, 30 * col, 750);
		}
		
		g.setColor(Color.WHITE);
		g.drawString("Score: ", 650, 150);
		g.drawString( Integer.toString(current.gets())  , 700, 150);
	
		if ( state == end )
		{
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER", 650, 50);
		}
		
		if ( state == pause )
		{
			g.setColor(Color.WHITE);
			g.drawString("| | GAME Pause ", 650, 50);
		}
	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		

		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			current.speedup();
			
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			current.moveright();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			current.moveleft();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			current.rotateshape();
		}
		
		if (state == end)
		{
			if (e.getKeyCode() == KeyEvent.VK_SPACE )
			{
				for (int row=0 ; row <board.length ; row++)
				{
					for (int col=0;col <board[row].length;col++)
					{
						board[row][col]=null;
					}
				}
				setcurrent();
				state=play;
			}
		}
		
		
		
		
			if (e.getKeyCode() == KeyEvent.VK_P )
			{
				if (state == play)
				{
					state=pause;
				}
				else if (state == pause)
				{
					state=play;
				}
			}
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			current.speeddown();
		}
		
	}
}
