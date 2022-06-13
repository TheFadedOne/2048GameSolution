
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameFrame extends JFrame implements ActionListener {

	public GamePanel game;
	
	public GameFrame()
	{
		startGame();
	}
	
	public void startGame()
	{
		game = new GamePanel();
		
		this.add(game);
		this.setTitle("Not 2048 - release 1.1");
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setFocusable(false);
		this.setLocationRelativeTo(null);
		ImageIcon image = new ImageIcon("images/2048.png");
		this.setIconImage(image.getImage());
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == game.resetGameButton)
		{
			
		}
	}
	
	
}
