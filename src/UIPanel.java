import java.awt.*;
import java.awt.Graphics.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.applet.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JDialog;

import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.plaf.PanelUI;

@SuppressWarnings("unused")

public class UIPanel<InputStream> extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	// Positions on X and Y for the Ball, & Player 1 + Player 2 Positions
	private int ballX = 20, 
			ballY = 300, 
			Player1X = 5, 
			Player1Y = 100,
			Player2X = 485, 
			Player2Y = 260;
	
	Thread connection;
	int right = 5; // To the Right
	int left = - 5; // To the Left
	int upward = 5; // Upward
	int downward = - 5; // Downward
	int width, height; // Width and Height of the Ball
	int contPlay1 = 0, contPlay2 = - 1;
	boolean player1FlagA, player1FlagB, player2FlagA, player2FlagB;
	boolean play, gameOver, ScoreObjective;
	static JButton playAgain;

	public UIPanel() {
		play = true;
		connection = new Thread(this);
		connection.start();
	}
	
	// Draw Components
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		// Draw Background Image
		Image backgroundImage = Toolkit.getDefaultToolkit().getImage(UIPanel.class.getResource("background.jpg"));
		g.drawImage(backgroundImage, 0, 0, null);

		// Draw Background Image (OLD THREAD)
		//ImageIcon icon = new ImageIcon("src/background.jpg");
		//g.drawImage(icon.getImage(), 0, 0, null);
		
		// Drawing URL Images (UNUSED TESTING)
		//BufferedImage image = ImageIO.read(new URL("http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png"));
		
		// Draw Ball
		g.setColor(Color.black);
		g.fillOval(ballX, ballY, 7, 7);

		// Draw Players
		g.setColor(Color.blue);
		g.fillRect(Player1X, Player1Y, 6, 50);
		g.fillRect(Player2X, Player2Y, 6, 50);

		// Draw Scores
		g.setColor(Color.white);
		g.drawString("PLAYER 1 SCORE: " + contPlay1, 18, 15);
		g.drawString("PLAYER 2 SCORE: " + contPlay2, 358, 15);
		
		// Draw Score Limit
		if (ScoreObjective)
			g.drawString("1st Player to 5 Wins. Ready? GO!", 158, 40);
			g.setColor(Color.white);
		
		// Draw Game Over
		if (gameOver)
			g.drawString("Game Over", 218, 40);
			g.setColor(Color.white);
				
			// Draw Play Again Button
        	playAgain = new JButton("Play Again?");
        	playAgain.addActionListener(new MyAction());
      	}
    
	/* Action Request of the Button. */
	public class MyAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(playAgain, "Request");
        }
    }
	
	// Positions on X and Y for the Ball
	public void DrawBall(int nx, int ny) 
	{
		ballX = nx;
		ballY = ny;
		this.width = this.getWidth();
		this.height = this.getHeight();
		repaint();
	}

	// Keyboard Movements (PART 1)
	public void keyPressed(KeyEvent evt)
	{
		switch (evt.getKeyCode())
		{
		
		// Keyboard Movement of Y for Player 1
		case KeyEvent.VK_W:
			player1FlagA = true;
			break;
		case KeyEvent.VK_S:
			player1FlagB = true;
			break;

		// Keyboard Movement of Y for Player 2
		case KeyEvent.VK_UP:
			player2FlagA = true;
			break;
		case KeyEvent.VK_DOWN:
			player2FlagB = true;
			break;
		}
	}

	// Keyboard Keystrokes (PART 2)
	public void keyReleased(KeyEvent evt) 
	{
		switch (evt.getKeyCode())
		{
		
		// Keyboard Movement of Y for Player 1
		case KeyEvent.VK_W:
			player1FlagA = false;
			break;
		case KeyEvent.VK_S:
			player1FlagB = false;
			break;

		// Keyboard Movement of Y for Player 2
		case KeyEvent.VK_UP:
			player2FlagA = false;
			break;
		case KeyEvent.VK_DOWN:
			player2FlagB = false;
			break;
		}
	}

	// Move Player 1
	public void movePlayer1() {
		if (player1FlagA == true && Player1Y >= - 10)
			Player1Y += downward;
		if (player1FlagB == true && Player1Y <= (this.getHeight() - 30))
			Player1Y += upward;
		DrawPlayer1(Player1X, Player1Y);
	}

	// Move Player 2
	public void movePlayer2() {
		if (player2FlagA == true && Player2Y >= - 10)
			Player2Y += downward;
		if (player2FlagB == true && Player2Y <= (this.getHeight() - 30))
			Player2Y += upward;
		DrawPlayer2(Player2X, Player2Y);
	}

	// Position on Y for Player 1
	public void DrawPlayer1(int x, int y)
	{
		this.Player1X = x;
		this.Player1Y = y;
		repaint();
	}

	// Position on Y for Player 2
	public void DrawPlayer2(int x, int y)
	{
		this.Player2X = x;
		this.Player2Y = y;
		repaint();
	}

	public void run() 
	{
		boolean MoveLeft = false;
		boolean MoveRight = false;

		while (true) {

			if (play) {
				
				// Ball Movement from Left to Right
				if (MoveLeft) {
					// Move Right
					ballX += right;
					if (ballX >= (this.getWidth() - 8))
						MoveLeft = false;
				} else {
					// Move Left
					ballX += left;
					if (ballX <= 0)
						MoveLeft = true;
				}

				// Ball Movement from Upward to Downward
				if (MoveRight) {
					// Moves Upward
					ballY += upward;
					if (ballY >= (this.getHeight() - 8))
						MoveRight = false;

				} else {
					// Moves Downward
					ballY += downward;
					if (ballY <= 0)
						MoveRight = true;
				}
				DrawBall(ballX, ballY);

				// Game Delay
				try {
					Thread.sleep(35);
				} catch (InterruptedException ex) 
				{

				};

				// Move Player 1
				movePlayer1();

				// Move Player 2
				movePlayer2();

				// Score of Player 1 Increases
				if (ballX >= (this.getWidth() - 8))
					contPlay1++;

				// Score of Player 2 Increases
				if (ballX == 0)
					contPlay2++;

				// Begin Score Limit
				if (contPlay2 == 0)
					ScoreObjective = true;
				
				// End Score Limit
				if (contPlay1 >= 1 || contPlay2 >= 1)
					ScoreObjective = false;
				
				// Game Over
				if (contPlay1 == 5 || contPlay2 == 5)
				{
					play = false;
					gameOver = true;
		        		playAgain.setBackground(new Color(248, 213, 131));
						playAgain.setBounds (new Rectangle (187, 427, 128, 40));
						playAgain.setLayout(null);
						add(playAgain, BorderLayout.PAGE_END);
						add(playAgain).setVisible(true);
				}

				// The Ball Stroke with Player 1
				if (ballX == (Player1X + 5) && ballY >= Player1Y
						&& ballY <= (Player1Y + 50))
					MoveLeft = true;

				// The Ball Stroke with Player 2
				if (ballX == (Player2X - 5) && ballY >= Player2Y
						&& ballY <= (Player2Y + 50))
					MoveLeft = false;
				}
			}
		}
};