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
					throw new MyException("航班表头信息格式错误");
				}
				String line = Arrays.asList(l.split(":")).get(1);
				List<String> linetimeandname = Arrays.asList(line.split(","));
				
				if (ans.getPlan(line) != null) {
					throw new MyException("航班已经存在");
				}
				
				//始发机场
				l = information.get(i + 2);
				if (Pattern.matches("DepartureAirport:(.*?)", l) == false) {
					throw new MyException("航班始发机场表示错误");
				}
				String departure = Arrays.asList(l.split(":")).get(1);
				ans.addLocation(departure);
	
				//到达机场
				l = information.get(i + 3);
				if (Pattern.matches("ArrivalAirport:(.*?)", l) == false) {
					throw new MyException("航班到达机场表示错误");
				}
				String arrive = Arrays.asList(l.split(":")).get(1);
				ans.addLocation(arrive);
				
				//出发时间
				l = information.get(i + 4);
				if (Pattern.matches(
						"DepatureTime:((\\d{4})-(([0][1-9])|(1[012]))-((0[1-9])|([12]\\d)|30) (([01]\\d)|(2[0-4])):(([0-5]\\d)|60))",
						l) == false) {
					throw new MyException("航班始发时间格式不对");
				}
				String beginTime = Arrays.asList(l.split(":")).get(1) + ":" + Arrays.asList(l.split(":")).get(2);
	
				//到达时间
				l = information.get(i + 5);
				if (Pattern.matches(
						"ArrivalTime:((\\d{4})-(([0][1-9])|(1[012]))-((0[1-9])|([12]\\d)|30) (([01]\\d)|(2[0-4])):(([0-5]\\d)|60))",
						l) == false) {
					throw new MyException("航班到达时间格式不对");
				}
				String endTime = Arrays.asList(l.split(":")).get(1) + ":" + Arrays.asList(l.split(":")).get(2);
	
				//飞机信息
				l = information.get(i + 6);
				if (Pattern.matches("Plane:(B|N)(\\d{4})", l) == false) {
					throw new MyException("航班飞机编号格式不对");
				}
				String plane = Arrays.asList(l.split(":")).get(1);
				
				l = information.get(i + 8);
				if (Pattern.matches("Type:([a-zA-Z0-9]+)", l) == false) {
					throw new MyException("航班飞机种类格式不对");
				}
				String type = Arrays.asList(l.split(":")).get(1);
	
				l = information.get(i + 9);
				if (Pattern.matches("Seats:(([5-9][0-9])|([1-4]\\d{2})|500)", l) == false) {
					throw new MyException("航班飞机座位数格式不对");
				}
				String seats = Arrays.asList(l.split(":")).get(1);
				
				l = information.get(i + 10);
				if (Pattern.matches("Age:(30|30.0|([1-2]?[0-9]([.][0-9])?))", l) == false) {
					throw new MyException("航班飞机机龄格式不对");
				}
				String age = Arrays.asList(l.split(":")).get(1);
				
				ans.addPlane(plane, type, Integer.parseInt(seats), Double.parseDouble(age));
							
				
				if(linetimeandname.get(0).equals(Arrays.asList(beginTime.split(" ")).get(0)) == false) {
					throw new MyException("航班信息出发时间不匹配");
				}
				Time begin = Time.getNewTime(beginTime);
				Time end = Time.getNewTime(endTime);
				if(begin.compareto(end) > 0) {
					throw new MyException("航班时间出发晚于到达");
				}
				Time oneday = Time.getNewTime("0000-00-01 00:00");
				begin.addTime(oneday);
				if(begin.compareto(end) < 0) {
					throw new MyException("航班时间出发与到达相差一天以上");
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
							throw new MyException("飞机信息不一致");
						}
						if(p1.getType().equals(p2.getType()) == false) {
							throw new MyException("飞机信息不一致");
						}
						if(p1.getSeats() != p2.getSeats()) {
							throw new MyException("飞机信息不一致");
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
