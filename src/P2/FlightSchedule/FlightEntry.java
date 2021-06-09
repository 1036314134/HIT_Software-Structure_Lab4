package P2.FlightSchedule;

import P1.Location;
import P1.PlanningEntry;
import P1.PlanningEntryCollection;
import P1.Time;
import P1.Timeslot;
import P3.Exception.MyException;


public class FlightEntry extends PlanningEntryCollection<Plane> {
	// Abstraction function:
	// ��ʾ�������࣬�����ɻ�����ɽ���ʱ�䡢��ɽ������
	
	// Representation invariant:
	// һ������ֻ��һ�ܷɻ�������������һ��ʱ���
	
	// Safety from rep exposure:
	// �������Ϊprivate final
	// ʹ�÷����Կ���
	
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
	 * ����һ�ܷɻ�
	 * @param planename �ɻ�����
	 * @param type ����
	 * @param seats ��λ��
	 * @param age ����
	 * @throws MyException 
	 */
	public void addPlane(String planename, String type, int seats, double age) throws MyException {
		if (this.getResource(planename) != null) {//�ɻ�����
			throw new MyException("�ɻ��Ѿ�����");
		}

		this.resources.add(Plane.getNewPlane(planename, type, seats, age));
		checkRep();
	}
	
	/**
	 * ���һ���ƻ�
	 * @param planname �ƻ�����
	 * @param from ʼ���ص�
	 * @param to ����ص�
	 * @param begintime ��ʼʱ��
	 * @param endtime ����ʱ��
	 * @throws MyException 
	 */
	public void addPlan(String planname, String from, String to, String begintime, String endtime) throws MyException {
		if (this.getLocation(from) == null || this.getLocation(to) == null) {//λ��δ����
			throw new MyException("����δ������");
		}
		if (this.getPlan(planname) != null) {//��������
			throw new MyException("��������");
		}
		if (Time.isLegalTime(begintime) == false || Time.isLegalTime(endtime) == false) {//ʱ�������ʽ����ȷ
			throw new MyException("ʱ���ʽ����");
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
