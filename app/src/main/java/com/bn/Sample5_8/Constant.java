package com.bn.Sample5_8;//������

//������ 
public class Constant {
	//������Ļ�ߴ�ĳ���
	public static final float SCREEN_WIDTH = 800;//��Ļ�Ŀ��
	public static final float SCREEN_HEIGHT = 480;//��Ļ�ĸ߶�
	//������Ⱦʱ�ĳ���
	public static final float blockSize = 1f;//������ĳߴ�
	public static final float W = SCREEN_WIDTH / 2.0f;//��Ļ���
	public static final float H = SCREEN_HEIGHT / 2.0f;//��Ļ���
	public static final float ratio = W / H;//��Ļ��߱�
	public static final float nRows = SCREEN_HEIGHT;//��Ļ����������
	public static final float nCols = SCREEN_WIDTH;//��Ļ����������
	//������ʵ3D�����н�ƽ�����
	public static final float N_3D = 24;//��ƽ�浽������ľ���
	public static final float W_3D = ratio;//��ƽ����
	public static final float H_3D = 1.0f;//��ƽ����
	//������ʵ�����и��������
	public static final float R = 0.6f;//��İ뾶
	public static final float CENTER_DIS = 0.7f;//��������������ϵy��ľ���
	public static final float PLANE_WIDTH = 3.5f;//ƽ����
	public static final float PLANE_HEIGHT = 4f;//ƽ�泤��
	
	public static final float[] BALL1_COLOR = {0.8f,0.2f,0.2f};	//��1����ɫ
	public static final float[] BALL2_COLOR = {0.2f,0.2f,0.8f};//��2����ɫ
	public static final float[] PLANE_COLOR = {0.2f,0.8f,0.2f};//ƽ�����ɫ
	//����������Ĳ���
	public static final float CAM_X = 15;//�����λ��x����
	public static final float CAM_Y = 7;//�����λ��y����
	public static final float CAM_Z = 32;//�����λ��z����
	//���ڹ�Դ�Ĳ���
	public static final float LIGHT_X = 100;//��Դλ��x����
	public static final float LIGHT_Y = 80;//��Դλ��y����
	public static final float LIGHT_Z = 0;//��Դλ��z����
	//������Ӱʱ�õļ�С������
	public static final float MNIMUM = 0.00001f;
}