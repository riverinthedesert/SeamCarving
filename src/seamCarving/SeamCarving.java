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


}
