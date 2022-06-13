import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;


public class GamePanel extends JPanel {
	
	private final int SCREEN_WIDTH = 600;
	private final int SCREEN_HEIGHT = 600;
	private GameArray game;
	private JLabel[][] gameLabels;
	private JLabel gameOverLabel;
	public boolean gameIsOver = false;
	private JLabel scoreLabel;
	private int currentScore = 0;
	public JButton resetGameButton;
	
	
	//IMAGES
	ImageIcon p0 = new ImageIcon("images/0.png");
	ImageIcon p2 = new ImageIcon("images/2.png");
	ImageIcon p4 = new ImageIcon("images/4.png");
	ImageIcon p8 = new ImageIcon("images/8.png");
	ImageIcon p16 = new ImageIcon("images/16.png");
	ImageIcon p32 = new ImageIcon("images/32.png");
	ImageIcon p64 = new ImageIcon("images/64.png");
	ImageIcon p128 = new ImageIcon("images/128.png");
	ImageIcon p256 = new ImageIcon("images/256.png");
	ImageIcon p512 = new ImageIcon("images/512.png");
	ImageIcon p1024 = new ImageIcon("images/1024.png");
	ImageIcon p2048 = new ImageIcon("images/2048.png");
	ImageIcon p4096 = new ImageIcon("images/4096.png");
	ImageIcon resetButton = new ImageIcon("images/reset_button.png");
	
	public GamePanel()
	{
		startGame();
	}
	
	
	public void startGame()
	{
		//initial panel setup
				this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
				this.setVisible(true);
				this.setFocusable(true);
				this.setLayout(null);
				this.setBackground(new Color(0xc9c6a5));
				game = new GameArray(false);
				gameLabels = new JLabel[4][4];
				
				//Score Label
				scoreLabel = new JLabel();
				this.add(scoreLabel);
				scoreLabel.setFocusable(false);
				scoreLabel.setFont(new Font("Comic Sans", Font.BOLD, 20));
				scoreLabel.setText("Score: " + String.valueOf(currentScore));
				scoreLabel.setBounds(250, 450, 150, 50);
				scoreLabel.setVisible(true);
				
				
				//Game Over Label
				gameOverLabel = new JLabel();
				this.add(gameOverLabel);
				gameOverLabel.setFont(new Font("Comic Sans", Font.BOLD, 40));
				gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
				gameOverLabel.setVerticalAlignment(SwingConstants.CENTER);
				gameOverLabel.setForeground(Color.RED);
				gameOverLabel.setText("GAME OVER");
				gameOverLabel.setBounds(150, 500, 300, 75);
				gameOverLabel.setVisible(false);
				
				
				//Restart Label
				resetGameButton = new JButton();
				this.add(resetGameButton);
				resetGameButton.setBounds(60, 500, 80, 60);
				resetGameButton.setVisible(true);
				resetGameButton.setIcon(resetButton);
				resetGameButton.setFocusable(false);
				resetGameButton.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent event) {
						// TODO Auto-generated method stub
						if (event.getSource() == resetGameButton)
						{
							game.reset();
							updateGameDisplay();
							gameOverLabel.setVisible(false);
						}
					}
				});
				
				
				//Key Listener
				this.addKeyListener(new KeyListener() {
					
					@Override
					public void keyPressed(KeyEvent e) {
						
						if (e.getKeyCode() == KeyEvent.VK_UP)
						{
							game.move('U');
							updateGameDisplay();
						}
						
						if (e.getKeyCode() == KeyEvent.VK_DOWN)
						{
							game.move('D');
							updateGameDisplay();
						}
						
						if (e.getKeyCode() == KeyEvent.VK_RIGHT)
						{
							game.move('R');
							updateGameDisplay();
						}
						
						if (e.getKeyCode() == KeyEvent.VK_LEFT)
						{
							game.move('L');
							updateGameDisplay();
						}
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}


				});
				
				setupGameLabels();
				updateGameDisplay();
	}
	
	
	//updates all of the display labels after a move has occurred on the game board 
	public void updateGameDisplay() 
	{
		updateScoreLabel();
		updateGameOver();
		
		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 4; ++j)
			{
				
				//setting tiles of different values to different colors
				switch (game.gameNums[i][j]) 
				{
					case 0:
						gameLabels[i][j].setIcon(p0);
						break;
					case 2:
						gameLabels[i][j].setIcon(p2);
						break;
					case 4:
						gameLabels[i][j].setIcon(p4);
						break;
					case 8:
						gameLabels[i][j].setIcon(p8);
						break;
					case 16:
						gameLabels[i][j].setIcon(p16);
						break;
					case 32:
						gameLabels[i][j].setIcon(p32);
						break;
					case 64:
						gameLabels[i][j].setIcon(p64);
						break;
					case 128:
						gameLabels[i][j].setIcon(p128);
						break;
					case 256:
						gameLabels[i][j].setIcon(p256);
						break;
					case 512:
						gameLabels[i][j].setIcon(p512);
						break;
					case 1024:
						gameLabels[i][j].setIcon(p1024);
						break;
					case 2048:
						gameLabels[i][j].setIcon(p2048);
						break;
					case 4096:
						gameLabels[i][j].setIcon(p4096);
						break;
				}
			}
		}
	}
	
	
	//updates score label
	public void updateScoreLabel()
	{
		currentScore = getArraySum(game.gameNums);
		scoreLabel.setText("Score: " + String.valueOf(currentScore));
	}
	
	
	//PREDICTS THE FUTURE!!! lol so cool
	//creates replication of GAME object and tests to see if there are any future moves possible, if not, the game is over
	public void updateGameOver()
	{
		gameIsOver = true;
		
		for (int i = 0; i < 4; ++i)
		{
			GameArray movePredictor = new GameArray(true);
			movePredictor.fillArray(game.gameNums);
			
			switch (i)
			{
				case 0:
					movePredictor.move('U');
					break;
					
				case 1:
					movePredictor.move('D');
					break;
					
				case 2:
					movePredictor.move('L');
					break;
					
				case 3:
					movePredictor.move('R');
					break;
			}
			
			if (getArraySum(movePredictor.gameNums) != getArraySum(game.gameNums))
			{
				gameIsOver = false;
				break;
			}
		}
		
		if (gameIsOver == true) {gameOverLabel.setVisible(true);}
	}
	
	
	//initial setup for game tiles
	public void setupGameLabels()
	{
		int numRepeats = 0;
		
		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 4; ++j)
			{
				gameLabels[i][j] = new JLabel();
				this.add(gameLabels[i][j]);
				gameLabels[i][j].setFocusable(false);
				gameLabels[i][j].setOpaque(true);
				gameLabels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				gameLabels[i][j].setVerticalAlignment(SwingConstants.CENTER);
				++numRepeats;
				
				//set label locations
				gameLabels[i][j].setBounds((100 + (100 * j)), (50 + (100 * i)), 100, 100);
				gameLabels[i][j].setVisible(true);
			}
		}
	}
	
	
	//adds up all values in array
	public int getArraySum(int[][] array)
	{
		int sum = 0;
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				sum = sum + array[i][j];
			}
		}
		return sum;
	}
}
