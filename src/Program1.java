/*
 * Name: Zi Zhou Wang
 * EID: zw3948
 */

import java.util.Vector;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Matching problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */
    public boolean isStableMatching(Matching given_matching) {
    	Vector<Vector<Integer>> apartment_pref = new Vector<Vector<Integer>>();
    	int numOfTenants = given_matching.getTenantCount();
    	apartment_pref.setSize(numOfTenants);
    	for(int landLordNumber = 0; landLordNumber < given_matching.getLandlordCount(); landLordNumber++){
    		for(int aptIndex = 0; aptIndex < given_matching.getLandlordOwners().get(landLordNumber).size(); aptIndex++){
    			apartment_pref.set(given_matching.getLandlordOwners().get(landLordNumber).get(aptIndex), given_matching.getLandlordPref().get(landLordNumber));
    		}
    	}
    	for(int tenantIndex = 0; tenantIndex < numOfTenants; tenantIndex++){
    		for(int aptIndex = 0; aptIndex < given_matching.getTenantCount(); aptIndex++){
    			int otherTenant = given_matching.getTenantMatching().indexOf(aptIndex);
    			int currentApartment = given_matching.getTenantMatching().get(tenantIndex);
    			if(currentApartment != aptIndex)
    				if(given_matching.getTenantPref().get(tenantIndex).get(aptIndex) < given_matching.getTenantPref().get(tenantIndex).get(currentApartment)){
        				if(apartment_pref.get(aptIndex).get(tenantIndex) < apartment_pref.get(aptIndex).get(otherTenant)){
        					return false;
        				}
        			}
    		}
    	}
        return true;
    }

    /**
     * Determines a solution to the Stable Matching problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableMatchingGaleShapley(Matching given_matching) {
    	Vector<Vector<Integer>> apartment_pref = new Vector<Vector<Integer>>();
    	Vector<Vector<Integer>> tenant_pref = new Vector<Vector<Integer>>();
    	int numOfTenants = given_matching.getTenantCount();
    	for(int tenantNum = 0; tenantNum < numOfTenants; tenantNum++){
    		Vector<Integer> copy = (Vector<Integer>) given_matching.getTenantPref().get(tenantNum).clone();
    		tenant_pref.add(copy);
    	}
    	apartment_pref.setSize(numOfTenants);
    	for(int landLordNumber = 0; landLordNumber < given_matching.getLandlordCount(); landLordNumber++){
    		for(int aptIndex = 0; aptIndex < given_matching.getLandlordOwners().get(landLordNumber).size(); aptIndex++){
    			Vector copy = new Vector(given_matching.getLandlordPref().get(landLordNumber));
    			apartment_pref.set(given_matching.getLandlordOwners().get(landLordNumber).get(aptIndex), copy);
    		}
    	}
    	/*for(int apartNum = 0; apartNum < given_matching.getTenantCount(); apartNum++){
    		for(int tenantNum = 0; tenantNum < given_matching.getTenantCount(); tenantNum++){
    			int currentPref = apartment_pref.get(apartNum).get(tenantNum);
    			apartment_pref.get(apartNum).set(tenantNum, (currentPref * 10000 + tenantNum));
    		}
    	}*/
    	for(int tenantNum = 0; tenantNum < given_matching.getTenantCount(); tenantNum++){
    		for(int apartNum = 0; apartNum < given_matching.getTenantCount(); apartNum++){
    			int currentPref = tenant_pref.get(tenantNum).get(apartNum);
    			tenant_pref.get(tenantNum).set(apartNum, (currentPref * 10000 + apartNum));
    		}
    	}
    	ArrayList<HashMap<Integer, Integer>> tenantPrefMap = new ArrayList<HashMap<Integer, Integer>>();
    	for(int tenantNum = 0; tenantNum < numOfTenants; tenantNum++){
    		HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();
			for (int apartNum = 0; apartNum < numOfTenants; apartNum++){
                temp.put(tenant_pref.get(tenantNum).get(apartNum), apartNum);
			}
			tenantPrefMap.add(temp);
    	}
    	Queue<Integer> freeTenants = new LinkedList<Integer>();
    	HashMap<Integer, Integer> pairs = new HashMap<Integer, Integer>();
    	for(int i = 0; i < numOfTenants; i ++){
    		freeTenants.add(i);
    	}
    	while(!freeTenants.isEmpty()){
    		int tenant = freeTenants.remove();
    		int min = Collections.min(tenantPrefMap.get(tenant).keySet());
    		int apartment = tenantPrefMap.get(tenant).get(min);
    		tenantPrefMap.get(tenant).remove(min);
    		if(!pairs.containsKey(apartment)){
    			pairs.put(apartment, tenant);
    		}
    		else{
    			int otherTenant = pairs.get(apartment);
    			if(apartment_pref.get(apartment).get(tenant) < apartment_pref.get(apartment).get(otherTenant)){
    				freeTenants.add(otherTenant);
    				pairs.replace(apartment, tenant);
    			}
    			else{
    				freeTenants.add(tenant);
    			}
    		}
    	}
    	Vector<Integer> tenant_matching = new Vector<Integer>();
    	tenant_matching.setSize(numOfTenants);
    	for (int apartment = 0; apartment < numOfTenants; apartment++) {
    	    tenant_matching.set(pairs.get(apartment), apartment);
    	}

    	given_matching.setTenantMatching(tenant_matching);
        return given_matching;
    }
}
