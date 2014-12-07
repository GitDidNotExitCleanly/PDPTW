import java.util.Vector;

import model.Customer;
import model.InsertionInformation;
import model.Route;
import model.Solution;
import model.VehicleProperty;


public class ProduceInitialSolution {
	private InsertionHeuristic ih;
	
	public ProduceInitialSolution(VehicleProperty vp) {
		ih = new InsertionHeuristic(vp);
	}
	
	public Solution produceInitialSolution() {
		Solution initial = new Solution();
		
		while (true) {
			// initialize a seed pd pair
			int route = initializeRoute(initial);
			
			while (ih.getUnroutedCustomers().size() != 0) {
				// get all c1 values
				Vector<InsertionInformation> temp = ih.computeMinimalC1(initial.get(route));
				
				if (temp.size() == 0) {
					break;
				}
				else {
					// get best c2 value
					InsertionInformation best = ih.computeMaximalC2(temp);
					// insert pd pair
					ih.insert(initial.get(route),best);
				}
			}
			
			if (ih.getUnroutedCustomers().size() == 0) {
				break;
			}
		}
		
		return initial;
	}
	
	// initial seed PD pair
	private int initializeRoute(Solution initial) {
		int pickupIndex = ih.selectSeed();
		Customer delivery = ih.getUnroutedCustomers().remove(pickupIndex+1);
		Customer pickup = ih.getUnroutedCustomers().remove(pickupIndex);
		Route newRoute = new Route();
		newRoute.add(pickup);		// pickup
		newRoute.add(delivery);		// delivery
		initial.insertAt(initial.size(), newRoute);

		return initial.size()-1;
	}
}
