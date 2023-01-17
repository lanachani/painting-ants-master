package org.polytechtours.javaperformance.tp.paintingants;

/*
 * CColonie.java
 *
 * Created on 11 avril 2007, 16:35
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
import java.util.Vector;

public class CColonie implements Runnable {

  private Boolean mContinue = Boolean.TRUE;
  private Vector<CFourmi> mColonie;
  private PaintingAnts mApplis;

  /**
   * Creates a new instance of CColonie.
   * @param pColonie
   * @param pApplis
   */
  public CColonie(Vector<CFourmi> pColonie, PaintingAnts pApplis) {
    mColonie = pColonie;
    mApplis = pApplis;
  }

  /**
   * Stops the colonization.
   */
  public void pleaseStop() {
    mContinue = false;
  }


  /**
   * Process of the colonization.
   */
  @Override
  public void run() {

    while (mContinue == true) {
      if (!mApplis.getPause()) {
        for (int i = 0; i < mColonie.size(); i++) {
          mColonie.get(i).deplacer();
          mApplis.compteur();
        }
      } else {
        /*
         * try { Thread.sleep(100); } catch (InterruptedException e) { break; }
         */
      }
    }
  }

  /**
   * Checks if the colonization is running or not.
   * @return True if the colonization continues, else false.
   */
  public Boolean getmContinue() {
    return mContinue;
  }
}
