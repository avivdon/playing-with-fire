import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
public class InstructionsFrame extends JFrame
{
	private MyActionListener al;
	private Button back;
	public InstructionsFrame()
	{		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("icons/PWF- BOMB 0.png").getImage());
		setTitle("Playing with Fire");
		al = new MyActionListener();		
		setLocation(250, 25);
		setSize(850, 700);
		JPanel p = new JPanel();
		p.setBackground(Color.black);
		p.setLayout(null);
		p.setSize(850, 600);
		back = new Button(100, 100, 10, 530, new ImageIcon("Button.png"), al);		
		p.add(back);
		JLabel background = new JLabel(new ImageIcon("Instructions.png"));
		background.setSize(850, 522);
		background.setLocation(-10, 127);
		p.add(background);
		JLabel  title = new JLabel(new ImageIcon("icons/Instructions.png"));
		title.setSize(432, 127);
		title.setLocation(209, 0);
		p.add(title);
		//JLabel title = new JLabel(new ImageIcon("Instructions.jpg"));
		//title.setSize(257, 91);
		//title.setLocation(297, 10);
		//p.add(title);
		//JLabel backgroundLabel = new JLabel(new ImageIcon("Background1.jpg"));
		//backgroundLabel.setSize(850, 600);
		//p.add(backgroundLabel);
		add(p);
		setVisible(true);
	}
	
	private class MyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton jb = (JButton)e.getSource();
			if(jb == back)
			{
				new MainFrame();
				dispose();
			}
		}
	}
}
