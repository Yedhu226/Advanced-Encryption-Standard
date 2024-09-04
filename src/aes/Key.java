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
import static aes.Operations.gsub;

/**
 *
 * @author yedhu226
 */
public class Key {

    private Matrix K;
    private Matrix[] rkeys=new Matrix[10];
    private String[] RCON = {
        "01", "02", "04", "08", "10", "20", "40", "80", "1b", "36"
    };

    public Key(String in) {
        K = new Matrix(in, true);
        Matrix T = new Matrix(in, true);
        for (int i = 0; i < 10; i++) {
            String[][] temp = new String[4][4];
            String[] last = new String[4];
            String[] first = new String[4];
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    last[x] = T.getByte(x, 3);
                    first[x] = T.getByte(x, 0);
                    temp[y][x]=T.getByte(x, y);
                }
            }

            last = gsub(last, RCON[i]);
            String[][] rkt = new String[4][4];
            for (int j = 0; j < 4; j++) {
                for (int x = 0; x < 4; x++) {
                    rkt[x][j] = XOR(first[x], last[x]);
                    first[x] = rkt[x][j];
                }
                if(j<3)
                    last=temp[j+1];
            }
            rkeys[i]=new Matrix(rkt);
        }
    }

    public Matrix getRkeys(int i) {
        return rkeys[i];
    }

    public Matrix getK() {
        return K;
    }

}
