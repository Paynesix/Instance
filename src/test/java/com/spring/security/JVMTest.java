package com.spring.security;

import java.util.Scanner;

/**
 * @program: payne
 * @description:
 * @author: wxy
 * @create: 2021-04-29 17:03
 * @Version 1.0
 **/
public class JVMTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] now = new int[1024];
        while (sc.hasNextLine()){
            int line = sc.nextInt();
            int[] lineArr = new int[line];
            for (int i = 0; i < line; i++) {
                lineArr[i] = i;
            }
            System.out.println("lineArr");
            sc.next();
            System.out.println("next");
        }
        System.out.println("over");
    }
}