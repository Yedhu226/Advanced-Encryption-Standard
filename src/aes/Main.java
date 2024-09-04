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

import static aes.aes.decrypt;
import static aes.aes.encrypt;
import java.util.Scanner;

/**
 *
 * @author yedhu226
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String P,K;
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Plaintext: ");
        P=sc.nextLine();
        System.out.println("Enter Key: ");
        K=sc.nextLine();
        System.out.println("Cipher Text ");
        String C=encrypt(P,K);
        System.out.println(C);
//        P=decrypt(C,K);
//        System.out.println(P);
    }
    
}
