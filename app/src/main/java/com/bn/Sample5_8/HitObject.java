package com.bn.Sample5_8;//������
import android.opengl.Matrix;//���������

public abstract class HitObject {
	Color3f color;//�������ɫ
	private float[] myMatrix;//�任����

	Camera cam;//�����

	public abstract boolean hit(Ray ray,Intersection inter);	//�󽻵�ķ���
	public abstract boolean hit(Ray ray);	//ֻ�ж��Ƿ��ཻ�ķ���
	public Color3f getColor() {//��ȡ������ɫ�ķ���
		return color;
	}
	 //���ع�����tʱ�̵�λ�õķ���(ʹ�ñ任ǰ�Ĺ���)
	public Point3 rayPos(Ray r,double t){
		return cam.eye.addVec(r.dir.multiConst((float)t));	//eye+dir*t�õ���ǰ�������
	}
	//��ȡ�任��Ĺ���(��r����任����任��ֵ��genRay��(��˱任����������))
	public void xfrmRay(Ray genRay, float[] invTransf, Ray r){		
		//��任���start
		float[] genStart = new float[4];
		Matrix.multiplyMV(genStart, 0, invTransf, 0, r.start.toQici4(), 0);
		genRay.start.set(genStart);//ֻȡ��������ǰ3��������Ϊstart
		
		//��任���dir
		float[] genDir = new float[4];
		Matrix.multiplyMV(genDir, 0, invTransf, 0, r.dir.toQici4(), 0);
		genRay.dir.set(genDir);//ֻȡ��������ǰ3��������Ϊdir
	}
	//��ȡ�任��ķ�����(��˱任�������ת�þ���)
	public void xfrmNormal(Vector3 genNormal, float[] invTranspM, Vector3 normal){
		//��任���Normal
		float[] tmpNormal = new float[4];//���ڴ洢�任������������
		Matrix.multiplyMV(tmpNormal, 0, invTranspM, 0, normal.toQici4(), 0);
		genNormal.set(tmpNormal);//ֻȡ��������ǰ3��������Ϊnormal
	}

	//ͨ���任��ĵ���任ǰ�ĵ�ķ��������������
	public Point3 xfrmPtoPreP(Point3 P){
		//ͨ����任���õ��任֮ǰ�ĵ�
		float[] inverM = getInvertMatrix();//��ȡ��任����
		float[] preP = new float[4];//�洢�任ǰ�����������
		Matrix.multiplyMV(preP, 0, inverM, 0, P.toQici4(), 0);//��任ǰ�ĵ�
		return new Point3(preP);//�任ǰ�ĵ���Ǳ任֮ǰ�ķ�����
	}
	//��ʼ���任����ķ���
	public void initMyMatrix() {
		myMatrix = new float[16];
	    Matrix.setIdentityM(myMatrix, 0);	    
	}
	
	//��ȡ����ı任����ķ���
	public float[] getMatrix(){		
		return myMatrix;
	}
	//��ȡ�����ķ���
	public float[] getInvertMatrix(){
		float[] invM = new float[16];//���ڴ洢������������
		Matrix.invertM(invM, 0, myMatrix, 0);//�������
		return invM;
	}
	//��ȡ��ת�þ���ķ���
	public float[] getInvertTransposeMatrix(){
		float[] invTranspM = new float[16];//���ڴ洢������������
		Matrix.transposeM(invTranspM, 0, myMatrix, 0);//��ת�þ���
		Matrix.invertM(invTranspM, 0, invTranspM, 0);//����ת�þ���
		return invTranspM;
	}
	//������x��y��z���ƶ�
	public void translate(float x, float y, float z) {
		Matrix.translateM(myMatrix, 0, x, y, z);
	}
	//����ָ������ת
	public void rotate(float angle, float x, float y, float z) {
		Matrix.rotateM(myMatrix, 0, angle, x, y, z);
	}
	//������x��y��z������
	public void scale(float x, float y, float z) {
		Matrix.scaleM(myMatrix, 0, x, y, z);
	}
}