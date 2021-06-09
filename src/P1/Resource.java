package P1;

public class Resource {
	// Abstraction function:
	// ��ʾһ����Դ

	// Representation invariant:
	// name��¼��Դ������Ϊ��

	// Safety from rep exposure:
	// name Ϊ private final
	// ʹ�÷����Կ���
	private final String name;
	
	protected void checkRep() {
		assert this.name != null;
	}
	
	//constructor
	public Resource(String name) {
		this.name = name;
	}
	
	//observer 
	public String getName() {
		return new String(this.name);
	}

	@Override
	public String toString() {
		return "Resource [name=" + name + "]";
	}
}
