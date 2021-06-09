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
		System.out.println("�������APP");
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

		System.out.println("�������Ҫ��ȡ���ļ�������");
		System.out.println("���磺FlightSchedule_1.txt��FlightSchedule_2.txt��FlightSchedule_3.txt��FlightSchedule_4.txt��FlightSchedule_5.txt");

		while (true) {
			try {
				lines = FlightReader.fileReader(in.next());
				mylogger.info("��ȡ�ļ��ɹ�");
				System.out.println("��ȡ�ļ��ɹ�");
			}catch(IOException e) {
				mylogger.info("û�и��ļ�");
				System.out.println("û�и��ļ�");
			}catch(MyException e) {
				mylogger.info(e.getMessage());
				System.out.println(e.getMessage());
			}
			if(lines != null) {
				break;
			}
			System.out.println("�����������ļ�");
		}

		System.out.println("��ѡ����Ҫִ�еĲ���");
		System.out.println("1.����һ�ܷɻ�");
		System.out.println("2.�Ƴ�һ�ܷɻ�");
		System.out.println("3.����һ������");
		System.out.println("4.�Ƴ�һ������");
		System.out.println("5.����һ�κ���");
		System.out.println("6.ȡ��ĳ������");
		System.out.println("7.��ĳ���ɻ�ȥ��ĳ������");
		System.out.println("8.����ĳ������");
		System.out.println("9.����ĳ������");
		System.out.println("10.�鿴ĳ�����̵�״̬");
		System.out.println("11.�鿴��ͻ");
		System.out.println("12.�鿴ĳ���ɻ��ĺ���");
		System.out.println("13.��ʾ�ƻ���");
		System.out.println("14.�뿪");

		boolean flag = true;

		while (flag) {
			switch (in.nextInt()) {
				case 1:
					System.out.println("�������Ҫ��ӵķɻ�����Ϣ");
					System.out.println("�ɻ����� �ɻ����� �ɻ���λ�� �ɻ�����");
					try {
						String planename = in.next();
						lines.addPlane(planename, in.next(), in.nextInt(), in.nextDouble());
						mylogger.info("��ӷɻ�" + planename + "�ɹ�");
						System.out.println("��ӳɹ�");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 2:
					System.out.println("�����Ҫɾ���ķɻ�������");
					String name = in.next();
					try {
						lines.removeResource(name);
						mylogger.info("ɾ���ɻ�" + name + "�ɹ�");
						System.out.println("ɾ���ɹ�");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 3:
					System.out.println("�����Ҫ���ӵĻ���������");
					try {
						String locationname = in.next();
						lines.addLocation(locationname);
						mylogger.info("���ӻ���" + locationname + "�ɹ�");
						System.out.println("���ӳɹ�");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 4:
					System.out.println("�����Ҫ�Ƴ��Ļ���������");
					String locationname = in.next();
					try {
						lines.removeLocation(locationname);
						mylogger.info("ɾ������" + locationname + "�ɹ�");
						System.out.println("ɾ���ɹ�");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 5:
					System.out.println("�����Ҫ���ӵĺ��̵���Ϣ");
					System.out.println("(�������� ʼ������ ������� ����ʱ�� ����ʱ��)");
					try {
						String planname = in.next();
						lines.addPlan(planname, in.next(), in.next(), in.next() + " " + in.next(),in.next() + " " + in.next());
						mylogger.info("���Ӻ���" + planname + "�ɹ�");
						System.out.println("���ӳɹ�");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 6:
					System.out.println("�����Ҫȡ���ĺ�������");
					try {
						String planname = in.next();
						lines.cancel(planname);
						mylogger.info("ȡ������" + planname + "�ɹ�");
						System.out.println("ȡ���ɹ�");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 7:
					System.out.println("������ɻ����ƺͺ�������");
					try {
						String planename = in.next();
						String planname = in.next();
						lines.allocateResource(planename, planname);
						mylogger.info("�ɻ�" + planename + "������˺���" + planname);
						System.out.println("����ɹ�");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 8:
					System.out.println("�����뺽����");
					try {
						String planname = in.next();
						lines.start(planname);
						System.out.println("����" + planname + "�����ɹ�");
					}catch(MyException e){
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 9:
					System.out.println("�����뺽����");
					try {
						String planname = in.next();
						lines.complete(planname);
						System.out.println("����" + planname + "�����ɹ�");
					}catch(MyException e) {
						mylogger.info(e.getMessage());
						System.out.println(e.getMessage());
					}
					break;
				case 10:
					System.out.println("�����Ҫ��ѯ�ĺ�������");
					PlanningEntry<Plane> plan = lines.getPlan(in.next());
					if (plan == null) {
						System.out.println("δ��ѯ���ú���");
					}else {
						System.out.println("�ú���״̬Ϊ:" + plan.getState().getName());
					}
					break;
				case 11:
					boolean f = false;
					System.out.println("��ѡ����ɻ���ͻ�Ĳ���,1����2");
					int k = in.nextInt();
					if(k == 1) {
						if(lines.checkLocationConflict(true)) {
							System.out.println("�ɻ��г�ͻ");
							f = true;
						}
					}else if(k == 2) {
						if(lines.checkLocationConflict(false)) {
							System.out.println("�ɻ��г�ͻ");
							f = true;
						}
					}else {
						System.out.println("�������");
						break;
					}
					if(lines.checkResourceConflict()) {
						System.out.println("�����г�ͻ");
						f = true;
					}
					if(!f) {
						System.out.println("û�г�ͻ");
					}
					break;
				case 12:
					System.out.println("������ɻ�����");
					String planename = in.next();
					System.out.println(lines.getResourceEntries(planename).toString());
					
					System.out.println("��ѡ������һ������");
					String linename2 = in.next();
					if(lines.getPreEntry(planename, linename2) == null) {
						System.out.println("û��ǰ�򺽰�");
					}else {
						System.out.println("ǰ�򺽰�Ϊ��" + lines.getPreEntry(planename, linename2).toString());
					}
					break;
				case 13:
					System.out.println("�������ǰʱ��ͻ���");
					lines.showBoard(in.next() + " " + in.next(), in.next());
					break;
				case 14:
					flag = false;
					break;
				default:
					System.out.println("���������������");
			}
		}

		in.close();
	}
}
