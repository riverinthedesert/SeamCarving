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

	/*	String PGMInfo = "P5" + Convert.ToChar(10) + '#' +
				" " + Convert.ToChar(10) +
				iw.ToString() + " " +
				ih.ToString() + Convert.ToChar(10) +
				"256" + Convert.ToChar(10);
		FileOutputStream OutputStream = File.createTempFile("ressource/IMAGES"+filename);
		BinaryWriter PGMWriter = new BinaryWriter(OutputStream);
		byte[] PGMInfoBuffer = System.Text.ASCIIEncoding.Default.GetBytes(PGMInfo);
		PGMWriter.Write(PGMInfoBuffer);
		byte[] data = new byte[iw * ih];
		for (int j = 0; j < ih; j++)
			for (int i = 0; i < iw; i++)
				data[i + j * iw] = (byte)bm.GetPixel(i, j).R;
		PGMWriter.Write(data);
		PGMWriter.Close();*/
		try {
			DataOutputStream writeFile = new DataOutputStream(new FileOutputStream("ressource/IMAGES" + filename));
			// Write the .pgm header (P5, 800 600, 256)
			writeFile.writeUTF("P5" + "\n");
			writeFile.writeUTF(image.length + " " + image[0].length + "\n");
			writeFile.writeUTF("256" + "\n");

			for (int i = 0; i < image.length; i++) {
				for (int j = 0; j < image[0].length; j++) {
					writeFile.write(image[i][j]); //ecriture
				}
				writeFile.writeUTF(" \n"); //changer ligne
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
				}else if((j > 0) && (j < image[i].length)) {
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
		//int []parent = new int[t];

		for(int i = 0 ; i < t-s ; i++){
			valeurDuSommet[i] = Integer.MAX_VALUE ; //+ L'INFINI;

		}
		valeurDuSommet[s] = 0 ;
		for (int j = s ; j < t ; j++ ){
			for (Edge e : g.next (j)){


				if (valeurDuSommet[j] >(valeurDuSommet[e.from] + e.cost) ){ //from sommet de depart , to sommet d'arriver , cost le cout de l'arc

					valeurDuSommet[j] = (valeurDuSommet[e.from] + e.cost) ;
					// inutile : parent[j] = e.to ;
				}
			}
		}
		return valeurDuSommet;
	}

	/**
	 * fonction qui créer le graphe correspondant au tableau itr
	 * @param itr tableau d'itr
	 * @return un graph
	 */
    public  Graph tograph(int[][] itr) {
		Graph graph=new Java.GraphArrayList(itr.length*itr[0].length+1);

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
	 * fonction qui réduit la taille d’une image bas ́ee sur l’algorithmique des graphes.
	 * @param fn nom de fichier pgm
	 */
	public  void seamCarving(String fn) {
		int[][] image=readpgm(fn);
		int[][] itr=interest(image);
		Graph graph=tograph(itr);
		bellman_ford(graph,0,itr.length*itr[0].length+1);
		writepgm(itr,fn+"1");
	}


}
