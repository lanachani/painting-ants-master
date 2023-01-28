package org.polytechtours.javaperformance.tp.paintingants;

import java.awt.*;

public class Convolution {
    static private final float[][] mMatriceConv9 = new float[3][3];
    static private final float[][] mMatriceConv25 = new float[5][5];
    static private final float[][] mMatriceConv49 = new float[7][7];

    /**
     * Initialisation of convolution matix: average smoothing of 9
     */
    public void MatriceConv9() {
        mMatriceConv9[0][0] = 1 / 16f;
        mMatriceConv9[0][1] = 2 / 16f;
        mMatriceConv9[0][2] = 1 / 16f;
        mMatriceConv9[1][0] = 2 / 16f;
        mMatriceConv9[1][1] = 4 / 16f;
        mMatriceConv9[1][2] = 2 / 16f;
        mMatriceConv9[2][0] = 1 / 16f;
        mMatriceConv9[2][1] = 2 / 16f;
        mMatriceConv9[2][2] = 1 / 16f;
    }

    /**
     * produit de convolution discrete sur 9 cases
     */
    public void prodConv9(int x, int y, Dimension mDimension, Color[][] mCouleurs, Graphics mGraphics, boolean mSuspendu) {
        int i, j, k, l, m, n;
        float R, G, B;
        Color lColor;

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                R = G = B = 0f;

                for (k = 0; k < 3; k++) {
                    for (l = 0; l < 3; l++) {
                        m = (x + i + k - 2 + mDimension.width) % mDimension.width;
                        n = (y + j + l - 2 + mDimension.height) % mDimension.height;
                        R += mMatriceConv9[k][l] * mCouleurs[m][n].getRed();
                        G += mMatriceConv9[k][l] * mCouleurs[m][n].getGreen();
                        B += mMatriceConv9[k][l] * mCouleurs[m][n].getBlue();
                    }
                }
                lColor = new Color((int) R, (int) G, (int) B);

                mGraphics.setColor(lColor);

                m = (x + i - 1 + mDimension.width) % mDimension.width;
                n = (y + j - 1 + mDimension.height) % mDimension.height;
                mCouleurs[m][n] = lColor;
                if (!mSuspendu) {
                    mGraphics.fillRect(m, n, 1, 1);
                }
            }
        }
    }

    /**
     * Initialisation of convolution matix: average smoothing of 25
     */
    public void MatriceConv25() {
        mMatriceConv25[0][0] = 1 / 44f;
        mMatriceConv25[0][1] = 1 / 44f;
        mMatriceConv25[0][2] = 2 / 44f;
        mMatriceConv25[0][3] = 1 / 44f;
        mMatriceConv25[0][4] = 1 / 44f;
        mMatriceConv25[1][0] = 1 / 44f;
        mMatriceConv25[1][1] = 2 / 44f;
        mMatriceConv25[1][2] = 3 / 44f;
        mMatriceConv25[1][3] = 2 / 44f;
        mMatriceConv25[1][4] = 1 / 44f;
        mMatriceConv25[2][0] = 2 / 44f;
        mMatriceConv25[2][1] = 3 / 44f;
        mMatriceConv25[2][2] = 4 / 44f;
        mMatriceConv25[2][3] = 3 / 44f;
        mMatriceConv25[2][4] = 2 / 44f;
        mMatriceConv25[3][0] = 1 / 44f;
        mMatriceConv25[3][1] = 2 / 44f;
        mMatriceConv25[3][2] = 3 / 44f;
        mMatriceConv25[3][3] = 2 / 44f;
        mMatriceConv25[3][4] = 1 / 44f;
        mMatriceConv25[4][0] = 1 / 44f;
        mMatriceConv25[4][1] = 1 / 44f;
        mMatriceConv25[4][2] = 2 / 44f;
        mMatriceConv25[4][3] = 1 / 44f;
        mMatriceConv25[4][4] = 1 / 44f;
    }

    /**
     * produit de convolution discrete sur 25 cases
     */
    public void prodConv25(int x, int y, Dimension mDimension, Color[][] mCouleurs, Graphics mGraphics, boolean mSuspendu) {
        int i, j, k, l, m, n;
        float R, G, B;
        Color lColor;

        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                R = G = B = 0f;

                for (k = 0; k < 5; k++) {
                    for (l = 0; l < 5; l++) {
                        m = (x + i + k - 4 + mDimension.width) % mDimension.width;
                        n = (y + j + l - 4 + mDimension.height) % mDimension.height;
                        R += mMatriceConv25[k][l] * mCouleurs[m][n].getRed();
                        G += mMatriceConv25[k][l] * mCouleurs[m][n].getGreen();
                        B += mMatriceConv25[k][l] * mCouleurs[m][n].getBlue();
                    }
                }
                lColor = new Color((int) R, (int) G, (int) B);
                mGraphics.setColor(lColor);
                m = (x + i - 2 + mDimension.width) % mDimension.width;
                n = (y + j - 2 + mDimension.height) % mDimension.height;

                mCouleurs[m][n] = lColor;
                if (!mSuspendu) {
                    mGraphics.fillRect(m, n, 1, 1);
                }
            }
        }
    }

    /**
     * Initialisation of convolution matix: average smoothing of 49
     */
    public void MatriceConv49() {
        mMatriceConv49[0][0] = 1 / 128f;
        mMatriceConv49[0][1] = 1 / 128f;
        mMatriceConv49[0][2] = 2 / 128f;
        mMatriceConv49[0][3] = 2 / 128f;
        mMatriceConv49[0][4] = 2 / 128f;
        mMatriceConv49[0][5] = 1 / 128f;
        mMatriceConv49[0][6] = 1 / 128f;

        mMatriceConv49[1][0] = 1 / 128f;
        mMatriceConv49[1][1] = 2 / 128f;
        mMatriceConv49[1][2] = 3 / 128f;
        mMatriceConv49[1][3] = 4 / 128f;
        mMatriceConv49[1][4] = 3 / 128f;
        mMatriceConv49[1][5] = 2 / 128f;
        mMatriceConv49[1][6] = 1 / 128f;

        mMatriceConv49[2][0] = 2 / 128f;
        mMatriceConv49[2][1] = 3 / 128f;
        mMatriceConv49[2][2] = 4 / 128f;
        mMatriceConv49[2][3] = 5 / 128f;
        mMatriceConv49[2][4] = 4 / 128f;
        mMatriceConv49[2][5] = 3 / 128f;
        mMatriceConv49[2][6] = 2 / 128f;

        mMatriceConv49[3][0] = 2 / 128f;
        mMatriceConv49[3][1] = 4 / 128f;
        mMatriceConv49[3][2] = 5 / 128f;
        mMatriceConv49[3][3] = 8 / 128f;
        mMatriceConv49[3][4] = 5 / 128f;
        mMatriceConv49[3][5] = 4 / 128f;
        mMatriceConv49[3][6] = 2 / 128f;

        mMatriceConv49[4][0] = 2 / 128f;
        mMatriceConv49[4][1] = 3 / 128f;
        mMatriceConv49[4][2] = 4 / 128f;
        mMatriceConv49[4][3] = 5 / 128f;
        mMatriceConv49[4][4] = 4 / 128f;
        mMatriceConv49[4][5] = 3 / 128f;
        mMatriceConv49[4][6] = 2 / 128f;

        mMatriceConv49[5][0] = 1 / 128f;
        mMatriceConv49[5][1] = 2 / 128f;
        mMatriceConv49[5][2] = 3 / 128f;
        mMatriceConv49[5][3] = 4 / 128f;
        mMatriceConv49[5][4] = 3 / 128f;
        mMatriceConv49[5][5] = 2 / 128f;
        mMatriceConv49[5][6] = 1 / 128f;

        mMatriceConv49[6][0] = 1 / 128f;
        mMatriceConv49[6][1] = 1 / 128f;
        mMatriceConv49[6][2] = 2 / 128f;
        mMatriceConv49[6][3] = 2 / 128f;
        mMatriceConv49[6][4] = 2 / 128f;
        mMatriceConv49[6][5] = 1 / 128f;
        mMatriceConv49[6][6] = 1 / 128f;
    }

    /**
     * produit de convolution discrete sur 49 cases
     */
    public void prodConv49(int x, int y, Dimension mDimension, Color[][] mCouleurs, Graphics mGraphics, boolean mSuspendu) {
        int i, j, k, l, m, n;
        float R, G, B;
        Color lColor;

        for (i = 0; i < 7; i++) {
            for (j = 0; j < 7; j++) {
                R = G = B = 0f;

                for (k = 0; k < 7; k++) {
                    for (l = 0; l < 7; l++) {
                        m = (x + i + k - 6 + mDimension.width) % mDimension.width;
                        n = (y + j + l - 6 + mDimension.height) % mDimension.height;
                        R += mMatriceConv49[k][l] * mCouleurs[m][n].getRed();
                        G += mMatriceConv49[k][l] * mCouleurs[m][n].getGreen();
                        B += mMatriceConv49[k][l] * mCouleurs[m][n].getBlue();
                    }
                }
                lColor = new Color((int) R, (int) G, (int) B);
                mGraphics.setColor(lColor);
                m = (x + i - 3 + mDimension.width) % mDimension.width;
                n = (y + j - 3 + mDimension.height) % mDimension.height;

                mCouleurs[m][n] = lColor;
                if (!mSuspendu) {
                    mGraphics.fillRect(m, n, 1, 1);
                }
            }
        }
    }
}
