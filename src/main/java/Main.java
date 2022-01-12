import java.util.Scanner;

public class Main {
    public static final short MAX_PTS_VIE = 100;
    public static final short PTS_BOUCLIER = 25;
    public static final short MAX_ATTAQUE_ENNEMI= 5;
    public static final short MAX_VIE_ENNEMI = 30;
    public static final short MAX_ATTAQUE_JOUEUR = 5;
    public static final short REGENARATION_BOUCLIER_PAR_TOUR = 10;

    public static String nomPersonnage ;
    public static short ptsDeVie ;
    public static short ptsBouclier ;
    public static short nbEnnemisTues ;
    public static boolean bouclierActif = true;

    public static void main(String[] args) {
       initPersonnage();
       short ennemi = 5;
       ennemi =attaquejoueur(ennemi);
       System.out.println(" il reste "+ennemi+ " point de vie de l'ennemi ");
    }


    public static void initPersonnage(){
        System.out.println("Quel est votre nom?");
        Scanner scanner = new Scanner(System.in);
        nomPersonnage = scanner.nextLine();
        System.out.println("Ok " + Util.color(nomPersonnage,Color.GREEN) + " C'est Parti!");
        ptsDeVie = MAX_PTS_VIE;
        ptsBouclier = bouclierActif ? PTS_BOUCLIER: 0;
        scanner.close();
    }

    public static boolean hasard (double pourcentage) {
        return pourcentage < Math.random();

    }
public static short nombreAuHasard (short nombre) {
    return (short) Math.round(Math.random() * nombre);
}
public static short attaquejoueur (short ptsVieEnnemi) {
    short forceAttaque = nombreAuHasard(MAX_ATTAQUE_JOUEUR);
    ptsVieEnnemi -= forceAttaque ;
    System.out.println (Util.color (nomPersonnage, Color.GREEN)
            + " attaque l'" +Util.color("ennemie!",Color.YELLOW) +
            " il lui fait perdre " +Util.color (forceAttaque , Color.BLUE ) +
            " Points de dommages ");
    return ptsVieEnnemi;
    }
}




