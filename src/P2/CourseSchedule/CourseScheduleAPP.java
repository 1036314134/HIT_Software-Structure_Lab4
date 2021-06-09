package P2.CourseSchedule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import P1.PlanningEntry;
import P2.FlightSchedule.FlightScheduleAPP;
import P3.Exception.MyException;


public class CourseScheduleAPP {
	public static void main(String args[]) throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.println("大学课表管理");
		CourseScheduleAPP.print();
		in.close();
	}
	
	public static void print() {
		CourseEntry courses = CourseEntry.getCourseEntry();
		Scanner in = new Scanner(System.in);
		
		 Logger mylogger=Logger.getLogger(FlightScheduleAPP.class.getName());
	     LogManager logManager = LogManager.getLogManager();
		 InputStream inputStream = null;
		 inputStream = FlightScheduleAPP.class.getClassLoader().getResourceAsStream("P3//Exception//log.properties");
		 try { 
	 		logManager.readConfiguration(inputStream);
	 	} catch (SecurityException | IOException e1) {
	 		e1.printStackTrace();
	 	}  
	 	logManager.addLogger(mylogger);

		System.out.println("请选择你要执行的操作");
		System.out.println("1.增加一个老师");
		System.out.println("2.移除一个老师");
		System.out.println("3.增加一间教室");
		System.out.println("4.移除一间教室");
		System.out.println("5.增加一节课");
		System.out.println("6.取消一节课");
		System.out.println("7.给某个老师排课");
		System.out.println("8.改变某节课的教室");
		System.out.println("9.启动某节课");
		System.out.println("10.结束某节课");
		System.out.println("11.获取某节课的状态");
		System.out.println("12.查看冲突");
		System.out.println("13.查看某个老师上的课");
		System.out.println("14.展示课表");
		System.out.println("15.结束");

		boolean flag = true;

		while (flag) {
			switch (in.nextInt()) {
			case 1:
				System.out.println("请输入老师的名字, 身份证号, 性别, 职位：");
				try {
					String teachername = in.next();
					courses.addTeacher(teachername, in.next(), in.next(), in.next());
					mylogger.info("添加老师" + teachername + "成功");
					System.out.println("添加成功");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				System.out.println("请输入要删除的老师的名字");
				String name = in.next();
				try {
					courses.removeResource(name);
					mylogger.info("删除老师" + name + "成功");
					System.out.println("删除成功");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("请给出要增加的教室的名字");
				try {
					String locationname = in.next();
					courses.addLocation(locationname);
					mylogger.info("增加教室" + locationname + "成功");
					System.out.println("增加成功");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println("请给出要删除的教室的名字");
				String locationname = in.next();
				try {
					courses.removeLocation(locationname);
					mylogger.info("删除教室" + locationname + "成功");
					System.out.println("删除成功");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 5:
				System.out.println("请给出要增加的课的信息");
				System.out.println("(课程名 教室名 开始时间 下课时间)");
				try {
					String coursename = in.next();
					courses.addPlan(coursename, in.next(), in.next() + " " + in.next(), in.next() + " " + in.next());
					mylogger.info("添加课程" + coursename + "成功");
					System.out.println("添加成功");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 6:
				System.out.println("请给出要取消的课的信息");
				String coursename = in.next();
				try {
					courses.cancel(coursename);
					mylogger.info("取消课程" + coursename + "成功");
					System.out.println("取消成功");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 7:
				System.out.println("请输入老师名 课程名");
				try {
					courses.allocateResource(in.next(), in.next());
					mylogger.info("分配老师成功");
					System.out.println("分配成功");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 8:
				System.out.println("请输入课程名 教室名");
				try {
					courses.changeclassroom(in.next(), in.next());
					mylogger.info("改变教室成功");
					System.out.println("改变成功");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 9:
				System.out.println("请输入课程名");
				try {
					String coursename1 = in.next();
					courses.start(coursename1);
					mylogger.info("启动课程" + coursename1 + "成功");
					System.out.println("启动成功");
				}catch(MyException e){
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 10:
				System.out.println("请输入课程名");
				try {
					String coursename1 = in.next();
					courses.complete(coursename1);
					mylogger.info("结束课程" + coursename1 + "成功");
					System.out.println("结束成功");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 11:
				System.out.println("请输入课程名");
				PlanningEntry<Teacher> course = courses.getPlan(in.next());
				if(course == null){
					System.out.println("未查询到该课程");
				}else {
					System.out.println("该课程状态为：" + course.getState().getName());
				}
				break;
			case 12:
				boolean f = false;
				System.out.println("请选择检查教室冲突的策略,1或者2");
				int k = in.nextInt();
				if(k == 1) {
					if(courses.checkLocationConflict(true)) {
						System.out.println("教室有冲突");
						f = true;
					}
				}else if(k == 2) {
					if(courses.checkLocationConflict(false)) {
						System.out.println("教室有冲突");
						f = true;
					}
				}else {
					System.out.println("输入错误");
					break;
				}
				if(courses.checkResourceConflict()) {
					System.out.println("老师有冲突");
					f = true;
				}
				if(!f) {
					System.out.println("没有冲突");
				}
				break;
			case 13:
				System.out.println("请输入老师名字");
				String teachername = in.next();
				System.out.println(courses.getResourceEntries(teachername).toString());
				
				System.out.println("请选择其中一节课");
				String coursename2 = in.next();
				if(courses.getPreEntry(teachername, coursename2) == null) {
					System.out.println("没有前序课程");
				}else {
					System.out.println("前序课程为：" + courses.getPreEntry(teachername, coursename2).toString());
				}
				break;
			case 14:
				System.out.println("请给出当前时间和教室");
				courses.showBoard(in.next() + " " + in.next(), in.next());
				break;
			case 15:
				flag = false;
				break;
			default:
				System.out.println("输入错误，请重新输入");
			}
		}

		in.close();
	}

}
