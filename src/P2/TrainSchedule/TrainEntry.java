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
	// ��ʾ���˻�·�ߣ��������ᡢվ�㡢ʱ���
	
	// Representation invariant:
	// һ��·�߿����ж�����ᡢ���վ�㣬��ʱ���������վ��������һ��
	
	// Safety from rep exposure:
	// �������Ϊprivate final
	// ʹ�÷����Կ���
	
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
	 * ����һ������
	 * @param name ������
	 * @param type ����
	 * @param seats ��λ��
	 * @param year �������
	 * @throws MyException 
	 */
	public void addTrain(String name, String type, int seats, int year) throws MyException {
		if (this.getResource(name) != null) {//�𳵴���
			throw new MyException("���Ѵ���");
		}
		
		this.resources.add(Train.getNewTrain(name, type, seats, year));
		checkRep();
	}
	
	/**
	 * ���һ���ƻ�
	 * @param planname �ƻ�����
	 * @param locations ����վ�����Ƶļ���
	 * @param times ����ʱ��ļ���
	 * @throws MyException 
	 */
	public void addPlan(String planname, List<String> locations, List<String> times) throws MyException {
		for(String l: locations) {//վ��δ����
			if (this.getLocation(l) == null) {
				throw new MyException("վ��δ������");
			}
		}
		if (this.getPlan(planname) != null) {//·������
			throw new MyException("·������");
		}
		for(String t: times) {//ʱ�������ʽ����ȷ
			if (Time.isLegalTime(t) == false) {
				throw new MyException("ʱ���ʽ����");
			}
		}
		if((locations.size()- 1) * 2  != times.size()) {//ʱ����վ����Ŀ��һ��
			throw new MyException("�������Ϣ��ʱ����վ����Ŀ��һ��");
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
