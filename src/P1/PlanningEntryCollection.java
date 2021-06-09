package P1;

import java.util.ArrayList;
import java.util.List;

import P3.Exception.MyException;



public abstract class PlanningEntryCollection<R extends Resource> {

	// Abstraction function:
	// 表示一系列计划项组成的整体
	//
	// Representation invariant:
	// resources记录所有资源
	// locations记录所有位置
	// plans记录所有计划项
	//
	// Safety from rep exposure:
	// 所有域均为protected final
	// 使用防御性拷贝
	
	protected final List<R> resources;
	protected final List<Location> locations;
	protected final List<PlanningEntry<R>> plans;
	
	protected void checkRep() {
		for (PlanningEntry<R> i : this.plans) {
			for (R j: i.getResources()) {
				assert resources.contains(j);
			}
		}
	}
	
	//constructor
	protected PlanningEntryCollection() {
		resources = new ArrayList<>();
		locations = new ArrayList<>();
		plans = new ArrayList<>();
		checkRep();
	}
	
	//methods
	/**
	 * 启动某个计划
	 * @param planname 计划名字
	 * @throws MyException 
	 */
	public void start(String planname) throws MyException {
		if(this.getPlan(planname).start()){
			throw new MyException("该计划目前状态不能启动");
		}
	}

	/**
	 * 取消某个计划
	 * @param planname 计划名字
	 * @throws MyException 
	 */
	public void cancel(String planname) throws MyException {
		if(this.getPlan(planname).cancel() == false) {
			throw new MyException("该计划目前状态不能取消");
		}
	}
	
	/**
	 * 阻塞某个计划
	 * @param planname
	 * @throws MyException 
	 */
	public void block(String planname) throws MyException {
		if(this.getPlan(planname).block() == false) {
			throw new MyException("该计划目前状态不能阻塞");
		}
	}
	
	/**
	 * 完成某个计划
	 * @param planname 计划名字
	 * @throws MyException 
	 */
	public void complete(String planname) throws MyException {
		if(this.getPlan(planname).complete() == false) {
			throw new MyException("该计划目前状态不能阻塞");
		}
	}
	
	/**
	 * 获取某个计划的状态
	 * @param planname 计划名字
	 * @return 计划状态
	 */
	public State getState(String planname) {
		return State.getNewState(this.getPlan(planname).getState().getName());
	}
	
	/**
	 * 获取某个资源的信息
	 * @param name 资源的名字
	 * @return 资源信息
	 */
	public R getResource(String name) {
		for (R i: this.resources) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * 删除资源
	 * @param name 资源的名字
	 * @throws MyException 
	 */
	public void removeResource(String name) throws MyException {
		if (this.getResource(name) == null) {
			throw new MyException("资源不存在");
		}
		if(checkResourceUsing(name)) {
			throw new MyException("资源已被分配");
		}
		this.resources.remove(this.getResource(name));
		checkRep();
	}
	
	/**
	 * 分配资源给计划项
	 * @param resourceName 资源名字
	 * @param planName 计划名字
	 * @throws MyException 
	 */
	public void allocateResource(String resourcename, String planname) throws MyException {
		R resource = this.getResource(resourcename);
		PlanningEntry<R> plan = this.getPlan(planname);
		
		if (resource == null || plan == null) {
			throw new MyException("资源或计划不存在");
		}
		this.getPlan(planname).addResource(resource);
		
		if(this.checkResourceConflict()) {
			this.getPlan(planname).removeResource(resourcename);
			throw new MyException("资源独占冲突");
		}
		
		checkRep();
	}
	
	/**
	 * 增加一个地址
	 * @param name 地址名字
	 * @throws MyException 
	 */
	public void addLocation(String name) throws MyException {
		if(name == null) {
			throw new MyException("位置名不能为空");
		}
		if(this.getLocation(name) != null) {
			throw new MyException("位置已经被加入");
		}
		this.locations.add(Location.getNewLocation(name));
		checkRep();
	}
	
	/**
	 * 获取地址信息
	 * @param name 地址名字
	 * @return 地址信息
	 */
	public Location getLocation(String name) {
		if (name == null) {
			return null;
		}
		for (Location i : this.locations) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * 删除一个地址
	 * @param name 地址名字
	 * @throws MyException 
	 */
	public void removeLocation(String name) throws MyException {
		if (this.getLocation(name) == null) {
			throw new MyException("位置不存在");
		}
		if(checkLocationUsing(name)) {
			throw new MyException("位置已被分配");
		}
		this.locations.remove(this.getLocation(name));
		checkRep();
	}
	
	/**
	 * 获取某个计划
	 * @param planname 计划名字
	 * @return 计划信息
	 */
	public PlanningEntry<R> getPlan(String planname){
		for(PlanningEntry<R> i: this.plans) {
			if(i.getName().equals(planname)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * 删除某个计划
	 * @param planname 计划名字
	 * @return 成功返回true，否则返回false
	 */
	public boolean removePlan(String planname) {
		PlanningEntry<R> plan = this.getPlan(planname);
		if (plan == null) {
			return false;
		}
		if (plan.getState().getName().equals("WAITING") || this.getState(planname).getName().equals("ALLOCATED")) {
			this.plans.remove(plan);
			checkRep();
			return true;
		}
		return false;
	}
	
	/**
	 * 显示信息板
	 * @param time 时间
	 * @param location 地点
	 */
	abstract public void showBoard(String time, String location);
	
	/**
	 * 检查地址冲突
	 * @return 冲突返回true, 否则返回false
	 */
	public boolean checkLocationConflict(boolean f) {
		return PlanningEntryAPIs.checkLocationConflict(plans, f);
	}
	
	/**
	 * 检查资源冲突
	 * @return 冲突返回true, 否则返回false
	 */
	public boolean checkResourceConflict() {
		return PlanningEntryAPIs.checkResourceExclusiveConflict(plans);
	}


	/**
	 * 找到使用某资源的所有计划
	 * @param resourceName 资源名
	 * @return 使用该资源的所有计划
	 */
	public List<PlanningEntry<R>> getResourceEntries(String resourceName) {
		List<PlanningEntry<R>> ans = new ArrayList<>();

		if (resourceName == null || plans == null) {
			return ans;
		}

		for (PlanningEntry<R> e: plans) {
			for (R r: e.getResources()) {
				if (r.getName().equals(resourceName) && ans.contains(e) == false) {
					ans.add(e);
					break;
				}
			}
		}
		return ans;
	}
	
	/**
	 * 获取前序计划项
	 * @param resourceName 资源名
	 * @param entryName 计划名
	 * @return 前序计划项
	 */
	public PlanningEntry<R> getPreEntry(String resourceName, String entryName) {
		return PlanningEntryAPIs.findPreEntryPerResource(this.getResource(resourceName), this.getPlan(entryName), plans);
	}
	
	/**
	 * 检查资源是否被指派
	 * @param resourcename 资源名字
	 * @return 被指派返回true, 否则返回false
	 */
	public boolean checkResourceUsing(String resourcename) {
		for(PlanningEntry<R> e: this.plans) {
			for(R r: e.getResources()) {
				if(r.getName().equals(resourcename)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 检查地址是否被指派
	 * @param locationname 地址名字
	 * @return 被指派返回true, 否则返回false
	 */
	public boolean checkLocationUsing(String locationname) {
		for(PlanningEntry<R> e: this.plans) {
			for(Location l: e.getLocations()) {
				if(l.getName().equals(locationname)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<PlanningEntry<R>> getallPlans(){
		return new ArrayList<>(this.plans);
	}
	
}
