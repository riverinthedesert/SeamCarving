package Java;
import java.io.*;
import Java.Edge;
public interface Graph
{
   public int vertices();
   

   public Iterable<Edge> next(int v);
   public Iterable<Edge> prev(int v);

   public void addEdge(Edge e);

    public default void writeFile(String s)
    {
	try
	    {			 
		PrintWriter writer = new PrintWriter(s, "UTF-8");
		writer.println("digraph G{");
		int u;
		int n = vertices();
		for (u = 0; u < n;  u++)
		    for (Edge e: next(u))
			writer.println(e.from + "->" + e.to + "[label=\"" + e.cost + "\"];");
		writer.println("}");
		writer.close();
	    }
	catch (IOException e)
	    {
	    }						
    }
  
}
