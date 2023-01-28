package org.polytechtours.javaperformance.tp.paintingants;

import java.awt.*;
import java.util.List;
import java.util.StringTokenizer;

public class Parameters extends java.applet.Applet {
    private String lChaine;
    private int R, G, B;
    private CFourmi lFourmi;
    private float lProbaTD, lProbaG, lProbaD, lProbaSuivre, lSeuilLuminance;
    private char lTypeDeplacement;
    private int lInitDirection, lTaille;
    private float lInit_x, lInit_y;
    private int lNbFourmis = -1;

    /**
     * @return Parameter information applet.
     */
    @Override
    public String[][] getParameterInfo() {
        return new String[][]{{"SeuilLuminance", "string", "Seuil de luminance"}, {"Img", "string", "Image"},
                {"NbFourmis", "string", "Nombre de fourmis"}, {"Fourmis", "string",
                "Paramètres des fourmis (RGB_déposée)(RGB_suivie)(x,y,direction,taille)(TypeDeplacement,ProbaG,ProbaTD,ProbaD,ProbaSuivre);...;"}};
    }

    /**
     * Analyse a sequence of characters.
     * @return pStr if it is a number, else a random value between x and y.
     */
    private float readFloatParameter(String pStr) {
        float lMin, lMax, lResult;
        // System.out.println(" chaine pStr: "+pStr);
        StringTokenizer lStrTok = new StringTokenizer(pStr, ":");
        // on lit une premiere valeur
        lMin = Float.parseFloat(lStrTok.nextToken());
        // System.out.println(" lMin: "+lMin);
        lResult = lMin;
        // on essaye d'en lire une deuxieme
        try {
            lMax = Float.parseFloat(lStrTok.nextToken());
            // System.out.println(" lMax: "+lMax);
            if (lMax > lMin) {
                // on choisit un nombre entre lMin et lMax
                lResult = (float) (Math.random() * (lMax - lMin)) + lMin;
            }
        } catch (java.util.NoSuchElementException e) {
            // il n'y pas de deuxieme nombre et donc le nombre retourné correspond au
            // premier nombre
        }
        return lResult;
    }

    /**
     * Reads the applet's params.
     *
     * @param pStr sequence of characters.
     * @return pStr if it is a number, else a random value between x and y.
     */
    private int readIntParameter(String pStr) {
        int lMin, lMax, lResult;
        StringTokenizer lStrTok = new StringTokenizer(pStr, ":");
        // on lit une premiere valeur
        lMin = Integer.parseInt(lStrTok.nextToken());
        lResult = lMin;
        // on essaye d'en lire une deuxieme
        try {
            lMax = Integer.parseInt(lStrTok.nextToken());
            if (lMax > lMin) {
                // on choisit un nombre entre lMin et lMax
                lResult = (int) (Math.random() * (lMax - lMin + 1)) + lMin;
            }
        } catch (java.util.NoSuchElementException e) {
            // il n'y pas de deuxieme nombre et donc le nombre retourné correspond au
            // premier nombre
        }
        return lResult;
    }

    /**
     * Create an ant from the parameters of the applet
     */
    public void createFourmisParam(PaintingAnts paint, CPainting mPainting, List<CFourmi> mColonie){
        // on affiche la chaine de parametres
        System.out.println("Paramètres:" + lChaine);

        // on va compter le nombre de fourmis dans la chaine de parametres :
        lNbFourmis = 0;
        // chaine de paramètres pour une fourmi
        StringTokenizer lSTFourmi = new StringTokenizer(lChaine, ";");
        while (lSTFourmi.hasMoreTokens()) {
            // chaine de parametres de couleur et proba
            StringTokenizer lSTParam = new StringTokenizer(lSTFourmi.nextToken(), "()");
            // lecture de la couleur déposée
            StringTokenizer lSTCouleurDeposee = new StringTokenizer(lSTParam.nextToken(), ",");
            R = readIntParameter(lSTCouleurDeposee.nextToken());
            if (R == -1) {
                R = (int) (Math.random() * 256);
            }

            G = readIntParameter(lSTCouleurDeposee.nextToken());
            if (G == -1) {
                G = (int) (Math.random() * 256);
            }
            B = readIntParameter(lSTCouleurDeposee.nextToken());
            if (B == -1) {
                B = (int) (Math.random() * 256);
            }
            Color lCouleurDeposee = new Color(R, G, B);
            System.out.print("Parametres de la fourmi " + lNbFourmis + ":(" + R + "," + G + "," + B + ")");

            // lecture de la couleur suivie
            StringTokenizer lSTCouleurSuivi = new StringTokenizer(lSTParam.nextToken(), ",");
            R = readIntParameter(lSTCouleurSuivi.nextToken());
            G = readIntParameter(lSTCouleurSuivi.nextToken());
            B = readIntParameter(lSTCouleurSuivi.nextToken());
            System.out.print("(" + R + "," + G + "," + B + ")");

            // lecture de la position de la direction de départ et de la taille de
            // la trace
            StringTokenizer lSTDeplacement = new StringTokenizer(lSTParam.nextToken(), ",");
            lInit_x = readFloatParameter(lSTDeplacement.nextToken());
            if (lInit_x < 0.0 || lInit_x > 1.0) {
                lInit_x = (float) Math.random();
            }
            lInit_y = readFloatParameter(lSTDeplacement.nextToken());
            if (lInit_y < 0.0 || lInit_y > 1.0) {
                lInit_y = (float) Math.random();
            }
            lInitDirection = readIntParameter(lSTDeplacement.nextToken());
            if (lInitDirection < 0 || lInitDirection > 7) {
                lInitDirection = (int) (Math.random() * 8);
            }
            lTaille = readIntParameter(lSTDeplacement.nextToken());
            if (lTaille < 0 || lTaille > 3) {
                lTaille = (int) (Math.random() * 4);
            }
            System.out.print("(" + lInit_x + "," + lInit_y + "," + lInitDirection + "," + lTaille + ")");

            // lecture des probas
            StringTokenizer lSTProbas = new StringTokenizer(lSTParam.nextToken(), ",");
            lTypeDeplacement = lSTProbas.nextToken().charAt(0);
            // System.out.println(" lTypeDeplacement:"+lTypeDeplacement);

            if (lTypeDeplacement != 'o' && lTypeDeplacement != 'd') {
                if (Math.random() < 0.5) {
                    lTypeDeplacement = 'o';
                } else {
                    lTypeDeplacement = 'd';
                }
            }

            lProbaG = readFloatParameter(lSTProbas.nextToken());
            lProbaTD = readFloatParameter(lSTProbas.nextToken());
            lProbaD = readFloatParameter(lSTProbas.nextToken());
            lProbaSuivre = readFloatParameter(lSTProbas.nextToken());
            // on normalise au cas ou
            float lSomme = lProbaG + lProbaTD + lProbaD;
            lProbaG /= lSomme;
            lProbaTD /= lSomme;
            lProbaD /= lSomme;

            System.out.println(
                    "(" + lTypeDeplacement + "," + lProbaG + "," + lProbaTD + "," + lProbaD + "," + lProbaSuivre + ");");

            // création de la fourmi
            lFourmi = new CFourmi(lCouleurDeposee, lProbaTD, lProbaG, lProbaD, lProbaSuivre, mPainting,
                    lTypeDeplacement, lInitDirection, lTaille, lSeuilLuminance, paint);
            mColonie.add(lFourmi);
            lNbFourmis++;
        }
    }

    /**
     * Create a random ant
     */
    public void createFourmisAlea(PaintingAnts paint, CPainting mPainting, List<CFourmi> mColonie){
        int i;
        Color[] lTabColor = new Color[lNbFourmis];
        int lColor;

        // initialisation aléatoire de la couleur de chaque fourmi
        for (i = 0; i < lNbFourmis; i++) {
            R = (int) (Math.random() * 256);
            G = (int) (Math.random() * 256);
            B = (int) (Math.random() * 256);
            lTabColor[i] = new Color(R, G, B);
        }

        // construction des fourmis
        for (i = 0; i < lNbFourmis; i++) {
            // la couleur suivie est la couleur d'une autre fourmi
            lColor = (int) (Math.random() * lNbFourmis);
            if (i == lColor) {
                lColor = (lColor + 1) % lNbFourmis;
            }

            // une chance sur deux d'avoir un déplacement perpendiculaire
            if ((float) Math.random() < 0.5f) {
                lTypeDeplacement = 'd';
            } else {
                lTypeDeplacement = 'o';
            }

            // position initiale
            lInit_x = (float) (Math.random()); // *mPainting.getLargeur()
            lInit_y = (float) (Math.random()); // *mPainting.getHauteur()

            // direction initiale
            lInitDirection = (int) (Math.random() * 8);

            // taille du trait
            lTaille = (int) (Math.random() * 4);

            // proba de déplacement :
            lProbaTD = (float) (Math.random());
            lProbaG = (float) (Math.random() * (1.0 - lProbaTD));
            lProbaD = (float) (1.0 - (lProbaTD + lProbaG));
            lProbaSuivre = (float) (0.5 + 0.5 * Math.random());

            System.out.print(
                    "Random:(" + lTabColor[i].getRed() + "," + lTabColor[i].getGreen() + "," + lTabColor[i].getBlue() + ")");
            System.out.print("(" + lTabColor[lColor].getRed() + "," + lTabColor[lColor].getGreen() + ","
                    + lTabColor[lColor].getBlue() + ")");
            System.out.print("(" + lInit_x + "," + lInit_y + "," + lInitDirection + "," + lTaille + ")");
            System.out.println(
                    "(" + lTypeDeplacement + "," + lProbaG + "," + lProbaTD + "," + lProbaD + "," + lProbaSuivre + ");");

            // création et ajout de la fourmi dans la colonie
            lFourmi = new CFourmi(lTabColor[i], lProbaTD, lProbaG, lProbaD, lProbaSuivre, mPainting,
                    lTypeDeplacement, lInitDirection, lTaille, lSeuilLuminance, paint);
            mColonie.add(lFourmi);
        }
    }

    /**
     * Reads the applet's params.
     */
    public void readParameterFourmis(PaintingAnts paint, CPainting mPainting, List<CFourmi> mColonie) {
        // Lecture des paramètres des fourmis

        // Lecture du seuil de luminance
        // <PARAM NAME="SeuilLuminance" VALUE="N">
        // N : seuil de luminance : -1 = random(2..60), x..y = random(x..y)
        lChaine = getParameter("SeuilLuminance");
        if (lChaine != null) {
            lSeuilLuminance = readFloatParameter(lChaine);
        } else {
            // si seuil de luminance n'est pas défini
            lSeuilLuminance = 40f;
        }
        System.out.println("Seuil de luminance:" + lSeuilLuminance);

        // Lecture du nombre de fourmis :
        // <PARAM NAME="NbFourmis" VALUE="N">
        // N : nombre de fourmis : -1 = random(2..6), x..y = random(x..y)
        lChaine = getParameter("NbFourmis");
        if (lChaine != null) {
            lNbFourmis = readIntParameter(lChaine);
        }
        // si le parametre NbFourmis n'est pas défini ou alors s'il vaut -1 :
        if (lNbFourmis == -1) {
            // Le nombre de fourmis est aléatoire entre 2 et 6 !
            lNbFourmis = (int) (Math.random() * 5) + 2;
        }

        lChaine = getParameter("Fourmis");
        if (lChaine != null) {
            createFourmisParam(paint, mPainting, mColonie);
        } else {
            // initialisation aléatoire des fourmis
            createFourmisAlea(paint, mPainting, mColonie);
        }
    }
}
