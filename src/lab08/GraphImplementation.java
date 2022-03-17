package lab08;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GraphImplementation {
	
	public static void main(String[] args) {
		generateRandomDotFile("dotgraph.txt", 100);
	}
	
	public static void generateRandomDotFile(String filename, int vertexCount) {
	    PrintWriter out = null;
	    try {
	      out = new PrintWriter(filename);
	    } 
	    catch (IOException e) {
	      System.out.println(e);
	    }

	    Random rng = new Random();

	    // randomly construct either a graph or a digraph
	    String edgeOp = "--";
	    if (true) {
	      out.print("di");
	      edgeOp = "->";
	    }
	    out.println("graph G {");

	    // generate a list of vertices
	    String[] vertex = new String[vertexCount];
	    for(int i = 0; i < vertexCount; i++)
	      vertex[i] = String.valueOf(i);

	    // randomly connect the vertices using 2 * |V| edges
	    for(int i = 0; i < vertexCount - 1; i++)
//	    	  out.println("\t" + vertex[i] + "->" + vertex[i + 1 + rng.nextInt(vertexCount - (i + 1))]);
	    	  out.println("\t" + rng.nextInt(vertexCount) + "->" + rng.nextInt(vertexCount));

	    out.println("}");
	    out.close();
	  }
}
