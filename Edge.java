public class Edge implements Comparable<Edge> {
	public City source;
	public City destination;
	public int weight;
	
	public Edge(City source, City destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
	
	public int compareTo(Edge otherEdge) {
		int weightDiff = this.weight - otherEdge.weight;
		
		if (weightDiff != 0) {
			return weightDiff;
		}
		
		return 0;	
	}
}

