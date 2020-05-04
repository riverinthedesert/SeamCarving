
package Java;
import Java.Graph;
import Java.Edge;
import Java.GraphArrayList;
import Java.SeamCarving;
class Test
{
   static boolean visite[];
   public static void dfs(Graph g, int u)
	 {
		visite[u] = true;
		System.out.println("Je visite " + u);
		for (Edge e: g.next(u))
		  if (!visite[e.to])
			dfs(g,e.to);
	 }
   
   public static void testGraph()
	 {
		int n = 5;
		int i,j;
		GraphArrayList g = new GraphArrayList(n*n+2);
		
		for (i = 0; i < n-1; i++)
		  for (j = 0; j < n ; j++)
			g.addEdge(new Edge(n*i+j, n*(i+1)+j, 1664 - (i+j)));

		for (j = 0; j < n ; j++)		  
		  g.addEdge(new Edge(n*(n-1)+j, n*n, 666));
		
		for (j = 0; j < n ; j++)					
		  g.addEdge(new Edge(n*n+1, j, 0));
		
		g.addEdge(new Edge(13,17,1337));
		g.writeFile("test.dot");
		// dfs à partir du sommet 3
		visite = new boolean[n*n+2];
		dfs(g, 3);
	 }

	public static void testFonctionInterest(){
		SeamCarving sc = new SeamCarving();
		int[][] image = new int[3][4];
		image[0][0] = 3 ;
		image[0][1] = 11 ;
		image[0][2] = 24 ;
		image[0][3] = 39 ;
		image[1][0] = 8 ;
		image[1][1] = 21 ;
		image[1][2] = 29 ;
		image[1][3] = 39 ;
		image[2][0] = 200 ;
		image[2][1] = 60 ;
		image[2][2] = 25 ;
		image[2][3] = 0 ;
		int [][] resultat = sc.interest(image);
		for (int i = 0 ; i < 3 ; i++){
			for (int j = 0 ; j < 4 ; j++){
				System.out.println(resultat[i][j]);
			}
		}


	}


   
   public static void main(String[] args)
	 {
		//testGraph();
		 //testFonctionInterest();
		 int nbPixelASuppr = 300;
		 if (args.length != 1){
		 	//si on met deux argument : le premier est le nom du fichier et le second est le nombre de pixel a supprimer
		 	if (args.length == 2){
		 		nbPixelASuppr = Integer.parseInt (args[1]) ;
			}else{
		 		System.out.println ("erreur dans le nombre d'arguments");
			}
		 }
		 SeamCarving seamCarving=new SeamCarving();
		 seamCarving.seamCarving(args[0], nbPixelASuppr);
	 }
}
