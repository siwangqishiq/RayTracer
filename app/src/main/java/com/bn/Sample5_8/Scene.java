package com.bn.Sample5_8;//������
import java.util.ArrayList;
import java.util.List;

import static com.bn.Sample5_8.Constant.*;
//������
public class Scene {
	Camera cam;//�����
	Light light;//��Դ
	Ray feeler = new Ray();//��Ӱ̽����
	List<HitObject> hitObjects;//�����б�
	
	Ball ball1;//��ɫ��
	Ball ball2;//��ɫ��
	Square sqare;//����ƽ��
	
	public Scene(Camera cam, Light light){
		this.cam=cam;//��ʼ�������
		this.light = light;//��ʼ����Դ
		hitObjects = new ArrayList<HitObject>();//��ʼ�������б�
		//����һ����ɫ����
		ball1 = new Ball(cam, new Color3f(BALL1_COLOR));
		//����һ����ɫ����
		ball2 = new Ball(cam, new Color3f(BALL2_COLOR));
		//����һ����ɫ��ƽ��
		sqare = new Square(cam, new Color3f(PLANE_COLOR));
		
		//��������볡����
		hitObjects.add(ball1);//����ɫ����������б�
		hitObjects.add(ball2);//����ɫ����������б�
		hitObjects.add(sqare);//����ɫ����ƽ����������б�
		
	}
	
	//�Գ����е�������б任���԰ڷŵ�λ
	public void transform(){	
		//Ϊ���������ʼ���任����
		for(HitObject pObj:hitObjects){
			pObj.initMyMatrix();
		}
		
		//��ת����ƽ��
		sqare.rotate(-90, 1, 0, 0);
		sqare.scale(PLANE_WIDTH/2.0f, PLANE_HEIGHT/2.0f, 1);//���ž���ƽ��
		
		//������1�ı任
		ball1.translate(-CENTER_DIS, R, 0);//ƽ�ƺ�ɫ��
		ball1.scale(R, R, R);//���ź�ɫ��
		
		//������2�ı任
		ball2.translate(CENTER_DIS, R, 0);//ƽ����ɫ��
		ball2.scale(R, R, R);//������ɫ��
	}
	
	/*
	 * ���ع��߶�Ӧ�����ظ���Ϣ,
	 * 
	 * ����ֵ��
	 * -1��ʾû�н��㣬
	 * 0��ʾ�н��㣬�������ײ�㲻����Ӱ��
	 * 1��ʾ�н��㣬�������ײ������Ӱ��
	 */
	public int shade(//���й��߸��ټ���ķ���
			Ray ray, //����
			Color3f color, //��һ�������ɫ
			Point3 vetex, //��һ�����λ��
			Vector3 normal//��һ����ķ�����
	){
		Intersection best = new Intersection();//���ڱ��浽ĿǰΪֹ����Ľ���
		getFirstHit(ray, best);//�������һ����
		if(best.numHits==0){//���û���κν���
			return -1;
		}		
		//���������������ཻ������ײ��ĸ���Ϣ
		color.set(best.hit[0].hitObject.getColor());//���㴦�������ɫ
		vetex.set(best.hit[0].hitPoint);//�����λ��
		//ͨ����ת�ñ任����任֮��ķ�����
		float[] inverTranspM = best.hit[0].hitObject.getInvertTransposeMatrix();//����ת�þ���	
		Vector3 preN = best.hit[0].hitNormal;//�任ǰ�ķ�����
		best.hit[0].hitObject.xfrmNormal(normal, inverTranspM, preN);//ͨ����ת�þ�����任��ķ�����
		
		//ȡ����������
		Point3 hitPoint = best.hit[0].hitPoint;
		//��Ӱ̽���������Ϊ�������㳯���۷����ƶ�һ��΢С�ľ���
		feeler.start.set(hitPoint.minus(ray.dir.multiConst(MNIMUM)));
		//��Ӱ̽�����ķ��򣬴���ײ��ָ���Դ
		feeler.dir = light.pos.minus(hitPoint);
		if(isInShadow(feeler)){//��������Ӱ�з���1
			return 1;//�н��㣬�������ײ������Ӱ��
		}
		return 0;//���㲻����Ӱ�з���0
	}
	
	public void getFirstHit(Ray ray, Intersection best){//���������������������������
		Intersection inter = new Intersection();//���������б����
		best.numHits=0;//��ʼʱ������Ϊ0
		/*
		 * �˴���������ÿ�������Ƿ��ཻ��
		 * ��ÿ�������ཻ����Ϣ����洢��best�С�
		 * ���ڹ����뵥�������ཻʱ��
		 * �ܻὫ����������������ཻ�㱣����best.hit[0]��(��ÿ�������hit��������)��
		 * ���ֻҪ����������ġ�����㡱��Ϣ���Ƚϣ��������ս������best.hit[0]�У�
		 * ���ɵó��������������������Ľ�����Ϣ
		 */
		for(HitObject pObj:hitObjects){//���������е�ÿһ������
			if(!pObj.hit(ray, inter)){//�����Ƿ��뵱ǰ�����ཻ
				continue;//�޽���������һ������
			}
			if(best.numHits==0 ||//��best�л�û�н�����Ϣ����best�еĽ��㲻�������
					inter.hit[0].hitTime<best.hit[0].hitTime){
				/*
				 * ע������һ���Ǹ���һ�ݣ�������ֱ�Ӹ������ã�
				 * ���������ֵһ��ᵼ��best��ֵҲ�䣡
				 */
				best.set(inter);//����ǰ������Ϣ����best
			}
		}
	}
	//��⽻���Ƿ�����Ӱ�еķ�������ڲ���Ϊ��Ӱ̽������Ӧ����
	public boolean isInShadow(Ray feeler){
		for(HitObject pObj:hitObjects){//������������
			if(pObj.hit(feeler)){//�����߱��κ����嵲ס��������Ӱ��
				return true;
			}
		}
		return false;//��������Ӱ��
	}
}