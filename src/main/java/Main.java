import java.util.Scanner;

public class Main {
    public static final short MAX_PTS_VIE = 100;
    public static final short PTS_BOUCLIER = 25;
    public static final short MAX_ATTAQUE_ENNEMI = 5;
    public static final short MAX_VIE_ENNEMI = 30;
    public static final short MAX_ATTAQUE_JOUEUR = 5;
    public static final short REGENARATION_BOUCLIER_PAR_TOUR = 10;

    public static String nomPersonnage;
    public static short ptsDeVie;
    public static short ptsBouclier;
    public static short nbEnnemisTues;
    public static boolean bouclierActif = true;

    public static void main(String[] args) {
        initPersonnage();

        short[] TableauEnnemis = initEnnemis();

        short nbEnnemis = 0;

//attaque alternee
        boolean boolAttaque = true;

        //engage le combat pour chaque ennemi
        for (int i = 0; i < TableauEnnemis.length; i++) {
            //si mon personnage est mort, je sors du for
            if(ptsDeVie <= 0){ break; }
            // variable locale (de la fonction Main)
            short ptsdevieennemi = TableauEnnemis[i];
            // tant que l'ennemi ou toi n'est pas mort, on doit faire un do while
            //savoir le point de vie de l'ennemi
            System.out.println("Combat avec un ennemi possédant " + ptsdevieennemi + " points de vie !"); do {
                // ecrire dans la console les points de vie restants
                // nomPersonnage (ptsDeVie ptsBouclier) vs ennemi (pdtdevieennemi)
                ptsdevieennemi = attaque(ptsdevieennemi, boolAttaque);
                //boolAttaque = true = le joueur attaque
                if (boolAttaque == true) { boolAttaque = false; }
                //boolAttaque = false = l'ennemi attaque
                else { System.out.println(Util.color(nomPersonnage, Color.GREEN) + "(" + ptsDeVie + " " + ptsBouclier + ")" + " vs ennemi (" + ptsdevieennemi + ")"); boolAttaque = true; } } while (ptsdevieennemi > 0 && ptsDeVie > 0); if (ptsdevieennemi <= 0) { nbEnnemis++;

                System.out.println("L'ennemi est mort ! Au suivant !"); }
            if (bouclierActif && ptsBouclier <= PTS_BOUCLIER){ ptsBouclier += 10; System.out.println("Régénération du bouclier : + 10"); }
            if ( i > 0) { System.out.println("Saisisser S pour passer au combat suivant ou n'importe quoi d'autre pour fuir ...");
                Scanner scanner = new Scanner(System.in);
                String lettreSaisie = scanner.nextLine();
                if (lettreSaisie != "S" && lettreSaisie != "s") { break; } } }
        if (ptsDeVie > 0) {
            if(nbEnnemis == TableauEnnemis.length) { System.out.println(nomPersonnage + " a tué tous les ennemis !");
            }
        }
        else { System.out.print("Nombre d'ennemis vaincu :" + nbEnnemis);
        }
    }


    public static void initPersonnage() {
        System.out.println("Saisir le nom de votre personnage");
        Scanner scanner = new Scanner(System.in);
        nomPersonnage = scanner.nextLine();
        System.out.println("OK " + Util.color(nomPersonnage, Color.GREEN) + " ! C'est parti !");
        ptsDeVie = MAX_PTS_VIE;
        ptsBouclier = bouclierActif ? PTS_BOUCLIER : 0;
    }

    public static boolean hasard(double pourcentage) {
        //pourcentage < résultat du chiffre random => true
        // sinon faux
        return pourcentage < Math.random();
    }

    public static short nombreAuHasard(short nombre) {
        return (short) Math.round(Math.random() * nombre);
    }

    public static short[] initEnnemis() {
        System.out.println("Combien souhaitez-vous combattre d'ennemis ?");
        Scanner scanner = new Scanner(System.in);
        int nbEnnemis = scanner.nextInt();
        System.out.println("Génération des ennemis...");
        short[] ennemis = new short[nbEnnemis];
        for (int i = 0; i < nbEnnemis; i++) {
            ennemis[i] = nombreAuHasard(MAX_VIE_ENNEMI);
            System.out.println("Ennemi numéro " + (i + 1) + " : " + Util.color(ennemis[i], Color.PURPLE));
        }
        return ennemis;
    }


    public static short attaqueJoueur(short ptsVieEnnemi) {
        //Déterminer la force de l'attaque du joueur
        short forceAttaque = nombreAuHasard(MAX_ATTAQUE_JOUEUR);
        //Retrancher les points de l'attaque
        ptsVieEnnemi -= forceAttaque;
        //Afficher les caractéristiques
        System.out.println(Util.color(nomPersonnage, Color.GREEN) + " attaque l'"
                + Util.color("ennemi", Color.YELLOW) + " ! Il lui fait perdre "
                + Util.color(forceAttaque, Color.PURPLE) + " points de dommages");
//Retourner le nombre de points de vie de l'ennemi après l'attaque
        return ptsVieEnnemi;
    }

    public static void afficherPersonnage() {
        System.out.print(Util.color(nomPersonnage, Color.GREEN) + " (" + Util.color(ptsDeVie, Color.RED));
        if (bouclierActif) {
            System.out.print(" " + Util.color(ptsBouclier, Color.BLUE));
        }
        System.out.print(")");
    }

    public static void attaqueEnnemi() {
        // Déterminer la force de l'attaque
        short forceAttaqueEnnemi = nombreAuHasard(MAX_ATTAQUE_ENNEMI);
        // Afficher le début de la description de l'attaque
        System.out.print(Util.color("L'ennemi ", Color.YELLOW) + "attaque " + Util.color(nomPersonnage, Color.GREEN));
        System.out.print(" ! Il lui fait perdre " + Util.color(forceAttaqueEnnemi, Color.PURPLE) + " points de dommages ! ");
        // Le bouclier absorbe les dégats en premier
        if (ptsBouclier > 0) {
            if (ptsBouclier >= forceAttaqueEnnemi) {
                ptsBouclier -= forceAttaqueEnnemi;
                System.out.print("Le bouclier perd " + Util.color(forceAttaqueEnnemi, Color.BLUE) + " points. ");
            } else {
                forceAttaqueEnnemi -= ptsBouclier;
                ptsBouclier = 0;
            }
        }
        // Les points de vie du joueur absorbent le reste
        if (forceAttaqueEnnemi > 0) {
            ptsDeVie -= forceAttaqueEnnemi;
            System.out.println(Util.color(nomPersonnage, Color.GREEN) + " perd " +
                    Util.color(forceAttaqueEnnemi, Color.RED) + " points de vie !");
        }
    }

    public static short attaque(short ennemi, boolean joueurAttaque) {
        //verifier si l 'un des deux combatants est mort=> si oui,on ne fait aucune attaque
        if (ptsDeVie <= 0 || ennemi <= 0) {
            return ennemi;
        }
        //On va faire attaquer le joueur si c'est à lui d'attaquer
        if (joueurAttaque) {
            ennemi = attaqueJoueur(ennemi);
        } else {
            //Sinon,on fait atttaquer l'ennemi
            attaqueEnnemi();
        }
        return ennemi;
    }

    public static void afficherEnnemi() {
        System.out.print(Util.color(nomPersonnage, Color.YELLOW) + " (" + Util.color(ptsDeVie, Color.RED));
        if (bouclierActif) {
            System.out.print(" " + Util.color(ptsBouclier, Color.BLUE));
        }
        System.out.print(")");
    }
}




