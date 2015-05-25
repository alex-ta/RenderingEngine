package com.engine.rendering.objects;

import com.math.Vector2D;
import com.math.Vector3D;

public class Vertex
{
	public static final int SIZE = 8;
	
	private Vector3D pos;
	private Vector2D texCoord;
	private Vector3D normal;
	
	public Vertex(Vector3D pos)
	{
		this(pos, new Vector2D(0,0));
	}
	
	public Vertex(Vector3D pos, Vector2D texCoord)
	{
		this(pos, texCoord, new Vector3D(0,0,0));
	}
	
	public Vertex(Vector3D pos, Vector2D texCoord, Vector3D normal)
	{
		this.pos = pos;
		this.texCoord = texCoord;
		this.normal = normal;
	}

	public Vector3D getPos()
	{
		return pos;
	}

	public void setPos(Vector3D pos)
	{
		this.pos = pos;
	}

	public Vector2D getTexCoord()
	{
		return texCoord;
	}

	public void setTexCoord(Vector2D texCoord)
	{
		this.texCoord = texCoord;
	}

	public Vector3D getNormal()
	{
		return normal;
	}

	public void setNormal(Vector3D normal)
	{
		this.normal = normal;
	}
}
