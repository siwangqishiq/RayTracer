package com.bn.Sample5_8;//������
//�������ÿ��ĳ�������ཻ��Ϣ����
public class Intersection {

	int numHits;//�����ཻʱ����ཻ�������Ŀ
	HitInfo[] hit=new HitInfo[8];//�����б�����
	
	public Intersection(){		
		for(int i=0; i<8; i++){//��ʼ�������б�����
			hit[i]=new HitInfo();
		}
	}
	public void set(Intersection inter){//������Intersection�������Ϣ���Ƶ�����
		for(int i=0; i<8; i++){
			this.hit[i].set(inter.hit[i]);
		}
		
		this.numHits = inter.numHits;
	}
}
