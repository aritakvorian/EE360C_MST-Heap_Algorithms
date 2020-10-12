import java.io.File;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

/*
	This Driver file will be replaced by ours during our grading.
*/
public class Driver {
    private static String filename; // input file name
    private static boolean testHeap; // set to true by -h flag
    private static boolean testDijkstra; // set to true by -d flag
    private static boolean testMST; // set to true by -m flag
    private static Program2 testProgram2; // instance of your graph
    private static ArrayList<City> cities;

    private static void usage() { // error message
        System.err.println("usage: java Driver [-h] [-d] [-m] <filename>");
        System.err.println("\t-h\tTest Heap implementation");
        System.err.println("\t-d\tTest Dijkstra implementation");
        System.err.println("\t-m\tTest the MST implementation");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        cities = new ArrayList<City>();
        //filename = "/Users/melaniefeng/eclipse-workspace/Program2/src/3.txt";
        //parseInputFile(filename);
        parseArgs(args);
        parseInputFile(filename);
        testRun();
    }

    public static void parseArgs(String[] args) {
        boolean flagsPresent = false;
        if (args.length == 0) {
            usage();
        }

        filename = "";

        testMST = false;
        for (String s : args) {
            if (s.equals("-h")) {
                flagsPresent = true;
                testHeap = true;
            } else if (s.equals("-d")) {
                flagsPresent = true;
                testDijkstra = true;
            } else if (s.equals("-m")) {
                flagsPresent = true;
                testMST = true;
            } else if (!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }

        if (!flagsPresent) {
            testHeap = true;
            testDijkstra = true;
            testMST = true;
        }
    }

    public static void parseInputFile(String filename)
            throws Exception {
        int numV = 0, numE = 0;
        Scanner sc = new Scanner(new File(filename));
        String[] inputSize = sc.nextLine().split(" ");
        numV = Integer.parseInt(inputSize[0]);
        numE = Integer.parseInt(inputSize[1]);
        HashMap<Integer, ArrayList<NeighborWeightTuple>> tempNeighbors = new HashMap<>();
        testProgram2 = new Program2(numV);
        for (int i = 0; i < numV; ++i) {

            String[] pairs = sc.nextLine().split(" ");
            String[] weightPairs = sc.nextLine().split(" ");

            Integer currNode = Integer.parseInt(pairs[0]);
            City currentCity = new City(currNode);
            cities.add(currNode, currentCity);
            ArrayList<NeighborWeightTuple> currNeighbors = new ArrayList<>();
            tempNeighbors.put(currNode, currNeighbors);

            for (int k = 1; k < pairs.length; k++) {
                Integer neighborVal = Integer.parseInt(pairs[k]);
                Integer weightVal = Integer.parseInt(weightPairs[k]);
                currNeighbors.add(new NeighborWeightTuple(neighborVal, weightVal));
            }
        }
        for (int i = 0; i < cities.size(); ++i) {
            City currCity = cities.get(i);
            ArrayList<NeighborWeightTuple> neighbors = tempNeighbors.get(i);
            for (NeighborWeightTuple neighbor : neighbors) {
                testProgram2.setEdge(currCity, cities.get(neighbor.neighborID), neighbor.weight);
            }
        }

        testProgram2.setAllNodesArray(cities);
    }

    // feel free to alter this method however you wish. We will replace it with our own version for testing.
    public static void testRun() {
        if (testHeap) {
            // test out your heap here
        	
        	Heap test = new Heap();
        	ArrayList<City> cities = getTestArray();
        	
        	System.out.println("Testing buildHeap function...\n");
        	test.buildHeap(cities);
        	test.printHeap();
        	
        	System.out.println("\nTesting insertNode function...\n");
        	City insertCity = new City(cities.size());
        	insertCity.setMinCost(1);
        	test.insertNode(insertCity);
        	test.printHeap();
        	
        	System.out.println("\nTesting extractMin function...\n");
        	City testExtract = test.extractMin();
        	System.out.println("The extracted city is City "+ testExtract.getCityName() + " with a min cost of " + testExtract.getMinCost());
        	test.printHeap();
        	
        	System.out.println("\nTesting delete function...\n");
        	test.delete(2);
        	System.out.println("After deleting index 2:");
        	test.printHeap();
        	
        	
        	
        }

        if (testDijkstra) {
            // test out Program2.java here!
            System.out.println("Given graph: ");
            System.out.println(testProgram2);
            System.out.println("Cost of shortest path from start to dest: \n" +
                    testProgram2.findCheapestPathPrice(cities.get(1), cities.get(4)));

            System.out.print("The shortest path from start to dest: \n");
            for (City c : testProgram2.findCheapestPath(cities.get(1), cities.get(4))) {
                System.out.print(c.getCityName() + " -> ");
            }
            System.out.print("(done)\n");
        }

        if (testMST) {
            System.out.println("Lowest total cost: \n" + testProgram2.findLowestTotalCost());
        }
    }

    private static class NeighborWeightTuple {
        public Integer neighborID;
        public Integer weight;

        NeighborWeightTuple(Integer neighborID, Integer weight) {
            this.neighborID = neighborID;
            this.weight = weight;
        }
    }
    
    
    public static ArrayList<City> getTestArray(){
    	ArrayList<City> cities = new ArrayList<City>();
    	
    	cities.add(new City(0)); cities.get(0).setMinCost(7);
    	cities.add(new City(1)); cities.get(1).setMinCost(4);
    	cities.add(new City(2)); cities.get(2).setMinCost(9);
    	cities.add(new City(3)); cities.get(3).setMinCost(18);
    	cities.add(new City(4)); cities.get(4).setMinCost(2);
    	cities.add(new City(5)); cities.get(5).setMinCost(3);
    	cities.add(new City(6)); cities.get(6).setMinCost(19);
    	cities.add(new City(7)); cities.get(7).setMinCost(4);
    	cities.add(new City(8)); cities.get(8).setMinCost(7);
    	
    	/*
    	for (int i = 0; i<17; i++) {
    		cities.add(new City(i));
    		cities.get(i).setMinCost(ThreadLocalRandom.current().nextInt(1, 80 + 1));
    	}*/
    	
    	return cities;
    }

}
