import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Bonus extends Element
{
	private int type; // | 0, 1 - Speed | 2, 3 - Power | 4 - Strength |
	public Bonus(int locX, int locY, int type)
	{
		this.type = type;
		solid = false;;
		coordX = locX;
		coordY = locY;
		setSize(40, 40);
		updateLocation();
	}
	public void updateIcon()
	{
		setVisible(true);
		if(type < 2)
			setIcon(new ImageIcon("icons/BONUS BOMBS.png"));
		else if(type < 4)
			setIcon(new ImageIcon("icons/BONUS BOMBPOWER.png"));
		else if(type < 5)
			setIcon(new ImageIcon("icons/BONUS STRENGTH.png"));
		else if(type < 6)
			setIcon(new ImageIcon("icons/BONUS SPEED.png"));
		else if(type < 7)
			setIcon(new ImageIcon("icons/BONUS LIFE.jpg"));
		else if(type < 8)
			setIcon(new ImageIcon("icons/BONUS SHIELD.png"));

		repaint();
	}
	public void chooseBonusType(Man man)
	{
		if(type < 2)
			man.giveBomb();
		else if(type < 4)
			man.incBombPower();
		else if(type < 5)
			man.giveStrength();
		else if(type < 6)
			man.incSpeed();
		else if(type < 7)
			man.incLife();
		else if(type < 8)
			man.activateBonusShield();
	}
}
