import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.FileNotFoundException;
public class PlayFrame extends JFrame
{
	private MyActionListener al;
	private Button [] numButton;
	private Button playButton;
	private Button back;
	private int opNum;
	private JLabel markButton;
	private JLabel numOfOpponentsL;
	public PlayFrame()
	{		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("icons/PWF- BOMB 0.png").getImage());
		setTitle("Playing with Fire");
		al = new MyActionListener();		
		setLocation(250, 25);
		setSize(850, 600);
		JPanel p = new JPanel();
		p.setBackground(Color.black);
		p.setLayout(null);
		p.setSize(850, 600);
		back = new Button(100, 100, 20, 440, new ImageIcon("Button.png"), al);		
		p.add(back);
		playButton = new Button(0, 0, 206, 50, new ImageIcon("icons/Play.png"), al);
		p.add(playButton);
		numOfOpponentsL = new JLabel("Select the number of opponents");
		numOfOpponentsL.setSize(340, 50);
		numOfOpponentsL.setLocation(250, 150);
		numOfOpponentsL.setFont(new Font("Serif", Font.PLAIN, 25));
		numOfOpponentsL.setForeground(Color.white);
		p.add(numOfOpponentsL);
		opNum = -1;
		numButton = new Button[3];
		numButton[0] = new Button(105, 166, 170, 230, new ImageIcon("Button1.png"), al);
		numButton[1] = new Button(105, 120, 375	, 240, new ImageIcon("Button2.png"), al);
		numButton[2] = new Button(97, 128, 580, 240, new ImageIcon("Button3.png"), al);
		for(int i = 0; i < 3; i++)
		{
			p.add(numButton[i]);
		}
		markButton = new JLabel(new ImageIcon("Pointing Hand.png"));
		p.add(markButton, 0);
		JLabel backgroundLabel = new JLabel(new ImageIcon("Background1.jpg"));
		backgroundLabel.setSize(850, 600);
		//p.add(backgroundLabel);
		add(p);
		setVisible(true);
	}
	
	private class MyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton jb = (JButton)e.getSource();
			for(int i = 0; i < 3; i++)
			{
				if(jb == numButton[i])
				{
					opNum = i;
					playButton.setSize(428, 126);
					markButton.setSize(80, 135);
					markButton.setLocation(150 + i * 205, 390);
					numOfOpponentsL.setFont(new Font("Serif", Font.PLAIN, 25));
				}
			}
			if(jb == playButton)
			{
				if(opNum >= 0)
				{
					try {
						new Game(opNum + 1);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
				}
				else
				{
					numOfOpponentsL.setFont(new Font("Serif", Font.BOLD, 25));
				}
			}
			if(jb == back)
			{
				new MainFrame();
				dispose();
			}
		}
	}
}
