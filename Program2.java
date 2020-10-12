import java.util.ArrayList;
import java.util.*;

public class Program2 {
    private ArrayList<City> cities;     //this is a list of all cities, populated by Driver class.
    private Heap minHeap;

    // feel free to add any fields you'd like, but don't delete anything that's already here

    public Program2(int numCities) {
        minHeap = new Heap();
        cities = new ArrayList<City>();
    }

    /**
     * findCheapestPathPrice(City start, City dest)
     *
     * @param start - the starting city.
     * @param dest  - the end (destination) city.
     * @return the minimum cost possible to get from start to dest.
     * If no path exists, return Integer.MAX_VALUE
     */
    public int findCheapestPathPrice(City start, City dest) {
        //set all min costs to infinity besides the start node
    	for (int i = 0; i<cities.size(); i++) {
    		cities.get(i).setMinCost(Integer.MAX_VALUE);
    	}
    	start.setMinCost(0);
    	
    	//minHeap is built
    	minHeap.buildHeap(cities);
    	
    	ArrayList<City> heap = minHeap.getHeap();
    	
    	//array to see if we've visited this node before
    	boolean[] explored = new boolean[cities.size()];
    	
    	while (!heap.isEmpty()) {
    		
    		//pull node out
    		City extracted = minHeap.extractMin();
    		explored[extracted.getCityName()] = true;
    		ArrayList<City> neighbors = extracted.getNeighbors();
    		
    		//loop through neighbors
    		for (int i = 0; i<neighbors.size(); i++) {
    			City neighbor = neighbors.get(i);
    			
    			//has not been explored
    			if (!explored[neighbor.getCityName()]){
    				if (extracted.getWeights().get(i) + extracted.getMinCost() < neighbor.getMinCost()) {
    					minHeap.changeKey(neighbor, extracted.getWeights().get(i) + extracted.getMinCost());
    					neighbor.previous = extracted;
    				}
    			}
    		}	
    	}    	
    	return dest.getMinCost();
    }

    /**
     * findCheapestPath(City start, City dest)
     *
     * @param start - the starting city.
     * @param dest  - the end (destination) city.
     * @return an ArrayList of nodes representing a minimum-cost path on the graph from start to dest.
     * If no path exists, return null
     */
    public ArrayList<City> findCheapestPath(City start, City dest) {
        
        //set all min costs to infinity besides the start node
    	for (int i = 0; i<cities.size(); i++) {
    		cities.get(i).setMinCost(Integer.MAX_VALUE);
    	}
    	start.setMinCost(0);
    	
    	//minHeap is built
    	minHeap.buildHeap(cities);
    	
    	ArrayList<City> heap = minHeap.getHeap();
    	
    	//array to see if we've visited this node before
    	boolean[] explored = new boolean[cities.size()];
    	
    	while (!heap.isEmpty()) {
    		
    		//pull node out
    		City extracted = minHeap.extractMin();
    		explored[extracted.getCityName()] = true;
    		ArrayList<City> neighbors = extracted.getNeighbors();
    		
    		//loop through neighbors
    		for (int i = 0; i<neighbors.size(); i++) {
    			City neighbor = neighbors.get(i);
    			
    			//has not been explored
    			if (!explored[neighbor.getCityName()]){
    				if (extracted.getWeights().get(i) + extracted.getMinCost() < neighbor.getMinCost()) {
    					minHeap.changeKey(neighbor, extracted.getWeights().get(i) + extracted.getMinCost());
    					neighbor.previous = extracted;
    				}
    			}
    		}	
    	}
    	int cost = dest.getMinCost();
    	
    	ArrayList<City> path = new ArrayList<City>();
    	path.add(dest);
    	while (path.get(path.size()-1) != start) {
    		if (dest.previous == null) {
    			return null;
    		}
    		path.add(dest.previous);
    		dest = dest.previous;
    	}
    	
    	Collections.reverse(path);
 	
    	return path;
    }

    /**
     * findLowestTotalCost()
     *
     * @return The sum of all edge weights in a minimum spanning tree for the given graph.
     * Assume the given graph is always connected.
     * The government wants to shut down as many tracks as possible to minimize costs.
     * However, they can't shut down a track such that the cities don't remain connected.
     * The tracks you're leaving open cost some money (aka the edge weights) to maintain. Minimize the overall cost.
     */
    public int findLowestTotalCost() {
        
    	//get edge list
    	ArrayList<Edge> edges = getEdgeList(cities);
        Collections.sort(edges);
    	
    	ArrayList<Edge> MST = new ArrayList<Edge>();
    	
    	while (edges.size() != 0) {
    		Edge extracted = edges.remove(0);
    		
    		if (!createsCycle(MST, extracted)) {
    			MST.add(extracted);
    		}
    	}
    	int sum = 0;
    	for (int i = 0; i<MST.size(); i++) {
    		sum += MST.get(i).weight;
    	}
    	
    	return sum;
    }   
    
    public boolean createsCycle(ArrayList<Edge> MST, Edge add) {

    	ArrayList<Edge> MSTcopy = new ArrayList<Edge>();
    	
    	for (int i = 0; i<MST.size(); i++) {
    		MSTcopy.add(MST.get(i));
    	}
    	 
    	//reset cities MST fields
    	for (int i = 0; i<cities.size(); i++) {
    		cities.get(i).MSTVisited = false;
    		cities.get(i).MSTneighbors.clear();
    	}
    	
    	
    	//create new adjacency list 
    	for (int i = 0; i<MSTcopy.size(); i++) {
    		Edge current = MSTcopy.get(i);
    		City source = current.source;
    		City destination = current.destination;
    		
    		source.MSTneighbors.add(destination);
    		destination.MSTneighbors.add(source);
    		
    	}
    	
    	City checkSource = add.source;
    	City checkDestination = add.destination;
    	
    	//either node is not in the current MST
    	if (checkSource.MSTneighbors.size() == 0 || checkDestination.MSTneighbors.size() == 0) {
    		return false;
    	}
    	else { //do DFS
    		checkSource.MSTVisited = true;
    		Queue<City> queue = new LinkedList<>();
    		
    		queue.add(checkSource);
    		
    		while (!queue.isEmpty()) {
    			City check = queue.remove();
    			
    			Iterator<City> i = check.MSTneighbors.iterator();
    			while (i.hasNext()) {
    				City next = i.next();
    				
    				if (!next.MSTVisited) {
    					next.MSTVisited = true;
    					queue.add(next);
    				}
    				
    			}
    			
    		}
 
    		
    		return checkDestination.MSTVisited;
    	}
    }
    
    
    
    public ArrayList<Edge> getEdgeList(ArrayList<City> cities){
    	ArrayList<Edge> edges = new ArrayList<Edge>();
    	
    	for (int i = 0; i<cities.size(); i++) {
    		City currentCity = cities.get(i);
    		
    		for (int j = 0; j<currentCity.getNeighbors().size(); j++) {
    			City destination = currentCity.getNeighbors().get(j);
    			int weight = currentCity.getWeights().get(j);
    			Edge temp = new Edge(currentCity, destination, weight);
    			edges.add(temp);
    		}
    		
    	}
    	
    	for (int i = 0; i<edges.size(); i++) {
    		for (int j = 1; j<edges.size(); j++) {
    			if (edges.get(i).source == edges.get(j).destination && edges.get(i).destination == edges.get(j).source) {
    				edges.remove(j);
    			}
    		}
    	}
    	
    	
    	return edges;
    }
    
    //returns edges and weights in a string.
    public String toString() {
        String o = "";
        for (City v : cities) {
            boolean first = true;
            o += "City ";
            o += v.getCityName();
            o += " has neighbors: ";
            ArrayList<City> ngbr = v.getNeighbors();
            for (City n : ngbr) {
                o += first ? n.getCityName() : ", " + n.getCityName();
                first = false;
            }
            first = true;
            o += " with weights ";
            ArrayList<Integer> wght = v.getWeights();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<City> getAllCities() {
        return cities;
    }

    //used by Driver class to populate each Node with correct neighbors and corresponding weights
    public void setEdge(City curr, City neighbor, Integer weight) {
        curr.setNeighborAndWeight(neighbor, weight);
    }

    //This is used by Driver.java and sets vertices to reference an ArrayList of all nodes.
    public void setAllNodesArray(ArrayList<City> x) {
        cities = x;
    }
}
