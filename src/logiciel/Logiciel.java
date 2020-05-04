package logiciel;

import Java.SeamCarving;

public class Logiciel {
    public static void main(String[] args){
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
        seamCarving.seamCarving(args[0],nbPixelASuppr);
    }
}
