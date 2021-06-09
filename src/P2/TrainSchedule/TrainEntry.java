package P2.TrainSchedule;

import java.util.List;

import P1.Location;
import P1.PlanningEntry;
import P1.PlanningEntryCollection;
import P1.Time;
import P1.Timeslot;
import P3.Exception.MyException;

public class TrainEntry extends PlanningEntryCollection<Train> {
	// Abstraction function:
	// 表示数趟火车路线，包含车厢、站点、时间段
	
	// Representation invariant:
	// 一个路线可以有多个车厢、多个站点，但时间段数量比站点数量少一个
	
	// Safety from rep exposure:
	// 所有域均为private final
	// 使用防御性拷贝
	
	//constructor
	private TrainEntry() {
		super();
		checkRep();
	}

	public static TrainEntry getTrainEntry() {
		return new TrainEntry();
	}
	
	//methods
	/**
	 * 增加一辆货车
	 * @param name 火车名字
	 * @param type 种类
	 * @param seats 座位数
	 * @param year 生产年份
	 * @throws MyException 
	 */
	public void addTrain(String name, String type, int seats, int year) throws MyException {
		if (this.getResource(name) != null) {//火车存在
			throw new MyException("火车已存在");
		}
		
		this.resources.add(Train.getNewTrain(name, type, seats, year));
		checkRep();
	}
	
	/**
	 * 添加一个计划
	 * @param planname 计划名称
	 * @param locations 所有站点名称的集合
	 * @param times 所有时间的集合
	 * @throws MyException 
	 */
	public void addPlan(String planname, List<String> locations, List<String> times) throws MyException {
		for(String l: locations) {//站点未加入
			if (this.getLocation(l) == null) {
				throw new MyException("站点未被加入");
			}
		}
		if (this.getPlan(planname) != null) {//路线已有
			throw new MyException("路线已有");
		}
		for(String t: times) {//时间输入格式不正确
			if (Time.isLegalTime(t) == false) {
				throw new MyException("时间格式错误");
			}
		}
		if((locations.size()- 1) * 2  != times.size()) {//时间与站点数目不一致
			throw new MyException("输入的信息中时间与站点数目不一致");
		}
		
		PlanningEntry<Train> line = PlanningEntry.getNewPlanningEntry(planname);
		int max = locations.size();
		for(int i = 0; i < max; i++) {
			line.addLocation(Location.getNewLocation(locations.get(i)));
		}
		for(int i = 0; i < 2*(max-1); i += 2) {
			Timeslot timeslot = Timeslot.getNewTimeslot(times.get(i), times.get(i+1));
			line.addTimeslot(timeslot);
		}
		this.plans.add(line);
		checkRep();
	}
	
	@Override
	public void showBoard(String time, String locationName) {
		if (Time.isLegalTime(time) == false) {
			return;
		}
		TrainBoard.Board(Time.getNewTime(time), this.getLocation(locationName), this.plans);
	}
}
