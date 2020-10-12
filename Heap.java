import java.util.ArrayList;
import java.util.Collections;

public class Heap {
    private ArrayList<City> minHeap;

    public Heap() {
        minHeap = new ArrayList<City>();
    }

    /**
     * buildHeap(ArrayList<City> cities)
     * Given an ArrayList of Cities, build a min-heap keyed on each City's minCost
     * Time Complexity - O(n)
     *
     * @param cities
     */
    public void buildHeap(ArrayList<City> cities) {
        // TODO: implement this method
    	
    	for (int i = 0; i<cities.size(); i++) {
    		minHeap.add(cities.get(i));
    	}
    	
    	for (int i = 0; i< minHeap.size(); i++) {
    		minHeap.get(i).index = i;
    	}
    	
    	int middle = cities.size()/2;
    	middle--;
    
    	for (int i = middle; i>=0; i--) {
    		
    		City currentCity = minHeap.get(i);
    		int currentIndex = i;
    		int currentKey = currentCity.getMinCost();
    		
    		City leftChild = minHeap.get((i*2)+1);
    		int leftChildIndex = (i*2)+1;
    		int leftChildKey = leftChild.getMinCost();
    		
    		City rightChild;
    		int rightChildIndex;
    		int rightChildKey;
    		
    		if (i == middle && ((middle*2) + 1) == cities.size()-1) {
    			rightChildKey = Integer.MIN_VALUE;
    		}
    		else {
    			rightChild = minHeap.get((i*2)+2);
    			rightChildIndex = (i*2)+2;
    			rightChildKey = rightChild.getMinCost();
    		}
    		
    		heapifyDown(minHeap, currentIndex);
    	}	
    }
    
    public void heapifyDown(ArrayList<City> cities, int i) {
    	int middle = cities.size()/2;
    	middle--;
    	
    	City currentCity = cities.get(i);
		int currentIndex = i;
		int currentKey = currentCity.getMinCost();
		
		City leftChild = cities.get((i*2)+1);
		int leftChildIndex = (i*2)+1;
		int leftChildKey = leftChild.getMinCost();
		
		City rightChild;
		int rightChildIndex = Integer.MIN_VALUE;
		int rightChildKey;
		
		if (i == middle && ((middle*2) + 1) == cities.size()-1) {
			rightChildKey = Integer.MIN_VALUE;
		}
		else {
			rightChild = cities.get((i*2)+2);
			rightChildIndex = (i*2)+2;
			rightChildKey = rightChild.getMinCost();
		}
		
		if (currentKey > leftChildKey && (currentKey <= rightChildKey || rightChildKey == Integer.MIN_VALUE)) { //swap current with left child
			cities.get(currentIndex).index = leftChildIndex;
			cities.get(leftChildIndex).index = currentIndex;
			Collections.swap(cities, currentIndex, leftChildIndex);
			if (leftChildIndex <= middle) {
				heapifyDown(cities, leftChildIndex);
			}
		}
		
		if (currentKey <= leftChildKey && (currentKey > rightChildKey && rightChildKey != Integer.MIN_VALUE)) { //swap with right
			cities.get(currentIndex).index = rightChildIndex;
			cities.get(rightChildIndex).index = currentIndex;
			Collections.swap(cities, currentIndex, rightChildIndex);
			if (rightChildIndex <= middle) {
				heapifyDown(cities, rightChildIndex);
			}
		}
		
		if (currentKey > leftChildKey && (currentKey > rightChildKey && rightChildKey != Integer.MIN_VALUE)) { //bigger than both
			if (leftChildKey > rightChildKey) { //swap right
				cities.get(currentIndex).index = rightChildIndex;
				cities.get(rightChildIndex).index = currentIndex;
				Collections.swap(cities, currentIndex, rightChildIndex);
				
				if (rightChildIndex <= middle) {
					heapifyDown(cities, rightChildIndex);
				}
			}
			else {
				cities.get(currentIndex).index = leftChildIndex;
				cities.get(leftChildIndex).index = currentIndex;
				Collections.swap(cities, currentIndex, leftChildIndex);
				
				if (leftChildIndex <= middle) {
					heapifyDown(cities, leftChildIndex);
				}
			}
		}
		
		if (currentKey == leftChildKey && currentKey == rightChildKey) {
			int currentName = cities.get(currentIndex).getCityName();
			int leftName = cities.get(leftChildIndex).getCityName();
			int rightName = cities.get(rightChildIndex).getCityName();
			
			if (leftName < currentName && leftName < rightName){
				cities.get(currentIndex).index = leftChildIndex;
				cities.get(leftChildIndex).index = currentIndex;
				Collections.swap(cities, currentIndex, leftChildIndex);
				
				if (leftChildIndex <= middle) {
					heapifyDown(cities, leftChildIndex);
				}
			}
			
			if (rightName < currentName && rightName < leftName){
				cities.get(currentIndex).index = rightChildIndex;
				cities.get(rightChildIndex).index = currentIndex;
				Collections.swap(cities, currentIndex, rightChildIndex);
				
				if (rightChildIndex <= middle) {
					heapifyDown(cities, rightChildIndex);
				}
			}
			
		}
		
    }

    /**
     * insertNode(City in)
     * Insert a City into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the City to insert.
     */
    public void insertNode(City in) {
        // TODO: implement this method
    	minHeap.add(in);
    	minHeap.get(minHeap.size()-1).index = minHeap.size()-1;
    	heapifyUp(minHeap, minHeap.size()-1);
    }
    
    public void heapifyUp(ArrayList<City> cities, int i) {
    	boolean atRoot = false;
    	if (i == 0) {
    		atRoot = true;
    	}
    	
    	if (!atRoot) {
        	int parentIndex = (i/2);
        	
        	if (i%2 == 0) { //check for odd index
        		parentIndex--;
        	}
        	
        	if (cities.get(parentIndex).getMinCost() == cities.get(i).getMinCost()) { //equal so check city name
        		if (cities.get(parentIndex).getCityName() > cities.get(i).getCityName()) { //need to swap
        			cities.get(i).index = parentIndex;
        			cities.get(parentIndex).index = i;
        			Collections.swap(cities, parentIndex, i);
        			
        			heapifyUp(cities, parentIndex);
        		}
        	}
        	
        	if (cities.get(parentIndex).getMinCost() > cities.get(i).getMinCost()) {
        		cities.get(i).index = parentIndex;
    			cities.get(parentIndex).index = i;
        		Collections.swap(cities, parentIndex, i);
        		heapifyUp(cities, parentIndex);
        	}
        	
    	}
    }

    /**
     * findMin()
     *
     * @return the minimum element of the heap. Must run in constant time.
     */
    public City findMin() {
        // TODO: implement this method
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public City extractMin() {
        // TODO: implement this method
    	City output = minHeap.get(0);
    	delete(0);
        return output;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        // TODO: implement this method
    	
    	if (index == minHeap.size()-1) { //last element
    		minHeap.remove(index);
    	}
    	else {
    		minHeap.get(index).index = minHeap.size()-1;
    		minHeap.get(minHeap.size()-1).index = index;
    		Collections.swap(minHeap, index, minHeap.size()-1);
    		minHeap.remove(minHeap.size()-1); //remove element
    		
    		if (index > (minHeap.size()/2)-1) { //has no kids
    			heapifyUp(minHeap, index);
    		}
    		
    		else if (index == 0) { //has no parent
    			heapifyDown(minHeap, index);
    		}
    		
    		else { //has parent and at least one kid
            	heapifyDown(minHeap, index);    			
    		}
    	}    	
    }

    /**
     * changeKey(City c, int newCost)
     * Updates and rebalances a heap for City c.
     * Time Complexity - O(log(n))
     *
     * @param c       - the city in the heap that needs to be updated.
     * @param newCost - the new cost of city c in the heap (note that the heap is keyed on the values of minCost)
     */
    public void changeKey(City c, int newCost) {
        // TODO: implement this method
    	int index = c.index;
    	
    	if (newCost > minHeap.get(index).getMinCost()) {
    		minHeap.get(index).setMinCost(newCost);
    		heapifyDown(minHeap, index);
    	}
    	else if(newCost < minHeap.get(index).getMinCost()) {
    		minHeap.get(index).setMinCost(newCost);
    		heapifyUp(minHeap, index);
    	}	
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getCityName() + " ";
        }
        return output;
    }
    
    public void printHeap() {
    	for (int i = 0; i<minHeap.size(); i++) {
    		System.out.println("Index " + i + " - City " + minHeap.get(i).getCityName() + " with key value of: " + minHeap.get(i).getMinCost());
    	}
    }

    public ArrayList<City> getHeap() {
    	return this.minHeap;
    }
    
///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<City> toArrayList() {
        return minHeap;
    }
}
