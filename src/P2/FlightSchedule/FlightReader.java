package P2.FlightSchedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import P1.PlanningEntry;
import P1.Time;
import P3.Exception.MyException;

public class FlightReader {
	public static FlightEntry fileReader(String path) throws IOException, MyException{
		File file = new File("txt\\" + path);
		List<String> information = new ArrayList<>();
		FlightEntry ans = FlightEntry.getFlightEntry();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				information.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			for (int i = 0; i < information.size(); i += 13) {
				String l = information.get(i);
				if (Pattern.matches("^Flight:(.*?)", l) == false) {
					throw new MyException("�����ͷ��Ϣ��ʽ����");
				}
				String line = Arrays.asList(l.split(":")).get(1);
				List<String> linetimeandname = Arrays.asList(line.split(","));
				
				if (ans.getPlan(line) != null) {
					throw new MyException("�����Ѿ�����");
				}
				
				//ʼ������
				l = information.get(i + 2);
				if (Pattern.matches("DepartureAirport:(.*?)", l) == false) {
					throw new MyException("����ʼ��������ʾ����");
				}
				String departure = Arrays.asList(l.split(":")).get(1);
				ans.addLocation(departure);
	
				//�������
				l = information.get(i + 3);
				if (Pattern.matches("ArrivalAirport:(.*?)", l) == false) {
					throw new MyException("���ൽ�������ʾ����");
				}
				String arrive = Arrays.asList(l.split(":")).get(1);
				ans.addLocation(arrive);
				
				//����ʱ��
				l = information.get(i + 4);
				if (Pattern.matches(
						"DepatureTime:((\\d{4})-(([0][1-9])|(1[012]))-((0[1-9])|([12]\\d)|30) (([01]\\d)|(2[0-4])):(([0-5]\\d)|60))",
						l) == false) {
					throw new MyException("����ʼ��ʱ���ʽ����");
				}
				String beginTime = Arrays.asList(l.split(":")).get(1) + ":" + Arrays.asList(l.split(":")).get(2);
	
				//����ʱ��
				l = information.get(i + 5);
				if (Pattern.matches(
						"ArrivalTime:((\\d{4})-(([0][1-9])|(1[012]))-((0[1-9])|([12]\\d)|30) (([01]\\d)|(2[0-4])):(([0-5]\\d)|60))",
						l) == false) {
					throw new MyException("���ൽ��ʱ���ʽ����");
				}
				String endTime = Arrays.asList(l.split(":")).get(1) + ":" + Arrays.asList(l.split(":")).get(2);
	
				//�ɻ���Ϣ
				l = information.get(i + 6);
				if (Pattern.matches("Plane:(B|N)(\\d{4})", l) == false) {
					throw new MyException("����ɻ���Ÿ�ʽ����");
				}
				String plane = Arrays.asList(l.split(":")).get(1);
				
				l = information.get(i + 8);
				if (Pattern.matches("Type:([a-zA-Z0-9]+)", l) == false) {
					throw new MyException("����ɻ������ʽ����");
				}
				String type = Arrays.asList(l.split(":")).get(1);
	
				l = information.get(i + 9);
				if (Pattern.matches("Seats:(([5-9][0-9])|([1-4]\\d{2})|500)", l) == false) {
					throw new MyException("����ɻ���λ����ʽ����");
				}
				String seats = Arrays.asList(l.split(":")).get(1);
				
				l = information.get(i + 10);
				if (Pattern.matches("Age:(30|30.0|([1-2]?[0-9]([.][0-9])?))", l) == false) {
					throw new MyException("����ɻ������ʽ����");
				}
				String age = Arrays.asList(l.split(":")).get(1);
				
				ans.addPlane(plane, type, Integer.parseInt(seats), Double.parseDouble(age));
							
				
				if(linetimeandname.get(0).equals(Arrays.asList(beginTime.split(" ")).get(0)) == false) {
					throw new MyException("������Ϣ����ʱ�䲻ƥ��");
				}
				Time begin = Time.getNewTime(beginTime);
				Time end = Time.getNewTime(endTime);
				if(begin.compareto(end) > 0) {
					throw new MyException("����ʱ��������ڵ���");
				}
				Time oneday = Time.getNewTime("0000-00-01 00:00");
				begin.addTime(oneday);
				if(begin.compareto(end) < 0) {
					throw new MyException("����ʱ������뵽�����һ������");
				}
	
				ans.addPlan(line, departure, arrive, beginTime, endTime);
				ans.allocateResource(plane, line);
				
			}
			
			
			for(PlanningEntry<Plane> i: ans.getallPlans()) {
				for(PlanningEntry<Plane> j: ans.getallPlans()) {
					if(i.getName().equals(j.getName())) {
						continue;
					}
					Plane p1 = i.getResources().get(0);
					Plane p2 = j.getResources().get(0);
					if(p1.getName().equals(p2.getName())) {
						if(p1.getAge() != p2.getAge()) {
							throw new MyException("�ɻ���Ϣ��һ��");
						}
						if(p1.getType().equals(p2.getType()) == false) {
							throw new MyException("�ɻ���Ϣ��һ��");
						}
						if(p1.getSeats() != p2.getSeats()) {
							throw new MyException("�ɻ���Ϣ��һ��");
						}
					}
				}
			}
			
		}catch(MyException e) {
			throw e;
		}
		
		return ans;
	}
}
