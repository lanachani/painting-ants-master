package org.polytechtours.javaperformance.tp.paintingants.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.polytechtours.javaperformance.tp.paintingants.CPainting;
import org.polytechtours.javaperformance.tp.paintingants.PaintingAnts;

import java.awt.*;

class CPaintingTest {

    private final int X = 2;
    private final int Y = 7;
    private final Color COULEUR_FOND = Color.WHITE;
    private final static int SIZE_DIMENSION = 10;

    private CPainting cPainting;

    private final Dimension dimension = new Dimension(SIZE_DIMENSION, SIZE_DIMENSION);

    @BeforeEach
    public void setUp() {
        PaintingAnts paintingAnts = new PaintingAnts();
        cPainting = new CPainting(dimension, paintingAnts);
    }
    @Test
    void getCouleurTest() {

        // WHEN
        Color color = cPainting.getCouleur(X, Y);

        // THEN
        Assertions.assertEquals(color, COULEUR_FOND);
    }

    @Test
    void getHauteurTest() {

        // WHEN
        int hauteur = cPainting.getHauteur();

        // THEN
        Assertions.assertEquals(hauteur, dimension.height);
    }

    @Test
    void getLargeurTest() {

        // WHEN
        int largeur = cPainting.getLargeur();

        // THEN
        Assertions.assertEquals(largeur, dimension.width);
    }

    // TODO
    @Test
    void init() {
    }

    @Test
    void mouseClicked() {

        // DATA

        // WHEN
    }

    // TODO
    @Test
    void paint() {
    }


    // FIXME
    @Test
    @Disabled
    void setCouleur() {

        // DATA
        Color nouvelleCouleur = Color.CYAN;

        // WHEN
        cPainting.setCouleur(X, Y, nouvelleCouleur, SIZE_DIMENSION);

        Assertions.assertEquals(nouvelleCouleur, cPainting.getCouleur(X, Y));
    }

    @Test
    void suspendre() {

        // WHEN
        cPainting.suspendre();
    }
}