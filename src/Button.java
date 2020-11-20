import javax.swing.*;
import java.awt.event.*;
public class Button extends JButton
{
	public Button(int sizeX, int sizeY, int locX, int locY, ImageIcon icon, ActionListener al)
	{
		setSize(sizeX, sizeY);
		setLocation(locX, locY);
		setIcon(icon);		
		setBorderPainted(false);
		setOpaque(false);
		setContentAreaFilled(false);
		addActionListener(al);	
	}
	public Button(int sizeX, int sizeY, int locX, int locY, String text, ActionListener al)
	{
		setSize(sizeX, sizeY);
		setLocation(locX, locY);
		setText(text);		
		setBorderPainted(false);
		setOpaque(false);
		setContentAreaFilled(false);
		addActionListener(al);	
	}
}
