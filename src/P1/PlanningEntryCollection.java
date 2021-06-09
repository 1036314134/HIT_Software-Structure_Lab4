package P1;

import java.util.ArrayList;
import java.util.List;

import P3.Exception.MyException;



public abstract class PlanningEntryCollection<R extends Resource> {

	// Abstraction function:
	// ��ʾһϵ�мƻ�����ɵ�����
	//
	// Representation invariant:
	// resources��¼������Դ
	// locations��¼����λ��
	// plans��¼���мƻ���
	//
	// Safety from rep exposure:
	// �������Ϊprotected final
	// ʹ�÷����Կ���
	
	protected final List<R> resources;
	protected final List<Location> locations;
	protected final List<PlanningEntry<R>> plans;
	
	protected void checkRep() {
		for (PlanningEntry<R> i : this.plans) {
			for (R j: i.getResources()) {
				assert resources.contains(j);
			}
		}
	}
	
	//constructor
	protected PlanningEntryCollection() {
		resources = new ArrayList<>();
		locations = new ArrayList<>();
		plans = new ArrayList<>();
		checkRep();
	}
	
	//methods
	/**
	 * ����ĳ���ƻ�
	 * @param planname �ƻ�����
	 * @throws MyException 
	 */
	public void start(String planname) throws MyException {
		if(this.getPlan(planname).start()){
			throw new MyException("�üƻ�Ŀǰ״̬��������");
		}
	}

	/**
	 * ȡ��ĳ���ƻ�
	 * @param planname �ƻ�����
	 * @throws MyException 
	 */
	public void cancel(String planname) throws MyException {
		if(this.getPlan(planname).cancel() == false) {
			throw new MyException("�üƻ�Ŀǰ״̬����ȡ��");
		}
	}
	
	/**
	 * ����ĳ���ƻ�
	 * @param planname
	 * @throws MyException 
	 */
	public void block(String planname) throws MyException {
		if(this.getPlan(planname).block() == false) {
			throw new MyException("�üƻ�Ŀǰ״̬��������");
		}
	}
	
	/**
	 * ���ĳ���ƻ�
	 * @param planname �ƻ�����
	 * @throws MyException 
	 */
	public void complete(String planname) throws MyException {
		if(this.getPlan(planname).complete() == false) {
			throw new MyException("�üƻ�Ŀǰ״̬��������");
		}
	}
	
	/**
	 * ��ȡĳ���ƻ���״̬
	 * @param planname �ƻ�����
	 * @return �ƻ�״̬
	 */
	public State getState(String planname) {
		return State.getNewState(this.getPlan(planname).getState().getName());
	}
	
	/**
	 * ��ȡĳ����Դ����Ϣ
	 * @param name ��Դ������
	 * @return ��Դ��Ϣ
	 */
	public R getResource(String name) {
		for (R i: this.resources) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * ɾ����Դ
	 * @param name ��Դ������
	 * @throws MyException 
	 */
	public void removeResource(String name) throws MyException {
		if (this.getResource(name) == null) {
			throw new MyException("��Դ������");
		}
		if(checkResourceUsing(name)) {
			throw new MyException("��Դ�ѱ�����");
		}
		this.resources.remove(this.getResource(name));
		checkRep();
	}
	
	/**
	 * ������Դ���ƻ���
	 * @param resourceName ��Դ����
	 * @param planName �ƻ�����
	 * @throws MyException 
	 */
	public void allocateResource(String resourcename, String planname) throws MyException {
		R resource = this.getResource(resourcename);
		PlanningEntry<R> plan = this.getPlan(planname);
		
		if (resource == null || plan == null) {
			throw new MyException("��Դ��ƻ�������");
		}
		this.getPlan(planname).addResource(resource);
		
		if(this.checkResourceConflict()) {
			this.getPlan(planname).removeResource(resourcename);
			throw new MyException("��Դ��ռ��ͻ");
		}
		
		checkRep();
	}
	
	/**
	 * ����һ����ַ
	 * @param name ��ַ����
	 * @throws MyException 
	 */
	public void addLocation(String name) throws MyException {
		if(name == null) {
			throw new MyException("λ��������Ϊ��");
		}
		if(this.getLocation(name) != null) {
			throw new MyException("λ���Ѿ�������");
		}
		this.locations.add(Location.getNewLocation(name));
		checkRep();
	}
	
	/**
	 * ��ȡ��ַ��Ϣ
	 * @param name ��ַ����
	 * @return ��ַ��Ϣ
	 */
	public Location getLocation(String name) {
		if (name == null) {
			return null;
		}
		for (Location i : this.locations) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * ɾ��һ����ַ
	 * @param name ��ַ����
	 * @throws MyException 
	 */
	public void removeLocation(String name) throws MyException {
		if (this.getLocation(name) == null) {
			throw new MyException("λ�ò�����");
		}
		if(checkLocationUsing(name)) {
			throw new MyException("λ���ѱ�����");
		}
		this.locations.remove(this.getLocation(name));
		checkRep();
	}
	
	/**
	 * ��ȡĳ���ƻ�
	 * @param planname �ƻ�����
	 * @return �ƻ���Ϣ
	 */
	public PlanningEntry<R> getPlan(String planname){
		for(PlanningEntry<R> i: this.plans) {
			if(i.getName().equals(planname)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * ɾ��ĳ���ƻ�
	 * @param planname �ƻ�����
	 * @return �ɹ�����true�����򷵻�false
	 */
	public boolean removePlan(String planname) {
		PlanningEntry<R> plan = this.getPlan(planname);
		if (plan == null) {
			return false;
		}
		if (plan.getState().getName().equals("WAITING") || this.getState(planname).getName().equals("ALLOCATED")) {
			this.plans.remove(plan);
			checkRep();
			return true;
		}
		return false;
	}
	
	/**
	 * ��ʾ��Ϣ��
	 * @param time ʱ��
	 * @param location �ص�
	 */
	abstract public void showBoard(String time, String location);
	
	/**
	 * ����ַ��ͻ
	 * @return ��ͻ����true, ���򷵻�false
	 */
	public boolean checkLocationConflict(boolean f) {
		return PlanningEntryAPIs.checkLocationConflict(plans, f);
	}
	
	/**
	 * �����Դ��ͻ
	 * @return ��ͻ����true, ���򷵻�false
	 */
	public boolean checkResourceConflict() {
		return PlanningEntryAPIs.checkResourceExclusiveConflict(plans);
	}


	/**
	 * �ҵ�ʹ��ĳ��Դ�����мƻ�
	 * @param resourceName ��Դ��
	 * @return ʹ�ø���Դ�����мƻ�
	 */
	public List<PlanningEntry<R>> getResourceEntries(String resourceName) {
		List<PlanningEntry<R>> ans = new ArrayList<>();

		if (resourceName == null || plans == null) {
			return ans;
		}

		for (PlanningEntry<R> e: plans) {
			for (R r: e.getResources()) {
				if (r.getName().equals(resourceName) && ans.contains(e) == false) {
					ans.add(e);
					break;
				}
			}
		}
		return ans;
	}
	
	/**
	 * ��ȡǰ��ƻ���
	 * @param resourceName ��Դ��
	 * @param entryName �ƻ���
	 * @return ǰ��ƻ���
	 */
	public PlanningEntry<R> getPreEntry(String resourceName, String entryName) {
		return PlanningEntryAPIs.findPreEntryPerResource(this.getResource(resourceName), this.getPlan(entryName), plans);
	}
	
	/**
	 * �����Դ�Ƿ�ָ��
	 * @param resourcename ��Դ����
	 * @return ��ָ�ɷ���true, ���򷵻�false
	 */
	public boolean checkResourceUsing(String resourcename) {
		for(PlanningEntry<R> e: this.plans) {
			for(R r: e.getResources()) {
				if(r.getName().equals(resourcename)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * ����ַ�Ƿ�ָ��
	 * @param locationname ��ַ����
	 * @return ��ָ�ɷ���true, ���򷵻�false
	 */
	public boolean checkLocationUsing(String locationname) {
		for(PlanningEntry<R> e: this.plans) {
			for(Location l: e.getLocations()) {
				if(l.getName().equals(locationname)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<PlanningEntry<R>> getallPlans(){
		return new ArrayList<>(this.plans);
	}
	
}
