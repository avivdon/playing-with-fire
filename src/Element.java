import javax.swing.ImageIcon;
import java.util.Random;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.Timer;
import java.awt.event.*;
import java.util.TimerTask;
public abstract class Element extends JLabel
{
	protected Boolean solid;
	protected double coordX;
	protected double coordY;
	protected int zoneType = 0;
	protected Bomb bomb = null;
	protected Timer timer = new Timer();
	protected Bonus bonus = null;
	protected int man; // id
	//protected Fire fire = null;
	protected int zoneTime = 0;
	public Boolean getSolid()
	{
		return solid;
	}
	public int getZoneType()
	{
		return zoneType;
	}
	public void setZoneType(int man, int dir)
	{
		//if(zoneType == 1)
		//{
			/*
			if(fire == null)
				this.fire = new Fire((int)coordX, (int)coordY, dir, this);
			else
			{
				fire.setDir(dir);
			}
			*/
			this.man = man;
			this.zoneType++;
			updateIcon(man, dir);
			if(zoneTime == 0)
				timer.schedule(new ZoneTimer(), 10);
			zoneTime = 1200;
			repaint();
			//return fire;
		//}
		//return null;
	}
	public int getMan()
	{
		return man;
	}
	public double getCoordX()
	{
		return coordX;
	}
	public double getCoordY()
	{
		return coordY;		
	}
	public void setCoordX(int x)
	{
		coordX = x;
		updateLocation();
	}
	public void setCoordY(int y)
	{
		coordY = y;
		updateLocation();
	}
	public void updateLocation()
	{
		if(Math.abs(Math.round(coordX) - coordX) <= 0.015625)
			this.coordX = Math.round(coordX);
		if(Math.abs(Math.round(coordY) - coordY) <= 0.015625)
			this.coordY = Math.round(coordY);
		setLocation((int)(this.coordX * 40), (int)(this.coordY * 40));
	}
	public Boolean getHasBomb()
	{
		if(bomb == null)
		return false;
		return true;
	}
	public Boolean getHasBonus()
	{
		if(bonus == null)
			return false;
		return true;
	}
	public Bomb getBomb()
	{
		return bomb;
	}
	public void putBomb(Bomb bomb)
	{
		this.bomb = bomb;
	}
	public Bomb removeBomb()
	{
		Bomb bomb = this.bomb;
		this.bomb = null;
		return bomb;
	}
	public Bonus removeBonus()
	{
		Bonus bonus = this.bonus;
		this.bonus = null;
		return bonus;
	}
	public int getZoneTime()
	{
		return zoneTime;
	}
	public void printElement()
	{
		if(this instanceof Floor)
			System.out.println("Floor: ");
		else if(this instanceof Block)
			System.out.println("Block: ");
		else
			System.out.println("Element:");
		System.out.print("coordX = " + coordX + " | coordY = " + coordY);
		System.out.println(" | HasBomb = " + getHasBomb() + " | HasBonus = " + getHasBonus() + " | Solid = " + getSolid() + " | ZoneType = " + getZoneType());
	}
	public void updateIcon(int color, int dir)
	{
		/*
		if(dir == 0)
			setIcon(new ImageIcon("GLE1 0.jpg"));
		else if(dir == 1)
			setIcon(new ImageIcon("FIREV 0.jpg"));
		else
			setIcon(new ImageIcon("GLE3 0.jpg"));
			*/
		setIcon(new ImageIcon("icons/FIRE" + dir + " " + color + ".jpg"));
		if(bonus != null)
			bonus.setVisible(false);
	}
	private class ZoneTimer extends TimerTask
	{
		public void run()
		{
			if(zoneTime > 0)
			{
				zoneTime--;
				timer.schedule(new ZoneTimer(), 1);
			}
			else
			{
				zoneType = 0;
				man = -1;
				setIcon(new ImageIcon("Floor.jpg"));
				if(bonus != null)
					bonus.updateIcon();
				solid = false;
			}
		}
	}
}
