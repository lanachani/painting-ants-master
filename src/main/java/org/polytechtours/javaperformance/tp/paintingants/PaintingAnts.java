package org.polytechtours.javaperformance.tp.paintingants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;

public class PaintingAnts extends java.applet.Applet implements Runnable {
    private static final long serialVersionUID = 1L;

    // l'objet graphique lui meme
    private CPainting mPainting;

    // les fourmis
    private final java.util.List<CFourmi> mColonie = Collections.synchronizedList(new ArrayList<>());
    private CColonie mColony;

    private Thread mApplis, mThreadColony;

    private long mCompteur = 0;
    private final Object mMutexCompteur = new Object();
    private boolean mPause = false;

    public BufferedImage mBaseImage;
    private Timer fpsTimer;

    /* Fourmis per second :) */
    private Long fpsCounter = 0L;
    /* stocke la valeur du compteur lors du dernier timer */
    private Long lastFps = 0L;

    /**
     * Incrémente le compteur.
     */
    public void compteur() {
        synchronized (mMutexCompteur) {
            mCompteur++;
        }
    }

    /**
     * Destroys the applet.
     */
    @Override
    public void destroy() {
        if (mApplis != null) {
            mApplis = null;
        }
    }

    /**
     * @return information Applet.
     */
    @Override
    public String getAppletInfo() {
        return "Painting Ants";
    }

    /**
     * @return State of break.
     */
    public boolean getPause() {
        return mPause;
    }

    public synchronized void IncrementFpsCounter() {
        fpsCounter++;
    }

    /**
     * Initialisation de l'applet
     */
    @Override
    public void init() {
        URL lFileName;
        URLClassLoader urlLoader = (URLClassLoader) this.getClass().getClassLoader();

        // lecture des parametres de l'applet

        Dimension mDimension = getSize();
        // parametres
        int mLargeur;
        int mHauteur;

        mPainting = new CPainting(mDimension, this);
        add(mPainting);

        // lecture de l'image
        lFileName = urlLoader.findResource("images/" + getParameter("Img"));
        try {
            if (lFileName != null) {
                mBaseImage = javax.imageio.ImageIO.read(lFileName);
            }
        } catch (java.io.IOException ignored) {
        }

        if (mBaseImage != null) {
            mLargeur = mBaseImage.getWidth();
            mHauteur = mBaseImage.getHeight();
            mDimension.setSize(mLargeur, mHauteur);
            resize(mDimension);
        }

        Parameters param = new Parameters();
        param.readParameterFourmis(this, mPainting, mColonie);

        setLayout(null);
    }

    /**
     * Paints the image and all active highlights.
     */
    @Override
    public void paint(Graphics g) {

        if (mBaseImage == null) {
            return;
        }
        g.drawImage(mBaseImage, 0, 0, this);
    }

    /**
     * Mettre en pause.
     */
    public void pause() {
        mPause = !mPause;
    }

    /**
     * Process painting of the applet.
     */
    @Override
    public void run() {
        int i;
        StringBuilder lMessage;

        mPainting.init();

        Thread currentThread = Thread.currentThread();

        mThreadColony.start();

        while (mApplis == currentThread) {
            if (mPause) {
                lMessage = new StringBuilder("pause");
            } else {
                synchronized (this) {
                    lMessage = new StringBuilder("running (" + lastFps + ") ");
                }

                synchronized (mMutexCompteur) {
                    mCompteur %= 10000;
                    for (i = 0; i < mCompteur / 1000; i++) {
                        lMessage.append(".");
                    }
                }
            }
            showStatus(lMessage.toString());

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                showStatus(e.toString());
            }
        }
    }

    /**
     * Launches the applet.
     */
    @Override
    public void start() {
        mColony = new CColonie(mColonie, this);
        mThreadColony = new Thread(mColony);
        mThreadColony.setPriority(Thread.MIN_PRIORITY);

        fpsTimer = new Timer(1000, e -> updateFPS());
        fpsTimer.setRepeats(true);
        fpsTimer.start();

        showStatus("starting...");
        // Create the thread.
        mApplis = new Thread(this);
        // and let it start running
        mApplis.setPriority(Thread.MIN_PRIORITY);
        mApplis.start();
    }

    /**
     * Stops the applet.
     */
    @Override
    public void stop() {
        showStatus("stopped...");

        fpsTimer.stop();

        // On demande au Thread Colony de s'arreter et on attend qu'il s'arrete
        mColony.pleaseStop();
        try {
            mThreadColony.join();
        } catch (Exception ignored) {
        }

        mThreadColony = null;
        mApplis = null;
    }

    /**
     * Updates Fourmis Per Second.
     */
    private synchronized void updateFPS() {
        lastFps = fpsCounter;
        fpsCounter = 0L;
    }
}
