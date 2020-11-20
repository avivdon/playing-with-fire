import javax.swing.ImageIcon;


public class Bomb extends Element
{
	//private Boolean isActive;
	private Boolean isMoving;
	private int power;
	private Element base;
	private int time;
	private Man putter;
	public Bomb(Man man, Element[][] elements)
	{
		putter = man;
		power = man.getBombPower();
		time = 3000;
		//isActive = true;
		isMoving = false;
		solid = true;
		base = man.getElement(elements);
		setSize(40, 40);
		coordX = Math.round(man.getCoordX());
		coordY = Math.round(man.getCoordY());
		updateLocation();
		setIcon(new ImageIcon("icons/PWF- BOMB " + putter.getId() + ".png"));
		setOpaque(false);
	}
	public int getPower()
	{
		return power;
	}
	public Man getPutter()
	{
		return putter;
	}
	public void incX()
	{
		coordX = coordX + 0.2;
		updateLocation();
	}
	public void incY()
	{
		coordY = coordY + 0.2;
		updateLocation();
	}
	public void decX()
	{
		coordX = coordX - 0.2;
		updateLocation();
	}
	public void decY()
	{
		coordY = coordY - 0.2;
		updateLocation();
	}
	/*
	public Boolean getIsActive()
	{
		return isActive;
	}
	
	public void disable()
	{
		isActive = false;
		solid = false;
	}
	*/
	public Boolean getIsMoving()
	{
		return isMoving;
	}
	public void setIsMoving(Boolean isMoving)
	{
		this.isMoving = isMoving;
	}
	public Element getBase()
	{
		return base;
	}
	public void setBase(Element element)
	{
		base = element;
	}
	public void decTime()
	{
		time--;
	}
	public int getTime()
	{
		return time;
	}
	public void trigger()
	{
		time = Math.min(time, 20);
	}
}
