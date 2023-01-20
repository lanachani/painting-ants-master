package org.polytechtours.javaperformance.tp.paintingants;
// package PaintingAnts_v3;
// version : 4.0

import java.awt.*;
import java.util.Random;

public class CFourmi {
    // Tableau des incrémentations à effectuer sur la position des fourmis
    // en fonction de la direction du deplacement
    static private final int[][] mIncDirection = new int[8][2];
    // le generateur aléatoire (Random est thread safe donc on la partage)
    private static final Random GenerateurAleatoire = new Random();
    // couleur déposé par la fourmi
    private final Color mCouleurDeposee;
    private final float mLuminanceCouleurSuivie;
    // objet graphique sur lequel les fourmis peuvent peindre
    private final CPainting mPainting;
    // Coordonées de la fourmi
    private int x, y;
    // Proba d'aller a gauche, en face, a droite, de suivre la couleur
    private final float[] mProba = new float[4];
    // Numéro de la direction dans laquelle la fourmi regarde
    private int mDirection;
    // Taille de la trace de phéromones déposée par la fourmi
    private final int mTaille;
    // Pas d'incrémentation des directions suivant le nombre de directions
    // allouées à la fourmies
    private final int mDecalDir;
    // l'applet
    private final PaintingAnts mApplis;
    // seuil de luminance pour la détection de la couleur recherchée
    private final float mSeuilLuminance;
    // nombre de déplacements de la fourmi
    private long mNbDeplacements;

    /**
     * Creates a new instance of CFourmi.
     *
     * @param pProbaTD     Probability to move forward.
     * @param pProbaG      Probability to turn left.
     * @param pProbaD      Probability to turn right.
     * @param pProbaSuivre Probability to be followed.
     */
    public CFourmi(Color pCouleurDeposee, float pProbaTD, float pProbaG, float pProbaD,
                   float pProbaSuivre, CPainting pPainting, char pTypeDeplacement, int pInitDirection,
                   int pTaille, float pSeuilLuminance, PaintingAnts pApplis) {

        mCouleurDeposee = pCouleurDeposee;
        mLuminanceCouleurSuivie = 0.2426f * pCouleurDeposee.getRed() + 0.7152f * pCouleurDeposee.getGreen()
                + 0.0722f * pCouleurDeposee.getBlue();
        mPainting = pPainting;
        mApplis = pApplis;

        // direction de départ
        mDirection = pInitDirection;

        // taille du trait
        mTaille = pTaille;

        // initialisation des probas
        mProba[0] = pProbaG; // proba d'aller à gauche
        mProba[1] = pProbaTD; // proba d'aller tout droit
        mProba[2] = pProbaD; // proba d'aller à droite
        mProba[3] = pProbaSuivre; // proba de suivre la couleur

        // nombre de directions pouvant être prises : 2 types de déplacement
        // possibles
        if (pTypeDeplacement == 'd') {
            mDecalDir = 2;
        } else {
            mDecalDir = 1;
        }

        // initialisation du tableau des directions
        CFourmi.mIncDirection[0][0] = 0;
        CFourmi.mIncDirection[0][1] = -1;
        CFourmi.mIncDirection[1][0] = 1;
        CFourmi.mIncDirection[1][1] = -1;
        CFourmi.mIncDirection[2][0] = 1;
        CFourmi.mIncDirection[2][1] = 0;
        CFourmi.mIncDirection[3][0] = 1;
        CFourmi.mIncDirection[3][1] = 1;
        CFourmi.mIncDirection[4][0] = 0;
        CFourmi.mIncDirection[4][1] = 1;
        CFourmi.mIncDirection[5][0] = -1;
        CFourmi.mIncDirection[5][1] = 1;
        CFourmi.mIncDirection[6][0] = -1;
        CFourmi.mIncDirection[6][1] = 0;
        CFourmi.mIncDirection[7][0] = -1;
        CFourmi.mIncDirection[7][1] = -1;

        mSeuilLuminance = pSeuilLuminance;
        mNbDeplacements = 0;
    }

    /**
     * Moves the ant.
     */
    public synchronized void deplacer() {
        float tirage, prob1, prob2, prob3, total;
        int[] dir = new int[3];
        int i, j;
        Color lCouleur;

        mNbDeplacements++;

        // le tableau dir contient 0 si la direction concernée ne contient pas la
        // couleur
        // à suivre, et 1 sinon (dir[0]=gauche, dir[1]=tt_droit, dir[2]=droite)
        i = modulo(x + CFourmi.mIncDirection[modulo(mDirection - mDecalDir, 8)][0], mPainting.getLargeur());
        j = modulo(y + CFourmi.mIncDirection[modulo(mDirection - mDecalDir, 8)][1], mPainting.getHauteur());
        if (mApplis.mBaseImage != null) {
            lCouleur = new Color(mApplis.mBaseImage.getRGB(i, j));
        } else {
            lCouleur = new Color(mPainting.getCouleur(i, j).getRGB());
        }
        if (testCouleur(lCouleur)) {
            dir[0] = 1;
        }

        i = modulo(x + CFourmi.mIncDirection[mDirection][0], mPainting.getLargeur());
        j = modulo(y + CFourmi.mIncDirection[mDirection][1], mPainting.getHauteur());
        if (mApplis.mBaseImage != null) {
            lCouleur = new Color(mApplis.mBaseImage.getRGB(i, j));
        } else {
            lCouleur = new Color(mPainting.getCouleur(i, j).getRGB());
        }
        if (testCouleur(lCouleur)) {
            dir[1] = 1;
        }
        i = modulo(x + CFourmi.mIncDirection[modulo(mDirection + mDecalDir, 8)][0], mPainting.getLargeur());
        j = modulo(y + CFourmi.mIncDirection[modulo(mDirection + mDecalDir, 8)][1], mPainting.getHauteur());
        if (mApplis.mBaseImage != null) {
            lCouleur = new Color(mApplis.mBaseImage.getRGB(i, j));
        } else {
            lCouleur = new Color(mPainting.getCouleur(i, j).getRGB());
        }
        if (testCouleur(lCouleur)) {
            dir[2] = 1;
        }

        // tirage d'un nombre aléatoire permettant de savoir si la fourmi va suivre
        // ou non la couleur
        tirage = GenerateurAleatoire.nextFloat();// Math.random();

        // la fourmi suit la couleur
        int sommeDir = dir[0] + dir[1] + dir[2];
        if (((tirage <= mProba[3]) && (sommeDir > 0)) || (sommeDir == 3)) {
            prob1 = (dir[0]) * mProba[0];
            prob2 = (dir[1]) * mProba[1];
            prob3 = (dir[2]) * mProba[2];
        }
        // la fourmi ne suit pas la couleur
        else {
            prob1 = (1 - dir[0]) * mProba[0];
            prob2 = (1 - dir[1]) * mProba[1];
            prob3 = (1 - dir[2]) * mProba[2];
        }
        total = prob1 + prob2 + prob3;
        prob1 = prob1 / total;
        prob2 = prob2 / total + prob1;

        // incrémentation de la direction de la fourmi selon la direction choisie
        tirage = GenerateurAleatoire.nextFloat();// Math.random();
        if (tirage < prob1) {
            mDirection = modulo(mDirection - mDecalDir, 8);
        } else {
            if (tirage >= prob2) {
                mDirection = modulo(mDirection + mDecalDir, 8);
            }
        }

        x += CFourmi.mIncDirection[mDirection][0];
        y += CFourmi.mIncDirection[mDirection][1];

        x = modulo(x, mPainting.getLargeur());
        y = modulo(y, mPainting.getHauteur());

        // coloration de la nouvelle position de la fourmi
        mPainting.setCouleur(x, y, mCouleurDeposee, mTaille);

        mApplis.IncrementFpsCounter();
    }

    /**
     * Gets the ant's number of movements.
     *
     * @return nombre de déplacement
     */
    public long getNbDeplacements() {
        return mNbDeplacements;
    }

    /**
     * Gets the coordinate X of the ant.
     *
     * @return X
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the coordinate Y of the ant.
     *
     * @return Y
     */
    public int getY() {
        return y;
    }

    /**
     * Allows the ant to reappear on the other side of the canvas
     * when it leaves it.
     *
     * @return modulo de x par m
     */
    private int modulo(int x, int m) {
        return (x + m) % m;
    }

    /**
     * Tests the equality of the color with the followed one.
     *
     * @return True if the color is equal to the followed one, else false.
     */
    private boolean testCouleur(Color pCouleur) {
        boolean lReponse = false;
        float lLuminance;

        /* on calcule la luminance */
        lLuminance = 0.2426f * pCouleur.getRed() + 0.7152f * pCouleur.getGreen() + 0.0722f * pCouleur.getBlue();

        /* test */
        if (Math.abs(mLuminanceCouleurSuivie - lLuminance) < mSeuilLuminance) {
            lReponse = true;
            // System.out.print(x);
        }

        return lReponse;
    }
}
