package P1;

import java.util.List;

public interface PlanningEntry<R> {
	
	//constructor
	public static <R extends Resource> PlanningEntry<R> getNewPlanningEntry(String name) {
		return CommonPlanningEntry.getNewCommonPlanningEntry(name);
	}
	
	/**
	 * ��ȡ�ƻ�������
	 * @return �ƻ�����
	 */
	public String getName();
	
	
	 /**
     * �����ƻ�
     * @return �ƻ��ɹ���������true
     * 			���򷵻�false
     */
	public boolean start();
	
	
	/**
     * ȡ���ƻ�
     * @return �ƻ��ɹ�ȡ������true
     * 			���򷵻�false
     */
	public boolean cancel();
	
	
	/**
	 * �����ƻ�
	 * @return �ƻ��ɹ���������true
     * 			���򷵻�false
	 */
	public boolean block();
	
	
	/**
     * ��ɼƻ�
     * @return �ƻ��ɹ���ɷ���true
     * 			���򷵻�false
     */
	public boolean complete();


	/**
	 * ��ȡ�ƻ���״̬
	 * @return �ƻ���״̬
	 */
	public State getState();

	
	/**
	 * ������Դ
	 * @param resource Ҫ�������Դ
	 * @return �ɹ����ӷ���true�����򷵻�false
	 */
	public boolean addResource(R resource);
	
	
	/**
	 * ɾ����Դ
	 * @param resourcename ��Դ��
	 * @return �ɹ����ӷ���true�����򷵻�false
	 */
	public boolean removeResource(String resourcename);
	
	
	/**
	 * ��ȡ������Դ
	 * @return ������Դ�ļ���
	 */
	public List<R> getResources();
	
	
	/**
	 * ��ӵ�ַ
	 * @param location ��ַ
	 * @return �ɹ���ӷ���true������Ϊfalse
	 */
	public boolean addLocation(Location location);
	
	
	/**
	 * ��ȡĳ����ַ
	 * 
	 * @param locationname ��ַ����
	 * @return ��ַ
	 */
	public Location getLocation(String locationname);
	
	/**
	 * ɾ����ַ
	 * @param locationname ��ַ����
	 * @return �ɹ�����true, ���򷵻�false
	 */
	public boolean removeLocation(String locationname);
	
	/**
	 * ��ȡ���е�ַ
	 * @return ���е�ַ�ļ���
	 */
	public List<Location> getLocations();
	
	/**
	 * ���ʱ���
	 * @param timeslot ʱ���
	 * @return �ɹ���ӷ���true������Ϊfalse
	 */
	public boolean addTimeslot(Timeslot timeslot);
	
	/**
	 * ��ȡʱ���
	 * @return ʱ��εļ���
	 */
	public List<Timeslot> getTimeslots();
	
	/**
	 * ��ȡ�ƻ���ʼʱ��
	 * @return ��ʼʱ��
	 */
	public Time getBegintime();
	
	/**
	 * ��ȡ�ƻ�����ʱ��
	 * @return ����ʱ��
	 */ 
	public Time getEndtime();
	
	/**
	 * �Ƚ������ƻ�������
	 * @param o ��һ���ƻ�
	 * @return �緵��-1, ����1, ��ͬ����0
	 */
	public int compareTo(CommonPlanningEntry<Resource> o);
}
