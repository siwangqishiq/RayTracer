package com.bn.Sample5_8;//������
//һ���������Ϣ
public class HitInfo{

	double hitTime;//�ཻʱ��
	HitObject hitObject;//�ཻ������
	boolean isEntering;//�����ǽ��뻹�����
	int surface;//�ཻ���ĸ�����
	Point3 hitPoint;//��������꣬�任���
	Vector3 hitNormal;//���㴦�ķ��������任ǰ��
	
	public HitInfo(){
		hitPoint = new Point3();//������ײ�����
		hitNormal = new Vector3();//��������������
	}
	/* 
	 * �˷������ܻ᲻�ԣ�����������ܳ���
	 * ����н�����˵�������Ի�����
	 */
	public void set(HitInfo hit){//���ݴ����HitInfo���󣬸��Ƹ�����Ϣ��������ĳ�Ա����
		this.hitTime=hit.hitTime;//�����ཻʱ��ֵ
		this.hitObject=hit.hitObject;//�����ཻ������
		this.isEntering=hit.isEntering;//���ù����ǽ��뻹�����
		this.surface=hit.surface;//�����ཻ���ĸ�����
		this.hitPoint.set(hit.hitPoint);//���ý�������꣬�任���
		this.hitNormal.set(hit.hitNormal);//���ý��㴦�ķ��������任ǰ��
	}
	@Override
	public String toString() {
		return "hitTime"+hitTime+",hitPoint"+hitPoint;
	}
}
