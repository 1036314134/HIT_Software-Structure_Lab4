package P3.Exception;

public class MyException extends Exception{
	private static final long serialVersionUID = 1L;
	// �ṩ�޲����Ĺ��췽��
	public MyException() { 
	} 
	 
	// �ṩһ���в����Ĺ��췽�������Զ�����
	public MyException(String message) { 
		super(message);// �Ѳ������ݸ�Throwable�Ĵ�String�����Ĺ��췽�� 
	} 
}
