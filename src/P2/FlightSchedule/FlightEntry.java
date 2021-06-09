package P2.FlightSchedule;

import P1.Location;
import P1.PlanningEntry;
import P1.PlanningEntryCollection;
import P1.Time;
import P1.Timeslot;
import P3.Exception.MyException;


public class FlightEntry extends PlanningEntryCollection<Plane> {
	// Abstraction function:
	// 表示数个航班，包含飞机、起飞降落时间、起飞降落机场
	
	// Representation invariant:
	// 一个航班只有一架飞机，两个机场和一个时间段
	
	// Safety from rep exposure:
	// 所有域均为private final
	// 使用防御性拷贝
	
	//constructor
	private FlightEntry() {
		super();
		checkRep();
	}

	public static FlightEntry getFlightEntry() {
		return new FlightEntry();
	}
	
	//methods
	/**
	 * 增加一架飞机
	 * @param planename 飞机名字
	 * @param type 种类
	 * @param seats 座位数
	 * @param age 机龄
	 * @throws MyException 
	 */
	public void addPlane(String planename, String type, int seats, double age) throws MyException {
		if (this.getResource(planename) != null) {//飞机存在
			throw new MyException("飞机已经存在");
		}

		this.resources.add(Plane.getNewPlane(planename, type, seats, age));
		checkRep();
	}
	
	/**
	 * 添加一个计划
	 * @param planname 计划名称
	 * @param from 始发地点
	 * @param to 到达地点
	 * @param begintime 开始时间
	 * @param endtime 结束时间
	 * @throws MyException 
	 */
	public void addPlan(String planname, String from, String to, String begintime, String endtime) throws MyException {
		if (this.getLocation(from) == null || this.getLocation(to) == null) {//位置未加入
			throw new MyException("机场未被加入");
		}
		if (this.getPlan(planname) != null) {//航班已有
			throw new MyException("航班已有");
		}
		if (Time.isLegalTime(begintime) == false || Time.isLegalTime(endtime) == false) {//时间输入格式不正确
			throw new MyException("时间格式错误");
		}
		
		Timeslot timeslot = Timeslot.getNewTimeslot(begintime, endtime);
		PlanningEntry<Plane> line = PlanningEntry.getNewPlanningEntry(planname);
		
		line.addLocation(Location.getNewLocation(from));
		line.addLocation(Location.getNewLocation(to));
		line.addTimeslot(timeslot);
		this.plans.add(line);
		
		checkRep();
	}

	
	@Override
	public void showBoard(String time, String locationName) {
		if (Time.isLegalTime(time) == false) {
			return;
		}
		FlightBoard.Board(Time.getNewTime(time), this.getLocation(locationName), this.plans);
	}

}
