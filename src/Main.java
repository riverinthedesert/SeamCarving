import Java.SeamCarving;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        seamCarving();
    }

    /**
     * fonction qui permet choisir tester quel image
     */
    public static void seamCarving(){
        SeamCarving seamCarving=new SeamCarving();
        System.out.println("entre ce que tu veux faire\n 1: je veux tester char.pgm (apr default)\n 2: je veux test le image dehors");
        Scanner scan = new Scanner(System.in);
        if (scan.hasNext()) {
            String str2 = scan.next();
            if(str2.equals("1")){
                seamCarving.seamCarving("ressource/IMAGES/chat.pgm");
            }else {
                if (str2.equals("2")) {
                    System.out.println("entre le image ce que tu veux tranform");
                    Scanner scan2 = new Scanner(System.in);
                    if (scan2.hasNext()) {
                        String str1 = scan2.next();
                        seamCarving.seamCarving("ressource/IMAGES/" + str1);
                    }
                    scan2.close();
                } else {
                    System.out.println("entrer le numéro 1 ou 2!");
                }
            }
            scan.close();
        }


    }
}
