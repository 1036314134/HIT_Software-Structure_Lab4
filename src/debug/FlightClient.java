package debug;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;



/**
 * Note that this class may use the other two class: Flight and Plane.
 * 
 * Debug and fix errors. DON'T change the initial logic of the code.
 *
 */
public class FlightClient {
	
	/**
	 * Given a list of flights and a list of planes, suppose each flight has not yet been
	 * allocated a plane to, this method tries to allocate a plane to each flight and ensures that
	 * there're no any time conflicts between all the allocations. 
	 * For example:
	 *  Flight 1 (2020-01-01 8:00-10:00) and Flight 2 (2020-01-01 9:50-10:40) are all allocated 
	 *  the same plane B0001, then there's conflict because from 9:50 to 10:00 the plane B0001
	 *  cannot serve for the two flights simultaneously.
	 *  
	 * @param planes a list of planes
	 * @param flights a list of flights each of which has no plane allocated
	 * @return false if there's no allocation solution that could avoid any conflicts
	 */
	
	public boolean planeAllocation(List<Plane> planes, List<Flight> flights) {
		boolean bFeasible = true;
		Random r = new Random();
		
		Collections.sort(flights, new Comparator<Flight>() {
			public int compare(Flight f1, Flight f2) {
				return f1.getFlightDate().compareTo(f2.getFlightDate());
			}
		});
		
		for (Flight f : flights) {
			boolean bAllocated = false;
			int active = 0;
			
			while (!bAllocated) {
				Plane p = planes.get(r.nextInt(planes.size()));
				Calendar fStart = f.getDepartTime();
				Calendar fEnd = f.getArrivalTime();
				boolean bConflict = false;
				
				for (Flight t : flights) {
					Plane q = t.getPlane();
					if(q == null)
						continue;
					if (! q.equals(p))
						continue;
					
					Calendar tStart = t.getDepartTime();
					Calendar tEnd = t.getArrivalTime();
					
					if ((fStart.compareTo(tStart) > 0 && fStart.compareTo(tEnd) < 0) || (tStart.compareTo(fStart) > 0 && tStart.compareTo(fEnd) < 0)) {
						bConflict = true;
						break;
					}
				}
				
				if (!bConflict) {
					f.setPlane(p);
					break;
				}
				active++;
				if(active >= planes.size()) {
					return false;
				}
			}
		}
		return bFeasible;
	}
}
