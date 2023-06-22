/*
 * File name: Lab06.java
 * Date:      2014/08/26 21:39
 * Author:    @author
 */

package cz.cvut.fel.pjv;
import java.util.ArrayList;

public class Lab02 {
   int lineCounter = 0;
   String newLine;
   private TextIO text = new TextIO();
   ArrayList<Double> array = new ArrayList<Double>(); // Array of ten or less numbers to be counted

   public void start(String[] args) {
      homework();
   }
   public void calculation(ArrayList<Double> calc_array){
      double average = 0;
      double sum = 0;
      double deviation = 0;
      double helper1 = 0;
      double helper2 = 0;
      for (int i = 0; i < calc_array.size(); i++) { // Sum
         sum = sum + calc_array.get(i);
      }
      average = sum / calc_array.size(); // Average

      helper1 = (1.0 / ((calc_array.size())));
      for (int i = 0; i < calc_array.size(); i++) {
         helper2 = helper2 + ((calc_array.get(i) - average) * (calc_array.get(i) - average));
      }
      double a = helper1 * helper2;
      deviation = Math.sqrt(a);

      System.out.format("%2d %.3f %.3f\n", calc_array.size(), average,  deviation);
   }

   public void homework() {

      while (true) {
         lineCounter++;
         if (text.stdin.hasNext()) {
            newLine = text.getLine();
            if (TextIO.isDouble(newLine)) {
               array.add(Double.parseDouble(newLine));
               if (array.size() == 10) {
                  // calculation when 10 numbers
                  calculation(array);
                  array.clear();
               }
            } else {
               System.err.println("A number has not been parsed from line " + lineCounter);
            }
         }
         else {
            if (array.size() <= 1) {
               System.err.print("End of input detected!\n");
               break;
            } else {
               System.err.print("End of input detected!\n");
               calculation(array);
               break;
            }
         }
         // End of input detected
      }
   }
}

/* end of Lab02.java */
