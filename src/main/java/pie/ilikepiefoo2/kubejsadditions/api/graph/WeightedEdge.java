package pie.ilikepiefoo2.kubejsadditions.api.graph;

import org.jetbrains.annotations.NotNull;

public class WeightedEdge<DATA> extends Edge<DATA> implements Comparable<WeightedEdge<DATA>> {
	public double weight;

	public WeightedEdge(@NotNull DATA from, @NotNull  DATA to, double weight) {
		super(from, to);
		this.weight = weight;
	}

	public WeightedEdge(@NotNull DATA from, @NotNull DATA to) {
		super(from, to);
		this.weight = Double.POSITIVE_INFINITY;
	}

	public WeightedEdge(@NotNull Edge<DATA> edge, double weight) {
		super(edge.from, edge.to);
		this.weight = weight;
	}

	public WeightedEdge(@NotNull Edge<DATA> edge) {
		super(edge.from, edge.to);
		this.weight = Double.POSITIVE_INFINITY;
	}

	public double getWeight() {
		return weight;
	}

	public double setWeight(double weight) {
		return this.weight = weight;
	}

	@Override
	public int compareTo(@NotNull WeightedEdge<DATA> o) {
		return Double.compare(weight, o.weight);
	}
}
