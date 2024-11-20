import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;

public class Sudoku {
    //.........................................................................
    // Fonctions utiles
    //.........................................................................


    /** pré-requis : min <= max
     *  résultat :   un entier saisi compris entre min et max, avec re-saisie éventuelle jusqu'à ce qu'il le soit
     */
    public static int saisirEntierMinMax(int min, int max){
        int n = Ut.lireProchainEntier();
        while (n < min || n > max){
            n = Ut.lireProchainEntier();
            System.out.println("Entier invalide !");
        }
        return n;
    }  // fin saisirEntierMinMax
    //.........................................................................


    /** MODIFICI
     *  pré-requis : mat1 et mat2 ont les mêmes dimensions
     *  action : copie toutes les valeurs de mat1 dans mat2 de sorte que mat1 et mat2 soient identiques
     */
    public static void copieMatrice(int [][] mat1, int [][] mat2){
	//________________________________________________________
        int ligne = mat1.length;
        int colonne = mat1[0].length;
        for (int i = 0; i < ligne; i++){
            for (int j = 0; j < colonne; j++){
                mat2[i][j]=mat1[i][j];
            }
        }
    }  // fin copieMatrice

    //.........................................................................


    /** pré-requis :  n >= 0
     *  résultat : un tableau de booléens représentant le sous-ensemble de l'ensemble des entiers 
     *             de 1 à n égal à lui-même 
     */
    public static boolean[] ensPlein(int n){
	//_____________________________________
        boolean [] ens = new boolean [n+1];
        for (int i = 0; i <= n; i++){
            ens[i]=true;
        }
        return ens;
    }// fin ensPlein

    //.........................................................................


    /** pré-requis : 1 <= val < ens.length
     *  action :     supprime la valeur val de l'ensemble représenté par ens, s'il y est
     *  résultat :   vrai ssi val était dans cet ensemble
     */
    public static boolean supprime(boolean[] ens, int val){
        if (ens[val] == true){
            ens[val] = false;
            return true;
        }
        else {
            return false;
        }
    }  // fin supprime 

    //.........................................................................


    /** pré-requis : l'ensemble représenté par ens n'est pas vide
     *  résultat :   un élément de cet ensemble
     */
    public static int uneValeur(boolean[] ens){
        int i = 1;
        while (i < ens.length) {
            if (ens[i]) {
                return i;
            }
            i++;
        }
        return -1;
    } // fin uneValeur 

    //.........................................................................

    /**
       1 2 3 4 5 6 7 8 9
       ------------------- 
       1 |6 2 9|7 8 1|3 4 5|
       2 |4 7 3|9 6 5|8 1 2|
       3 |8 1 5|2 4 3|6 9 7|
       ------------------- 
       4 |9 5 8|3 1 2|4 7 6|
       5 |7 3 2|4 5 6|1 8 9|
       6| 1 6 4|8 7 9|2 5 3|
       ------------------- 
       7 3 8 1|5 2 7|9 6 4
       8 |5 9 6|1 3 4|7 2 8|
       9 |2 4 7|6 9 8|5 3 1|
       ------------------- 

 
       1 2 3 4 5 6 7 8 9
       ------------------- 
       1 |6 0 0|0 0 1|0 4 0|
       2 |0 0 0|9 6 5|0 1 2|
       3 |8 1 0|0 4 0|0 0 0|
       ------------------- 
       4 |0 5 0|3 0 2|0 7 0|
       5 |7 0 0|0 0 0|1 8 9|
       6||0 0 0|0 7 0|0 0 3|
       ------------------- 
       7 |3 0 0|0 2 0|9 0 4|
       8 |0 9 0|0 0 0|7 2 0|
       9 |2 4 0|6 9 0|0 0 0|
       ------------------- 


       * pré-requis : 0<=k<=3 et g est une grille k^2xk^2 dont les valeurs sont comprises 
       *              entre 0 et k^2 et qui est partitionnée en k sous-carrés kxk
       * action : affiche la  grille g avec ses sous-carrés et avec les numéros des lignes 
       *          et des colonnes de 1 à k^2.
       * Par exemple, pour k = 3, on obtient le dessin d'une grille de Sudoku
       *  
       */
    public static void afficheGrille(int k, int[][] g) {
        int n = k * k;
        System.out.print("   ");
        for (int i = 1; i <= n; i++) {
            if (i != 9) {
                System.out.print(i + " ");
            }
            else {
                System.out.print(i);
            }
        }
        System.out.println("\n---------------------");
        for (int i = 0; i < n; i++) {
            System.out.print((i + 1) + " |");

            for (int j = 0; j < n; j++) {
                if (g[i][j] == 0) {
                    System.out.print(" "); 
                } else {
                    System.out.print(g[i][j]);
                }
                if ((j + 1) % k == 0) {
                    System.out.print("|");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            if ((i + 1) % k == 0) {
                System.out.println("---------------------");
            }
        }
    } // fin afficheGrille

    //.........................................................................

    /** pré-requis : k > 0, 0 <= i< k^2 et 0 <= j < k^2
     *  résultat : (i,j) étant les coordonnées d'une case d'une grille k^2xk^2 partitionnée 
     *             en k sous-carrés kxk, retourne les coordonnées de la case du haut à gauche
     *             du sous-carré de la grille contenant cette case.
     *  Par exemple, si k=3, i=5 et j=7, la fonction retourne (3,6).
     */
    
    public static int[] debCarre(int k, int i, int j) {
        int[] result = new int[2];
        result[0] = (i / k) * k; 
        result[1] = (j / k) * k; 
        return result;
    }// fin debCarre


    //.........................................................................

    // Initialisation
    //.........................................................................


    /** MODIFICI
     *  pré-requis : gComplete est une matrice 9X9
     *  action   :   remplit gComplete pour que la grille de Sudoku correspondante soit complète
     *  stratégie :  les valeurs sont données directement dans le code et on peut utiliser copieMatrice pour mettre à jour gComplete
     */
    public static void initGrilleComplete(int [][] gComplete){
        int[][] new_g = {{6,2,9,7,8,1,3,4,5},
                         {4,7,3,9,6,5,8,1,2},
                         {8,1,5,2,4,3,6,9,7},
                         {9,5,8,3,1,2,4,7,6},
                         {7,3,2,4,5,6,1,8,9},
                         {1,6,4,8,7,9,2,5,3},
                         {3,8,1,5,2,7,9,6,4},
                         {5,9,6,1,3,4,7,2,8},
                         {2,4,7,6,9,8,5,3,1}};
        copieMatrice(new_g,gComplete);
    } // fin initGrilleComplete

    //.........................................................................



    /** MODIFICI
     *  pré-requis : gSecret est une grille de Sudoku complète de mêmes dimensions que gIncomplete et 0 <= nbTrous <= 81
     *  action :     modifie gIncomplete pour qu'elle corresponde à une version incomplète de la grille de Sudoku gSecret (gIncomplete peut être complétée en gSecret), 
     *               avec nbTrous trous à des positions aléatoires
     */
    public static void initGrilleIncomplete(int nbTrous, int [][] gSecret, int [][] gIncomplete){
        copieMatrice(gSecret,gIncomplete);
        int test=0;
        for (int x = 0; x < nbTrous; x++){
            test=0;
            while (test == 0){
                int i = Ut.randomMinMax(0,8);
                int j = Ut.randomMinMax(0,8);
                if (gIncomplete[i][j]!=0){
                    gIncomplete[i][j]=0;
                    test=1;
                }
            }
        }
    } // fin initGrilleIncomplete

    //.........................................................................


    /** MODIFICI
     *  pré-requis : 0 <= nbTrous <= 81 ; g est une grille 9x9 (vide a priori)
     *  action :   remplit g avec des valeurs saisies au clavier comprises entre 0 et 9
     *               avec exactement nbTrous valeurs nulles
     *               et avec re-saisie jusqu'à ce que ces conditions soient vérifiées.
     *               On suppose dans la version de base que la grille saisie est bien une grille de Sudoku incomplète.
     *  stratégie : utilise la fonction saisirEntierMinMax
    */
    public static void saisirGrilleIncomplete(int nbTrous, int [][] g) {
        int nbr_0 = -1;
        while (nbTrous != nbr_0){
            nbr_0 = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.println("Entrez la valeur pour la case [" + (i + 1) + "][" + (j + 1) + "] (0 pour un trou) : ");
                    g[i][j] = Ut.lireProchainEntier();
                    while (g[i][j] < 0 || g[i][j] > 9) {
                        System.out.println("Veuillez entrer un nombre valide entre 0 et 9 : ");
                        g[i][j] = Ut.lireProchainEntier();
                    }
                    if (g[i][j] == 0) {
                        nbr_0++;
                    }
                }
            }
            if (nbr_0 != nbTrous) {
                System.out.println("Nombre incorrect de trous (" + nbr_0 + "), veuillez réessayer.");
            }
        }  
    } // fin saisirGrilleIncomplete

    //.........................................................................



    /** pré-requis : gOrdi est une grille de Sudoku incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action : met dans valPossibles l'ensemble des entiers de 1 à 9 pour chaque trou de gOrdi
     *           et leur nombre dans nbValPoss       
     */
    public static void initPleines(int[][] gOrdi, boolean[][][] valPossibles, int[][] nbValPoss) {
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (gOrdi[i][j] == 0){
                    valPossibles[i][j] = ensPlein(9);
                    nbValPoss[i][j] = 9;
                }
            }
        }
    } // fin initPleines

    //.........................................................................


    /** pré-requis : gOrdi est une grille de Sudoku incomplète,
     *               0<=i<9, 0<=j<9,g[i][j]>0,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action : supprime dans les matrices valPossibles et nbValPoss la valeur gOrdi[i][j] pour chaque case de la ligne,
     *           de la colonne et du carré contenant la case (i,j) correspondant à un trou de gOrdi.
     */
    public static void suppValPoss(int[][] gOrdi, int i, int j, boolean[][][] valPossibles, int[][] nbValPoss) {
        int val = gOrdi[i][j];
    
        for (int y = 0; y < 9; y++) {
            if (gOrdi[i][y] == 0 && valPossibles[i][y][val]) {
                valPossibles[i][y][val] = false;
                nbValPoss[i][y]--;
            }
        }
    
        for (int x = 0; x < 9; x++) {
            if (gOrdi[x][j] == 0 && valPossibles[x][j][val]) {
                valPossibles[x][j][val] = false;
                nbValPoss[x][j]--;
            }
        }
    
        int[] debutCarre = debCarre(3, i, j);
        for (int x = debutCarre[0]; x < debutCarre[0] + 3; x++) {
            for (int y = debutCarre[1]; y < debutCarre[1] + 3; y++) {
                if (gOrdi[x][y] == 0 && valPossibles[x][y][val]) {
                    valPossibles[x][y][val] = false;
                    nbValPoss[x][y]--;
                }
            }
        }
    }

// fin suppValPoss


    //.........................................................................

    /** pré-requis : gOrdi est une grille de Sudoju incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     * action :      met dans valPossibles l'ensemble des valeurs possibles de chaque trou de gOrdi
     *               et leur nombre dans nbValPoss       
     */

    public static void initPossibles(int[][] gOrdi, boolean[][][] valPossibles, int[][] nbValPoss) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                valPossibles[i][j] = ensPlein(9);
                nbValPoss[i][j] = 9;
            }
        }
    
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gOrdi[i][j] != 0) {
                    suppValPoss(gOrdi,i,j,valPossibles,nbValPoss);
                }
            }
        }
    }// fin initPossibles



 // fin initPossibles

    //.........................................................................


    /** pré-requis : gSecret, gHumain et gOrdi sont des grilles 9x9
     *  action :     demande au joueur humain de saisir le nombre nbTrous compris entre 0 et 81,
     *               met dans gSecret une grille de Sudoku complète,
     *               met dans gHumain une grille de Sudoku incomplète, pouvant être complétée en gSecret
     *               et ayant exactement nbTrous trous de positions aléatoires,
     *               met dans gOrdi une grille de Sudoku incomplète saisie par le joueur humain
     *               ayant  nbTrous trous,
     *               met dans valPossibles l'ensemble des valeurs possibles de chaque trou de gOrdi
     *               et leur nombre dans nbValPoss.
     * retour : la valeur de nbTrous
     */
    public static int initPartie(int [][] gSecret, int [][] gHumain, int [][] gOrdi,
				 boolean[][][] valPossibles, int [][]nbValPoss){
                    
        int nbTrous = saisirEntierMinMax(0,81);
        if (nbTrous != 0){            
            initGrilleComplete(gSecret);
            initGrilleIncomplete(nbTrous,gSecret,gHumain);
            saisirGrilleIncomplete(nbTrous,gOrdi);
            while (contientDesDoublons(gOrdi)){
                saisirGrilleIncomplete(nbTrous,gOrdi);
            }
            initPossibles(gOrdi, valPossibles, nbValPoss);
        }
        return nbTrous;
    }  // fin initPartie

    //...........................................................
    // Tour du joueur humain
    //...........................................................

    /** pré-requis : gHumain est une grille de Sudoju incomplète pouvant se compléter en 
     *               la  grille de Sudoku complète gSecret
     *
     *  résultat :   le nombre de points de pénalité pris par le joueur humain pendant le tour de jeu
     *
     *  action :     effectue un tour du joueur humain   
     */
    public static int tourHumain(int [][] gSecret, int [][] gHumain){
        int penalite = 0;
        int i = saisirEntierMinMax(0,8);
        int j = saisirEntierMinMax(0,8);
        while (gHumain[i][j]!=0){
            i = saisirEntierMinMax(0,8);
            j = saisirEntierMinMax(0,8);
        }
        while (gHumain[i][j]!=gSecret[i][j]){
            int chiffre = saisirEntierMinMax(0,9);
            if (chiffre == 0){
                gHumain[i][j]=gSecret[i][j];
                penalite++;
            }
            else if (chiffre == gSecret[i][j]){
                gHumain[i][j]=chiffre;
            } 
            else{
                penalite++;
            }
        }
        return penalite;
    }  // fin  tourHumain

    //.........................................................................

    // Tour de l'ordinateur
    //.........................................................................

    /** pré-requis : gOrdi et nbValPoss sont des matrices 9x9
     *  résultat :   le premier trou (i,j) de gOrdi (c'est-à-dire tel que gOrdi[i][j]==0)
     *               évident (c'est-à-dire tel que nbValPoss[i][j]==1) dans l'ordre des lignes,
     *                s'il y en a, sinon le premier trou de gOrdi dans l'ordre des lignes
     * 
     */
    public static int[] chercheTrou(int[][] gOrdi, int[][] nbValPoss) {
        boolean trouTrouve = false;
        int i = 0;
        int j = 0;
        while (i < 9 && trouTrouve==false){
            j=0;
            while (j < 9 && trouTrouve==false){
                if (nbValPoss[i][j]==1){
                    trouTrouve=true;
                }
                j++;
            }
            i++;
        }
        if (nbValPoss[i-1][j-1]==1){
            int[] result={i-1,j-1};
            return result;
        }
        i=0;
        j=0;
        while (i < 9 && trouTrouve==false){
            j=0;
            while (j < 9 && trouTrouve==false){
                if (gOrdi[i][j]==0){
                    trouTrouve=true;
                }
                j++;
            }
            i++;
        }
        int[] result={i-1,j-1};
        return result;
    } // fin  ChercheTrou

    //.........................................................................

    /** pré-requis : gOrdi est une grille de Sudoju incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action :     effectue un tour de l'ordinateur      
     */
    public static int tourOrdinateur(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){

        int[] trou = chercheTrou(gOrdi,nbValPoss);
        if (nbValPoss[trou[0]][trou[1]]==1){
            int i = 1;
            while (valPossibles[trou[0]][trou[1]][i]!=true){
                i++;
            }
            gOrdi[trou[0]][trou[1]]=i;
            return 0;
        }
        int result = saisirEntierMinMax(1,9);
        if (jockerNonValide(result, valPossibles, trou)){
            System.out.println("Tricheur ! Vous avez perdu !");
        }
        gOrdi[trou[0]][trou[1]]=result;
        return 1;
    }  // fin tourOrdinateur

    //.........................................................................

    // Partie
    //.........................................................................



    /** pré-requis : aucun
     *  action :     effectue une partie de Sudoku entre le joueur humain et l'ordinateur
     *  résultat :   0 s'il y a match nul, 1 si c'est le joueur humain qui gagne et 2 sinon
     */
    public static int partie(){
        int[][] gSecret = new int[9][9];
        int[][] gHumain = new int[9][9];
        int[][] gOrdi = new int[9][9];
        boolean[][][] valPossibles = new boolean[9][9][10];
        int[][] nbValPoss = new int [9][9];
        int nbTrous = initPartie(gSecret,gHumain,gOrdi,valPossibles,nbValPoss);
        int penaHumain = 0;
        int penaOrdi = 0;
        int penaTour = 0;
        for (int i = 0; i < nbTrous; i++){
            penaHumain = penaHumain + tourHumain(gSecret,gHumain);        
            initPossibles(gOrdi, valPossibles, nbValPoss);
            penaTour = tourOrdinateur(gOrdi,valPossibles,nbValPoss);
            if (penaTour == -1){
                return 2;
            }
            penaOrdi = penaOrdi + penaTour;
            if (contientUnTrouSansValeurPossible(gOrdi,nbValPoss)){
                return 2;
            }
        }
        if (penaHumain < penaOrdi){
            return 1;
        }
        else if (penaOrdi < penaHumain){
            return 2;
        }
        return 0;
    }  // fin partie


/** pré-requis : une grille de sudoku incomplete
     *  résultat :   vrai si il y a au moins 2 fois le même chiffre (à part 0) sur une 
     *               même ligne, colonne ou zone sinon renvoit faux
     */

    public static boolean contientDesDoublons(int[][] grille) {
        for (int i = 0; i < 9; i++) {
            boolean[] test = new boolean[10];
            for (int j = 0; j < 9; j++) {
                int valeur = grille[i][j];
                if (valeur != 0) {
                    if (test[valeur]) {
                        return true; 
                    }
                    test[valeur] = true;
                }
            }
        }
    
        for (int j = 0; j < 9; j++) {
            boolean[] test = new boolean[10];
            for (int i = 0; i < 9; i++) {
                int valeur = grille[i][j];
                if (valeur != 0) {
                    if (test[valeur]) {
                        return true; 
                    }
                    test[valeur] = true;
                }
            }
        }
    
        for (int ligne = 0; ligne < 9; ligne += 3) {
            for (int colonne = 0; colonne < 9; colonne += 3) {
                boolean[] test = new boolean[10];
                for (int i = ligne; i < ligne + 3; i++) {
                    for (int j = colonne; j < colonne + 3; j++) {
                        int valeur = grille[i][j];
                        if (valeur != 0) {
                            if (test[valeur]) {
                                return true; 
                            }
                            test[valeur] = true;
                        }
                    }
                }
            }
        }
    
        return false;
    }
    /** pré-requis : une grille de sudoku incomplète 
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  résultat :   vrai si il y a un trou avec aucune valeur possible, sinon faux
     */
    public static boolean contientUnTrouSansValeurPossible(int[][] grille, int[][] nbValeursPossibles) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grille[i][j] == 0 && nbValeursPossibles[i][j] == 0) {
                    return true; 
                }
            }
        }
        return false; 
    }


    /** pré-requis : joker est un entier compris entre 1 et 9
     *               valPossibles est une matrice 9x9 de tableau de 10 booléens 
     *               et trou est un tableau de 2 entiers
     *  résultat :   vrai si le joker n'est pas une valeur possible à la case trou, 
     *               sinon faux
     */
    public static boolean jockerNonValide(int joker, boolean[][][] valPossibles, int[] trou ) {
        if (valPossibles[trou[0]][trou[1]][joker]==false){
            return true;
        }
        return false;
    }
    
    

    //.........................................................................


    /** pré-requis : aucun
     *  action :     effectue une partie de Sudoku entre le joueur humain et l'ordinateur
     *               et affiche qui a gagné
     */
    public static void main(String[] args){
    	System.out.println("Bienvenue dans le jeu de Sudoku!");

        int resultatPartie = partie();

        if (resultatPartie == 1) {
            System.out.println("Félicitations! Vous avez gagné!");
        } else if (resultatPartie == 2) {
            System.out.println("Dommage! L'ordinateur a gagné!");
        } else if (resultatPartie == 0) {
            System.out.println("Match nul!");
        } else {
            System.out.println("Une erreur est survenue pendant la partie.");
        }
    }  // fin main
} // fin SudokuBase
