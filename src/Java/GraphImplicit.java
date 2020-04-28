package Java;
import java.util.ArrayList;
import Java.Graph;
import Java.Edge;
class GraphImplicit implements Graph{
    int N;
    
@SuppressWarnings("unchecked")    
    GraphImplicit(int N){
	this.N = N;
    }

    public int vertices(){
	return N;
    }

	public void addEdge(Edge e){}
    
@SuppressWarnings("unchecked")    
    public Iterable<Edge> next(int v)
	 {
	     ArrayList<Edge> edges = new ArrayList();
	     for (int i = v; i < N; i++)
		  edges.add(new Edge(v,i,i));
	     return edges;
		      
	 }
@SuppressWarnings("unchecked")
   public Iterable<Edge> prev(int v)
	 {
	     ArrayList<Edge> edges = new ArrayList();
	     for (int i = 0; i < v-1; i++)
		  edges.add(new Edge(i,v,v));
	     return edges;

	 }

    
}
