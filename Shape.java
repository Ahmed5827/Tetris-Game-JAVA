package tetris;

import java.awt.Color;
import java.awt.Graphics;

public class Shape {
	
	private static int  s =0;
	
	private int x=4 , y=0;
	private int normal=500;
	private int fast =50;
	private static int delaytimemov=500;
	private long bt;
	
	public int cords[][];
	private int dx=0;
	private boolean colision = false;
	
	
	private Board board;
	private Color color;
	
	public Shape(int cords[][],Board board ,Color color)
	{
		this.cords = cords;
		this.board=board;
		this.color=color;
		
	}
	
	public void setX(int x) {
		this.x=x;
	}
	
	public void setY(int y) {
		this.y=y;
	}
	
	public void reset()
	{
		this.x=4;
		this.y=0;
		this.colision=false;
		sets(1);
		
	}
	
	
	
	public void update()
	{
		if (colision)
		{
			
			// fill the color on board
			for (int row =0 ; row < cords.length ; row++)
			{
				for (int col=0;col < cords[0].length;col++)
				{
					if (cords[row][col]!=0)
					{
						//set current cords to a color
						board.getboard()[y+row][x+col]=color;
					}
				}
			}
			checkline();
			// set current
			board.setcurrent();
			
			return;
			
		}
		//move shape left & right
		boolean moveX = true;
		if ((!(x+dx+cords[0].length > 20) && !(x+dx <0)))
		{
			for (int row=0;row < cords.length ; row++)
			{
				for (int col=0 ; col < cords[row].length ; col++ )
				{
					
					if (cords[row][col]!=0) {
						if (board.getboard()[y+row][x+dx+col]!= null)
						{
							moveX=false;
						}
					}
				
				}
			}
			if (moveX)
			{
				x+=dx;
			}
			
			
		}
		dx=0;
		
		if (System.currentTimeMillis()- bt > delaytimemov)
		{
		if (!(y+cords.length >= 25))
		{
			for (int row=0 ; row <cords.length;row++)
			{
				for (int col =0;col<cords[row].length;col++)
				{
					if (cords[row][col]!=0)
					{
						if (board.getboard()[y+1+row][x+dx+col] != null)
						{
							colision = true;
							
						}
					}
				
				}
			}
			if (!colision) {
				y++;
			}
			
		}
		else 
		{
			colision=true;
			
			
		}
		bt=System.currentTimeMillis();
		}
		
	}
	
	private void checkline()
	{
		
		int bottomline = board.getboard().length -1;
		for (int topline = board.getboard().length-1 ; topline>0 ; topline--)
		{
			int count=0;
			for (int col=0 ; col<board.getboard()[0].length ; col ++)
			{
				if (board.getboard()[bottomline][col] != null)
				{
					
					count++;
				}
				
				
				board.getboard()[bottomline][col]=board.getboard()[topline][col];		
			}
			
			if (count < board.getboard()[0].length) {
				
				bottomline--;
				
				
			}
			
			
			}
		
		
	}
	public void rotateshape()
	{
		int [][]rotatedshape= transposematrix(cords);
		reverserow( rotatedshape );
		//check for right side & buttom
		if ((x+rotatedshape[0].length >20) || (y+rotatedshape.length>20))
		{
			return;
		}
		//check collision with other shapes before rotation
		for (int row =0;row<rotatedshape.length ; row++)
		{
			for (int col=0 ; col <rotatedshape[row].length ; col++)
			{
				if (board.getboard()[y+row][x+col]!=null)
				{
					return;
				}
			}
		}
			cords=rotatedshape;
	}
	
	private int [][]transposematrix(int [][] matrix)
	{
		int [][] temp = new int [matrix[0].length][matrix.length];
		for (int row=0 ; row < matrix.length;row++)
		{
			for (int col=0 ; col<matrix[0].length ; col++)
			{
				temp[col][row] = matrix[row][col];
			}
			
		}
		return temp;
	}
	private void reverserow( int [][]matrix )
	{
		int mid = matrix.length/2;
		for (int row=0 ; row<mid;row++)
		{
			int []temp = matrix[row];
			matrix[row] = matrix[matrix.length-row-1];
			matrix[matrix.length - row -1 ]=temp;
		}
	}
	
	
	// color the place of the shape in movment
	public void render (Graphics g)
	{
		
				for (int row =0;row<cords.length;row++)
				{
					
					for (int  col =0;col<cords[0].length;col++)
					{
						if (cords[row][col]!=0)
						{
							g.setColor(color);
							g.fillRect(col*30+x*30, row*30+y*30, 30, 30);
						}
					
					}
				}
	}
	
	public void speedup()
	{
		delaytimemov=fast;
	}
	public void speeddown()
	{
		delaytimemov=normal;
	}
	public void moveright()
	{
		dx=1;
	}
	public void moveleft()
	{
		dx=-1;
	}
	
	
	public int [] [] getcords()
	{
		return cords;
	}
	
	public int getx()
	{
		return x;
		
	}
	public int gety()
	{
		return y;
	}
	
	public int gets()
	{
		return s;
	}
	public void sets( int x)
	{
		s=s+x;
	}	

	
	
}
