package P1;

public class Location {

	// Abstraction function:
	// ��ʾ��Ŀ�г��ֵ�λ��
	//
	// Representation invariant:
	// name��¼λ��������Ϊ��
	//
	// Safety from rep exposure:
	// �������Ϊprivate,final
	// ʹ�÷����Կ���
	
	private final String name;
	
	protected void checkRep() {
		assert this.name != null;
	}
	
	//constructor
	private Location(String name) {
		this.name = name;
		checkRep();
	}
	
	public static Location getNewLocation(String name) {
		return new Location(name);
	}
	
	//method
	public String getName() {
		return new String(this.name);
	}
}
