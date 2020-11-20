import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Fire extends Element
{
	private int dir;
	private Element base;
	public Fire(int locX, int locY, int dir, Element element)
	{
		solid = false;;
		base = element;
		coordX = locX;
		coordY = locY;
		this.dir = dir;
		setSize(40, 40);
		updateLocation();
		updateIcon();
	}
	public void updateIcon()
	{
		if(dir == 0)
			setIcon(new ImageIcon("GLE1.jpg"));
		else if(dir == 1)
			setIcon(new ImageIcon("GLE2.jpg"));
		else
			setIcon(new ImageIcon("GLE3.jpg"));
	}
	public void setDir(int dir)
	{
		this.dir = dir;
		updateIcon();
	}
	public Element getBase()
	{
		return base;
	}
}
