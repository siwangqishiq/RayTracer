#version 300 es
precision mediump float;//����Ĭ�ϵĸ��㾫��
uniform vec3 uColor;    //���������ɫ
uniform vec3 uPosition;//�������Ӧ�ĵ�һ��ײ��λ��
uniform vec3 uNormal;//�������Ӧ�ĵ�һ��ײ�㷨����
uniform vec3 uLightLocation;//��Դλ��
uniform vec3 uCamera;	//�����λ��
uniform int isShadow;			//��Ӱ���Ʊ�־
out vec4 fragColor;//�������ƬԪ��ɫ
//��λ����ռ���ķ���
void pointLight
(
  inout vec4 ambient,//���������
  inout vec4 diffuse,//ɢ������
  inout vec4 specular,//���淴������  
  in vec4 lightAmbient,//��Ļ��������
  in vec4 lightDiffuse,//���ɢ������
  in vec4 lightSpecular//��ľ��淴������
)
{
  ambient=lightAmbient;			//ֱ�ӵó������������ǿ��  
  vec3 newNormal=normalize(uNormal);
  //����ӱ���㵽�������ʸ��
  vec3 eye= normalize(uCamera-uPosition);  
  //����ӱ���㵽��Դλ�õ�ʸ��
  vec3 vp = normalize(uLightLocation-uPosition);
  vec3 halfVector=normalize(vp+eye);	//����������ߵİ�����    
  float shininess=50.0;				//�ֲڶȣ�ԽСԽ�⻬
  float nDotViewPosition=max(0.0,dot(newNormal,vp)); 	//��������vp�ĵ����0�����ֵ
  diffuse=lightDiffuse*nDotViewPosition;				//����ɢ��������ǿ��
  float nDotViewHalfVector=dot(newNormal,halfVector);	//������������ĵ�� 
  float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess)); 	//���淴���ǿ������
  specular=lightSpecular*powerFactor;    			//���㾵��������ǿ��
}

void main()
{
   //������ɫ
   vec4 baseColor = vec4(uColor,1.0);
   if(isShadow == 0){ //��������Ӱ������Ҫ�������
   		vec4 ambient, diffuse, specular;
   		pointLight(ambient,diffuse,specular,
   				vec4(0.15,0.15,0.15,1.0),vec4(0.9,0.9,0.9,1.0),vec4(0.7,0.7,0.7,1.0));
   		//�ۺ�3��ͨ���������ǿ�ȼ�ƬԪ����ɫ���������ƬԪ����ɫ�����ݸ�����
   		fragColor=baseColor*ambient + baseColor*diffuse + baseColor*specular;   		
   } else {//�������Ӱ��ֻ���㻷����
   		fragColor=baseColor*vec4(0.15,0.15,0.15,1.0);
   }
}