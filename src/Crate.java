import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Crate extends Element
{
	public Crate(int locX, int locY)
	{
		solid = true;
		coordX = locX;
		coordY = locY;
		setSize(40, 40);
		updateLocation();
		setIcon(new ImageIcon("Crate.jpg"));
	}
}
