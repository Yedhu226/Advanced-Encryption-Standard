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

import static aes.Substitute.getSubstituteByte;
import java.math.BigInteger;

/**
 *
 * @author yedhu226
 */
public class Operations {

    static String XOR(String A, String B) {
        return DecimalToHex(XOR(HexToDecimal(A), HexToDecimal(B)));
    }

    static int XOR(int A, int B) {
        return A ^ B;
    }

    static Integer HexToDecimal(String in) {
        return Integer.valueOf(new BigInteger(in, 16).toString(10));
    }

    static String DecimalToHex(int in) {
        String h = Integer.toHexString(in);
        if (h.length() < 2) {
            h = "0" + h;
        }
        return h;
    }

    static int gmul(int a, int b) {
        int p = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0) {
                p ^= a;
            }
            boolean highBitSet = (a & 0x80) != 0;
            a <<= 1;
            if (highBitSet) {
                a ^= 0x1b;
            }
            b >>= 1;
        }
        return p & 0xFF;
    }
    
    
    
    static String[] gsub(String[] col,String rcon){
        String t=getSubstituteByte(col[0]);
        for(int i=0;i<col.length-1;i++){
            col[i]=getSubstituteByte(col[i+1]);
       
        }
        col[col.length-1]=t;
        col[0]=XOR(col[0],rcon);
        return col;
    }
}
