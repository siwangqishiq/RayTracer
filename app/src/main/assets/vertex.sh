#version 300 es
uniform mat4 uMVPMatrix; //�ܱ任����
in vec3 aPosition;  //����λ��
void main()     
{
   gl_Position = uMVPMatrix * vec4(aPosition,1.0); //�����ܱ任�������˴λ��ƴ˶���λ��
}