package Java;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
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

    public void writepgm(int[][] image, String filename) throws  IOException{
		DataOutputStream writeFile = new DataOutputStream(new FileOutputStream(filename));

		// Write the .pgm header (P5, 800 600, 250)
		String type="pgm",width="600",height="600",max="250";

	//	writeFile.writeUTF(type + "\n");
	//	writeFile.writeUTF(width + "  " + height + "\n");
	//	writeFile.writeUTF(max + "\n");
		for(int i = 0; i < Integer.parseInt(height); i++){
			for(int j = 0; j < Integer.parseInt(width); j++){
				writeFile.write(image[i][j]);
			//	writeFile.writeByte(image[i][j]); //Write the number
			//	writeFile.writeUTF(" "); //Add white space
			}
	//		writeFile.writeUTF(" \n"); //finished one line so drop to next
		}
		writeFile.close();
		//　
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

}
