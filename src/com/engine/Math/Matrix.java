package com.engine.Math;

public class Matrix {
	
	// Multiplikation verbessern !! zeile * spalte geht kürzer vlt [i][j] * [j][i]
	
	public float [][]m;

	public Matrix(){
		m = new float[4][4];
	}
	public float get(int x, int y)	{
		return m[x][y];
	}
	public void set(int x, int y, float value){
		m[x][y]=value;
	}
	
	public Matrix initIdentity(){
		m[0][0]=1;	m[0][1]=0;	m[0][2]=0;	m[0][3]=0;
		m[1][0]=0;	m[1][1]=1;	m[1][2]=0;	m[1][3]=0;
		m[2][0]=0;	m[2][1]=0;	m[2][2]=1;	m[2][3]=0;
		m[3][0]=0;	m[3][1]=0;	m[3][2]=0;	m[3][3]=1;
		
		return this;
	}
	
	public Matrix initTranslation(float x, float y, float z){
		m[0][0]=1;	m[0][1]=0;	m[0][2]=0;	m[0][3]=x;
		m[1][0]=0;	m[1][1]=1;	m[1][2]=0;	m[1][3]=y;
		m[2][0]=0;	m[2][1]=0;	m[2][2]=1;	m[2][3]=z;
		m[3][0]=0;	m[3][1]=0;	m[3][2]=0;	m[3][3]=1;
		
		return this;
	}
	
	public Matrix initRotation(float x, float y, float z){
		
		Matrix rx = new Matrix();
		Matrix ry = new Matrix();
		Matrix rz = new Matrix();
		
		x = (float)Math.toRadians(x);
		y = (float)Math.toRadians(y);
		z = (float)Math.toRadians(z);
		
		float cos = (float)Math.cos(z);
		float sin = (float)Math.sin(z);
			
		rz.m[0][0]=cos;	rz.m[0][1]=-sin;	rz.m[0][2]=0;	rz.m[0][3]=0;
		rz.m[1][0]=sin;	rz.m[1][1]=cos;		rz.m[1][2]=0;	rz.m[1][3]=0;
		rz.m[2][0]=0;	rz.m[2][1]=0;		rz.m[2][2]=1;	rz.m[2][3]=0;
		rz.m[3][0]=0;	rz.m[3][1]=0;		rz.m[3][2]=0;	rz.m[3][3]=1;
		
		cos = (float)Math.cos(x);
		sin = (float)Math.sin(x);
		
		rx.m[0][0]=1;	rx.m[0][1]=0;		rx.m[0][2]=0;		rx.m[0][3]=0;
		rx.m[1][0]=0;	rx.m[1][1]=cos;		rx.m[1][2]=-sin;	rx.m[1][3]=0;
		rx.m[2][0]=0;	rx.m[2][1]=sin;		rx.m[2][2]=cos;		rx.m[2][3]=0;
		rx.m[3][0]=0;	rx.m[3][1]=0;		rx.m[3][2]=0;		rx.m[3][3]=1;
		
		cos = (float)Math.cos(y);
		sin = (float)Math.sin(y);
		
		ry.m[0][0]=cos;	ry.m[0][1]=0;	ry.m[0][2]=-sin;	ry.m[0][3]=0;
		ry.m[1][0]=0;	ry.m[1][1]=1;	ry.m[1][2]=0;		ry.m[1][3]=0;
		ry.m[2][0]=sin;	ry.m[2][1]=0;	ry.m[2][2]=cos;		ry.m[2][3]=0;
		ry.m[3][0]=0;	ry.m[3][1]=0;	ry.m[3][2]=0;		ry.m[3][3]=1;
	
		m = (rz.mul(ry.mul(rx))).m;
		return this;
	}
	
	public Matrix initScale(float x, float y, float z){
		m[0][0]=x;	m[0][1]=0;	m[0][2]=0;	m[0][3]=0;
		m[1][0]=0;	m[1][1]=y;	m[1][2]=0;	m[1][3]=0;
		m[2][0]=0;	m[2][1]=0;	m[2][2]=z;	m[2][3]=0;
		m[3][0]=0;	m[3][1]=0;	m[3][2]=0;	m[3][3]=1;
		
		return this;
	}
	
	
	public Matrix initPerspective(float fov, float aspectRatio, float zNear, float zFar){
		float halfFOV = (float)Math.tan(fov/2);
		float depth = zNear-zFar;
		float zscale = (-zNear-zFar)/depth;
		
		
		m[0][0]=1.0f/halfFOV*aspectRatio;	m[0][1]=0;			  m[0][2]=0;		m[0][3]=0;
		m[1][0]=0;							m[1][1]=1.0f/halfFOV; m[1][2]=0;		m[1][3]=0;
		m[2][0]=0;							m[2][1]=0;			  m[2][2]=zscale;	m[2][3]=2*zFar*zNear/depth;
		m[3][0]=0;							m[3][1]=0;			  m[3][2]=1;		m[3][3]=0;
		
		return this;
	}
	
	public Matrix initOrthographic(float left,float right,float bottom,float top, float zNear, float zFar){
		float width = right-left;
		float height = bottom-top;
		float depth = zFar-zNear;
		
		m[0][0]=2/width;	m[0][1]=0;			m[0][2]=0;			m[0][3]=-(right+left)/width;
		m[1][0]=0;			m[1][1]=2/height;	m[1][2]=0;			m[1][3]=-(top+left)/height;
		m[2][0]=0;			m[2][1]=0;			m[2][2]=-2/depth;	m[2][3]=-(zFar+zNear)/depth;
		m[3][0]=0;			m[3][1]=0;			m[3][2]=0;			m[3][3]=1;
		
		return this;
	}
	
	
	public Matrix initCameraRotation(Vector3D forward, Vector3D up){
		Vector3D f = forward;
		f.normalize();
		Vector3D r = up;
		r.normalize();
		r = r.cross(f);
		Vector3D u = f.cross(r);
		
		
		m[0][0]=r.x;	m[0][1]=r.y;	m[0][2]=r.z;	m[0][3]=0;
		m[1][0]=u.x;	m[1][1]=u.y;	m[1][2]=u.z;	m[1][3]=0;
		m[2][0]=f.x;	m[2][1]=f.y;	m[2][2]=f.z;	m[2][3]=0;
		m[3][0]=0;		m[3][1]=0;		m[3][2]=0;		m[3][3]=1;
		
		return this;
	}	
	
	public Matrix mul(Matrix v){
		Matrix res = new Matrix();
		for(int i =0; i<res.m.length;i++){
			for(int j=0; j<res.m[0].length;j++){
				res.set(i, j, m[i][0] * v.get(0,j)+
							  m[i][1] * v.get(1,j)+
							  m[i][2] * v.get(2,j)+
							  m[i][3] * v.get(3,j));
			}
		}
		return res;
	}
	
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		for(int i =0; i<m.length;i++){
			for(int j=0; j<m[0].length; j++)
				buff.append(m[i][j]+" ");
			buff.append("\n");
		}
		return buff.toString();
	}


}
