package training_programs;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MenuGame {
  JFrame frame;
  GameWindow paintGame = new GameWindow(400, 300);
  MenuWindow paintMenu = new MenuWindow();
  int x = 10;
  int y = 10;
  Rectangle dim = new Rectangle(100, 100, 400, 300);
  boolean isGame = false;
  int stage = 1;

  public static void main(String[] args) {
    new MenuGame().go();
  }

  public void go() {
    frame = new JFrame("X - O");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);

    Thread chmg = new Thread(new ChoiserMenuGame());
    chmg.start();
    
    frame.setBounds(dim);
    frame.setVisible(true);
  }
  
  class ChoiserMenuGame implements Runnable {

	@Override
	public void run() {
		try {
		while(stage != 4) {
			if(stage == 1) {
				frame.getContentPane().removeAll();
				
				MenuWindow menuWindow = new MenuWindow();
				frame.getContentPane().add(BorderLayout.CENTER, menuWindow);
				menuWindow.addMouseListener(menuWindow);
				frame.validate();
		    	frame.repaint();
		    	stage = 3;
		    } else if(stage == 2) {
		    	frame.getContentPane().removeAll();
		    	
		    	GameWindow gameWindow = new GameWindow(400, 300);
		    	frame.getContentPane().add(BorderLayout.CENTER, gameWindow);
		    	gameWindow.addMouseListener(gameWindow);
		    	frame.validate();
		    	frame.repaint();
				stage = 3;
		    } else
		    	Thread.sleep(50);
		}
		frame.dispose();
		} catch(Exception e) { e.printStackTrace(); }
	}
  }
  
  class MenuWindow extends JPanel implements MouseListener {
	  
	public void paintComponent(Graphics g) {
		g.setColor(new Color(30, 150, 220));
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    
	    g.setColor(new Color(0, 0, 0));
	    g.drawString("New Game", (int)(this.getWidth()/2)-5, (int)(this.getHeight()/2)-5);
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		stage = 2;
		this.removeMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
  
  }
  
  class GameWindow extends JPanel implements MouseListener {
	int widthBox;
    int heightBox;
    Color col = new Color(50, 40, 20);
    int tolLin = 4;
    Color fone = new Color(0, 200, 255);
    int[][] battle = new int[3][3];
    int numHods = 0;

    public GameWindow(int w, int h) {
    	widthBox = (int) (w/3);
    	heightBox = (int) ((h-30)/3); // TODO Корявая запись по вычислению размеров
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    			battle[i][j] = 0;
    }

    public void paintComponent(Graphics g) {
      paintBattlePole(g);
      paintHodes(g);
      //paintHods(g);
    }

    public void paintBattlePole(Graphics g) {
      g.setColor(fone);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      g.setColor(new Color(10, 30, 20));
      g.fillRect((widthBox-(int)(tolLin/2)), 0, tolLin, this.getHeight());
      g.setColor(new Color(10, 30, 20));
      g.fillRect((widthBox*2-(int)(tolLin/2)), 0, tolLin, this.getHeight());

      g.setColor(new Color(10, 30, 20));
      g.fillRect(0, (heightBox-(int)(tolLin/2)), this.getWidth(), tolLin);
      g.setColor(new Color(10, 30, 20));
      g.fillRect(0, (heightBox*2-(int)(tolLin/2)), this.getWidth(), tolLin);
    }

    public void paintHodes(Graphics g) {
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    			if(battle[i][j] == 1) {
    				int x = (int)(widthBox*(i+1) - widthBox/2);
    				int y = (int)(heightBox*(j+1) - heightBox/2);
    				paintO(g, x, y, 58, 70, fone, new Color(255, 0, 0));
    			} else if(battle[i][j] == 2) {
    				int x = (int)(widthBox*(i+1) - widthBox/2);
    				int y = (int)(heightBox*(j+1) - heightBox/2);
    				paintX(g, x, y, 26, 32, fone, new Color(255, 0, 0));
    			}
    }
    
    public void paintX(Graphics g, int x, int y, int size1, int size2, Color col1, Color col2) {
    	g.setColor(col2);
        g.fillRect((x-size2), (y-size2), size2*2, size2*2);
        g.setColor(col1);
        g.fillRect((x-size1), (y-size1), size1*2, size1*2);
    }
    		
    public void paintO(Graphics g, int x, int y, int diam1, int diam2, Color col1, Color col2) {
      g.setColor(col2);
      g.fillOval((x-(int)diam2/2), (y-(int)diam2/2), diam2, diam2);
      g.setColor(col1);
      g.fillOval((x-(int)diam1/2), (y-(int)diam1/2), diam1, diam1);
    }

    public void paintHods(Graphics g) {
    	g.setColor(col);
        g.fillOval((x-25), (y-25), 50, 50);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
      Point p = e.getPoint();
      x = (int) p.getX();
      y = (int) p.getY();
      
      int r = 0;
      int g = 0;
      int b = 0;
      int i = (int) (255/3.5f);
      int kx = 0;
      int ky = 0;
      
      if(x <= widthBox) {
    	  r = 255-i;
    	  kx = 0; 
      }
      else if(x > widthBox && x <= widthBox*2) {
    	  r = 255-i*2;
    	  b = i*2;
    	  kx = 1;
      }
      else if(x > widthBox*2) {
    	  r = 255-i*3;
    	  kx = 2;
      }

      if(y <= heightBox) {
    	  g = 255-i;
    	  ky = 0;
      }
      else if(y > heightBox && y <= heightBox*2) {
    	  g = 255-i*2;
    	  b = i*2;
    	  ky = 1;
      }
      else if(y > heightBox*2) {
    	  g = 255-i*3;
    	  ky = 2;
      }
      
      //  Ход игрока
      if(battle[kx][ky] == 0) {
    	  battle[kx][ky] = 2;
    	  numHods++;
      }
      
      if(numHods <= 4)
    	  hodeKomp();
      
      if(getWin() != null) {
    	  stage = 1;
    	  this.removeMouseListener(this);
      }
      
      col = new Color(r, g, b);
      repaint();
    }

    public void hodeKomp() {
    	boolean endHod = true;
    	while(endHod) {
    		int kompx = (int)(Math.random()*3);
    		int kompy = (int)(Math.random()*3);
    		if(battle[kompx][kompy] == 0) {
    			battle[kompx][kompy] = 1;
    			endHod = false;
    		}
    	}
    }
    
    public String getWin() {
    	String winner = null;
    	boolean endGame = true;
    	
    	for(int i = 0; i < 3; i++)
    		if(battle[i][0] == 2 && battle[i][1] == 2 && battle[i][2] == 2) {
    			winner = "You Win!";
    		} else if(battle[i][0] == 1 && battle[i][1] == 1 && battle[i][2] == 1) {
    			winner = "Win Comp";
    		} else if(battle[0][i] == 2 && battle[1][i] == 2 && battle[2][i] == 2) {
    			winner = "You Win!";
    		} else if(battle[0][i] == 1 && battle[1][i] == 1 && battle[2][i] == 1) {
    			winner = "Win Comp";
    		}
    	
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    			if(battle[i][j] == 0) {
    				endGame = false;
    			}
    	
    	//if(endGame)
    		return winner;
    	/*else
    		return "Nicia!";*/
    }
    
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}
  }
}
