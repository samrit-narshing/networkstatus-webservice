/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Unicode {

public static void main(String[] args) {
System.out.println("Use CTRL+C to quite to program.");

// Create the reader for reading in the text typed in the console. 
InputStreamReader inputStreamReader = new InputStreamReader(System.in);
BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

try {
  String line = "पाटन";
  for (char c : line.toCharArray()) {
         System.out.printf("\\u%04x \n", (int) c); 
      }

  }
 catch (Exception ioException) {
       ioException.printStackTrace();
  }
 }
}