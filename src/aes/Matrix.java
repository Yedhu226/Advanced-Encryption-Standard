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

import static aes.Operations.DecimalToHex;
import static aes.Operations.HexToDecimal;
import static aes.Operations.XOR;
import static aes.Operations.gmul;
import static aes.Substitute.getInverseByte;
import static aes.Substitute.getSubstituteByte;

/**
 * Custom Data Structure Class that contains add(xor), substitute bytes, shift
 * and mix columns.
 *
 * @author yedhu226
 */
public class Matrix {

    private int rows = 4;
    private int cols = 4;
    private String Mat[][] = new String[rows][cols];
    private int[][] mix_op = {
        {2, 3, 1, 1},
        {1, 2, 3, 1},
        {1, 1, 2, 3},
        {3, 1, 1, 2}
    };

    private int[][] inv_mix_op = {
        {0x0e, 0x0b, 0x0d, 0x09},
        {0x09, 0x0e, 0x0b, 0x0d},
        {0x0d, 0x09, 0x0e, 0x0b},
        {0x0b, 0x0d, 0x09, 0x0e}
    };

    public Matrix(String in) {
        if (in.length() == 32) {
            rows = cols = 4;
        }
        for (int p = 0; p < in.length();) {
            int i = 0;
            while (i < 4) {
                for (int j = 0; j < this.rows; j++) {
                    Mat[i][j] = Character.toString(in.charAt(p)) + Character.toString(in.charAt(p + 1));
                }
                p += 2;
                i++;
            }
        }
    }

    public Matrix(String in, boolean t) {
        if (in.length() == 32) {
            rows = cols = 4;
        }
        for (int p = 0; p < in.length();) {
            int i = 0;
            while (i < 4) {
                for (int j = 0; j < this.rows; j++) {
                    Mat[j][i] = Character.toString(in.charAt(p)) + Character.toString(in.charAt(p + 1));
                }
                p += 2;
                i++;
            }
        }
    }

    public Matrix(String in[][]) {
        this.rows = in.length;
        this.cols = in[0].length;
        this.Mat = in;
    }

    public Matrix(Matrix m) {
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                this.Mat[i][j] = m.getByte(i, j);
            }
        }
        this.rows = m.getRows();
        this.cols = m.getCols();
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
        for (int i = 1; i < this.rows; i++) {
            for (int x = 0; x < i; x++) {
                String temp = Mat[i][0];
                for (int j = 0; j < this.cols - 1; j++) {
                    Mat[i][j] = Mat[i][j + 1];
                }
                Mat[i][3] = temp;
            }
        }
    }

    void Inverse_Shift() {
        for (int i = 1; i < this.rows; i++) {
            for (int x = 0; x < i; x++) {
                String temp = Mat[i][3];
                for (int j = 1; j < this.cols - 1; j++) {
                    Mat[i][j] = Mat[i][j - 1];
                }
                Mat[i][0] = temp;
            }
        }
    }

    void Substitute_Bytes() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Mat[i][j] = getSubstituteByte(Mat[i][j]);
            }
        }
    }

    void Inverse_Bytes() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Mat[i][j] = getInverseByte(Mat[i][j]);
            }
        }
    }

    void Mix_Columns() {
        int temp[][] = new int[rows][cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                temp[i][j] = HexToDecimal(Mat[i][j]);
            }
        }
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Mat[i][j] = DecimalToHex(gmul(temp[i][j], mix_op[i][j]));
            }
        }

    }

    void Inverse_Mix_Columns() {
        int temp[][] = new int[rows][cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                temp[i][j] = HexToDecimal(Mat[i][j]);
            }
        }
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Mat[i][j] = DecimalToHex(gmul(temp[i][j], inv_mix_op[i][j]));
            }
        }

    }

    String flatten() {
        String r = "";
        for (String[] i : Mat) {
            for (String j : i) {
                r += j;
            }
        }
        return r;
    }

}
