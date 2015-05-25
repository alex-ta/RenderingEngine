package com.math;

public class Transform
{
	private Transform parent;
	private Matrix parentMatrix;

	private Vector3D pos;
	private Quaternion rot;
	private Vector3D scale;

	private Vector3D oldPos;
	private Quaternion oldRot;
	private Vector3D oldScale;

	public Transform()
	{
		pos = new Vector3D(0,0,0);
		rot = new Quaternion(0,0,0,1);
		scale = new Vector3D(1,1,1);

		parentMatrix = new Matrix().initIdentity();
	}

	public void update()
	{
		if(oldPos != null)
		{
			oldPos.set(pos);
			oldRot.set(rot);
			oldScale.set(scale);
		}
		else
		{
			oldPos = new Vector3D(0,0,0).set(pos).add(1.0f);
			oldRot = new Quaternion(0,0,0,0).set(rot).mul(0.5f);
			oldScale = new Vector3D(0,0,0).set(scale).add(1.0f);
		}
	}

	public void rotate(Vector3D axis, float angle)
	{
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	public boolean hasChanged()
	{
		if(parent != null && parent.hasChanged())
			return true;

		if(!pos.equals(oldPos))
			return true;

		if(!rot.equals(oldRot))
			return true;

		if(!scale.equals(oldScale))
			return true;

		return false;
	}

	public Matrix getTransformation()
	{
		Matrix translationMatrix = new Matrix().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix rotationMatrix = rot.toRotationMatrix();
		Matrix scaleMatrix = new Matrix().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	private Matrix getParentMatrix()
	{
		if(parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	public void setParent(Transform parent)
	{
		this.parent = parent;
	}

	public Vector3D getTransformedPos()
	{
		return getParentMatrix().transform(pos);
	}

	public Quaternion getTransformedRot()
	{
		Quaternion parentRotation = new Quaternion(0,0,0,1);

		if(parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	public Vector3D getPos()
	{
		return pos;
	}
	
	public void setPos(Vector3D pos)
	{
		this.pos = pos;
	}

	public Quaternion getRot()
	{
		return rot;
	}

	public void setRot(Quaternion rotation)
	{
		this.rot = rotation;
	}

	public Vector3D getScale()
	{
		return scale;
	}

	public void setScale(Vector3D scale)
	{
		this.scale = scale;
	}
}
