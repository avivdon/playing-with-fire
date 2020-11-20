import javax.swing.*;
import java.awt.event.*;
public class HighScoreFrame extends JFrame
{
	private MyActionListener al;
	private Button back;
	public HighScoreFrame()
	{		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		al = new MyActionListener();		
		setLocation(250, 25);
		setSize(850, 600);
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setSize(850, 600);
		back = new Button(128, 127, 361, 430, new ImageIcon("Back.gif"), al);		
		p.add(back);
		JLabel title = new JLabel(new ImageIcon("High Score.jpg"));
		title.setSize(239, 68);
		title.setLocation(305, 30);
		p.add(title);
		JLabel table = new JLabel(new ImageIcon("Table.jpg"));
		table.setSize(650, 338);
		table.setLocation(100, 98);
		p.add(table);
		JLabel backgroundLabel = new JLabel(new ImageIcon("Background1.jpg"));
		backgroundLabel.setSize(850, 600);
		p.add(backgroundLabel);
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
