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
import org.polytechtours.javaperformance.tp.paintingants.CColonie;
import org.polytechtours.javaperformance.tp.paintingants.CFourmi;
import org.polytechtours.javaperformance.tp.paintingants.CPainting;
import org.polytechtours.javaperformance.tp.paintingants.PaintingAnts;

import java.awt.*;
import java.util.Vector;


@ExtendWith(MockitoExtension.class)
class CColonieTest {

    @InjectMocks
    private CColonie cColonie;

    @Mock
    private PaintingAnts paintingAnts;

    private final static float PROBA = 0.5F;
    private final static float COORD_X = 3.2F;
    private final static float COORD_Y = 6.7F;
    private final static int SIZE_DIMENSION = 10;
    private final static float SEUIL_LUMINANCE = 20.0F;


    @BeforeEach
    public void setUp() {
        Dimension dimension = new Dimension(SIZE_DIMENSION, SIZE_DIMENSION);
        PaintingAnts paintingAnts = new PaintingAnts();
        CPainting cPainting = new CPainting(dimension, paintingAnts);
        CFourmi cFourmi = new CFourmi(Color.PINK, PROBA, PROBA, PROBA, PROBA, cPainting, 'r', 0, 0, SEUIL_LUMINANCE, paintingAnts);

        Vector<CFourmi> cFourmiVector = new Vector<>();
        cFourmiVector.addElement(cFourmi);
        cColonie = new CColonie(cFourmiVector, paintingAnts);
    }

    @Test
    void pleaseStopTest() {

        // WHEN
        cColonie.pleaseStop();

        // THEN
        Assertions.assertEquals(cColonie.getmContinue(), false);
    }

    // TODO : Revoir les tests run.
    // FIXME

    /**
     * Condition 2 : l'application n'est pas sur pause.
     */
    @Test
    @Disabled
    void runTest1() {

        // GIVEN
        Mockito.doReturn(false).when(paintingAnts).getPause();

        // WHEN
        cColonie.run();

        // THEN
    }

    /**
     * Condition 2 : l'application est sur pause.
     */
    @Test
    @Disabled
    void runTest2() {

        // GIVEN
        Mockito.doReturn(true).when(paintingAnts).getPause();

        // WHEN
        cColonie.run();

        // THEN
    }

    @Test
    void getmContinueTest() {

        // WHEN
        boolean mContinue = cColonie.getmContinue();

        // THEN
        Assertions.assertTrue(mContinue);
    }
}