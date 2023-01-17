package org.polytechtours.javaperformance.tp.paintingants.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.polytechtours.javaperformance.tp.paintingants.CFourmi;
import org.polytechtours.javaperformance.tp.paintingants.CPainting;
import org.polytechtours.javaperformance.tp.paintingants.PaintingAnts;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CFourmiTest {


    private CFourmi cFourmi;

    private final static float PROBA = 0.5F;
    private final static float COORD_X = 3.2F;
    private final static float COORD_Y = 6.7F;
    private final static float SEUIL_LUMINANCE = 20.0F;
    private final static int SIZE_DIMENSION = 10;

    @BeforeEach
    public void setUp() {
        Dimension dimension = new Dimension(SIZE_DIMENSION, SIZE_DIMENSION);
        PaintingAnts paintingAnts = new PaintingAnts();
        CPainting cPainting = new CPainting(dimension, paintingAnts);
        cFourmi = new CFourmi(Color.PINK, Color.RED, PROBA, PROBA, PROBA, PROBA, cPainting, 'r', COORD_X, COORD_Y, 0, 0, SEUIL_LUMINANCE, paintingAnts);
    }

    // FIXME
    @Disabled
    @Test
    void deplacerTest() {

        // THEN

        // WHEN
        cFourmi.deplacer();
    }

    @Test
    void getNbDeplacementsTest() {

        // WHEN
        long nbDeplacements = cFourmi.getNbDeplacements();

        // THEN
        Assertions.assertTrue(nbDeplacements >= 0);
    }

    @Test
    void getXTest() {

        // WHEN
        int x = cFourmi.getX();

        // THEN
        Assertions.assertTrue(x >= 0);
    }

    @Test
    void getYTest() {

        // WHEN
        int y = cFourmi.getY();

        // THEN
        Assertions.assertTrue(y >= 0);
    }
}