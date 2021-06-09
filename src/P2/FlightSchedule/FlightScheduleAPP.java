package P2.FlightSchedule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import P1.PlanningEntry;
import P3.Exception.MyException;

public class FlightScheduleAPP {
	public static void main(String args[]) throws IOException, MyException {
		Scanner in = new Scanner(System.in);
		System.out.println("航班管理APP");
		FlightScheduleAPP.print();
		in.close();
	}
	
	
	public static void print() throws IOException, MyException {
		FlightEntry lines = null;
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

		System.out.println("请给出你要读取的文件的名字");
		System.out.println("例如：FlightSchedule_1.txt、FlightSchedule_2.txt、FlightSchedule_3.txt、FlightSchedule_4.txt、FlightSchedule_5.txt");

		while (true) {
			try {
				lines = FlightReader.fileReader(in.next());
				mylogger.info("读取文件成功");
				System.out.println("读取文件成功");
			}catch(IOException e) {
				mylogger.info("没有该文件");
				System.out.println("没有该文件");
			}catch(MyException e) {
				mylogger.info(e.getMessage());
				System.out.println(e.getMessage());
			}
			if(lines != null) {
				break;
			}
			System.out.println("请重新输入文件");
		}

		System.out.println("请选择你要执行的操作");
		System.out.println("1.增加一架飞机");
		System.out.println("2.移除一架飞机");
		System.out.println("3.新增一个机场");
		System.out.println("4.移除一个机场");
		System.out.println("5.增加一次航程");
		System.out.println("6.取消某个航程");
		System.out.println("7.让某个飞机去飞某个航程");
		System.out.println("8.启动某个航程");
		System.out.println("9.结束某个航程");
		System.out.println("10.查看某个航程的状态");
		System.out.println("11.查看冲突");
		System.out.println("12.查看某个飞机的航程");
		System.out.println("13.显示计划版");
		System.out.println("14.离开");

		boolean flag = true;

		while (flag) {
			switch (in.nextInt()) {
				case 1:
					System.out.println("请给出你要添加的飞机的信息");
					System.out.println("飞机名称 飞机种类 飞机座位数 飞机机龄");
					try {
						String planename = in.next();
						lines.addPlane(planename, in.next(), in.nextInt(), in.nextDouble());
						mylogger.info("添加飞机" + planename + "成功");
						System.out.println("添加成功");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 2:
					System.out.println("请给出要删除的飞机的名字");
					String name = in.next();
					try {
						lines.removeResource(name);
						mylogger.info("删除飞机" + name + "成功");
						System.out.println("删除成功");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 3:
					System.out.println("请给出要增加的机场的名字");
					try {
						String locationname = in.next();
						lines.addLocation(locationname);
						mylogger.info("增加机场" + locationname + "成功");
						System.out.println("增加成功");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 4:
					System.out.println("请给出要移除的机场的名字");
					String locationname = in.next();
					try {
						lines.removeLocation(locationname);
						mylogger.info("删除机场" + locationname + "成功");
						System.out.println("删除成功");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 5:
					System.out.println("请给出要增加的航程的信息");
					System.out.println("(航程名称 始发机场 到达机场 出发时间 到达时间)");
					try {
						String planname = in.next();
						lines.addPlan(planname, in.next(), in.next(), in.next() + " " + in.next(),in.next() + " " + in.next());
						mylogger.info("增加航程" + planname + "成功");
						System.out.println("增加成功");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 6:
					System.out.println("请给出要取消的航程名称");
					try {
						String planname = in.next();
						lines.cancel(planname);
						mylogger.info("取消航程" + planname + "成功");
						System.out.println("取消成功");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 7:
					System.out.println("请给出飞机名称和航程名称");
					try {
						String planename = in.next();
						String planname = in.next();
						lines.allocateResource(planename, planname);
						mylogger.info("飞机" + planename + "分配给了航程" + planname);
						System.out.println("分配成功");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 8:
					System.out.println("请输入航班名");
					try {
						String planname = in.next();
						lines.start(planname);
						System.out.println("航班" + planname + "启动成功");
					}catch(MyException e){
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 9:
					System.out.println("请输入航班名");
					try {
						String planname = in.next();
						lines.complete(planname);
						System.out.println("航班" + planname + "结束成功");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 10:
					System.out.println("请给出要查询的航程名称");
					PlanningEntry<Plane> plan = lines.getPlan(in.next());
					if (plan == null) {
						System.out.println("未查询到该航程");
					}else {
						System.out.println("该航程状态为:" + plan.getState().getName());
					}
					break;
				case 11:
					boolean f = false;
					System.out.println("请选择检查飞机冲突的策略,1或者2");
					int k = in.nextInt();
					if(k == 1) {
						if(lines.checkLocationConflict(true)) {
							System.out.println("飞机有冲突");
							f = true;
						}
					}else if(k == 2) {
						if(lines.checkLocationConflict(false)) {
							System.out.println("飞机有冲突");
							f = true;
						}
					}else {
						System.out.println("输入错误");
						break;
					}
					if(lines.checkResourceConflict()) {
						System.out.println("机场有冲突");
						f = true;
					}
					if(!f) {
						System.out.println("没有冲突");
					}
					break;
				case 12:
					System.out.println("请输入飞机名字");
					String planename = in.next();
					System.out.println(lines.getResourceEntries(planename).toString());
					
					System.out.println("请选择其中一个航班");
					String linename2 = in.next();
					if(lines.getPreEntry(planename, linename2) == null) {
						System.out.println("没有前序航班");
					}else {
						System.out.println("前序航班为：" + lines.getPreEntry(planename, linename2).toString());
					}
					break;
				case 13:
					System.out.println("请给出当前时间和机场");
					lines.showBoard(in.next() + " " + in.next(), in.next());
					break;
				case 14:
					flag = false;
					break;
				default:
					System.out.println("输入错误，重新输入");
			}
		}

		in.close();
	}
}
