package com.bn.Sample5_8;//������
import static com.bn.Sample5_8.Constant.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.annotation.SuppressLint;
import android.opengl.GLES30;

//����
@SuppressLint("NewApi") public class ColorRect 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ���������� 
    //3D�����е���
    int muColorHandle; //ƬԪ��ɫ�������� 
    int mu3DPosHandle; //�������Ӧ�ĵ�һ��ײ��λ��һ�±�������
    int muNormalHandle; //�������Ӧ�ĵ�һ��ײ�㷨����һ�±�������
    int muLightLocationHandle;//��Դλ��һ�±�������
    int muCameraHandle; //�����λ��һ�±�������
    int muIsShadow;//�Ƿ������Ӱһ�±�������
    
    String mVertexShader;//������ɫ���ű��ַ���	 
    String mFragmentShader;//ƬԪ��ɫ���ű��ַ���
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
    int vCount=0; //��������  
    float[] color3 = new float[3];//�������Ӧ�ĵ�һ��ײ�����ɫ
    float[] vertexPos3D = new float[3];//�������Ӧ�ĵ�һ��ײ���λ��
    float[] normal3D = new float[3];//�������Ӧ�ĵ�һ��ײ��ķ�����
    float[] lightPos3D = new float[3];//��Դλ��
    float[] cameraPos3D = new float[3];//�����λ��
    int isShadow;//�������Ӧ�ĵ�һ��ײ���Ƿ�����Ӱ�б�־
    
    float u;//����������Ļ�ϵ�λ��
    float v;
    public ColorRect(MySurfaceView mv)
    {    	
    	//��ʼ��������������
    	initVertexData();
    	//��ʼ����ɫ��        
    	intShader(mv);
    }
    
    //��ʼ�������������ݵķ���
    public void initVertexData()
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6;
       
        float vertices[]=new float[]
        {	
        	0,0,0,//0
        	Constant.blockSize,0,0,//1
        	Constant.blockSize,Constant.blockSize,0,//2
        	  
        	0,0,0,//0
        	Constant.blockSize,Constant.blockSize,0,//2
        	0,Constant.blockSize,0//3
        };
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
    }

    //��ʼ����ɫ��
    public void intShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ���������� 
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");       
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
        
        //3D�����е���
        //��ȡ������3D�����ж�����ɫ�������� 
        muColorHandle = GLES30.glGetUniformLocation(mProgram, "uColor");
        //��ȡ������3D�����ж���λ���������� 
        mu3DPosHandle = GLES30.glGetUniformLocation(mProgram, "uPosition");
        //��ȡ�����ж��㷨������������  
        muNormalHandle = GLES30.glGetUniformLocation(mProgram, "uNormal");
        //��ȡ�����й�Դλ������
        muLightLocationHandle = GLES30.glGetUniformLocation(mProgram, "uLightLocation");
        //��ȡ�����������λ������
        muCameraHandle = GLES30.glGetUniformLocation(mProgram, "uCamera");
        //��ȡ�������Ƿ������Ӱ��������
        muIsShadow=GLES30.glGetUniformLocation(mProgram, "isShadow"); 
    }
	public void drawSelf() {//���ƻ�����ķ���
		MatrixState.pushMatrix();//�����ֳ�
		MatrixState.translate(u, v, 0);//�ƶ����ӿ��е�u, vλ�ô�
		//ָ��ʹ��ĳ����ɫ������
		GLES30.glUseProgram(mProgram);
		//�����ձ任��������Ⱦ����
		GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0);
		
		//3D�����е���
		//���������Ӧ�ĵ�һ��ײ����ɫ������Ⱦ����
		GLES30.glUniform3fv(muColorHandle, 1, color3, 0);	
		// ��3D�����ж����λ�ô�����Ⱦ����
		GLES30.glUniform3fv(mu3DPosHandle, 1, vertexPos3D, 0);
		// ��3D�����ж���ķ�����������Ⱦ����
		GLES30.glUniform3fv(muNormalHandle, 1, normal3D, 0);
		// ��3D�����еƹ�λ�ô�����Ⱦ����
		GLES30.glUniform3fv(muLightLocationHandle, 1, lightPos3D, 0);
		// ��3D�������������λ�ô�����Ⱦ����
		GLES30.glUniform3fv(muCameraHandle, 1, cameraPos3D, 0);	 
        //���Ƿ������Ӱ���Դ�����Ⱦ����
        GLES30.glUniform1i(muIsShadow, isShadow);      
		
		// ������λ�����ݴ�����Ⱦ����
		GLES30.glVertexAttribPointer(maPositionHandle, 3, GLES30.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
		// ������λ����������
		GLES30.glEnableVertexAttribArray(maPositionHandle);
		//���ƾ���
		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
		
		MatrixState.popMatrix();//�ָ��ֳ�
	}

    public void setColor(float r,float g,float b){//������ɫ�ķ���
    	this.color3[0] = r;
    	this.color3[1] = g;
    	this.color3[2] = b;
    }
    public void setPos3D(float x,float y,float z){
    	this.vertexPos3D[0] = x;
    	this.vertexPos3D[1] = y;
    	this.vertexPos3D[2] = z;
    }
    public void setNormal3D(float x,float y,float z){
    	this.normal3D[0] = x;
    	this.normal3D[1] = y;
    	this.normal3D[2] = z;
    }
    public void setLightPos3D(float x,float y,float z){
    	this.lightPos3D[0] = x;
    	this.lightPos3D[1] = y;
    	this.lightPos3D[2] = z;
    }
    public void setCameraPos3D(float x,float y,float z){
    	this.cameraPos3D[0] = x;
    	this.cameraPos3D[1] = y;
    	this.cameraPos3D[2] = z;
    }
    public void setShadow(int isShadow){
    	this.isShadow = isShadow;
    }
    
    public void setPos(float u,float v){
    	this.u=u;
    	this.v=v;
    }
    public void setColRow(int col,int row){
    	//�����������������������Ļ�ϵ�λ��
    	float u=-W+W*(2*col/nCols);
        float v=-H+H*(2*row/nRows);
        this.setPos(u, v);//���û�����λ��
    }
}
