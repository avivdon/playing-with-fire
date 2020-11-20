import java.awt.Color;
import java.util.Collections;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
public class GamePanel extends JPanel
{
	private int[][] square;
	private Element[][] elements;
	private MyActionListener al;
	private MyKeyListener kl;
	private Button a;
	private Man[] m;
	private Timer timer;
	private int isMoving = 0;
	//private TimeLeft timeLeft;
	private MoveTimer moveTimer;
	//private BotsTimer botsTimer1, botsTimer2;
	//private CheckOnFireTimer checkOnFireTimer;
	//private Vector<Bomb> bombs;
	private Boolean moveFlag = false;
	private int time = 120;
	private int aliveMen;
	private int playerId;
	public GamePanel(ScorePanel[] scorePanel, JFrame game)
	{
		playerId = 0;
		Vector <Integer> manOrder = new Vector <Integer>();
		for(int i = 0; i < scorePanel.length; i++)
		{
			manOrder.add(i);
		}
		Collections.shuffle(manOrder);
		System.out.println(manOrder);
		if(scorePanel.length <= playerId)
		{
			manOrder.remove(0);
			manOrder.add(playerId);
			Collections.shuffle(manOrder);
		}
		setSize(600, 520);
		setLocation(146, 5);
		setLayout(null);		
		timer = new Timer();
		kl = new MyKeyListener();
		//al = new MyActionListener();
		//a = new Button(40, 40, 0, 0, "A", al);
		//a.addKeyListener(kl);
		//add(a);
		game.addKeyListener(kl);
		game.setFocusable(true);
		game.requestFocusInWindow();
		square = new int[15][13];
		elements = new Element[15][13];
		m = new Man[scorePanel.length];
		JLabel la;
		int mX = 1, mY = 1;
		try
        {
            Scanner s = new Scanner( new BufferedReader(new FileReader("Map.txt")));         
        for(int i = 0; i < square.length; i++)
        {
            for(int j = 0; j < square[0].length; j++)
            {                
                square[i][j] = s.nextInt();
                if(square[i][j] == 3)
                {
                	mX = i;
                	mY = j;
                	square[i][j] = 0;
                }
                /*
                if(square[i][j] >= 3)
                {
                	if(!manOrder.isEmpty())
                	{
                		m[manOrder.get(0)] = new Man(i, j, manOrder.get(0), scorePanel[manOrder.get(0)]);
                		add(m[manOrder.get(0)], 0);
                		manOrder.remove(0);
                	}
                	square[i][j] = 0;
                }
                
                */
                if(square[i][j] == 4)
                {
                	m[1] = new Man(i, j, 1, scorePanel[1]);
                	add(m[1], 0);
                	square[i][j] = 0;
                }
                if(square[i][j] == 5)
                {
                	if(m.length > 2)
                	{
                		m[2] = new Man(i, j, 2, scorePanel[2]);
                		add(m[2], 0);
                	}
                	square[i][j] = 0;
                }
                if(square[i][j] == 6)
                {
                	if(m.length > 3)
                	{
                		m[3] = new Man(i, j, 3, scorePanel[3]);
                		add(m[3], 0);
                	}
                	square[i][j] = 0;
                }
                
                
                	
            }
        }
        s.close(); 
    }
     catch(FileNotFoundException e)
     {
         System.out.println("File not found"); 
         e.printStackTrace(); 
     }

 
		for(int i = 0; i < 15; i++)
		{
			for(int j = 0; j < 13; j++)
			{
				if(i == 0 || i == 14 || j == 0 || j == 12)
				{
					square[i][j] = 1;
				}
			}
		}
		m[0] = new Man(mX, mY, 0, scorePanel[0]);
		add(m[0], 0);
		for(int i = 0; i < 15; i++)
		{
			for(int j = 0; j < 13; j++)
			{
				if(square[i][j] == 0)
				{
					elements [i][j] = new Floor(i, j, false);
				}
				else if(square[i][j] == 1)
				{
					elements [i][j] = new Block(i, j);
				}
				else if(square[i][j] == 2)
				{
					elements [i][j] = new Crate(i, j);
				}
				else if(square[i][j] == 3)
				{
					elements [i][j] = new Floor(i, j, false);
				}
				//if(square[i][j] >= 0 && square[i][j] <= 2)
					add(elements[i][j], 2);
			}

		}
		for(int i = 0; i < 15; i++)
		{
			for(int j = 1; j < 13; j++)
			{
				la = new JLabel(new ImageIcon("Floor.jpg"));
				la.setSize(40, 40);
				la.setLocation(40 * i, 40 * j);
			}
		}
		repaintMan();
		//bombs = new Vector<Bomb>();
		aliveMen = m.length;
		timer.schedule(new TimeLeft(game), 10);
		for(int i = 0; i < m.length; i++)
		{
			timer.schedule(new CheckOnFireTimer(m[i]), 10);
			if(i != playerId)
				timer.schedule(new BotsTimer(m[i]), 500);
		}

	}
	
	public Boolean isLegalMove(Man man, int dir) //0 - forward, 1 - backward, 2 - right, 3 - left.
	{
		switch(dir)
		{
		case 0: return (!getElement(man, 0, -man.getSpeed()).getSolid()) & (!getElement(man, 0.99, -man.getSpeed()).getSolid());
		case 1: return (!getElement(man, 0, man.getSpeed() + 0.99).getSolid()) & (!getElement(man, 0.99, man.getSpeed() + 0.99).getSolid());
		case 2: return (!getElement(man, man.getSpeed() + 0.99, 0).getSolid()) & (!getElement(man, man.getSpeed() + 0.99, 0.99).getSolid());
		case 3: return (!getElement(man, -man.getSpeed(), 0).getSolid()) & (!getElement(man, -man.getSpeed(), 0.99).getSolid());
		}
		return false;
	}
	public Boolean freeOfBomb(Man man, int dir) //0 - forward, 1 - backward, 2 - right, 3 - left.
	{
		if(!man.getCanPushBomb() && !onBomb(man))
		switch(dir)
		{
			case 0: return (!getElement(man, 0, -man.getSpeed()).getHasBomb()) & (!getElement(man, 0.99, -man.getSpeed()).getHasBomb());
			case 1: return (!getElement(man, 0, man.getSpeed() + 0.99).getHasBomb()) & (!getElement(man, 0.99, man.getSpeed() + 0.99).getHasBomb());
			case 2: return (!getElement(man, man.getSpeed() + 0.99, 0).getHasBomb()) & (!getElement(man, man.getSpeed() + 0.99, 0.99).getHasBomb());
			case 3: return (!getElement(man, -man.getSpeed(), 0).getHasBomb()) & (!getElement(man, -man.getSpeed(), 0.99).getHasBomb());
		}
		return true;
	}
	public Boolean onBomb(Man man)
	{
			if((!getElement(man, 0, 0).getHasBomb() && !getElement(man, 0, 0.99).getHasBomb()) && (!getElement(man, 0, 0).getHasBomb() && !getElement(man, 0.99, 0).getHasBomb()))
			return false;
		return true;
	}
	public void move(Man man)
	{
		int dir = man.getDir();
		if(isLegalMove(man, dir) && freeOfBomb(man, dir))
		{
			switch(dir)
			{
			case 0: moveForward(man);
					break;
			case 1: moveBackward(man);
					break;
			case 2: moveRight(man);
					break;
			case 3: moveLeft(man);
					break;
			}
			if(getElement(man, 0, 0).getHasBonus() && getElement(man, 0, 0).getCoordX() == man.getCoordX() && getElement(man, 0, 0).getCoordY() == man.getCoordY())
			{
				awardBonus(getElement(man, 0, 0), man);
			}
		}
		else
		{
			if(dir == 0)
			{
				if(!elements[(int)Math.round(man.getCoordX() + 0.49)][(int)(man.getCoordY() - 1)].getSolid() && (!elements[(int)Math.round(man.getCoordX() + 0.49)][(int)(man.getCoordY() - 1)].getHasBomb() || man.getCanPushBomb() || onBomb(man)))
					dir = 2;
				else if(!elements[(int)Math.round(man.getCoordX() + 0.51) - 1][(int)(man.getCoordY() - 1)].getSolid() && (!elements[(int)Math.round(man.getCoordX() + 0.51) - 1][(int)(man.getCoordY() - 1)].getHasBomb() || man.getCanPushBomb() || onBomb(man))) 
					dir = 3;
			}
			else if(dir == 1)
			{
				if(!elements[(int)Math.round(man.getCoordX() + 0.49)][(int)(man.getCoordY() + 1)].getSolid() && (!elements[(int)Math.round(man.getCoordX() + 0.49)][(int)(man.getCoordY() + 1)].getHasBomb() || man.getCanPushBomb() || onBomb(man)))
					dir = 2;
				else if(!elements[(int)Math.round(man.getCoordX() + 0.51) - 1][(int)(man.getCoordY() + 1)].getSolid() && (!elements[(int)Math.round(man.getCoordX() + 0.51) - 1][(int)(man.getCoordY() + 1)].getHasBomb() || man.getCanPushBomb() || onBomb(man)))
					dir = 3;
			}
			else if (dir == 2)
			{
				if(!elements[(int)(man.getCoordX() + 1)][(int)Math.round(man.getCoordY() + 0.49)].getSolid() && (!elements[(int)(man.getCoordX() + 1)][(int)Math.round(man.getCoordY() + 0.49)].getHasBomb() || man.getCanPushBomb() || onBomb(man)))
					dir = 1;
				else if(!elements[(int)(man.getCoordX() + 1)][(int)Math.round(man.getCoordY() + 0.51) - 1].getSolid() && (!elements[(int)(man.getCoordX() + 1)][(int)Math.round(man.getCoordY() + 0.51) - 1].getHasBomb() || man.getCanPushBomb() || onBomb(man)))
					dir = 0;
			}
			else 
			{
				if(!elements[(int)(man.getCoordX() - 1)][(int)Math.round(man.getCoordY() + 0.49)].getSolid() && (!elements[(int)(man.getCoordX() - 1)][(int)Math.round(man.getCoordY() + 0.49)].getHasBomb() || man.getCanPushBomb() || onBomb(man)))
					dir = 1;
				else if(!elements[(int)(man.getCoordX() - 1)][(int)Math.round(man.getCoordY() + 0.51) - 1].getSolid() && (!elements[(int)(man.getCoordX() - 1)][(int)Math.round(man.getCoordY() + 0.51) - 1].getHasBomb() || man.getCanPushBomb() || onBomb(man)))
					dir = 0;
			}
			if(isLegalMove(man, dir) && freeOfBomb(man, dir))
			{
				switch(dir)
				{
				case 0: moveForward(man);
						break;
				case 1: moveBackward(man);
						break;
				case 2: moveRight(man);
						break;
				case 3: moveLeft(man);
						break;
				}
				if(getElement(man, 0, 0).getHasBonus() && getElement(man, 0, 0).getCoordX() == man.getCoordX() && getElement(man, 0, 0).getCoordY() == man.getCoordY())
				{
					//System.out.println("First Has Bonus: " + getElement(man, 0, 0).getHasBonus());
					awardBonus(getElement(man, 0, 0), man);
				}
			}
		}

	}
	public void moveForward(Man man)
	{
		man.decY();
		if(getElement(man, 0, 0).getHasBomb() && (!getElement(man, 0, -1).getSolid()) && (!getElement(man, 0, -1).getHasBomb()) && ((int)(man.getCoordY() + man.getSpeed()) == (int)man.getCoordY() + 1) && man.getCanPushBomb())
		{
			Bomb bomb = getElement(man, 0, 0).getBomb();
			bomb.setIsMoving(true);
			timer.schedule(new MoveBombTimer(0, bomb), 5);
		}
	}
	public void moveBackward(Man man)
	{
		man.incY();
		if(getElement(man, 0, 1).getHasBomb() && (!getElement(man, 0, 2).getSolid()) && (!getElement(man, 0, 2).getHasBomb()) && ((man.getCoordY() - man.getSpeed()) == (int)(man.getCoordY())) && man.getCanPushBomb())
		{
			Bomb bomb = getElement(man, 0, 1).getBomb();
			bomb.setIsMoving(true);
			timer.schedule(new MoveBombTimer(1, bomb), 5);
		}
	}
	public void moveRight(Man man)
	{
		man.incX();
		if(getElement(man, 1, 0).getHasBomb() && (!getElement(man, 2, 0).getSolid()) && (!getElement(man, 2, 0).getHasBomb()) && ((man.getCoordX() - man.getSpeed()) == (int)(man.getCoordX())) && man.getCanPushBomb())
		{
			Bomb bomb = getElement(man, 1, 0).getBomb();
			bomb.setIsMoving(true);
			timer.schedule(new MoveBombTimer(2, bomb), 5);
		}
	}
	public void moveLeft(Man man)
	{
		man.decX();
		if(getElement(man, 0, 0).getHasBomb() && (!getElement(man, -1, 0).getSolid()) && (!getElement(man, -1, 0).getHasBomb()) && ((int)(man.getCoordX() + man.getSpeed()) == (int)man.getCoordX() + 1) && man.getCanPushBomb())
		{
			Bomb bomb = getElement(man, 0, 0).getBomb();
			bomb.setIsMoving(true);
			timer.schedule(new MoveBombTimer(3, bomb), 5);
		}
	}
	public void awardBonus(Element floor, Man man)
	{
		Bonus bonus = floor.removeBonus();
		//System.out.println("Has Bonus: "+floor.getHasBonus());
		bonus.chooseBonusType(man);
		remove(bonus);
		if(man.getShield() == 8000) //normal time
			timer.schedule(new ShieldsTimer(man),  1);
	}
	/*
	public void moveBomb(int dir, Man man)
	{
		System.out.println("IHHIF");
		if(man.getCanPushBomb())
		{
			Boolean foundBomb = false;
			int i = 0;
			if(dir == 0)
				while(!foundBomb && i < bombs.size())
				{
					System.out.println("bomb("+i+"/"+bombs.size()+"):  ("+bombs.get(i).getCoordX()+", "+bombs.get(i).getCoordY()+"), Man: ("+man.getCoordX()+", "+man.getCoordY()+")");
					if(bombs.get(i).getCoordX() == man.getCoordX() && (int)bombs.get(i).getCoordY() == (int)man.getCoordY())
					{
						foundBomb = true;
						bombs.get(i).setIsMoving(true);
						timer.schedule(new MoveBombTimer(dir, bombs.get(i)), 5);
					}
					i++;
				}
			if(dir == 1)
				while(!foundBomb && i < bombs.size())
				{
					System.out.println("bomb("+i+"/"+bombs.size()+"):  ("+bombs.get(i).getCoordX()+", "+bombs.get(i).getCoordY()+"), Man: ("+man.getCoordX()+", "+man.getCoordY()+")");
					if(bombs.get(i).getCoordX() == man.getCoordX() && (int)bombs.get(i).getCoordY() - 1 == (int)man.getCoordY())
					{
						foundBomb = true;
						bombs.get(i).setIsMoving(true);
						timer.schedule(new MoveBombTimer(dir, bombs.get(i)), 5);
					}
					i++;
				}	
			if(dir == 2)
				while(!foundBomb && i < bombs.size())
				{
					System.out.println("bomb("+i+"/"+bombs.size()+"):  ("+bombs.get(i).getCoordX()+", "+bombs.get(i).getCoordY()+"), Man: ("+man.getCoordX()+", "+man.getCoordY()+")");
					if(bombs.get(i).getCoordY() == man.getCoordY() && (int)bombs.get(i).getCoordX() - 1 == (int)man.getCoordX())
					{
						foundBomb = true;
						bombs.get(i).setIsMoving(true);
						timer.schedule(new MoveBombTimer(dir, bombs.get(i)), 5);
					}
					i++;
				}	
			if(dir == 3)
				while(!foundBomb && i < bombs.size())
				{
					System.out.println("bomb("+i+"/"+bombs.size()+"):  ("+bombs.get(i).getCoordX()+", "+bombs.get(i).getCoordY()+"), Man: ("+man.getCoordX()+", "+man.getCoordY()+")");
					if(bombs.get(i).getCoordY() == man.getCoordY() && (int)bombs.get(i).getCoordX() == (int)man.getCoordX())
					{
						foundBomb = true;
						bombs.get(i).setIsMoving(true);
						timer.schedule(new MoveBombTimer(dir, bombs.get(i)), 5);
					}
					i++;
				}
		}
	}
	*/
	public void explode(Bomb bomb)
	{
		Element element = bomb.getBase();
		element.removeBomb();
		int man = bomb.getPutter().getId();
		remove(bomb);
		//bomb.disable();
		int i = (int)element.getCoordX();
		int j = (int)element.getCoordY();
		element.setZoneType(man, 2);
		repaintMan();
		int power = bomb.getPower();
		int count = 0;
		Floor floor;
		Bonus bonus; 
		while(count < power)
		{
			if(elements[i - count - 1][j] instanceof Crate)
			{
				floor = new Floor(i - count - 1, j, true);
				remove(elements[i - count - 1][j]);
				elements[i - count - 1][j] = floor;
				add(floor, 2);
				elements[i - count - 1][j].setZoneType(man, 0);
				repaintMan();
				bonus = ((Floor) elements[i - count - 1][j]).tryBonus();
				if(bonus != null)
					add(bonus, 2);
				count = power;
			}
			else if(elements[i - count - 1][j].getSolid())
				count = power;
			else
			{
				elements[i - count - 1][j].setZoneType(man, 0);
				repaintMan();
				count++;
				if(elements[i - count][j].getHasBomb())
				{
					elements[i - count][j].getBomb().trigger();
				}
			}
		}
		count = 0;
		while(count < power)
		{
			if(elements[i + count + 1][j] instanceof Crate)
			{
				floor = new Floor(i + count + 1, j, true);
				remove(elements[i + count + 1][j]);
				elements[i + count + 1][j] = floor;
				add(floor, 2);
				elements[i + count + 1][j].setZoneType(man, 0);
				repaintMan();
				bonus = ((Floor) elements[i + count + 1][j]).tryBonus();
				if(bonus != null)
					add(bonus, 2);
				count = power;
			}
			else if(elements[i + count + 1][j].getSolid())
				count = power;
			else
			{
				elements[i + count + 1][j].setZoneType(man, 0);
				repaintMan();
				count++;
				if(elements[i + count][j].getHasBomb())
				{
					elements[i + count][j].getBomb().trigger();
				}
			}
		}
		count = 0;
		while(count < power)
		{
			if(elements[i][j - count - 1] instanceof Crate)
			{
				floor = new Floor(i, j - count - 1, true);
				remove(elements[i][j - count - 1]);
				elements[i][j - count - 1] = floor;
				add(floor, 2);
				elements[i][j - count - 1].setZoneType(man, 1);
				repaintMan();
				bonus = ((Floor) elements[i][j - count - 1]).tryBonus();
				if(bonus != null)
					add(bonus, 2);
				count = power;
			}
			else if(elements[i][j - count - 1].getSolid())
				count = power;
			else
			{
				elements[i][j - count - 1].setZoneType(man, 1);
				repaintMan();
				count++;
				if(elements[i][j - count].getHasBomb())
				{
					elements[i][j - count].getBomb().trigger();
				}
			}
		}
		count = 0;
		while(count < power)
		{
			if(elements[(int)element.getCoordX()][(int)element.getCoordY() + count + 1] instanceof Crate)
			{
				floor = new Floor(i, j + count + 1, true);
				remove(elements[i][j + count + 1]);
				elements[i][j + count + 1] = floor;
				add(floor, 2);
				elements[i][j + count + 1].setZoneType(man, 1);
				repaintMan();
				bonus = ((Floor) elements[i][j + count + 1]).tryBonus();
				if(bonus != null)
				{
					add(bonus, 2);
				}
				count = power;
			}
			else if(elements[i][j + count + 1].getSolid())
				count = power;
			else
			{
				elements[i][j + count + 1].setZoneType(man, 1);
				repaintMan();
				count++;
				if(elements[i][j + count].getHasBomb())
				{
					elements[i][j + count].getBomb().trigger();
				}
			}
		}
		
	}
	public void repaintMan()
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i].isAlive())
			{
				remove(m[i]);
				add(m[i], 0);
			}
		}
	}
	public void rebaseBomb(Bomb bomb)
	{
		bomb.setIsMoving(false);
		bomb.getBase().removeBomb();
		bomb.setBase(getElement(bomb, 0.5, 0.5));
		bomb.getBase().putBomb(bomb);
	}
	public Element getElement(Element element, double x, double y)
	{
		return elements[(int)(element.getCoordX() + x)][(int)(element.getCoordY() + y)];
	}
	public int getElementRow(Element element)
	{
		return (int)(getElement(element, 0.5, 0.5).getCoordX());
	}
	public int getElementLine(Element element)
	{
		return (int)(getElement(element, 0.5, 0.5).getCoordY());
	}
	public void placeBomb(Man man)
	{
		if(!getElement(man, 0.5, 0.5).getHasBomb() && man.isAlive())
		{
			Bomb bomb = man.dropBomb(elements);
			if(bomb != null)
			{
				add(bomb ,1);
				bomb.getBase().putBomb(bomb);
				repaint();
				timer.schedule(new BombsTimer(bomb), 1);	
			}
		}		
	}
	private class MyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{

		}
	}
	private class MyKeyListener implements KeyListener
	{
		
		public void keyTyped(KeyEvent e) 
		{
			
		}
		public void keyPressed(KeyEvent e) 
		{
			int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_UP)
			{
				if(isMoving == 0 && m[playerId].isAlive() && aliveMen > 1 && time > 0)
				{		
					isMoving++;
					m[playerId].setDir(0);
					moveTimer = new MoveTimer(m[playerId], true);
					moveFlag = true;
					timer.schedule(moveTimer, 12);

				}
			}
			if(keyCode == KeyEvent.VK_DOWN )
			{
				if(isMoving == 0 && m[playerId].isAlive() && aliveMen > 1 && time > 0)
				{	
					isMoving++;				
					m[playerId].setDir(1);
					moveTimer = new MoveTimer(m[playerId], true);
					moveFlag = true;
					timer.schedule(moveTimer, 12);

				}
			}
			if(keyCode == KeyEvent.VK_RIGHT)
			{
				if(isMoving == 0 && m[playerId].isAlive() && aliveMen > 1 && time > 0)
				{		
					isMoving++;
					m[playerId].setDir(2);
					moveTimer = new MoveTimer(m[playerId], true);
					moveFlag = true;
					timer.schedule(moveTimer, 12);

				}
			}
			if(keyCode == KeyEvent.VK_LEFT)
			{
				if(isMoving == 0 && m[playerId].isAlive() && aliveMen > 1 && time > 0)
				{							
					isMoving++;		
					m[playerId].setDir(3);
					moveTimer = new MoveTimer(m[playerId], true);
					moveFlag = true;
					timer.schedule(moveTimer, 12);

				}
			}
			if(keyCode == KeyEvent.VK_SPACE)
			{
				if(m[playerId].isAlive() && aliveMen > 1 && time > 0)
					placeBomb(m[playerId]);	
			}
			if(keyCode == KeyEvent.VK_ENTER)
			{
				//System.out.println("m ("+m.getCoordX()+", "+m.getCoordY()+"), speed = "+m.getSpeed() + "    Element("+elements[(int)(m.getCoordX() + 0.5)][(int)(m.getCoordY() + 0.5)].getCoordX()+", "+elements[(int)(m.getCoordX() + 0.5)][(int)(m.getCoordY() + 0.5)].getCoordY()+") | x = "+(elements[(int)(m.getCoordX() + 0.5)][(int)(m.getCoordY() + 0.5)].getCoordX() == m.getCoordX()) + "  | y = "+(elements[(int)(m.getCoordX() + 0.5)][(int)(m.getCoordY() + 0.5)].getCoordY() == m.getCoordY()) + "Row: "+getElementRow(m) + " | Line: " + getElementLine(m));
				//System.out.println("m1("+m1.getCoordX()+", "+m1.getCoordY()+"), speed = "+m1.getSpeed() + "    Element("+elements[(int)(m1.getCoordX() + 0.5)][(int)(m1.getCoordY() + 0.5)].getCoordX()+", "+elements[(int)(m1.getCoordX() + 0.5)][(int)(m1.getCoordY() + 0.5)].getCoordY()+") | x = "+(elements[(int)(m1.getCoordX() + 0.5)][(int)(m1.getCoordY() + 0.5)].getCoordX() == m1.getCoordX()) + "  | y = "+(elements[(int)(m1.getCoordX() + 0.5)][(int)(m1.getCoordY() + 0.5)].getCoordY() == m1.getCoordY()) + "Row: "+getElementRow(m1) + " | Line: " + getElementLine(m1) + " | dir = " + m1.getDir());
			}
			if(keyCode == KeyEvent.VK_BACK_SPACE)
			{
				getElement(m[playerId], 0.5, 0.5).printElement();
				m[1].setSpeed(0.015625);
			}
		}

		
		public void keyReleased(KeyEvent e) 
		{
			int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN ||keyCode == KeyEvent.VK_RIGHT ||keyCode == KeyEvent.VK_LEFT && isMoving > 0)
			{
				isMoving--;
				if(isMoving < 0)
					isMoving = 0;
			}
		}
	}
	private class BombsTimer extends TimerTask
	{
		private Bomb bomb;
		public BombsTimer(Bomb bomb)
		{
			this.bomb = bomb;
		}
		public void run()
		{
			if(aliveMen > 1 && time > 0)
			{
				if(bomb.getTime() > 0)
				{
					bomb.decTime();
					timer.schedule(new BombsTimer(bomb), 1);
				}
				else
				{
					rebaseBomb(bomb);
					bomb.getPutter().bombIsBack();
					explode(bomb);
					repaint();
				}
			}
		}
	}
	private class ShieldsTimer extends TimerTask
	{
		private Man man;
		public ShieldsTimer(Man man)
		{
			this.man = man;
		}
		public void run()
		{
			if(man.hasShield() && aliveMen > 1 && time > 0)
			{
				man.decShield();
				timer.schedule(new ShieldsTimer(man), 1);
			}
			/*
			else
			{
				//man.decShield();
			}
			*/
		}
	}
	private class MoveTimer extends TimerTask
	{
		private Man man;
		//private int dir;
		private Boolean firstMove;
		public MoveTimer(Man man,  Boolean firstMove)
		{
			this.man = man;
			//this.dir = dir;
			this.firstMove = firstMove;
		}
		public void run()
		{
			if(!moveFlag || firstMove)
			{
				if(isMoving == 1 && m[playerId].isAlive() && aliveMen > 1 && time > 0)
				{
					move(man);
					moveFlag = false;
					timer.schedule(new MoveTimer(man, false), 9); //3 in school				
				}
			}
			//else
				//cancel();
		}
	}
	private class BotsTimer extends TimerTask
	{
		private Man man;
		public BotsTimer(Man man)
		{
			this.man = man;
		}
		public void run()
		{
			if(man.isAlive() && aliveMen > 1 && time > 0)
			{
				int x = 0;
				if(man.canChangeDir())
				{
					man.mainAlgorithm(elements,m);
					if(man.getGoingToDropBomb())
					{
						placeBomb(man);
						//x = -5;
						//man.setDir(-1);
					}
				}
				if(man.getDir() != -1)
					move(man);
				timer.schedule(new BotsTimer(man), 10 + x);
			}
		}
	}
	private class CheckOnFireTimer extends TimerTask
	{
		private Man man;
		public CheckOnFireTimer(Man man)
		{
			this.man = man;
		}
		public void run()
		{
			if(!man.hasShield() && (getElement(man, 0.5, 0.5).getZoneTime() > 0 || getElement(man, 0.79, 0.5).getZoneTime() > 0 || getElement(man, 0.29, 0.5).getZoneTime() > 0 || getElement(man, 0.5, 0.79).getZoneTime() > 0 || getElement(man, 0.5, 0.29).getZoneTime() > 0))
			{
				man.decLife();
				int id = -1;
				if(getElement(man, 0.5, 0.5).getZoneTime() > 0)
					id = getElement(man, 0.5, 0.5).getMan();
				else if(getElement(man, 0.79, 0.5).getZoneTime() > 0)
					id = getElement(man, 0.79, 0.5).getMan();
				else if(getElement(man, 0.29, 0.5).getZoneTime() > 0)
					id = getElement(man, 0.29, 0.5).getMan();
				else if(getElement(man, 0.5, 0.79).getZoneTime() > 0)
					id = getElement(man, 0.5, 0.79).getMan();
				else if(getElement(man, 0.5, 0.29).getZoneTime() > 0)
					id = getElement(man, 0.5, 0.29).getMan();
				if(id != -1 && id != man.getId())
				{
					m[id].killedOtherMan();
				}
				if(man.isAlive())
				{
					man.activateShield();
					timer.schedule(new ShieldsTimer(man), 1);
					timer.schedule(new CheckOnFireTimer(man), 300); //shield time
				}
				else
				{
					remove(man);
					repaint();
					aliveMen--;
				}
			}
			else if(man.isAlive()) // May delete the condition
				timer.schedule(new CheckOnFireTimer(man), 10);
		}
	}
	private class MoveBombTimer extends TimerTask
	{
		private int dir;
		private Bomb bomb;
		public MoveBombTimer(int dir, Bomb bomb)
		{
			this.dir = dir;
			this.bomb = bomb;
		}
		public void run()
		{
			if(aliveMen > 1 && time > 0)
			{
				if(dir == 0)
					{
						if(getElement(bomb, 0, -0.19).getSolid() || getElement(bomb, 0, -0.19).getHasBomb())
						{
							bomb.getBase().removeBomb();
							bomb.setIsMoving(false);
							getElement(bomb, 0, 0).putBomb(bomb);
							bomb.setBase(getElement(bomb, 0, 0));
						}
						if(bomb.getIsMoving())
						{
							bomb.decY();
							timer.schedule(new MoveBombTimer(dir, bomb), 15); //5 in school
						}
					}
				if(dir == 1)
				{
					if(getElement(bomb, 0, 1.19).getSolid() || getElement(bomb, 0, 1.19).getHasBomb())
					{
						bomb.getBase().removeBomb();
						bomb.setIsMoving(false);
						getElement(bomb, 0, 0).putBomb(bomb);
						bomb.setBase(getElement(bomb, 0, 0));
					}
					if(bomb.getIsMoving())
					{
						bomb.incY();
						timer.schedule(new MoveBombTimer(dir, bomb), 15);
					}
				}
				if(dir == 2)
				{
					if(getElement(bomb, 1.19, 0).getSolid() || getElement(bomb, 1.19, 0).getHasBomb())
					{
						bomb.getBase().removeBomb();
						bomb.setIsMoving(false);
						getElement(bomb, 0, 0).putBomb(bomb);
						bomb.setBase(getElement(bomb, 0, 0));
					}
					if(bomb.getIsMoving())
					{
						bomb.incX();
						timer.schedule(new MoveBombTimer(dir, bomb), 15);
					}
				}
				if(dir == 3)
				{
					if(getElement(bomb, -0.19, 0).getSolid() || getElement(bomb, -0.19, 0).getHasBomb())
					{
						bomb.getBase().removeBomb();
						bomb.setIsMoving(false);
						getElement(bomb, 0, 0).putBomb(bomb);
						bomb.setBase(getElement(bomb, 0, 0));
					}
					if(bomb.getIsMoving())
					{
						bomb.decX();
						timer.schedule(new MoveBombTimer(dir, bomb), 15);
					}
				}
			}
		}
	}
	private class TimeLeft extends TimerTask
	{
		private JFrame game;
		public TimeLeft(JFrame game)
		{
			this.game = game;
		}
		public void run()
		{
			if(time > 0 && aliveMen > 1)
			{
				time--;
				game.setTitle("Time Left: " + time + " seconds");
				game.requestFocusInWindow();
				timer.schedule(new TimeLeft(game), 1000);
			}
			else
			{
				int id = 0;
				if(time == 0)
				{
					int max = 0;
					for(int i = 0; i < m.length; i++)
					{
						if(m[i].getScorePanel().getScore() > max)
						{
							id = i;
							max = m[i].getScorePanel().getScore();
						}
					}
					
				}
				else
				{
					for(int i = 0; i < m.length; i++)
					{
						if(m[i].isAlive())
						{
							id = i;
						}
					}
				}
				for(int i = 0; i < m.length; i++)
				{
					if(m[i].getId() == id)
						m[i].getScorePanel().win();
					else if(time == 0 && m[i].isAlive())
						m[i].getScorePanel().dead("Low score");
				}
				String color = new String();
				if(id == 0)
					color = "Blue";
				else if(id == 1)
					color = "Red";
				else if(id == 2)
					color = "Green";
				else if(id == 3)
					color = "Purple";
				game.setTitle(color + " Wins!");

			}
		}
	}


}
