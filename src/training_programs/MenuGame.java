package training_programs;

import javax.swing.*;
import java.awt.event.*;
import java.text.AttributedCharacterIterator;
import java.awt.*;

public class MenuGame {
	JFrame frame;
	Rectangle dim = new Rectangle(100, 100, 400, 300);
	int stage = 1;
	String resultMessage = "";

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
		// stage = 1 - start menu - new game or exit
		// stage = 2 - game window
		// stage = 3 - result window with exit and replace button
		// stage = 4 - work stage for wait
		// stage = 5 - exit game
		
		@Override
		public void run() {
			try {
			while(stage != 5) {
				if(stage == 1) {
					StartWindow startWindow = new StartWindow();
					frame.getContentPane().add(BorderLayout.CENTER, startWindow);
					startWindow.addMouseListener(startWindow);
					startWindow.addMouseMotionListener(startWindow);
					frame.validate();
					frame.repaint();
					stage = 4;
				} else if(stage == 2) {
					GameWindow gameWindow = new GameWindow(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
					frame.getContentPane().removeAll();
					frame.getContentPane().add(BorderLayout.CENTER, gameWindow);
					gameWindow.addMouseListener(gameWindow);
					frame.validate();
					frame.repaint();
					stage = 4;
				} else if(stage == 3) {
					frame.getContentPane().removeAll();
					
					EndWindow endWindow = new EndWindow();
					frame.getContentPane().add(BorderLayout.CENTER, endWindow);
					endWindow.addMouseListener(endWindow);
					frame.validate();
					frame.repaint();
					stage = 4;
				} else
					Thread.sleep(50);
			}
			frame.dispose();
			} catch(Exception e) { e.printStackTrace(); }
		}
	}

	class StartWindow extends JPanel implements MouseListener, MouseMotionListener {
		private static final long serialVersionUID = 1L;
		int widthBox;
		int heightBox;
		float[] selButton = {1f, 1f};
		
		public void paintComponent(Graphics g) {
			paintBackground(g);
			paintButton(g);
		}
		
		public void paintBackground(Graphics g) {
			g.setColor(new Color(30, 150, 220));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			widthBox = (int) (this.getWidth()/4);
			heightBox = (int) (this.getHeight()/16);
		}
		
		public void paintButton(Graphics g) {
			Font font = new Font("arial", Font.BOLD, (int)(widthBox/4f));
			g.setFont(font);
			// Button new game
			g.setColor(new Color((int)(160*selButton[0]), (int)(70*selButton[0]), (int)(40*selButton[0])));
			g.fillRect(widthBox, heightBox*4, widthBox*2, heightBox*3);
			g.setColor(new Color((int)(231*selButton[0]), (int)(150*selButton[0]), (int)(80*selButton[0])));
			g.drawString("New Game", widthBox+25, 95);
			
			// Button exit game
			g.setColor(new Color((int)(50*selButton[1]), (int)(40*selButton[1]), (int)(160*selButton[1])));
			g.fillRect(widthBox, heightBox*8, widthBox*2, heightBox*3);
			g.setColor(new Color((int)(110*selButton[1]), (int)(205*selButton[1]), (int)(200*selButton[1])));
			g.drawString("Exit", widthBox+70, 160);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			Point p = e.getPoint();
			int x = (int) p.getX();
			int y = (int) p.getY();
			
			String s = getSelectedButtonMenu(x, y);
			switch (s) {
			case "New":
				stage = 2;
				this.removeMouseListener(this);
				this.removeMouseMotionListener(this);
				break;
			case "Settings":
				System.out.println("Setings");
				break;
			case "Exit":
				stage = 5;
				this.removeMouseListener(this);
				this.removeMouseMotionListener(this);
				break;
			}
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			Point p = e.getPoint();
			int x = (int) p.getX();
			int y = (int) p.getY();
						
			String s = getSelectedButtonMenu(x, y);
			switch (s) {
			case "New":
				selButton[0] = 1.1f;
				selButton[1] = 1f;
				break;
			case "Exit":
				selButton[0] = 1f;
				selButton[1] = 1.2f;
				break;
			default:
				selButton[0] = 1f;
				selButton[1] = 1f;
				break;
			}
			repaint();
		}

		private String getSelectedButtonMenu(int x, int y) {
			String s = "";
			
			if(x >= widthBox && x <= widthBox*3 && y >= heightBox*4 && y <= heightBox*7) { // new game
				s = "New";
			} else if(x >= widthBox && x <= widthBox*3 && y >= heightBox*8 && y <= heightBox*11) { // exit
				s = "Exit";
			}
			
			return s;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

		@Override
		public void mouseDragged(MouseEvent arg0) {}

	} // End class StartWindow

	class GameWindow extends JPanel implements MouseListener {

		private static final long serialVersionUID = 1L;
		int widthBox;
		int heightBox;
		Color col = new Color(50, 40, 20);
		int lineThick = 4;
		Color foneColor = new Color(0, 200, 255);
		Color lineColor = new Color(30, 110, 240);
		Color playerXColor = new Color(50, 210, 40);
		Color playerOColor = new Color(200, 120, 50);
		int[][] battle = new int[3][3];
		int numMove = 0;

		public GameWindow(int w, int h) {
			widthBox = (int) (w/3);
			heightBox = (int) (h/3);
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					battle[i][j] = 0;
		}

		public void paintComponent(Graphics g) {
			paintBattleground(g);
			paintMoves(g);
		}

		public void paintBattleground(Graphics g) {
			g.setColor(foneColor);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
	
			g.setColor(lineColor);
			g.fillRect((widthBox-(int)(lineThick/2)), 0, lineThick, this.getHeight());
			g.setColor(lineColor);
			g.fillRect((widthBox*2-(int)(lineThick/2)), 0, lineThick, this.getHeight());
	
			g.setColor(lineColor);
			g.fillRect(0, (heightBox-(int)(lineThick/2)), this.getWidth(), lineThick);
			g.setColor(lineColor);
			g.fillRect(0, (heightBox*2-(int)(lineThick/2)), this.getWidth(), lineThick);
		}

		public void paintMoves(Graphics g) {
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(battle[i][j] == 1) {
						int x = (int)(widthBox*(i+1) - widthBox/2);
						int y = (int)(heightBox*(j+1) - heightBox/2);
						paintO(g, x, y, 58, 70, foneColor, playerOColor);
					} else if(battle[i][j] == 2) {
						int x = (int)(widthBox*(i+1) - widthBox/2);
						int y = (int)(heightBox*(j+1) - heightBox/2);
						paintX(g, x, y, 26, 32, foneColor, playerXColor);
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

		@Override
		public void mousePressed(MouseEvent e) {
			Point p = e.getPoint();
			int x = (int) p.getX();
			int y = (int) p.getY();
			int kx = 0;
			int ky = 0;
			
			if(x <= widthBox)
				kx = 0; 
			else if(x > widthBox && x <= widthBox*2)
				kx = 1;
			else if(x > widthBox*2)
				kx = 2;
		
			if(y <= heightBox)
				ky = 0;
			else if(y > heightBox && y <= heightBox*2)
				ky = 1;
			else if(y > heightBox*2)
				ky = 2;
			
			//Ход игрока
			if(battle[kx][ky] == 0) {
				battle[kx][ky] = 2;
				numMove++;
			}
			
			// Ход компьютера
			if(numMove <= 4)
				moveComp();
			
			resultMessage = getWin();
			if(resultMessage != null) {
				stage = 3;
				this.removeMouseListener(this);
			}

			repaint();
		}

		public void moveComp() {
			boolean endMove = true;
			while(endMove) {
				int kompx = (int)(Math.random()*3);
				int kompy = (int)(Math.random()*3);
				if(battle[kompx][kompy] == 0) {
					battle[kompx][kompy] = 1;
					endMove = false;
				}
			}
		}
		
		public String getWin() {
			String winner = null;
			boolean endGame = false;
			
			for(int i = 0; i < 3; i++)
				if(battle[i][0] == 2 && battle[i][1] == 2 && battle[i][2] == 2) {
					winner = "You Won!";
				} else if(battle[i][0] == 1 && battle[i][1] == 1 && battle[i][2] == 1) {
					winner = "Won tho Computer!";
				} else if(battle[0][i] == 2 && battle[1][i] == 2 && battle[2][i] == 2) {
					winner = "You Won!";
				} else if(battle[0][i] == 1 && battle[1][i] == 1 && battle[2][i] == 1) {
					winner = "Won tho Computer!";
				}
			
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					if(battle[i][j] == 0) {
						endGame = true;
					}
			
			if(winner != null)
				return winner;
			else if(!endGame)
				return "Draw in game!";
			else return null; 
		}
		
		@Override
		public void mouseExited(MouseEvent e) {}
	
		@Override
		public void mouseEntered(MouseEvent e) {}
	
		@Override
		public void mouseReleased(MouseEvent e) {}
	
		@Override
		public void mouseClicked(MouseEvent e) {}
	} // End class GameWindow

	class EndWindow extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			g.setColor(new Color(200, 150, 180));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			g.setColor(new Color(0, 0, 0));
			g.drawString("End Game - " + resultMessage, (int)(this.getWidth()/2)-40, (int)(this.getHeight()/2));
		}
			
		@Override
		public void mousePressed(MouseEvent arg0) {
			stage = 1;
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
	} // End class EndWindow
}
