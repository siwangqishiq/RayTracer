package com.bn.Sample5_8;//������
/*
 * ������ԭ�㣬�뾶Ϊ1�ı�׼��
 * ע�⣺��任�����������Ҳ�ǶԵ�
 */
public class Ball extends HitObject {//������ԭ�㣬�뾶Ϊ1�ı�׼��
	public Ball(Camera cam, Color3f color){//��ʼ����ɫ�������
		this.cam=cam;
		this.color=color;
	}
	   
	@Override
	public boolean hit(Ray r,Intersection inter) {//��д�ļ��������������ײ��ķ���
		/*
		 * ������S+ct��任������Ľ�����Ҫ���²��裺
		 * 1�������任����S'+c't
		 * 2�������任������ͨ���������ײʱ��t
		 * 3������ײʱ��t�����ʽS+ct�õ�ʵ�ʵĽ�������
		 * 
		 * 
		 * ��ˣ�genRayֻ�Ǳ任��Ĺ��ߣ�
		 * ֻ���������ײʱ��t��
		 * ��t�󽻵�ʱ�ñ任ǰ�Ĺ���r
		 */
		Ray genRay=new Ray();//�任��Ĺ���		
		xfrmRay(genRay, getInvertMatrix(), r);//������任��Ĺ���

		double A,B,C;//���㽻��ķ��̵�3��ϵ��
		A = Vector3.dot(genRay.dir,genRay.dir);	//�����һ��ϵ��A
		B = Vector3.dot(genRay.start, genRay.dir);//�����һ��ϵ��B
		C = Vector3.dot(genRay.start, genRay.start)-1.0f;//�����һ��ϵ��C
		double discrim = B*B-A*C;                        //���б�ʽ��ֵ
		if(discrim<0.0){//���б�ʽֵС��0��û�н���
			return false;
		}
		int num=0;//Ŀǰ�Ľ������
		double discRoot = (float) Math.sqrt(discrim);//���б�ʽ��ƽ����
		double t1 = (-B-discRoot)/A;		//��һ������Ķ�Ӧ�ཻʱ��
		if(t1>0.00001){
			inter.hit[0].hitTime=t1;//��¼�����ʱ��
			inter.hit[0].hitObject=this;//��¼��������������
			inter.hit[0].isEntering=true;
			inter.hit[0].surface=0;			
			Point3 P = rayPos(r,t1);//��������(ʹ�ñ任ǰ�Ĺ���)
			inter.hit[0].hitPoint.set(P);//�任��Ľ���λ��		
			Point3 preP = xfrmPtoPreP(P);//ͨ���任��ĵ���任ǰ�ĵ�
			inter.hit[0].hitNormal.set(preP);//�任ǰ�ĵ���Ǳ任ǰ�ķ�����
			
			num=1;//��������
		}
		double t2 = (-B+discRoot)/A;//�ڶ�������Ķ�Ӧ�ཻʱ��
		if(t2>0.00001){
			inter.hit[num].hitTime=t2;//��¼�����ʱ��
			inter.hit[num].hitObject=this;//��¼��������������
			inter.hit[num].isEntering=true;
			inter.hit[num].surface=0;			
			Point3 P = rayPos(r,t2);//��������(ʹ�ñ任ǰ�Ĺ���)
			inter.hit[num].hitPoint.set(P);
			Point3 preP = xfrmPtoPreP(P);//ͨ���任��ĵ���任ǰ�ĵ�
			inter.hit[num].hitNormal.set(preP);//�任ǰ�ĵ���Ǳ任ǰ�ķ�����
			
			num++;//��������
		}
		inter.numHits=num;
		return (num>0);//����һ����һ�����ϵ���Ч�����򷵻�true
	}

	@Override
	public boolean hit(Ray r) {//��������Ӱ̽�����ж��Ƿ�����Ӱ�еķ���
		Ray genRay=new Ray();//�任��Ĺ���		
		xfrmRay(genRay, getInvertMatrix(), r);//��ȡ�任��Ĺ���

		double A,B,C;//���㽻��ķ��̵�3��ϵ��
		A = Vector3.dot(genRay.dir,genRay.dir);	//�����һ��ϵ��A
		B = Vector3.dot(genRay.start, genRay.dir);//�����һ��ϵ��B
		C = Vector3.dot(genRay.start, genRay.start)-1.0f;//�����һ��ϵ��C
		double discrim = B*B-A*C;                        //���б�ʽֵ
		if(discrim<0.0){//���б�ʽС��0��û�н���
			return false;
		}
		double discRoot = (float) Math.sqrt(discrim);//���б�ʽ��ƽ����
		double t1 = (-B-discRoot)/A;		//��һ���ཻ��ʱ��
		//ֻ���ܴ�0��1֮�����ײ����Ϊ�ڹ�Դ����һ�಻�������Ӱ
		if(t1<0 || t1>1){//���ཻʱ�䲻��0��1��������Ӱ��
			return false;
		}
		return true;//��������Ӱ��
	}
}
