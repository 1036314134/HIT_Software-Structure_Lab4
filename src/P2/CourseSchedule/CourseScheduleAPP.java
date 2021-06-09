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
		System.out.println("��ѧ�α����");
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

		System.out.println("��ѡ����Ҫִ�еĲ���");
		System.out.println("1.����һ����ʦ");
		System.out.println("2.�Ƴ�һ����ʦ");
		System.out.println("3.����һ�����");
		System.out.println("4.�Ƴ�һ�����");
		System.out.println("5.����һ�ڿ�");
		System.out.println("6.ȡ��һ�ڿ�");
		System.out.println("7.��ĳ����ʦ�ſ�");
		System.out.println("8.�ı�ĳ�ڿεĽ���");
		System.out.println("9.����ĳ�ڿ�");
		System.out.println("10.����ĳ�ڿ�");
		System.out.println("11.��ȡĳ�ڿε�״̬");
		System.out.println("12.�鿴��ͻ");
		System.out.println("13.�鿴ĳ����ʦ�ϵĿ�");
		System.out.println("14.չʾ�α�");
		System.out.println("15.����");

		boolean flag = true;

		while (flag) {
			switch (in.nextInt()) {
			case 1:
				System.out.println("��������ʦ������, ���֤��, �Ա�, ְλ��");
				try {
					String teachername = in.next();
					courses.addTeacher(teachername, in.next(), in.next(), in.next());
					mylogger.info("�����ʦ" + teachername + "�ɹ�");
					System.out.println("��ӳɹ�");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				System.out.println("������Ҫɾ������ʦ������");
				String name = in.next();
				try {
					courses.removeResource(name);
					mylogger.info("ɾ����ʦ" + name + "�ɹ�");
					System.out.println("ɾ���ɹ�");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("�����Ҫ���ӵĽ��ҵ�����");
				try {
					String locationname = in.next();
					courses.addLocation(locationname);
					mylogger.info("���ӽ���" + locationname + "�ɹ�");
					System.out.println("���ӳɹ�");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println("�����Ҫɾ���Ľ��ҵ�����");
				String locationname = in.next();
				try {
					courses.removeLocation(locationname);
					mylogger.info("ɾ������" + locationname + "�ɹ�");
					System.out.println("ɾ���ɹ�");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 5:
				System.out.println("�����Ҫ���ӵĿε���Ϣ");
				System.out.println("(�γ��� ������ ��ʼʱ�� �¿�ʱ��)");
				try {
					String coursename = in.next();
					courses.addPlan(coursename, in.next(), in.next() + " " + in.next(), in.next() + " " + in.next());
					mylogger.info("��ӿγ�" + coursename + "�ɹ�");
					System.out.println("��ӳɹ�");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 6:
				System.out.println("�����Ҫȡ���Ŀε���Ϣ");
				String coursename = in.next();
				try {
					courses.cancel(coursename);
					mylogger.info("ȡ���γ�" + coursename + "�ɹ�");
					System.out.println("ȡ���ɹ�");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 7:
				System.out.println("��������ʦ�� �γ���");
				try {
					courses.allocateResource(in.next(), in.next());
					mylogger.info("������ʦ�ɹ�");
					System.out.println("����ɹ�");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 8:
				System.out.println("������γ��� ������");
				try {
					courses.changeclassroom(in.next(), in.next());
					mylogger.info("�ı���ҳɹ�");
					System.out.println("�ı�ɹ�");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 9:
				System.out.println("������γ���");
				try {
					String coursename1 = in.next();
					courses.start(coursename1);
					mylogger.info("�����γ�" + coursename1 + "�ɹ�");
					System.out.println("�����ɹ�");
				}catch(MyException e){
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 10:
				System.out.println("������γ���");
				try {
					String coursename1 = in.next();
					courses.complete(coursename1);
					mylogger.info("�����γ�" + coursename1 + "�ɹ�");
					System.out.println("�����ɹ�");
				}catch(MyException e) {
					mylogger.info(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			case 11:
				System.out.println("������γ���");
				PlanningEntry<Teacher> course = courses.getPlan(in.next());
				if(course == null){
					System.out.println("δ��ѯ���ÿγ�");
				}else {
					System.out.println("�ÿγ�״̬Ϊ��" + course.getState().getName());
				}
				break;
			case 12:
				boolean f = false;
				System.out.println("��ѡ������ҳ�ͻ�Ĳ���,1����2");
				int k = in.nextInt();
				if(k == 1) {
					if(courses.checkLocationConflict(true)) {
						System.out.println("�����г�ͻ");
						f = true;
					}
				}else if(k == 2) {
					if(courses.checkLocationConflict(false)) {
						System.out.println("�����г�ͻ");
						f = true;
					}
				}else {
					System.out.println("�������");
					break;
				}
				if(courses.checkResourceConflict()) {
					System.out.println("��ʦ�г�ͻ");
					f = true;
				}
				if(!f) {
					System.out.println("û�г�ͻ");
				}
				break;
			case 13:
				System.out.println("��������ʦ����");
				String teachername = in.next();
				System.out.println(courses.getResourceEntries(teachername).toString());
				
				System.out.println("��ѡ������һ�ڿ�");
				String coursename2 = in.next();
				if(courses.getPreEntry(teachername, coursename2) == null) {
					System.out.println("û��ǰ��γ�");
				}else {
					System.out.println("ǰ��γ�Ϊ��" + courses.getPreEntry(teachername, coursename2).toString());
				}
				break;
			case 14:
				System.out.println("�������ǰʱ��ͽ���");
				courses.showBoard(in.next() + " " + in.next(), in.next());
				break;
			case 15:
				flag = false;
				break;
			default:
				System.out.println("�����������������");
			}
		}

		in.close();
	}

}
