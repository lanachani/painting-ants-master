package org.polytechtours.javaperformance.tp.paintingants.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.polytechtours.javaperformance.tp.paintingants.CPainting;
import org.polytechtours.javaperformance.tp.paintingants.PaintingAnts;

@ExtendWith(MockitoExtension.class)
class PaintingAntsTest {

    private static final String APPLET_INFO = "Painting Ants";


    @InjectMocks
    PaintingAnts paintingAnts = new PaintingAnts();

    @Mock
    CPainting cPainting;

    @Mock
    Thread thread;

    @Test
    void compteur() {

    }

    @Test
    void destroy() {
    }

    @Test
    void getAppletInfoTest() {

        // WHEN
        String applet = paintingAnts.getAppletInfo();

        // THEN
        Assertions.assertEquals(applet, APPLET_INFO);
    }

    @Test
    void getParameterInfo() {
    }

    @Test
    void getPauseTest() {
    }

    @Test
    void incrementFpsCounter() {
    }

    @Test
    void init() {
    }

    @Test
    void paint() {
    }

    @Test
    void pauseTest() {

        // DATA
        boolean mPauseAvant = paintingAnts.getPause();

        // WHEN
        paintingAnts.pause();

        // THEN
        Assertions.assertNotEquals(mPauseAvant, paintingAnts.getPause());
    }

    // FIXME
    @Disabled
    @Test
    void runTest() {

        // GIVEN
        Mockito.doNothing().when(cPainting).init();

        // WHEN
        paintingAnts.run();

        // THEN
        Mockito.verify(cPainting, Mockito.times(1)).init();
    }

    // FIXME
    @Test
    @Disabled
    void start() {

        // WHEN
        paintingAnts.start();
    }

    //FIXME
    @Disabled
    @Test
    void stop() {

        // WHEN
        paintingAnts.stop();
    }
}