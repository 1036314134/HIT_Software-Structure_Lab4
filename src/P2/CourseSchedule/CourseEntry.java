package P2.CourseSchedule;

import P1.Location;
import P1.PlanningEntry;
import P1.PlanningEntryCollection;
import P1.Time;
import P1.Timeslot;
import P3.Exception.MyException;

public class CourseEntry extends PlanningEntryCollection<Teacher>{
	// Abstraction function:
	// 表示数节课，包含老师、教室、上课时间
	
	// Representation invariant:
	// 一节课只有一个时间段和一个教室
	
	// Safety from rep exposure:
	// 所有域均为private final
	// 使用防御性拷贝
	
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
	 * 增加一个老师
	 * @param name 老师名字
	 * @param id 身份证号
	 * @param gender 性别
	 * @param work 职称
	 * @throws MyException 
	 */
	public void addTeacher(String name, String id, String gender, String work) throws MyException {
		if (this.getResource(name) != null) {
			throw new MyException("老师已加入");
		}
		this.resources.add(Teacher.getNewTeacher(name, id, gender, work));
		checkRep();
	}
	
	/**
	 * 增加一节课
	 * @param planname 课程名称
	 * @param locationname 教室名字
	 * @param begintime 开始时间
	 * @param endtime 结束时间
	 * @throws MyException 
	 */
	public void addPlan(String planname, String locationname, String begintime, String endtime) throws MyException {
		if (this.getLocation(locationname) == null) {//位置未加入
			throw new MyException("教室未被加入");
		}
		if(this.getPlan(planname) != null) {//课程已有
			throw new MyException("课程已有");
		}
		if (Time.isLegalTime(begintime) == false || Time.isLegalTime(endtime) == false) {//时间输入格式不正确
			throw new MyException("时间格式错误");
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
	 * 改变一节课的教室
	 * @param planname 课程名
	 * @param locationname 要更改为的教室名
	 * @throws MyException 
	 */
	public void changeclassroom(String planname, String locationname) throws MyException {
		if(this.getPlan(planname) == null || this.getLocation(locationname) == null) {
			throw new MyException("课程或教室不存在");
		}
		String oldname = this.getPlan(planname).getLocations().get(0).getName();
		PlanningEntry<Teacher> newcourse = this.getPlan(planname);
		PlanningEntry<Teacher> oldcourse = this.getPlan(planname);
		
		if(newcourse.getState().getName().equals("RUNNING")){
			throw new MyException("课程正在进行");
		}
		if(newcourse.getState().getName().equals("ENDED")) {
			throw new MyException("课程已结束");
		}
		if(newcourse.getState().getName().equals("CANCELLED")) {
			throw new MyException("课程已取消");
		}
		newcourse.removeLocation(oldname);
		newcourse.addLocation(Location.getNewLocation(locationname));
		this.removePlan(planname);
		this.plans.add(newcourse);
		if(this.checkLocationConflict(true)) {
			this.removePlan(planname);
			this.plans.add(oldcourse);
			throw new MyException("位置独占冲突");
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
