package P1;

import java.util.ArrayList;
import java.util.List;



public class CommonPlanningEntry<R extends Resource> implements PlanningEntry<R>{
	//Abstraction function:
	//��ʾһ���ƻ��������ƻ����ơ���Դ��λ�á�ʱ���
	
	// Representation invariant:
	// name������¼�ƻ�������Ϊ��
	// state������¼�ƻ�״̬
	// timeslots��¼��Ŀ���е�ʱ���
	// locations��¼��Ŀ����λ��
	// resources��¼��Ŀ������Դ
	
	// Safety from rep exposure:
	// ����state��Ϊprivate final����
	// ʹ�÷����Կ���
	
	private final String name;
	private State state;
	private final List<Timeslot> timeslots;
	private final List<Location> locations;
	private final List<R> resources;
	
	protected void checkRep() {
		assert this.name != null;
	}
	
	//constructor
	private CommonPlanningEntry(String name) {
		this.name = name;
		this.state= State.getNewState("WAITING");
		this.timeslots = new ArrayList<>();
		this.resources = new ArrayList<>();
		this.locations = new ArrayList<>();
		
		checkRep();
	}
	
	public static <R extends Resource> CommonPlanningEntry<R> getNewCommonPlanningEntry(String name) {
		return new CommonPlanningEntry<R>(name);
	}
	
	//method
	@Override 
	public boolean start() {
		if(this.state.canchange("RUNNING")) {
			this.state = State.getNewState("RUNNING");
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean cancel() {
		if(this.state.canchange("CANCELLED")) {
			this.state = State.getNewState("CANCELLED");
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean block() {
		if(this.state.canchange("BLOCKED")) {
			this.state = State.getNewState("BLOCKED");
			return true;
		}else {
			return false;
		}
	}
	
	@Override 
	public boolean complete() {
		if(this.state.canchange("ENDED")) {
			this.state = State.getNewState("ENDED");
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public String getName() {
		return new String(this.name);
	}
	
	@Override
	public State getState() {
		return State.getNewState(this.state.getName());
	}
	
	@Override
	public boolean addResource(R resource) {
		if (this.getState().getName().equals("WAITING") || this.getState().getName().equals("ALLOCATED")) {
			this.state = State.getNewState("ALLOCATED"); 
			for(R i: resources) {
				if(i.getName().equals(resource.getName())) {
					return false;
				}
			}
			this.resources.add(resource);
			checkRep();
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean removeResource(String resourcename) {
		for(int i = 0; i < resources.size(); i++) {
			if(resources.get(i).getName().equals(resourcename)) {
				resources.remove(i);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<R> getResources() {
		return new ArrayList<>(this.resources);
	}
	
	@Override
	public boolean addLocation(Location location) {
		if(locations.contains(location)) {
			return false;
		}
		this.locations.add(location);
		checkRep();
		return true;
	}
	
	@Override
	public Location getLocation(String locationname) {
		for(Location i: locations) {
			if(i.getName().equals(locationname)) {
				return i;
			}
		}
		return null;
	}
	
	@Override
	public boolean removeLocation(String locationname) {
		for(int i = 0; i < locations.size(); i++) {
			if(locations.get(i).getName().equals(locationname)) {
				locations.remove(i);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Location> getLocations() {
		List<Location> ans = new ArrayList<>();
		for(int i = 0; i < locations.size(); i++) {
			ans.add(locations.get(i));
		}
		return ans;
	}
	
	@Override
	public boolean addTimeslot(Timeslot timeslot) {
		if(timeslots.contains(timeslot)) {
			return false;
		}
		this.timeslots.add(timeslot);
		checkRep();
		return true;
	}
	
	@Override
	public List<Timeslot> getTimeslots() {
		List<Timeslot> ans = new ArrayList<>();
		for(int i = 0; i < timeslots.size(); i++) {
			ans.add(timeslots.get(i));
		}
		return ans;
	}
	
	@Override
	public Time getBegintime() {
		return this.getTimeslots().get(0).getBegin();
	}
	
	@Override
	public Time getEndtime() {
		int index = this.getTimeslots().size() - 1;
		return this.getTimeslots().get(index).getEnd();
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
	public int compareTo(CommonPlanningEntry<Resource> o) {
		return this.getBegintime().compareto(o.getEndtime());
	}

}
