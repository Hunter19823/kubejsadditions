package pie.ilikepiefoo2.kubejsadditions.api.dot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class DotFileCreator {
	private static final Logger LOGGER = LogManager.getLogger();
	private final StringBuilder sb = new StringBuilder();

	public DotFileCreator(String graphName) {
		sb.append("digraph ").append(graphName).append(" {\n");
	}

	public void addNode(String nodeName) {
		sb.append("\t").append(nodeName).append(";\n");
	}

	public void addNode(String nodeName, String metadata) {
		sb.append("\t").append(nodeName).append(" [").append(metadata).append("];\n");
	}

	public void addEdge(String fromNode, String toNode) {
		sb.append("\t").append(fromNode).append(" -> ").append(toNode).append(";\n");
	}

	public void addEdge(String fromNode, String toNode, String metadata) {
		sb.append("\t").append(fromNode).append(" -> ").append(toNode).append(" [").append(metadata).append("];\n");
	}

	public void createDotFile() throws IOException {
		File file = new File("out/recipe_graph.dot");
		FileWriter writer = new FileWriter(file);
		writer.write(sb+"}");
		writer.close();
	}

	@Override
	public String toString() {
		return sb + "}";
	}
}
