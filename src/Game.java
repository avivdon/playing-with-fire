import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.FileNotFoundException;
public class Game extends JFrame
{
	private MyActionListener al;
	private Button back;
	private GamePanel p;
	public Game(int opNum) throws FileNotFoundException
	{		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("icons/PWF- BOMB 0.png").getImage());
		al = new MyActionListener();
		MyKeyListener kl = new MyKeyListener();
		addKeyListener(kl);
		setLocation(200, 60);
		setSize(768, 569);
		JPanel mainP = new JPanel();
		mainP.setLayout(null);
		mainP.setBackground(Color.black);		
		add(mainP);
		JPanel boardP = new JPanel();
		boardP.setLayout(null);
		boardP.setSize(135, 520);
		boardP.setLocation(5, 5);
		boardP.setBackground(Color.gray);
		back = new Button(90, 30, 21, 480, new ImageIcon("ButtonMini.png"), al);		
		boardP.add(back);
		System.out.println(opNum);
		ScorePanel[] scorePanel = new ScorePanel[opNum + 1];
		for(int i = 0 ; i < scorePanel.length; i++)
		{
			scorePanel[i] = new ScorePanel(i);
			boardP.add(scorePanel[i]);
		}
		mainP.add(boardP);
		p = new GamePanel(scorePanel, this);
		mainP.add(p);
		setVisible(true);
	}
	
	private class MyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton jb = (JButton)e.getSource();
			if(jb == back)
			{
				new PlayFrame();
				dispose();
			}
		}
	}
	private class MyKeyListener implements KeyListener
	{
		
		public void keyTyped(KeyEvent e) 
		{
			
		}
		
		public void keyPressed(KeyEvent e) 
		{
			/*
			int keyCode = e.getKeyCode();
			switch(keyCode)
			{
			case KeyEvent.VK_UP:
				System.exit(0);
				break;
			case KeyEvent.VK_DOWN:
				p.move(1);
				break;
			case KeyEvent.VK_RIGHT:
				p.move(2);
				break;
			case KeyEvent.VK_LEFT:
				p.move(3);
				break;
			}
			*/
		}

		
		public void keyReleased(KeyEvent e) 
		{
			
		}
	}
}
