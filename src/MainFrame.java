import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
public class MainFrame extends JFrame
{
	protected Button quitButton;
	protected Button playButton;
	protected Button instructionsButton;
	protected Button hsButton;
	protected MyActionListener al;
	public MainFrame()
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("icons/PWF- BOMB 0.png").getImage());
		setTitle("Playing with Fire");
		al = new MyActionListener();
		setLocation(250, 25);		
		setSize(850, 600);
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setSize(850, 600);
		playButton = new Button(428, 126, 206, 50, new ImageIcon("icons/PLAY.png"), al);
		p.add(playButton);
		instructionsButton = new Button(432, 127, 209, 176, new ImageIcon("icons/INSTRUCTIONS.png"), al);
		p.add(instructionsButton);
		//hsButton = new Button(374, 110, 238, 303, new ImageIcon("icons/HIGH_SCORE.png"), al);
		//p.add(hsButton);
		quitButton = new Button(335, 98, 257, 303, new ImageIcon("icons/Quit.png"), al);
		p.add(quitButton);
		//JLabel iconLabel = new JLabel(new ImageIcon("Icon.jpg"));
		//iconLabel.setSize(250, 187);
		//iconLabel.setLocation(300, 0);
		//p.add(iconLabel);
		JLabel backgroundLabel = new JLabel(new ImageIcon("icons/BACKGROUND.png"));
		backgroundLabel.setSize(850, 600);
		p.add(backgroundLabel);
		p.setBackground(Color.gray);
		add(p);

		setVisible(true);	
	}
	protected class MyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton jb = (JButton)e.getSource();
			if(jb == playButton)
			{
				new PlayFrame();
				dispose();
			}
			else if(jb == instructionsButton) 
			{
				new InstructionsFrame();
				dispose();
			}
			else if(jb == hsButton)
			{
				new HighScoreFrame();
				dispose();
			}
			else if(jb == quitButton)
			{
				System.exit(0);
			}			
			
		}
	}

	
}
