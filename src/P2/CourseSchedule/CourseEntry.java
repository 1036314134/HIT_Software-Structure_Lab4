package P2.CourseSchedule;

import P1.Location;
import P1.PlanningEntry;
import P1.PlanningEntryCollection;
import P1.Time;
import P1.Timeslot;
import P3.Exception.MyException;

public class CourseEntry extends PlanningEntryCollection<Teacher>{
	// Abstraction function:
	// ��ʾ���ڿΣ�������ʦ�����ҡ��Ͽ�ʱ��
	
	// Representation invariant:
	// һ�ڿ�ֻ��һ��ʱ��κ�һ������
	
	// Safety from rep exposure:
	// �������Ϊprivate final
	// ʹ�÷����Կ���
	
	//constructor
	private CourseEntry() {
		super();
		checkRep();
	}

	public static CourseEntry getCourseEntry() {
		CourseEntry entry = new CourseEntry();
		return entry;
	}
	
	//methods
	/**
	 * ����һ����ʦ
	 * @param name ��ʦ����
	 * @param id ���֤��
	 * @param gender �Ա�
	 * @param work ְ��
	 * @throws MyException 
	 */
	public void addTeacher(String name, String id, String gender, String work) throws MyException {
		if (this.getResource(name) != null) {
			throw new MyException("��ʦ�Ѽ���");
		}
		this.resources.add(Teacher.getNewTeacher(name, id, gender, work));
		checkRep();
	}
	
	/**
	 * ����һ�ڿ�
	 * @param planname �γ�����
	 * @param locationname ��������
	 * @param begintime ��ʼʱ��
	 * @param endtime ����ʱ��
	 * @throws MyException 
	 */
	public void addPlan(String planname, String locationname, String begintime, String endtime) throws MyException {
		if (this.getLocation(locationname) == null) {//λ��δ����
			throw new MyException("����δ������");
		}
		if(this.getPlan(planname) != null) {//�γ�����
			throw new MyException("�γ�����");
		}
		if (Time.isLegalTime(begintime) == false || Time.isLegalTime(endtime) == false) {//ʱ�������ʽ����ȷ
			throw new MyException("ʱ���ʽ����");
		}
		
		PlanningEntry<Teacher> plan = PlanningEntry.getNewPlanningEntry(planname);
		Timeslot timeslot = Timeslot.getNewTimeslot(begintime, endtime);
		Location location = Location.getNewLocation(locationname);
		plan.addLocation(location);
		plan.addTimeslot(timeslot);
		
		
		plans.add(plan);
		checkRep();
	}
	
	/**
	 * �ı�һ�ڿεĽ���
	 * @param planname �γ���
	 * @param locationname Ҫ����Ϊ�Ľ�����
	 * @throws MyException 
	 */
	public void changeclassroom(String planname, String locationname) throws MyException {
		if(this.getPlan(planname) == null || this.getLocation(locationname) == null) {
			throw new MyException("�γ̻���Ҳ�����");
		}
		String oldname = this.getPlan(planname).getLocations().get(0).getName();
		PlanningEntry<Teacher> newcourse = this.getPlan(planname);
		PlanningEntry<Teacher> oldcourse = this.getPlan(planname);
		
		if(newcourse.getState().getName().equals("RUNNING")){
			throw new MyException("�γ����ڽ���");
		}
		if(newcourse.getState().getName().equals("ENDED")) {
			throw new MyException("�γ��ѽ���");
		}
		if(newcourse.getState().getName().equals("CANCELLED")) {
			throw new MyException("�γ���ȡ��");
		}
		newcourse.removeLocation(oldname);
		newcourse.addLocation(Location.getNewLocation(locationname));
		this.removePlan(planname);
		this.plans.add(newcourse);
		if(this.checkLocationConflict(true)) {
			this.removePlan(planname);
			this.plans.add(oldcourse);
			throw new MyException("λ�ö�ռ��ͻ");
		}
	}
	
	@Override
	public void showBoard(String time, String roomName) {
		if (Time.isLegalTime(time) == false) {
			return;
		}
		CourseBoard.Board(Time.getNewTime(time), this.getLocation(roomName), this.plans);
	}
}
