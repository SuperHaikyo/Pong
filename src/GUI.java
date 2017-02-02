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

public class GUI extends JFrame {
	
	public static final String GAME_VERSION = "V9-ALPHA";
	private static final long serialVersionUID = 1L;

private JPanel jContentPane = null;
 private UIPanel<?> panel = null;
 
 @SuppressWarnings("rawtypes")
 
// Draw the Panel
private UIPanel<?> getPanel() 
{
  if (panel == null) 
  {
   panel = new UIPanel();
  }
  return panel;
  }
 
 /**
  * This is the Default Constructor
  */
 public GUI() 
 {
  super();
  
  /* GameSounds Testing */
  //GameSounds sound = new GameSounds();
  //GameSounds.music();
  
  initialize();
  
        // Listeners for Keyboard
        this.addKeyListener(new KeyAdapter()
        {
         // Method for Key Pressed
            public void keyPressed(KeyEvent evt) 
            {
                formKeyPressed(evt);
            }
            // Method for Key Released
            public void keyReleased(KeyEvent evt) 
            {
                formKeyReleased(evt);
            }
        });
 }
 
    // Send the Key Pressed to UIPanel
 private void formKeyPressed(KeyEvent evt)
    {
        panel.keyPressed(evt);
    }
    
    // Send the Key Pressed to UIPanel
    private void formKeyReleased(KeyEvent evt)
    {
        panel.keyReleased(evt);
    }


 /**
  * This method initializes settings of the game
  * 
  * @return void
  */
 private void initialize() 
 {
  this.setResizable(false);
  this.setBounds(new Rectangle(312, 184, 450, 450)); // Position on Desktop
  this.setMinimumSize(new Dimension(500, 500));
  this.setMaximumSize(new Dimension(500, 500));
  this.setContentPane(getJContentPane());
  this.setLocationRelativeTo(null);
  this.setTitle("Pong: The Return of the Ball");
 }


 /**
  * This method initializes borders of the game
  * 
  * @return javax.swing.JPanel
  */
 
 private JPanel getJContentPane() 
 {
  if (jContentPane == null) {
   jContentPane = new JPanel();
   jContentPane.setLayout(new BorderLayout());
   jContentPane.add(getPanel(), BorderLayout.CENTER);
  }
  return jContentPane;
 }
 
 public static void main(String[] args) 
 {
	 
	 /* Console Commands. */
	 System.err.println("Pong: The Return of the Ball");
	 System.err.println("Version 9-ALPHA");
	 System.err.println("Modified by: AJ Valentino");
	 System.err.println("Built on:");
	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	 Calendar cal = Calendar.getInstance();
	 System.err.println(dateFormat.format(cal.getTime()));
	 
	 SwingUtilities.invokeLater(new Runnable() {
   public void run() {
    GUI thisClass = new GUI(); 
    thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    thisClass.setVisible(true);
   }
  });
 }
}