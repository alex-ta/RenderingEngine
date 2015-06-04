package com.math;

public class Vector2D 
{
	private float x;
	private float y;
	
	public Vector2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float length()
	{
		return (float)Math.sqrt(x * x + y * y);
	}

	public float max()
	{
		return Math.max(x, y);
	}

	public float dot(Vector2D r)
	{
		return x * r.getX() + y * r.getY();
	}
	
	public Vector2D normalized()
	{
		float length = length();
		
		return new Vector2D(x / length, y / length);
	}

	public float cross(Vector2D r)
	{
		return x * r.getY() - y * r.getX();
	}

	public Vector2D lerp(Vector2D dest, float lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	public Vector2D rotate(float angle)
	{
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2D((float)(x * cos - y * sin),(float)(x * sin + y * cos));
	}
	
	public Vector2D add(Vector2D r)
	{
		return new Vector2D(x + r.getX(), y + r.getY());
	}
	
	public Vector2D add(float r)
	{
		return new Vector2D(x + r, y + r);
	}
	
	public Vector2D sub(Vector2D r)
	{
		return new Vector2D(x - r.getX(), y - r.getY());
	}
	
	public Vector2D sub(float r)
	{
		return new Vector2D(x - r, y - r);
	}
	
	public Vector2D mul(Vector2D r)
	{
		return new Vector2D(x * r.getX(), y * r.getY());
	}
	
	public Vector2D mul(float r)
	{
		return new Vector2D(x * r, y * r);
	}
	
	public Vector2D div(Vector2D r)
	{
		return new Vector2D(x / r.getX(), y / r.getY());
	}
	
	public Vector2D div(float r)
	{
		return new Vector2D(x / r, y / r);
	}
	
	public Vector2D abs()
	{
		return new Vector2D(Math.abs(x), Math.abs(y));
	}
	
	public String toString()
	{
		return "(" + x + " " + y + ")";
	}

	public Vector2D set(float x, float y) { this.x = x; this.y = y; return this; }
	public Vector2D set(Vector2D r) { set(r.getX(), r.getY()); return this; }

	public float getX() 
	{
		return x;
	}

	public void setX(float x) 
	{
		this.x = x;
	}

	public float getY() 
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public boolean equals(Vector2D r)
	{
		return x == r.getX() && y == r.getY();
	}
}