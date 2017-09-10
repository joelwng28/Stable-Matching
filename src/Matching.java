import java.util.Vector;

/**
 * A Matching represents a candidate solution to the Stable Matching problem. A
 * Matching may or may not be stable.
 */
public class Matching {
    /** Number of landlords */
    private Integer l;

    /** Number of tenants */
    private Integer n;

    /** A list describing each landlord's ownership list */
    private Vector<Vector<Integer>> landlord_own;
    
    /** A list containing each landlord's preference list */
    private Vector<Vector<Integer>> landlord_pref;

    /** A list containing each tenants's preference list */
    private Vector<Vector<Integer>> tenant_pref;

    /**
     * Matching information representing the index of apartment a tenant is
     * matched to, -1 if not matched.
     * 
     * An empty matching is represented by a null value for this field.
     */
    private Vector<Integer> tenant_matching;

    public Matching(Integer l, Integer n,
    		Vector<Vector<Integer>> landlord_own,
            Vector<Vector<Integer>> landlord_pref,
            Vector<Vector<Integer>> tenant_pref
    		) {
        this.l = l;
        this.n = n;
        this.landlord_own = landlord_own;
        this.landlord_pref = landlord_pref;
        this.tenant_pref = tenant_pref;
        this.tenant_matching = null;
    }
    
    public Matching(Integer l, Integer n,
    		Vector<Vector<Integer>> landlord_own,
            Vector<Vector<Integer>> landlord_pref,
            Vector<Vector<Integer>> tenant_pref,
            Vector<Integer> tenant_matching
    		) {
        this.l = l;
        this.n = n;
        this.landlord_own = landlord_own;
        this.landlord_pref = landlord_pref;
        this.tenant_pref = tenant_pref;
        this.tenant_matching = tenant_matching;
    }

    /**
     * Constructs a solution to the stable matching problem, given the problem
     * as a Matching. Take a Matching which represents the problem data with no
     * solution, and a tenant_matching which solves the problem given in data.
     * 
     * @param data
     *            The given problem to solve.
     * @param tenant_matching
     *            The solution to the problem.
     */
    public Matching(Matching data, Vector<Integer> tenant_matching) {
        this(data.l, data.n, data.landlord_own,
        		data.landlord_pref, data.tenant_pref,
                tenant_matching);
    }

    /**
     * Creates a Matching from data which includes an empty solution.
     * 
     * @param data
     *            The Matching containing the problem to solve.
     */
    public Matching(Matching data) {
        this(data.l, data.n, data.landlord_own,
        		data.landlord_pref, data.tenant_pref,
                new Vector<Integer>(0));
    }

    public void setTenantMatching(Vector<Integer> tenant_matching) {
        this.tenant_matching = tenant_matching;
    }

    public Integer getLandlordCount() {
        return l;
    }

    public Integer getTenantCount() {
        return n;
    }

    public Vector<Vector<Integer>> getLandlordOwners() {
        return landlord_own;
    }
    
    public Vector<Vector<Integer>> getLandlordPref() {
        return landlord_pref;
    }

    public Vector<Vector<Integer>> getTenantPref() {
        return tenant_pref;
    }

    public Vector<Integer> getTenantMatching() {
        return tenant_matching;
    }

    
    public String getInputSizeString() {
        return String.format("l=%d n=%d\n", l, n);
    }
    
    public String getSolutionString() {
        if (tenant_matching == null) {
            return "";
        }
        
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < tenant_matching.size(); i++) {
            String str = String.format("Tenant %d Apartment %d", i, tenant_matching.get(i));
            s.append(str);
            if (i != tenant_matching.size() - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }
    
    public String toString() {
        return getInputSizeString() + getSolutionString();
    }

}
