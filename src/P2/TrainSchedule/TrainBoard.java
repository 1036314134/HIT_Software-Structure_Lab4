package P2.TrainSchedule;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import P1.Location;
import P1.PlanningEntry;
import P1.Resource;
import P1.Time;

public class TrainBoard {
	private static JFrame container;
	private static JTable jTable;


	public static <R extends Resource> void Board(Time time, Location location, List<PlanningEntry<R>> plans) {
		List<PlanningEntry<R>> list = new ArrayList<>();
		Time earlytime = Time.getNewTime(time.toString());
		Time latetime = Time.getNewTime(time.toString());
		Time time1 = Time.getNewTime("0000-00-00 -1:00");
		Time time2 = Time.getNewTime("0000-00-00 1:00");
		earlytime.addTime(time1);
		latetime.addTime(time2);
		//System.out.println(earlytime.toString()+ " "+ latetime.toString());
		
		for (PlanningEntry<R> line: plans) {//�ִ���
			int max = line.getLocations().size();
			int index = -1;
			for(int i = 1; i < max; i++) {
				if(line.getLocations().get(i).getName().equals(location.getName())) {
					index = i;
					break;
				}
			}
			if(index == -1) {//���ִ�
				continue;
			}
			if(line.getTimeslots().get(index-1).getEnd().compareto(earlytime) < 0 
					|| line.getTimeslots().get(index-1).getEnd().compareto(latetime) > 0) {//ʱ���ڷ�Χ��
				continue;
			}
			if(line.getResources().isEmpty()) {//·��δ�����г�
				continue;
			}
			list.add(line);
		}

		CreateJTable(time, location, list, true);
		
		list.clear();

		for (PlanningEntry<R> line: plans) {//��������
			if(line.getLocations().get(0).equals(location) == false) {
				continue;
			}
			if(line.getTimeslots().get(0).getBegin().compareto(earlytime) < 0 
					|| line.getTimeslots().get(0).getBegin().compareto(latetime) > 0) {
				continue;
			}
			if(line.getResources().isEmpty()) {//·��δ�����г�
				continue;
			}
			list.add(line);
		}

		CreateJTable(time, location, list, false);
	}

	

	private static <R extends Resource> void CreateJTable(Time time, Location location, List<PlanningEntry<R>> lines, boolean arrived) {
		String[] columnName = { "ʱ��", "����", "·��", "״̬" };

		Object[][] data = new Object[lines.size()][4];
		for (int i = 0; i < lines.size(); i++) {
			int index = -1;
			for(int j = 0; j < lines.get(i).getLocations().size(); j++) {
				if(lines.get(i).getLocations().get(j).getName().equals(location.getName())) {
					index = j;
					break;
				}
			}
			if(arrived) {
				data[i][0] = String.format("%02d", lines.get(i).getTimeslots().get(index-1).getBegin().getHour()) + ":"
						+ String.format("%02d", lines.get(i).getTimeslots().get(index-1).getBegin().getMinute());
			}else {
				data[i][0] = String.format("%02d", lines.get(i).getTimeslots().get(0).getEnd().getHour()) + ":"
					+ String.format("%02d", lines.get(i).getTimeslots().get(0).getEnd().getMinute());
			}
			
			
			data[i][1] = "";
			for(int j = 0; j < lines.get(i).getResources().size(); j++) {
				data[i][1] = data[i][1] + " " + lines.get(i).getResources().get(j).getName();
			}
			
			String linename = lines.get(i).getLocations().get(0).getName() + "-";
			int length = lines.get(i).getLocations().size();
			linename = linename + lines.get(i).getLocations().get(length-1).getName();
			data[i][2] = linename;
			
			
			if(lines.get(i).getState().getName().equals("CANCELLED")) {
				data[i][3] = "��ȡ��";
				continue;
			}
			
			if(time.compareto(lines.get(i).getTimeslots().get(0).getBegin()) > 0) {
				lines.get(i).start();
			}
			
			if(index != 0) {
				if(time.compareto(lines.get(i).getTimeslots().get(index-1).getEnd()) > 0) {
					lines.get(i).complete();
				}
				for(int j = 0; j < lines.get(i).getTimeslots().size()-1; j++) {
					if(time.compareto(lines.get(i).getTimeslots().get(j).getEnd()) > 0
							&& time.compareto(lines.get(i).getTimeslots().get(j+1).getBegin()) < 0){
						lines.get(i).block();
					}
				}
			}
			
			if(lines.get(i).getState().getName().equals("ALLOCATED")) {
				data[i][3] = "��������";
				continue;
			}
			
			if(lines.get(i).getState().getName().equals("RUNNING")) {
				data[i][3] = "�ѳ���";
				continue;
			}
			if(lines.get(i).getState().getName().equals("BLOCKED")) {
				data[i][3] = "��ͣĳ�м�վ";
				continue;
			}
			if(lines.get(i).getState().getName().equals("ENDED")) {
				data[i][3] = "�ѵ���";
				continue;
			}
			
		}

		jTable = new JTable(data, columnName);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(jTable);

		if (arrived) {
			container = new JFrame(time.toString() + " " + location.getName() + " �ִ���");
		}else {
			container = new JFrame(time.toString() + " " + location.getName() + " �������");
		}
		
		container.setSize(800, 600);
		container.setLayout(new BorderLayout());
		container.add(jScrollPane, BorderLayout.CENTER);
		container.setVisible(true);
	}
}
