/*
 * Copyright (C) 2024 yedhu226
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package aes;

import static aes.Operations.XOR;

/**
 *
 * @author yedhu226
 */
public class Matrix {

    private int rows;
    private int cols;
    private String Mat[][] = new String[rows][cols];

    public Matrix(String in) {
        if (in.length() == 32) {
            rows = cols = 4;
        }
        for (int p = 0; p < in.length(); p++) {
            int i = 0;
            while (i < 4) {
                for (int j = 0; j < cols; j += 2) {
                    Mat[i][j] = Character.toString(in.charAt(j)) + Character.toString(in.charAt(j + 1));
                }
                i++;
            }
        }
    }

    public Matrix(String in[][]) {
        this.rows = in.length;
        this.cols = in[0].length;
        this.Mat = in;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public String getByte(int i, int j) {
        return this.Mat[i][j];
    }

    static Matrix Add(Matrix M1, Matrix M2) {
        String Mat[][] = new String[M1.getRows()][M1.getCols()];
        for (int i = 0; i < M1.getRows(); i++) {
            for (int j = 0; j < M1.getCols(); j++) {
                Mat[i][j] = XOR(M1.getByte(i, j), M2.getByte(i, j));
            }
        }
        return new Matrix(Mat);
    }

    void Shift() {
        for (int i = 1; i < 4; i++) {
            for (int x = 0; x < i; x++) {
                String temp=Mat[i][0];
                for (int j = 0; j < 3; j++) {
                    Mat[i][j]=Mat[i][j+1];
                }
                Mat[i][3]=temp;
            }
        }
    }
}
