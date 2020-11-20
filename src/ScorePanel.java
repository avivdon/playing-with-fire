import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
public class ScorePanel extends JPanel
{
	private int id;
	private Timer timer;
	private int time;
	private String shield = "not activated";
	private int power = 2;
	private int bombs = 1;
	private int speed = 100;
	private int life = 3;
	private int score = (life - 1) * 300;
	private String strength = "no";
	private JLabel speedT, bombsT, powerT, strengthT, shieldT, lifeT, scoreT, bonus, dead, win;
	private Color color;
	public ScorePanel(int id)
	{
		this.id = id;
		time = 0;
		timer = new Timer();
		setLocation(-1, 119 * id);
		setSize(137, 119);
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.black));
		if(id == 0)
			//setBackground(Color.blue);
			color = Color.cyan;
		else if(id == 1)
			//setBackground(Color.red);
			color = Color.red;
		else if(id == 2)
			//setBackground(Color.green);
			color = Color.green;
		else
			//setBackground(Color.magenta);
			color = Color.magenta;
		setBackground(Color.gray);
		speedT = new JLabel("Speed: " + speed + "%");
		bombsT = new JLabel("Bombs: " + bombs);
		powerT = new JLabel("Power: " + power);
		strengthT = new JLabel("Strong: " + strength);
		shieldT = new JLabel("Shield: " + shield);
		lifeT = new JLabel("Life: " + life);
		scoreT = new JLabel("Score: " + score);
		bonus = new JLabel();
		bonus.setSize(40, 40);
		bonus.setLocation(80, 20);
		speedT.setForeground(color);
		bombsT.setForeground(color);
		powerT.setForeground(color);
		strengthT.setForeground(color);
		shieldT.setForeground(color);
		lifeT.setForeground(color);
		scoreT.setForeground(color);
		speedT.setSize(80, 17);
		bombsT.setSize(80, 17);
		powerT.setSize(80, 17);
		strengthT.setSize(80, 17);
		shieldT.setSize(135, 17);
		lifeT.setSize(80, 17);
		scoreT.setSize(80, 17);
		speedT.setLocation(3, 0);
		bombsT.setLocation(3, 17);
		powerT.setLocation(3, 34);
		strengthT.setLocation(3, 51);
		shieldT.setLocation(3, 68);
		lifeT.setLocation(3, 85);
		scoreT.setLocation(3, 102);
		add(speedT);
		add(bombsT);
		add(powerT);
		add(strengthT);
		add(shieldT);
		add(lifeT);
		add(scoreT);
	}
	public void addScore(int points)
	{
		score = score + points;
		scoreT.setText("Score: " + score);
		if(time == 0)
		{
			bonus.setIcon(new ImageIcon("icons/PWF " + points + " " + id + ".png"));
			add(bonus);
			repaint();
			time = 500;
			timer.schedule(new BonusTimer(), time);
		}
		else
		{
			bonus.setIcon(new ImageIcon("icons/PWF " + points + " " + id + ".png"));
			repaint();
			time = 500;
		}
	}
	public void incSpeed()
	{
		speed = speed * 2;
		speedT.setText("Speed: " + speed + "%");
		addScore(150);
	}
	public void activateShield()
	{
		shield = "activated";
		shieldT.setText("Shield: " + shield);
		setBackground(Color.white);
	}
	public void activateBonusShield()
	{
		shield = "activated";
		shieldT.setText("Shield: " + shield);
		setBackground(Color.white);
		addScore(300);
	}
	public void incLife()
	{
		life++;
		lifeT.setText("Life: " + life);
		addScore(300);
	}
	public void decLife()
	{
		life--;
		lifeT.setText("Life: " + life);
		addScore(-300);
	}
	public void dead(String string)
	{
		remove(speedT);
		remove(bombsT);
		remove(powerT);
		remove(strengthT);
		remove(shieldT);
		remove(lifeT);
		if(dead != null)
			remove(dead);
		//remove(scoreT);
		repaint();
		setBackground(color);
		scoreT.setForeground(Color.white);
		dead = new JLabel(string);
		dead.setForeground(Color.black);
		dead.setSize(135, 20);
		dead.setLocation(3, 0);
		add(dead);
	}
	public void win()
	{
		remove(speedT);
		remove(bombsT);
		remove(powerT);
		remove(strengthT);
		remove(shieldT);
		remove(lifeT);
		if(dead != null)
			remove(dead);
		//remove(scoreT);
		repaint();
		setBackground(color);
		scoreT.setForeground(Color.white);
		win = new JLabel("The winner!");
		win.setForeground(Color.white);
		win.setSize(135, 20);
		win.setLocation(3, 0);
		add(win);
	}
	public void strength()
	{
		strength = "yes";
		strengthT.setText("Strong: " + strength);
		addScore(200);
	}
	public void addBomb()
	{
		bombs++;
		bombsT.setText("Bombs: " + bombs);
		addScore(100);
	}
	public void addPower()
	{
		power++;
		powerT.setText("Power: " + power);
		addScore(100);
	}
	public void disactivateShield()
	{
		shield = "not activated";
		shieldT.setText("Shield: " + shield);
		setBackground(Color.gray);
	}
	public int getScore()
	{
		return score;
	}
	private class BonusTimer extends TimerTask
	{
		public void run()
		{
			if(time == 0)
			{
				remove(bonus);
				repaint();
			}
			else
			{
				time = time - 10;
				timer.schedule(new BonusTimer(), 10);
			}
		}
	}
}
