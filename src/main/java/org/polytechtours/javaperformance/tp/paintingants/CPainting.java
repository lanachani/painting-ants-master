package org.polytechtours.javaperformance.tp.paintingants;
// package PaintingAnts_v2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Painting Ants.
 *
 * @author Nicolas Monmarché
 * @version 1.0
 */
public class CPainting extends Canvas implements MouseListener {
    private static final long serialVersionUID = 1L;
    // Objet de type Graphics permettant de manipuler l'affichage du Canvas
    private Graphics mGraphics;
    // Objet ne servant que pour les bloc synchronized pour la manipulation du
    // tableau des couleurs
    private final Object mMutexCouleurs = new Object();
    // tableau des couleurs, il permert de conserver en memoire l'état de chaque
    // pixel du canvas, ce qui est necessaire au deplacemet des fourmi
    // il sert aussi pour la fonction paint du Canvas
    private final Color[][] mCouleurs;
    // couleur du fond
    private final Color mCouleurFond = new Color(255, 255, 255);
    // dimensions
    private final Dimension mDimension;


    private final PaintingAnts mApplis;

    private boolean mSuspendu = false;

    /**
     * Class constructor.
     */
    public CPainting(Dimension pDimension, PaintingAnts pApplis) {
        int i, j;
        addMouseListener(this);

        mApplis = pApplis;

        mDimension = pDimension;
        setBounds(new Rectangle(0, 0, mDimension.width, mDimension.height));

        this.setBackground(mCouleurFond);

        // initialisation de la matrice des couleurs
        mCouleurs = new Color[mDimension.width][mDimension.height];
        synchronized (mMutexCouleurs) {
            for (i = 0; i != mDimension.width; i++) {
                for (j = 0; j != mDimension.height; j++) {
                    mCouleurs[i][j] = new Color(mCouleurFond.getRed(), mCouleurFond.getGreen(), mCouleurFond.getBlue());
                }
            }
        }
    }

    /**
     * @return Color of the cell art (x, y).
     */
    public Color getCouleur(int x, int y) {
        synchronized (mMutexCouleurs) {
            return mCouleurs[x][y];
        }
    }

    /**
     * @return Painting height.
     */
    public int getHauteur() {
        return mDimension.height;
    }

    /**
     * @return Painting width.
     */
    public int getLargeur() {
        return mDimension.width;
    }

    /**
     * Initialise le fond à la couleur blanche
     * et initialise le tableau des couleurs avec la couleur blanche.
     */
    public void init() {
        int i, j;
        mGraphics = getGraphics();
        synchronized (mMutexCouleurs) {
            mGraphics.clearRect(0, 0, mDimension.width, mDimension.height);

            // initialisation de la matrice des couleurs
            for (i = 0; i != mDimension.width; i++) {
                for (j = 0; j != mDimension.height; j++) {
                    mCouleurs[i][j] = new Color(mCouleurFond.getRed(), mCouleurFond.getGreen(), mCouleurFond.getBlue());
                }
            }
        }
        Convolution conv = new Convolution();
        conv.MatriceConv9();
        conv.MatriceConv25();
        conv.MatriceConv49();
        mSuspendu = false;
    }

    /**
     * Action done when we click.
     */
    public void mouseClicked(MouseEvent pMouseEvent) {
        pMouseEvent.consume();
        if (pMouseEvent.getButton() == MouseEvent.BUTTON1) {
            // double clic sur le bouton gauche = effacer et recommencer
            if (pMouseEvent.getClickCount() == 2) {
                init();
            }
            // simple clic = suspendre les calculs et l'affichage
            mApplis.pause();
        } else {
            // bouton du milieu (roulette) = suspendre l'affichage mais
            // continuer les calculs
            if (pMouseEvent.getButton() == MouseEvent.BUTTON2) {
                suspendre();
            } else {
                // clic bouton droit = effacer et recommencer
                // case pMouseEvent.BUTTON3:
                init();
            }
        }
    }

    public void mouseEntered(MouseEvent pMouseEvent) {
    }

    public void mouseExited(MouseEvent pMouseEvent) {
    }

    public void mousePressed(MouseEvent pMouseEvent) {

    }

    public void mouseReleased(MouseEvent pMouseEvent) {
    }


    /**
     * Override the function called when the component must be drawn again
     */
    @Override
    public void paint(Graphics pGraphics) {
        int i, j;

        synchronized (mMutexCouleurs) {
            for (i = 0; i < mDimension.width; i++) {
                for (j = 0; j < mDimension.height; j++) {
                    pGraphics.setColor(mCouleurs[i][j]);
                    pGraphics.fillRect(i, j, 1, 1);
                }
            }
        }
    }

    /**
     * Colors the corresponding pixel and update the table of colors.
     */
    public void setCouleur(int x, int y, Color c, int pTaille) {
        Convolution conv = new Convolution();

        synchronized (mMutexCouleurs) {
            if (!mSuspendu) {
                // on colorie la case sur laquelle se trouve la fourmi
                mGraphics.setColor(c);
                mGraphics.fillRect(x, y, 1, 1);
            }

            mCouleurs[x][y] = c;

            // on fait diffuser la couleur :
            switch (pTaille) {
                case 0:
                    // on ne fait rien = pas de diffusion
                    break;
                case 1:
                    conv.prodConv9(x, y, mDimension, mCouleurs, mGraphics, mSuspendu);
                    break;
                case 2:
                    conv.prodConv25(x, y, mDimension, mCouleurs, mGraphics, mSuspendu);
                    break;
                case 3:
                    conv.prodConv49(x, y, mDimension, mCouleurs, mGraphics, mSuspendu);
                    break;
            }// end switch
        }
    }

    /**
     * Changes de suspension state.
     */
    public void suspendre() {
        mSuspendu = !mSuspendu;
        if (!mSuspendu) {
            repaint();
        }
    }
}
