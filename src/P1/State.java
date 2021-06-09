package P1;

public class State {
	// Abstraction function:
	// ��ʾ�ƻ���״̬
	//
	// Representation invariant:
	// name��¼״̬������Ϊ��
	//
	// Safety from rep exposure:
	// name��private��
	//ʹ�÷����Կ���
	
	private String name;
	
	protected void checkRep() {
		assert this.name != null;
	}
	
	//constructor
	private State(String name) {
		this.name = name;
		checkRep();
	}
	
	public static State getNewState(String name) {
		return new State(name);
	}
	
	//methods
	public String getName() {
		return new String(this.name);
	}
	
	/**
	 * �ж�Ŀǰ��״̬�ܷ�ת����ָ��״̬
	 * @param name ָ��״̬��
	 * @return ���Է���true�� ���򷵻�false
	 */
	public boolean canchange(String name) {
		switch(name) {
		case "WAITING":
			return false;
		case "ALLOCATED":
			if(this.getName().equals("WAITING")) {
				return true;
			}else {
				return false;
			}
		case "RUNNING":
			if(this.getName().equals("ALLOCATED") || this.getName().equals("BLOCKED")) {
				return true;
			}else {
				return false;
			}
		case "BLOCKED":
			if(this.getName().equals("RUNNING")) {
				return true;
			}else {
				return false;
			}
		case "ENDED":
			if(this.getName().equals("RUNNING")) {
				return true;
			}else {
				return false;
			}
		case "CANCELLED":	
			if(this.getName().equals("WAITING") || this.getName().equals("ALLOCATED") || this.getName().equals("BLOCKED")) {
				return true;
			}else {
				return false;
			}
		default:
			return false;
		}
	}
}
