import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
public class Floor extends Element
{
	public Floor(int locX, int locY, Boolean solid)
	{
		this.solid = solid;
		coordX = locX;
		coordY = locY;
		setSize(40, 40);
		updateLocation();
		setIcon(new ImageIcon("Floor.jpg"));
	}
	public Bonus tryBonus()
	{
		Random r = new Random();
		int num = r.nextInt(16);
		if(num < 8) // | 0, 1 - Bomb | 2, 3 - Power | 4 - Strength | 5 - Speed | 6 - Life | 7 - Shield | 8-15 - Nothing |
		{
			bonus = new Bonus((int)coordX, (int)coordY, num);
			return bonus;
		}
		return null;
	}
	public Bonus getBonus()
	{
		return bonus;
	}
}
