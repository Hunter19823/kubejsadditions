package pie.ilikepiefoo2.kubejsadditions.api.graph;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class TopologicalSort<DATA, EDGE extends WeightedEdge<DATA>> {
	private final static Logger LOGGER = LogManager.getLogger();
	private final WeightedDiGraph<DATA, EDGE> data;
	private final LinkedList<DATA> order;
	private boolean containsCycle;

	public TopologicalSort(WeightedDiGraph<DATA, EDGE> data) {
		this.data = data;
		this.order = new LinkedList<>();
		this.containsCycle = false;
	}
	private void calculateOrder() {
		CLEAR_GRAPH:
		while(!data.isEmpty() && !this.containsCycle) {
			for (DATA vertex : this.data) {
				if (this.data.getInDegree(vertex) == 0) {
					this.order.add(vertex);
					this.data.removeVertex(vertex);
					continue CLEAR_GRAPH;
				}
			}
			this.containsCycle = true;
		}
	}

	public LinkedList<DATA> getOrder() {
		this.calculateOrder();
		return this.order;
	}

	public boolean containsCycle() {
		this.calculateOrder();
		return this.containsCycle;
	}

}
