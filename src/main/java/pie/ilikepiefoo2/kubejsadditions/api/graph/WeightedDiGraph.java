package pie.ilikepiefoo2.kubejsadditions.api.graph;

public interface WeightedDiGraph<DATA,EDGE extends WeightedEdge<DATA>> extends Iterable<DATA>{
	/**
	 * Adds a weighted edge to the graph.
	 * If the two vertices are not already in the graph,
	 * they will be added.
	 *
	 * @param edge the edge to add
	 */
	void addEdge(EDGE edge);

	/**
	 * Adds a vertex to the graph.
	 *
	 * @param vertex the vertex to add
	 */
	void addVertex(DATA vertex);

	/**
	 * Removes an edge from the graph.
	 *
	 * @param edge the edge to remove
	 */
	void removeEdge(EDGE edge);

	/**
	 * Removes all the edges of a vertex.
	 *
	 * @param vertex the vertex to remove all edges from
	 */
	void removeAllEdges(DATA vertex);

	/**
	 * Removes all the edges from a vertex to another vertex.
	 * @param from the source vertex
	 * @param to the target vertex
	 */
	void removeAllEdges(DATA from, DATA to);

	/**
	 * Removes a vertex from the graph.
	 * As well as removing all edges connected to that vertex.
	 *
	 * @param vertex the vertex to remove
	 */
	void removeVertex(DATA vertex);

	/**
	 * Whether the graph contains an edge
	 * with the given vertices.
	 *
	 * @param from the first vertex of the edge
	 * @param to the second vertex of the edge
	 * @return true if the graph contains an edge with the given vertices
	 */
	boolean hasEdge(DATA from, DATA to);

	/**
	 * Whether the graph contains a vertex.
	 *
	 * @param vertex the vertex to check
	 * @return true if the graph contains the vertex
	 */
	boolean hasVertex(DATA vertex);

	/**
	 * Returns the shortest edge between two vertices.
	 * (The edge with the lowest weight)
	 * (Ignores self-loops)
	 *
	 * @param from the first vertex
	 * @param to the second vertex
	 * @return the shortest edge between the two vertices
	 */
	EDGE getShortestEdge(DATA from, DATA to);


	/**
	 * The number of total edges in the graph.
	 *
	 * @return the number of total edges in the graph
	 */
	int getEdgeCount();

	/**
	 * Returns the number of out-degrees of the vertex.
	 *
	 * @param vertex the vertex
	 * @return the number of out-degrees of the vertex
	 */
	int getEdgeCount(DATA vertex);

	/**
	 * Returns the number of edges from a
	 * source vertex to a target vertex.
	 *
	 * @param from the source vertex
	 * @param to the target vertex
	 * @return the number of edges from the source vertex to the target vertex
	 */
	int getEdgeCount(DATA from, DATA to);

	/**
	 * Returns the number of neighbors of a vertex.
	 *
	 * @param vertex the vertex
	 * @return the number of neighbors of the vertex
	 */
	int getNeighborCount(DATA vertex);

	/**
	 * Returns the number of vertices in the graph.
	 *
	 * @return the number of vertices in the graph
	 */
	int getVertexCount();

	/**
	 * Returns the number of self-cycles in the graph.
	 * A self-cycle is a cycle that contains a vertex.
	 *
	 * @return the number of self-cycles in the graph
	 */
	int getSelfCycleCount();

	/**
	 * Returns the number of self-loops of the vertex.
	 * A self-loop is an edge that connects a vertex to itself.
	 *
	 * @param vertex the vertex
	 * @return the number of self-loops of the vertex
	 */
	int getSelfCycleCount(DATA vertex);

	/**
	 * Returns the unique out-degree of a vertex.
	 * (Not counting self-loops or parallel edges).
	 *
	 * @param vertex the vertex
	 * @return the degree of the vertex
	 */
	int getOutDegree(DATA vertex);

	/**
	 * Returns the in-degree of a vertex.
	 * (Not counting self-loops or parallel edges).
	 * @param vertex the vertex
	 * @return the in-degree of the vertex
	 */
	int getInDegree(DATA vertex);

	/**
	 * Clears the graph.
	 */
	void clear();

	/**
	 * Returns whether the graph is empty or not.
	 *
	 * @return true if the graph is empty
	 */
	boolean isEmpty();

	/**
	 * Returns an iterable over all vertices in the graph.
	 *
	 * @return an iterable over all vertices in the graph
	 */
	Iterable<DATA> getVertices();

	/**
	 * Returns an iterable over all neighbors of a vertex.
	 *
	 * @param vertex the vertex
	 * @return an iterable over all neighbors of the vertex
	 */
	Iterable<DATA> getNeighbors(DATA vertex);

	/**
	 * Returns an iterable over all edges from the
	 * first vertex to the second vertex.
	 *
	 * @param from the first vertex
	 * @param to the second vertex
	 * @return an iterable over all edges between the two vertices
	 */
	Iterable<EDGE> getEdges(DATA from, DATA to);



}
