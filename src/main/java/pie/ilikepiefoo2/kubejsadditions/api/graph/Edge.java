package pie.ilikepiefoo2.kubejsadditions.api.graph;

import org.jetbrains.annotations.NotNull;

public class Edge<DATA> {
	public final DATA from;
	public final DATA to;

	public Edge(@NotNull DATA from, @NotNull  DATA to) {
		this.from = from;
		this.to = to;
	}

	public DATA getFrom() {
		return from;
	}

	public DATA getTo() {
		return to;
	}

	@Override
	public int hashCode() {
		return from.hashCode() + to.hashCode();
	}
}
