// feel free to add things to this file. Just don't remove anything

import java.util.*;

public class City {
    private int minCost;
    private int cityName;
    private ArrayList<City> neighbors;
    private ArrayList<Integer> weights;
    public int index;
    public City previous;
    public ArrayList<City> MSTneighbors;
    public boolean MSTVisited;

    public City(int x) {
        cityName = x;
        minCost = Integer.MAX_VALUE;
        neighbors = new ArrayList<City>();
        weights = new ArrayList<Integer>();
        index = 0;
        MSTVisited = false;
        MSTneighbors = new ArrayList<City>();
    }

    public void setNeighborAndWeight(City n, Integer w) {
        neighbors.add(n);
        weights.add(w);
    }

    public ArrayList<City> getNeighbors() {
        return neighbors;
    }

    public ArrayList<Integer> getWeights() {
        return weights;
    }

    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int x) {
        minCost = x;
    }

    public void resetMinCost() {
        minCost = Integer.MAX_VALUE;
    }

    public int getCityName() {
        return cityName;
    }
}
