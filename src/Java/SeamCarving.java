package Java;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import Java.Graph;
import Java.Edge;
public class SeamCarving
{

   public static int[][] readpgm(String fn)
	 {		
        try {
            InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
            BufferedReader d = new BufferedReader(new InputStreamReader(f));
            String magic = d.readLine();
            String line = d.readLine();
		   while (line.startsWith("#")) {
			  line = d.readLine();
		   }
		   Scanner s = new Scanner(line);
		   int width = s.nextInt();
		   int height = s.nextInt();		   
		   line = d.readLine();
		   s = new Scanner(line);
		   int maxVal = s.nextInt();
		   int[][] im = new int[height][width];
		   s = new Scanner(d);
		   int count = 0;
		   while (count < height*width) {
			  im[count / width][count % width] = s.nextInt();
			  count++;
		   }
		   return im;
        }
		
        catch(Throwable t) {
            t.printStackTrace(System.err) ;
            return null;
        }
    }

	/**
	 * fonction qui ecrit un fichier pgm
	 * @param image tableau de image
	 * @param filename nom de fichier pgm
	 */
    public void writepgm(int[][] image, String filename){



		FileOutputStream fop = null;
		File file;
		FileWriter writeFile;
		try {
			file= new File(this.getClass().getResource("/").getPath()+"new.pgm");

			//si le fichier n'exist pas
			if (!file.exists()) {
				file.createNewFile();
			}
			writeFile = new FileWriter (file);
			writeFile.write("P2\n");
			writeFile.write(image[0].length + " " + image.length + "\n");
			writeFile.write("255\n");

			for (int i = 0; i < image.length; i++) {
				for (int j = 0; j < image[0].length; j++) {
					writeFile.write(image[i][j]+" "); //ecriture
					//System.out.print (image[i][j]);
				}
				writeFile.write("\n"); //changer ligne
			}
			writeFile.close();
		}
		catch(Throwable t) {
			t.printStackTrace(System.err) ;
		}

	}


	/**
	 * fonction qui prend  une  image  et  qui  renvoie  untableau de la mˆeme taille, contenant, pour chaque pixel, son facteur d’int ́erˆet
	 * @param image l'image concernee
	 * @return un tableau
	 */
	public int [][] interest(int[][] image){
		int [][] facteurInterest = new int[image.length][image[0].length] ;
		int voisinDeDroite ;
		int voisinDeGauche ;
		int moyenne ;
		for (int i = 0 ; i < image.length ; i++){
			for(int j = 0 ; j < image[i].length ; j++){
				//si le pixel n'a pas de voisin de droite
				if(j+1 >= image[i].length){
					voisinDeGauche = image[i][j-1] ;
					facteurInterest[i][j] = Math.abs(image[i][j] - voisinDeGauche);
				}else if(j-1 < 0){ //si le pixel n'a pas de voisin de gauche
					voisinDeDroite = image[i][j+1] ;
					facteurInterest[i][j] = Math.abs(image[i][j] - voisinDeDroite) ;
				}else if(j < image[i].length) {
					//si il y a un voisin de droite et de gauche = pas de probleme
					voisinDeDroite = image[i][j + 1];
					voisinDeGauche = image[i][j - 1];
					moyenne = (voisinDeDroite + voisinDeGauche) / 2;
					facteurInterest[i][j] = Math.abs(image[i][j] - moyenne) ;

				}
			}
		}
		return facteurInterest ;
	}

	/**
	 * Fonction qui qui calcule le chemin de coˆut minimal entreles sommetssettdans le grapheG
	 * @param g le graphe
	 * @param s le sommet de depart
	 * @param t le sommet d'arrivee
	 * @return un tableau 
	 */
	public int[] bellman_ford(Graph g, int s , int t){ // graphe g sommet de depart s et sommer d'arrivee t
		int [] valeurDuSommet = new int[t-s] ;
		int []parent = new int[t-s];

		for(int i = 0 ; i < t-s ; i++){
			valeurDuSommet[i] = Integer.MAX_VALUE ; //+ L'INFINI;
			//System.out.println (valeurDuSommet[i]);
		}
		valeurDuSommet[0] = 0 ;
		for (int j = 0 ; j < t-s ; j++ ){
			for (Edge e : g.next (j)){


				if (valeurDuSommet[j] >(valeurDuSommet[e.from] + e.cost) ){ //from sommet de depart , to sommet d'arriver , cost le cout de l'arc

					valeurDuSommet[j] = (valeurDuSommet[e.from] + e.cost) ;
					parent[j] = e.to;

					// inutile : parent[j] = e.to ;
				}else{
					//System.out.println(parent[j]);
				}
			}

		}
		//return valeurDuSommet;
		return parent;
	}

	/**
	 * fonction qui créer le graphe correspondant au tableau itr
	 * @param itr tableau d'itr
	 * @return un graph
	 */
    public  Graph tograph(int[][] itr) {
		Graph graph=new Java.GraphArrayList(itr.length*itr[0].length+2);

		// edge dans le début  de la graphe
		for (int j=0;j<itr[0].length;j++) {
			graph.addEdge(new Edge(0,j+1,0));
		}

		// edge dans intermédiaire de la graphe
		for(int i=0;i<itr.length-1;i++){
			graph.addEdge(new Edge(i*itr[0].length+1,(i+1)*itr[0].length,itr[i][0]));
			graph.addEdge(new Edge(i*itr[0].length+1,(i+1)*itr[0].length+1,itr[i][0]));
			for (int j=1;j<itr[0].length-1;j++) {
				graph.addEdge(new Edge(i*itr[0].length+j+1,(i+1)*itr[0].length+j,itr[i][j]));
				graph.addEdge(new Edge(i*itr[0].length+j+1,(i+1)*itr[0].length+j+1,itr[i][j]));
				graph.addEdge(new Edge(i*itr[0].length+j+1,(i+1)*itr[0].length+j+2,itr[i][j]));
			}
			graph.addEdge(new Edge((i+1)*itr[0].length,(i+2)*itr[0].length,itr[i][itr[0].length-1]));
			graph.addEdge(new Edge((i+1)*itr[0].length,(i+2)*itr[0].length-1,itr[i][itr[0].length-1]));
		}

		// edge dans le but  de la graphe
		for (int j=0;j<itr[0].length;j++) {
			graph.addEdge(new Edge((itr.length-1)*itr[0].length+j+1,itr.length*itr[0].length+1,itr[itr.length-1][j]));
		}

		return graph;
	}

	/**
	 * Fonction intermediaire qui permet d'appliquer une fois bellman ford pour supprimer une colonne de pixel sur l'image
	 * @param image l'image traité
	 * @return la nouvelle image
	 */
	public int[][] imageDecoupe(int[][] image){
        //itr=interest(image);
		int[][] itr=interest(image);// juste pour bellman
		Graph graph=tograph(itr);
		//graph.writeFile ("test");
    	int[] bf = bellman_ford(graph,0,itr.length*itr[0].length+1);
		int[][] nouveauTableau = new int[image.length][image[0].length-1]; //a modifier
		int k = 0 ;
		int l = 0 ;
		for (int i = 0 ; i < image.length ; i++){
			//System.out.println (bf[i]);
			for (int j = 0 ; j < image[0].length - 1 ; j++){

				////////////////////////////////////////////////////////////////////////////////////
				//c'est la que ça plante j'arrive pas a gerer l'implémentation differente
				////////////////////////////////////////////////////////////////////////////////////
				//System.out.print (image[i][j]);
				if(i*image[0].length+j != bf[i]){//if(image[i][j] != bf[i]){
					nouveauTableau[i][j] = image[i][j] ;



				}else{
					//l--;

					//nouveauTableau[i][j] = image[i][j+1];
					//j++ ;
				}

			}


			//System.out.println();
		}
		return nouveauTableau;
	}


	/**
	 * fonction qui réduit la taille d’une image bas ́ee sur l’algorithmique des graphes.
	 * @param fn nom de fichier pgm
	 */
	public  void seamCarving(String fn) {
		int[][] image=readpgm(fn);
		/*for(int i = 0 ; i < image.length ; i++){
			for (int j = 0 ; j < image[0].length; j++){
				System.out.print (image[i][j]);
			}
			System.out.println ();
		}*/

//int[][] itr=interest(image);
		//int[][] nouveauTableau ;// = imageDecoupe(graph, image, itr);
		for (int i = 0 ; i < 300  ; i++){
			image = imageDecoupe(image);
		}

		writepgm(image,fn);//avec le nouv tab

		//int[][] itr=interest(image);
		//writepgm(itr,fn);
	}


}
