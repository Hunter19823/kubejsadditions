package pie.ilikepiefoo2.kubejsadditions.api;

import org.jetbrains.annotations.NotNull;
import pie.ilikepiefoo2.kubejsadditions.api.graph.WeightedDiGraph;
import pie.ilikepiefoo2.kubejsadditions.api.graph.WeightedEdge;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class WeightedDiGraphImpl<DATA, EDGE extends WeightedEdge<DATA>> implements WeightedDiGraph<DATA, EDGE> {
	protected final HashMap<DATA, HashMap<DATA, TreeSet<EDGE>>> edges = new HashMap<>();
	protected final HashMap<DATA, HashSet<DATA>> reverseEdges = new HashMap<>();
	protected int vertexCount = 0;
	protected int edgeCount = 0;
	protected int selfLoops = 0;

	@Override
	public void addEdge(EDGE edge) {
		addVertex(edge.getFrom());
		addVertex(edge.getTo());
		edges.get(edge.getFrom()).putIfAbsent(edge.getTo(), new TreeSet<>());
		edges.get(edge.getFrom()).get(edge.getTo()).add(edge);
		reverseEdges.get(edge.getTo()).add(edge.getFrom());
		if (edge.getFrom().equals(edge.getTo())) {
			selfLoops++;
		}
		edgeCount++;
	}

	@Override
	public void addVertex(DATA vertex) {
		if(!edges.containsKey(vertex)) {
			edges.put(vertex, new HashMap<>());
			reverseEdges.put(vertex, new HashSet<>());
			vertexCount++;
		}
	}

	@Override
	public void removeEdge(EDGE edge) {
		if(edges.get(edge.getFrom()).containsKey(edge.getTo())) {
			if (edges.get(edge.getFrom()).get(edge.getTo()).remove(edge)) {
				edgeCount--;
				if(edge.getFrom().equals(edge.getTo())) {
					selfLoops--;
				}
			}
		}
	}

	@Override
	public void removeAllEdges(DATA vertex) {
		if(edges.containsKey(vertex)) {
			edges.get(vertex).forEach((to, edges) -> {
				edgeCount -= edges.size();
				if(vertex.equals(to)) {
					selfLoops -= edges.size();
				}
				edges.clear();
				reverseEdges.get(to).remove(vertex);
			});
			edges.remove(vertex).clear();
			reverseEdges.remove(vertex).clear();
		}
	}

	@Override
	public void removeAllEdges(DATA from, DATA to) {
		if(edges.get(from).containsKey(to)) {
			edgeCount -= edges.get(from).get(to).size();
			if(from.equals(to)) {
				selfLoops -= edges.get(from).get(to).size();
			}
			edges.get(from).get(to).clear();
			edges.get(from).remove(to);
			reverseEdges.get(to).remove(from);
		}
	}

	@Override
	public void removeVertex(DATA vertex) {
		if(hasVertex(vertex)) {
			removeAllEdges(vertex);
			vertexCount--;
		}
	}

	@Override
	public boolean hasEdge(DATA from, DATA to) {
		if(edges.containsKey(from))
			return edges.get(from).containsKey(to);
		return false;
	}

	@Override
	public boolean hasVertex(DATA vertex) {
		return edges.containsKey(vertex) && reverseEdges.containsKey(vertex);
	}

	@Override
	@Nullable
	public EDGE getShortestEdge(DATA from, DATA to) {
		if(edges.containsKey(from)) {
			if(edges.get(from).containsKey(to)) {
				return edges.get(from).get(to).first();
			}
		}
		return null;
	}

	@Override
	public int getEdgeCount() {
		return edgeCount;
	}

	@Override
	public int getEdgeCount(DATA vertex) {
		if(edges.containsKey(vertex)) {
			int count = 0;
			for(var connections : edges.get(vertex).values()){
				count += connections.size();
			}
			return count;
		}
		return 0;
	}

	@Override
	public int getEdgeCount(DATA from, DATA to) {
		if(edges.containsKey(from))
			if(edges.get(from).containsKey(to))
				return edges.get(from).get(to).size();

		return 0;
	}

	@Override
	public int getNeighborCount(DATA vertex) {
		if(edges.containsKey(vertex))
			return edges.get(vertex).size();
		return 0;
	}

	@Override
	public int getVertexCount() {
		return vertexCount;
	}

	@Override
	public int getSelfCycleCount() {
		return selfLoops;
	}

	@Override
	public int getSelfCycleCount(DATA vertex) {
		if(edges.containsKey(vertex)) {
			if(edges.get(vertex).containsKey(vertex)) {
				return edges.get(vertex).get(vertex).size();
			}
		}
		return 0;
	}

	@Override
	public int getOutDegree(DATA vertex) {
		if(edges.containsKey(vertex)) {
			return edges.get(vertex).size();
		}
		return 0;
	}

	@Override
	public int getInDegree(DATA vertex) {
		if(reverseEdges.containsKey(vertex)) {
			if(reverseEdges.get(vertex).contains(vertex)) {
				return reverseEdges.get(vertex).size() - 1;
			}
			return reverseEdges.get(vertex).size();
		}
		return 0;
	}

	@Override
	public void clear() {
		// Perform a deep clear of the graph to avoid memory leaks
		edges.values().parallelStream().forEach(
				connections -> {
					connections.values().parallelStream().forEach(
							TreeSet::clear
					);
					connections.clear();
				}
		);
		reverseEdges.values().parallelStream().forEach(
				HashSet::clear
		);
		edges.clear();
		reverseEdges.clear();
		vertexCount = 0;
		edgeCount = 0;
		selfLoops = 0;
	}

	@Override
	public boolean isEmpty() {
		return vertexCount == 0;
	}

	@Override
	public Iterable<DATA> getVertices() {
		return edges.keySet();
	}

	@Override
	public Iterable<DATA> getNeighbors(DATA vertex) {
		if(edges.containsKey(vertex)) {
			return edges.get(vertex).keySet();
		}
		return Collections.emptySet();
	}

	@Override
	public Iterable<EDGE> getEdges(DATA from, DATA to) {
		if(edges.containsKey(from)) {
			if(edges.get(from).containsKey(to)) {
				return edges.get(from).get(to);
			}
		}
		return Collections.emptySet();
	}

	@NotNull
	@Override
	public Iterator<DATA> iterator() {
		return edges.keySet().iterator();
	}
}
