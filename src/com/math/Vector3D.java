package com.math;

public class Vector3D 
{
	private float x;
	private float y;
	private float z;
	
	public Vector3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float length()
	{
		return (float)Math.sqrt(x * x + y * y + z * z);
	}

	public float max()
	{
		return Math.max(x, Math.max(y, z));
	}

	public float dot(Vector3D r)
	{
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}
	
	public Vector3D cross(Vector3D r)
	{
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();
		
		return new Vector3D(x_, y_, z_);
	}
	
	public Vector3D normalized()
	{
		float length = length();
		
		return new Vector3D(x / length, y / length, z / length);
	}

	public Vector3D rotate(Vector3D axis, float angle)
	{
		float sinAngle = (float)Math.sin(-angle);
		float cosAngle = (float)Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add(           //Rotation on local X
				(this.mul(cosAngle)).add(                     //Rotation on local Z
						axis.mul(this.dot(axis.mul(1 - cosAngle))))); //Rotation on local Y
	}

	public Vector3D rotate(Quaternion rotation)
	{
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3D(w.getX(), w.getY(), w.getZ());
	}

	public Vector3D lerp(Vector3D dest, float lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	public Vector3D add(Vector3D r)
	{
		return new Vector3D(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	public Vector3D add(float r)
	{
		return new Vector3D(x + r, y + r, z + r);
	}
	
	public Vector3D sub(Vector3D r)
	{
		return new Vector3D(x - r.getX(), y - r.getY(), z - r.getZ());
	}
	
	public Vector3D sub(float r)
	{
		return new Vector3D(x - r, y - r, z - r);
	}
	
	public Vector3D mul(Vector3D r)
	{
		return new Vector3D(x * r.getX(), y * r.getY(), z * r.getZ());
	}
	
	public Vector3D mul(float r)
	{
		return new Vector3D(x * r, y * r, z * r);
	}
	
	public Vector3D div(Vector3D r)
	{
		return new Vector3D(x / r.getX(), y / r.getY(), z / r.getZ());
	}
	
	public Vector3D div(float r)
	{
		return new Vector3D(x / r, y / r, z / r);
	}
	
	public Vector3D abs()
	{
		return new Vector3D(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	
	public String toString()
	{
		return "(" + x + " " + y + " " + z + ")";
	}

	public Vector2D getXY() { return new Vector2D(x, y); }
	public Vector2D getYZ() { return new Vector2D(y, z); }
	public Vector2D getZX() { return new Vector2D(z, x); }

	public Vector2D getYX() { return new Vector2D(y, x); }
	public Vector2D getZY() { return new Vector2D(z, y); }
	public Vector2D getXZ() { return new Vector2D(x, z); }

	public Vector3D set(float x, float y, float z) { this.x = x; this.y = y; this.z = z; return this; }
	public Vector3D set(Vector3D r) { set(r.getX(), r.getY(), r.getZ()); return this; }

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

	public float getZ() 
	{
		return z;
	}

	public void setZ(float z) 
	{
		this.z = z;
	}

	public boolean equals(Vector3D r)
	{
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}
}