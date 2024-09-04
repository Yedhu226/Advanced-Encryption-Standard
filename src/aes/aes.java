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

/**
 *
 * @author yedhu226
 */
public class aes {

    static String encrypt(String P, String K) {
        Matrix p = new Matrix(P);
        Key k = new Key(K);
        p = Matrix.Add(p, k.getK());

        for (int i = 0; i < 9; i++) {
            p.Substitute_Bytes();
            p.Shift();
            p.Mix_Columns();
            p = Matrix.Add(p, k.getRkeys(i));
        }
        p.Substitute_Bytes();
        p.Shift();
        return Matrix.Add(p, k.getRkeys(9)).flatten();
    }

    static String decrypt(String C, String K) {
        Matrix c = new Matrix(C);
        Key k = new Key(K);
        c = Matrix.Add(c, k.getRkeys(9));

        for (int i = 8; i >=0; i--) {
            c.Inverse_Shift();
            c.Inverse_Bytes();
            c = Matrix.Add(c, k.getRkeys(i));
            c.Inverse_Mix_Columns();
        }
        c.Inverse_Shift();
        c.Inverse_Bytes();
        
        return Matrix.Add(c, k.getK()).flatten();
    }
}
