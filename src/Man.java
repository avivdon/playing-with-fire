import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Vector;
public class Man extends Element
{
	private int id;
	private ScorePanel scorePanel;
	private double speed;
	private Boolean canPushBomb;
	private int dir;
	//private Boolean hold;
	//private Vector<Integer> path, path1, path2, path3, path4, path5; // | 1 - Attack | 2 - Bonus | 3 - Crate | 4 - Danger |
	//private Element followSecond;
	//private int pathType;
	//private int previousPathType;
	//private int previousTaskSteps;
	private int availableBombs;
	private int shield;
	private int bombPower;
	private Boolean goingToDropBomb;
	private int life;
	public Man(int locX, int locY, int id, ScorePanel scorePanel)
	{
		this.id = id;
		this.scorePanel = scorePanel;
		bombPower = 2;
		goingToDropBomb = false;
		shield = 0;
		availableBombs = 1;
		life = 3;
		//if(id == 0)
		//	availableBombs = 10;
		//previousPathType = 0;
		//pathType = 0;
		//previousTaskSteps = 0;
		//hold = false;
		dir = 1;
		canPushBomb = false;
		//speed = 0.0078125;
		speed = 0.015625;
		//speed = 0.03125;
		//speed = 0.125;
		solid = false;
		coordX = locX;
		coordY = locY;
		timer = new Timer();
		setSize(40, 40);
		updateLocation();
		setIcon(new ImageIcon("icons/PWF M- front " + id + ".png"));
		setOpaque(false);
	}
	public Man(Element man)
	{
		solid = false;
		setSize(40, 40);
		setLocation(man.getLocation());
		setIcon(new ImageIcon("Front Blue.png"));
		setOpaque(false);
	}
	public int getId()
	{
		return id;
	}
	public void incX()
	{
		setIcon(new ImageIcon("icons/PWF M- RSIDE " + id + ".png"));
		coordX = coordX + speed;
		//fixLocation();
		setLocation((int)(coordX * 40), (int)(coordY * 40));
	}
	public void incY()
	{
		setIcon(new ImageIcon("icons/PWF M- front " + id + ".png"));
		coordY = coordY + speed;
		//fixLocation();
		setLocation((int)(coordX * 40), (int)(coordY * 40));

	}
	public void decX()
	{
		setIcon(new ImageIcon("icons/PWF M- LSIDE " + id + ".png"));
		coordX = coordX - speed;
		//fixLocation();
		setLocation((int)(coordX * 40), (int)(coordY * 40));

	}
	public void decY()
	{
		setIcon(new ImageIcon("icons/PWF M-back " + id + ".png"));
		coordY = coordY - speed;
		//fixLocation();
		setLocation((int)(coordX * 40), (int)(coordY * 40));

	}
	public double getSpeed()
	{
		return speed;
	}
	public void incSpeed()
	{
		if(speed == 0.0078125)
		{
			speed = 0.03125;
			scorePanel.incSpeed();
		}
		else if(speed == 0.015625)
		{
			speed = 0.03125;
			scorePanel.incSpeed();
		}
		else if(speed == 0.03125)
		{
				speed = 0.0625;
				scorePanel.incSpeed();
				//System.out.println("BONUS: " + coordX + " ---> " + Math.round(coordX) + " New Speed: " + speed);
		}
		
		else if(speed > 0.0625) // ==
		{
			speed = 0.125;
			scorePanel.incSpeed();
			//System.out.println("BONUS: " + coordX + " ---> " + Math.round(coordX) + " New Speed: " + speed);
		}
		
	}
	public ScorePanel getScorePanel()
	{
		return scorePanel;
	}
	public void decLife()
	{
		life--;
		if(life > 0)
			scorePanel.decLife();
		else
			scorePanel.dead("Burned out");
	}
	public void incLife()
	{
		life++;
		scorePanel.incLife();
	}
	public Boolean isAlive()
	{
		if(life > 0)
			return true;
		return false;
	}
	public void incBombPower()
	{
		if(bombPower < 6)
		{
			bombPower++;
			scorePanel.addPower();
		}
	}
	public void killedOtherMan()
	{
		scorePanel.addScore(300);
	}
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	public Bomb dropBomb(Element[][] elements)
	{
		if(availableBombs > 0)
		{
			availableBombs--;
			goingToDropBomb = false;
			Bomb bomb = new Bomb(this, elements);
			return bomb;
		}
		return null;
	}
	public void bombIsBack()
	{
		availableBombs++;
	}
	public void giveBomb()
	{
		availableBombs++;
		scorePanel.addBomb();
	}
	public Boolean getCanPushBomb()
	{
		return canPushBomb;
	}
	public void giveStrength()
	{
		canPushBomb = true;
		scorePanel.strength();
	}
	public int getBombPower()
	{
		return bombPower;
	}
	public Boolean getGoingToDropBomb()
	{
		return goingToDropBomb;
	}
	public Element getElement(Element[][] elements)
	{
		return elements[(int)(coordX + 0.5)][(int)(coordY + 0.5)];
	}
	public int getDir()
	{
		return dir;
	}
	public void setDir(int dir)
	{
		this.dir = dir;
	}
	public void fixLocation()
	{
		if(Math.abs(Math.round(coordX) - coordX) <= speed / 2)
		{
			//System.out.print(coordX);
			this.coordX = Math.round(coordX);
			//System.out.print(" ---> "+coordX);
			this.coordX = (int)coordX;
			//System.out.println(" ---> "+coordX);
		}
		if(Math.abs(Math.round(coordY) - coordY) <= speed / 2)
		{
			this.coordY = Math.round(coordY);
			this.coordY = (int)coordY;
		}
	}
	public void setHold(Boolean hold)
	{
		//this.hold = hold;
	}
	public Boolean getHold()
	{
		return true;
	}
	public void activateShield()
	{
		//System.out.println("m" + id + ": Shield Activated!");
		shield = 3000;
		scorePanel.activateShield();
	}
	public void activateBonusShield()
	{
		shield = 8000;
		scorePanel.activateBonusShield();
	}
	public void decShield()
	{
		shield--;
		if(shield == 0)
		{
			//System.out.println("m" + id + ": Shield has timed out!");
			scorePanel.disactivateShield();
		}
	}
	public int getShield()
	{
		return shield;
	}
	public Boolean hasShield()
	{
		if(shield > 0)
			return true;
		return false;
		
	}
	public Vector<Integer> findPath4(Element [][] a, int x, int y, int dir, int count)
	{
		int index = x * 100 + y;
		if(a[x][y] instanceof Floor)
		{
			if(!a[x][y].getHasBomb() && !checkDanger(a, x, y) && a[x][y].getZoneType() == 0)
			{
				Vector<Integer> s2 = new Vector<Integer>();
				s2.add(index);
				return s2;
			}
		}
		if(!(a[x][y] instanceof Floor) && count > 0)
		{
			return null;
		}
		if(count == 10)
		{
			return null;
		}
		if(a[x][y].getZoneType() > 0)
			return null;
		Vector<Integer> r = null, u = null, l = null, d = null;
		if(dir != 3)
			r  = findPath4(a, x + 1, y, 2, count + 1);
		if(dir != 1)
			u  = findPath4(a, x, y - 1, 0, count + 1);
		if(dir != 2)
			l  = findPath4(a, x - 1, y, 3, count + 1);
		if(dir != 0)
			d  = findPath4(a, x, y + 1, 1, count + 1);
		if(r != null)
		{
			if(r.contains(index))
				r = null;
			else
				r.add(index);
		}
		if(u != null)
		{
			if(u.contains(index))
				u = null;
			else
				u.add(index);
		}
		if(l != null)
		{
			if(l.contains(index))
				l = null;
			else
				l.add(index);
		}
		if(d != null)
		{
			if(d.contains(index))
				d = null;
			else
				d.add(index);
		}
		int rS = 10000, uS = 10000, lS = 10000, dS = 10000;
		if(r != null)
			rS = r.size();
		if(u != null)
			uS = u.size();
		if(l != null)
			lS = l.size();
		if(d != null)
			dS = d.size();
		if(rS <= uS && rS <= lS && rS <= dS)
			return r;
		if(uS <= rS && uS <= lS && uS <= dS)
			return u;
		if(lS <= uS && lS <= rS && lS <= dS)
			return l;
		if(dS <= uS && dS <= rS && dS <= lS)
			return d;
		return d;

	}
	public Vector<Integer> findPath5(Element [][] a, int x, int y, int dir, int count, int bX, int bY) // Similar to findPath4(), but also checks if putting a Bomb is safe.
	{
		int index = x * 100 + y;
		if(a[x][y] instanceof Floor)
		{
			if(!a[x][y].getHasBomb() && !checkDanger(a, x, y) && a[x][y].getZoneType() == 0 && x != bX && y != bY)
			{
				int i = 1;
				while(a[x + i][y] instanceof Floor && i <= bombPower)
				{
					if(x + i == bX && y == bY)
							return null;
					i++;
				}
				
				i = 1;
				while(a[x - i][y] instanceof Floor && i <= bombPower)
				{
					if(x - i == bX && y == bY)
						return null;
					i++;
				}
				
				i = 1;
				while(a[x][y + i] instanceof Floor && i <= bombPower)
				{
					if(x == bX && y + i == bY)
						return null;
					i++;
				}
				
				i = 1;
				while(a[x][y - i] instanceof Floor && i <= bombPower)
				{
					if(x == bX && y - i == bY)
						return null;
					i++;
				}
				Vector<Integer> s2 = new Vector<Integer>();
				s2.add(index);
				return s2;
			}
		}
		if(!(a[x][y] instanceof Floor) && count > 0)
		{
			return null;
		}
		if(count == 10)
		{
			return null;
		}
		if(a[x][y].getZoneType() > 0)
			return null;
		Vector<Integer> r = null, u = null, l = null, d = null;
		if(dir != 3)
			r  = findPath5(a, x + 1, y, 2, count + 1, bX, bY);
		if(dir != 1)
			u  = findPath5(a, x, y - 1, 0, count + 1, bX, bY);
		if(dir != 2)
			l  = findPath5(a, x - 1, y, 3, count + 1, bX, bY);
		if(dir != 0)
			d  = findPath5(a, x, y + 1, 1, count + 1, bX, bY);
		if(r != null)
		{
			if(r.contains(index))
				r = null;
			else
				r.add(index);
		}
		if(u != null)
		{
			if(u.contains(index))
				u = null;
			else
				u.add(index);
		}
		if(l != null)
		{
			if(l.contains(index))
				l = null;
			else
				l.add(index);
		}
		if(d != null)
		{
			if(d.contains(index))
				d = null;
			else
				d.add(index);
		}
		int rS = 10000, uS = 10000, lS = 10000, dS = 10000;
		if(r != null)
			rS = r.size();
		if(u != null)
			uS = u.size();
		if(l != null)
			lS = l.size();
		if(d != null)
			dS = d.size();
		if(rS <= uS && rS <= lS && rS <= dS)
			return r;
		if(uS <= rS && uS <= lS && uS <= dS)
			return u;
		if(lS <= uS && lS <= rS && lS <= dS)
			return l;
		if(dS <= uS && dS <= rS && dS <= lS)
			return d;
		return d;

	}
	public Vector<Integer> findPath2(Element [][] a, int x, int y, int dir, int count) // Look for Bonus
	{
		int index = x * 100 + y;
		if(!(a[x][y] instanceof Floor))
		{
			return null;
		}
		if(a[x][y].getHasBonus())
		{
			Vector<Integer> s2 = new Vector<Integer>();
			s2.add(index);
			return s2;
		}
		if(count == 10)
		{
			return null;
		}
		if(a[x][y].getZoneType() > 0)
			return null;
		if(count < 5)
			if(checkDanger(a, x, y))
				return null;
		
		Vector<Integer> r = null, u = null, l = null, d = null;
		if(dir != 3)
			r  = findPath2(a, x + 1, y, 2, count + 1);
		if(dir != 1)
			u  = findPath2(a, x, y - 1, 0, count + 1);
		if(dir != 2)
			l  = findPath2(a, x - 1, y, 3, count + 1);
		if(dir != 0)
			d  = findPath2(a, x, y + 1, 1, count + 1);
		if(r != null)
		{
			if(r.contains(index))
				r = null;
			else
				r.add(index);
		}
		if(u != null)
		{
			if(u.contains(index))
				u = null;
			else
				u.add(index);
		}
		if(l != null)
		{
			if(l.contains(index))
				l = null;
			else
				l.add(index);
		}
		if(d != null)
		{
			if(d.contains(index))
				d = null;
			else
				d.add(index);
		}
		
		int rS = 10000, uS = 10000, lS = 10000, dS = 10000;
		if(r != null)
			rS = r.size();
		if(u != null)
			uS = u.size();
		if(l != null)
			lS = l.size();
		if(d != null)
			dS = d.size();
		if(rS <= uS && rS <= lS && rS <= dS)
			return r;
		if(uS <= rS && uS <= lS && uS <= dS)
			return u;
		if(lS <= uS && lS <= rS && lS <= dS)
			return l;
		if(dS <= uS && dS <= rS && dS <= lS)
			return d;
		return d;

	}
	public Vector<Integer> findPath3(Element [][] a, int x, int y, int dir, int count)
	{
		int index = x * 100 + y;
		if(a[x][y] instanceof Crate)
		{
				Vector<Integer> s2 = new Vector<Integer>();
				s2.add(index);
				return s2;
		}
		if(!(a[x][y] instanceof Floor))
		{
			return null;
		}
		if(count == 10)
		{
			return null;
		}
		if(a[x][y].getZoneType() > 0)
			return null;
		
		if(count < 10)
			if(checkDanger(a, x, y))
				return null;
		
		Vector<Integer> r = null, u = null, l = null, d = null;
		if(dir != 3)
			r  = findPath3(a, x + 1, y, 2, count + 1);
		if(dir != 1)
			u  = findPath3(a, x, y - 1, 0, count + 1);
		if(dir != 2)
			l  = findPath3(a, x - 1, y, 3, count + 1);
		if(dir != 0)
			d  = findPath3(a, x, y + 1, 1, count + 1);
		if(r != null)
		{
			if(r.contains(index))
				r = null;
			else
				r.add(index);
		}
		if(u != null)
		{
			if(u.contains(index))
				u = null;
			else
				u.add(index);
		}
		if(l != null)
		{
			if(l.contains(index))
				l = null;
			else
				l.add(index);
		}
		if(d != null)
		{
			if(d.contains(index))
				d = null;
			else
				d.add(index);
		}
		
		int rS = 10000, uS = 10000, lS = 10000, dS = 10000;
		if(r != null)
			rS = r.size();
		if(u != null)
			uS = u.size();
		if(l != null)
			lS = l.size();
		if(d != null)
			dS = d.size();
		if(rS <= uS && rS <= lS && rS <= dS)
			return r;
		if(uS <= rS && uS <= lS && uS <= dS)
			return u;
		if(lS <= uS && lS <= rS && lS <= dS)
			return l;
		if(dS <= uS && dS <= rS && dS <= lS)
			return d;
		return d;

	}
	public Boolean checkDanger(Element [][] a, int x, int y)
	{
		Bomb bomb;
		int i = 1;
		while(a[x + i][y] instanceof Floor && i < 6)
		{
			bomb = a[x + i][y].getBomb();
			if(bomb != null)
				if(bomb.getTime() < 2500 && bomb.getPower() >= i)
					return true;
			i++;
		}
		
		i = 1;
		while(a[x - i][y] instanceof Floor && i < 6)
		{
			bomb = a[x - i][y].getBomb();
			if(bomb != null)
				if(bomb.getTime() < 2500 && bomb.getPower() >= i)
					return true;
			i++;
		}
		
		i = 1;
		while(a[x][y + i] instanceof Floor && i < 6)
		{
			bomb = a[x][y + i].getBomb();
			if(bomb != null)
				if(bomb.getTime() < 2500 && bomb.getPower() >= i)
					return true;
			i++;
		}
		
		i = 1;
		while(a[x][y - i] instanceof Floor && i < 6)
		{
			bomb = a[x][y - i].getBomb();
			if(bomb != null)
				if(bomb.getTime() < 2500 && bomb.getPower() >= i)
					return true;
			i++;
		}
		return false;
	}
	public Vector<Integer> findPath1(Element [][] a, Vector<Integer> indexes, int x, int y, int dir, int count)
	{
		int index = x * 100 + y;
		if(indexes.contains(index))
		{
				Vector<Integer> s2 = new Vector<Integer>();
				s2.add(index);
				return s2;
		}
		//int r = 0, u = 0, l = 0, d = 0;
		if(!(a[x][y] instanceof Floor) && count > 0)
		{
			//System.out.println("x = " + x + " | y = " + y);
			return null;
		}
		if(count == 20)
		{
			//System.out.println("*count* x = " + x + " | y = " + y);
			return null;
		}
		if(a[x][y].getZoneType() > 0)
			return null;
		if(count < 10 && count > 0)
			if(checkDanger(a, x, y))
				return null;
		//System.out.println("*x = " + x + " | y = " + y);
		Vector<Integer> r = null, u = null, l = null, d = null;
		if(dir != 3)
			r  = findPath1(a, indexes, x + 1, y, 2, count + 1);
		if(dir != 1)
			u  = findPath1(a, indexes, x, y - 1, 0, count + 1);
		if(dir != 2)
			l  = findPath1(a, indexes, x - 1, y, 3, count + 1);
		if(dir != 0)
			d  = findPath1(a, indexes, x, y + 1, 1, count + 1);
		//System.out.println("** x = " + x + " | y = " + y);
		if(r != null)
		{
			if(r.contains(index))
				r = null;
			else
				r.add(index);
		}
		if(u != null)
		{
			if(u.contains(index))
				u = null;
			else
				u.add(index);
		}
		if(l != null)
		{
			if(l.contains(index))
				l = null;
			else
				l.add(index);
		}
		if(d != null)
		{
			if(d.contains(index))
				d = null;
			else
				d.add(index);
		}
		int rS = 10000, uS = 10000, lS = 10000, dS = 10000;
		if(r != null)
			rS = r.size();
		if(u != null)
			uS = u.size();
		if(l != null)
			lS = l.size();
		if(d != null)
			dS = d.size();
		if(rS <= uS && rS <= lS && rS <= dS)
			return r;
		if(uS <= rS && uS <= lS && uS <= dS)
			return u;
		if(lS <= uS && lS <= rS && lS <= dS)
			return l;
		if(dS <= uS && dS <= rS && dS <= lS)
			return d;
		return d;

	}
	/*
	public void chooseDirection(Element [][] elements, Element element)

	{
		if(!checkDanger(elements, (int)(coordX + 0.5), (int)(coordY + 0.5)) && !(elements[(int)(coordX + 0.5)][(int)(coordY + 0.5)].getHasBomb()) && availableBombs > 0)
		{
			int dX = (int)(element.getCoordX() + 0.5);
			int dY = (int)(element.getCoordY() + 0.5);
			if(Math.abs((double)dX - coordX) <= 1 && (double)dY == coordY || Math.abs((double)dY - coordY) <= 1 && (double)dX == coordX)
			{
			if(dX > (int)(coordX))
				setDir(2);
			else if(dX < (int)(coordX))
				setDir(3);
			else if(dY < (int)(coordY))
				setDir(0);
			else if(dY > (int)(coordY))
				setDir(1);
			}
			//if(previousPathType == 0)
				if((dir > 1 && Math.abs((double)dX - coordX) <= 1 && dY == (int)(coordY + 0.5)) || (dir <= 1 && Math.abs((double)dY - coordY) <= 1 && dX == (int)(coordX + 0.5)))
				{
					if(hold == false)
					//System.out.println("$hold = true | dX = " + dX + " | dY = " + dY + " | ("+coordX+", "+coordY);
						System.out.println("Place Bomb");
					setHold(true);
				}
				else
				{
					//if(hold == true)
					//System.out.println("*hold = false");
					setHold(false);
				}
			
			path = findPath1(elements, dX, dY, dX, dY, (int)(coordX + 0.5), (int)(coordY + 0.5), 4, 0);
			if(path == null || (previousTaskSteps < 5 && previousTaskSteps > 0 && previousPathType == 9))
			{
				path = findPath2(elements, (int)(coordX + 0.5), (int)(coordY + 0.5), 4, 0);
				if(path == null)
				{
					path = findPath3(elements, (int)(coordX + 0.5), (int)(coordY + 0.5), 4, 0);
					previousPathType = 2;
				}
				else
					previousPathType = 1;
			}
			System.out.print("previousPathType: " + previousPathType + " | path: " + path);
			System.out.println(" | Element("+elements[(int)(coordX + 0.5)][(int)(coordY + 0.5)].getCoordX()+", "+elements[(int)(coordX + 0.5)][(int)(coordY + 0.5)].getCoordY()+")  | dir = " + dir + " | hold: " + hold + " | (" + coordX + ", " + coordY + ") ");
			//if(path != null)
				if(path != null && path.size() > 1)
				{
					previousTaskSteps = path.size();
					int x = path.get(path.size() - 2) / 100;
					int y = path.get(path.size() - 2) % 100;
					int x1 = path.get(0) / 100;
					int y1 = path.get(0) % 100;
					//System.out.println("coordX = " + coordX + " | x = " + x);
					//System.out.println("coordY = " + coordY + " | y = " + y);
					if(x > (coordX))
						setDir(2);
					if(x < (coordX))
						setDir(3);
					if(y < (coordY))
						setDir(0);
					if(y > (coordY))
						setDir(1);
					if(path.size() > 1)
					setHold(false);
				}
				else
				{
					
					if(dX > (int)(coordX + 0.5))
						setDir(2);
					if(dX < (int)(coordX - 1))
						setDir(3);
					if(dY < (int)(coordY + 0.5))
						setDir(0);
					if(dY > (int)(coordY - 1))
						setDir(1);
					if(dX == (coordX) && dY == (coordY))
					{
						if(hold == false)
						{
							System.out.println("*Place Bomb");
						}
						setHold(true);
					}
					else
						setHold(false);
				}
			previousPathType = 0;
			previousTaskSteps = 0;

		}
		else
		{
			//System.out.print("DANGER! ");
			//System.out.println("m1("+ coordX+ ", "+coordY+"), speed = " + speed + "    Element("+elements[(int)(coordX + 0.5)][(int)(coordY + 0.5)].getCoordX()+", "+elements[(int)(coordX + 0.5)][(int)(coordY + 0.5)].getCoordY()+")  | dir = " + dir);

			path = findPath4(elements, (int)(coordX + 0.5), (int)(coordY + 0.5), 4, 0);
			previousPathType = 4;
			System.out.print("previousPathType: " + previousPathType + " | path: " + path);
			System.out.println(" | Element("+elements[(int)(coordX + 0.5)][(int)(coordY + 0.5)].getCoordX()+", "+elements[(int)(coordX + 0.5)][(int)(coordY + 0.5)].getCoordY()+")  | dir = " + dir);
			if(path != null)
				if(path.size() > 2)
				{
					int x = path.get(path.size() - 2) / 100;
					int y = path.get(path.size() - 2) % 100;
					int x1 = path.get(0) / 100;
					int y1 = path.get(0) % 100;
					//System.out.println("coordX = " + coordX + " | x = " + x);
					//System.out.println("coordY = " + coordY + " | y = " + y);
					if(x > (int)(coordX))
						setDir(2);
					if(x < (int)(coordX))
						setDir(3);
					if(y < (int)(coordY))
						setDir(0);
					if(y > (int)(coordY))
						setDir(1);
					//if(path.size() > 2)
					setHold(false);
				}
				else
				{
					int x = path.get(1) / 100;
					int y = path.get(1) % 100;
					int x1 = path.get(0) / 100;
					int y1 = path.get(0) % 100;
					//System.out.println("coordX = " + coordX + " | x = " + x);
					//System.out.println("coordY = " + coordY + " | y = " + y);
					if(x > (int)(coordX))
						setDir(2);
					if(x < (int)(coordX))
						setDir(3);
					if(y < (int)(coordY))
						setDir(0);
					if(y > (int)(coordY))
						setDir(1);
					//if(hold == false)
					//	System.out.println("Safe!");
					//setHold(true);
				}
			/*
				else
				{
					
					if(x1 > (int)(coordX + 0.5))
						setDir(2);
					if(x1 < (int)(coordX - 1))
						setDir(3);
					if(y1 < (int)(coordY + 0.5))
						setDir(0);
					if(y1 > (int)(coordY - 1))
						setDir(1);
					if(x1 == (coordX) && y1 == (coordY))
					{
						if(hold == false)
							System.out.println("Safe!");
						setHold(true);
					}

				}
				
		}

	}
	*/
	public Boolean canChangeDir()
	{
		if(coordX == (int)(coordX) && coordY == (int)(coordY))
			return true;
		return false;
	}
	public void determineDir(Vector<Integer> path)
	{
		int dX, dY, index;
		index = path.get(path.size() - 2);
		dX = index / 100;
		dY = index % 100;
		if(dX > coordX)
			dir = 2; //right
		if(dX < coordX)
			dir = 3; //left
		if(dY > coordY)
			dir = 1; //down
		if(dY < coordY)
			dir = 0; //up
	}
	public Vector<Integer> getTargetsIndex(Man[] m)
	{
		Vector<Integer> index = new Vector<Integer>();
		for(int i = 0; i < m.length; i++)
		{
			if(id != m[i].getId() && m[i].isAlive())
			{
				index.add((int)(m[i].getCoordX() + 0.5) * 100 + (int)(m[i].getCoordY() + 0.5));
			}
		}
		return index;
	}
	public void mainAlgorithm(Element [][] elements, Man[] m)
	{
		Vector<Integer> path1, path2, path3, path4, path5; // | 1 - Attack | 2 - Bonus | 3 - Crate | 4 - Danger |
		int x = (int)(coordX + 0.5);
		int y = (int)(coordY + 0.5);
		int index, dX, dY;
		goingToDropBomb = false;
		if(checkDanger(elements, x, y) || elements[x][y].getHasBomb()) // Check if Man is in danger.
		{
			path4 = findPath4(elements, x, y, 4, 0);
			if(path4 != null)
			{
				determineDir(path4);
			}
			else
				dir = -1; // There is nothing to do - Man is going to die
		}
		else // if Man is not in danger
			{
				path2 = findPath2(elements, x, y, 4, 0);
				if(path2 != null && path2.size() < 6) // if Man has to go up to 4 tiles to Bonus
				{
					determineDir(path2);
				}
				else // if Man is not close to Bonus
				{
					path1 = findPath1(elements, getTargetsIndex(m),x, y, 4, 0);
					if(path1 != null && path1.size() < 3 && availableBombs > 0) // if close to Enemy and has Bomb
					{
						path5 = findPath5(elements, x, y, 4, 0, x, y);
						if(path5 != null && path5.size() > 2)
						{
							//System.out.println(path5);
							goingToDropBomb = true;
							determineDir(path5);
							dir = -1;
						}
					}
					if(!goingToDropBomb && path1 != null && path1.size() > 2 && ((path2 != null && path1.size() < path2.size() && availableBombs > 0) || (path2 == null)))
					{
								determineDir(path1);
					}
					else if(!goingToDropBomb && path2 != null)
					{
						determineDir(path2);
					}
					else
					{
						path3 = findPath3(elements, x, y, 4, 0);
						if(path3 != null && path3.size() < 3 && availableBombs > 0) // if close to Crate and has Bomb
						{
							path5 = findPath5(elements, x, y, 4, 0, x, y);
							if(path5 != null && path5.size() > 2)
							{
								//System.out.println(path5);
								goingToDropBomb = true;
								determineDir(path5);
								//dir = -1;
							}
						}
						else if(path3 != null)
						{
							determineDir(path3);
						}
						else
							dir = -1;
					}


				}
			}
	}
}
