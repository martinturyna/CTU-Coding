package cz.cvut.fel.pjv;

import java.util.Scanner;

public class Lab01 {

   Scanner sc = new Scanner(System.in);

   public void start(String[] args) {
      homework();
   }

   public void homework() {
      System.out.print("Vyber operaci (1-soucet, 2-rozdil, 3-soucin, 4-podil):");
      int choiceNumber = sc.nextInt();
      if (choiceNumber > 4 || choiceNumber < 1) {
         System.out.print("\nChybna volba!\n");
      }
      else {
         switch (choiceNumber) {
            case 1: addition();
               break;
            case 2: subtraction();
               break;
            case 3: multiplication();
               break;
            case 4: division();
               break;
            default:
               break;
         }
      }

   }

   public void addition() {

      System.out.print("\nZadej scitanec: ");
      double firstAddend = sc.nextDouble();

      System.out.print("\nZadej scitanec: ");
      double secondAddend = sc.nextDouble();

      System.out.print("\nZadej pocet desetinnych mist: ");
      int decimalPlaces = sc.nextInt();

      if (decimalPlaces < 0) {
         System.out.print("\nChyba - musi byt zadane kladne cislo!\n");
      }
      else {
         System.out.format("\n%." + decimalPlaces + "f + %." + decimalPlaces + "f = %." + decimalPlaces + "f\n", firstAddend, secondAddend, firstAddend + secondAddend);
      }
   }

   public void subtraction() {

      System.out.print("\nZadej mensenec: ");
      double minued = sc.nextDouble();

      System.out.print("\nZadej mensitel: ");
      double subtrahend = sc.nextDouble();

      System.out.print("\nZadej pocet desetinnych mist: ");
      int decimalPlaces = sc.nextInt();

      if (decimalPlaces < 0) {
         System.out.print("\nChyba - musi byt zadane kladne cislo!\n");
      }
      else {
         System.out.format("\n%." + decimalPlaces + "f - %." + decimalPlaces + "f = %." + decimalPlaces + "f\n", minued, subtrahend, minued - subtrahend);
      }
   }

   public void multiplication() {

      System.out.print("\nZadej cinitel: ");
      double firstFactor = sc.nextDouble();

      System.out.print("\nZadej cinitel: ");
      double secondFactor = sc.nextDouble();

      System.out.print("\nZadej pocet desetinnych mist: ");
      int decimalPlaces = sc.nextInt();

      if (decimalPlaces < 0) {
         System.out.print("\nChyba - musi byt zadane kladne cislo!\n");
      }
      else {
         System.out.format("\n%." + decimalPlaces + "f * %." + decimalPlaces + "f = %." + decimalPlaces + "f\n", firstFactor, secondFactor, firstFactor * secondFactor);
      }
   }

   public void division() {

      System.out.print("\nZadej delenec: ");
      double dividend = sc.nextDouble();

      System.out.print("\nZadej delitel: ");
      double divisor = sc.nextDouble();

      if (divisor == 0) {
         System.out.print("\nPokus o deleni nulou!\n");
      }
      else {
         System.out.print("\nZadej pocet desetinnych mist: ");
         int decimalPlaces = sc.nextInt();

         if (decimalPlaces < 0) {
            System.out.print("\nChyba - musi byt zadane kladne cislo!\n");
         }
         else {
            System.out.format("\n%." + decimalPlaces + "f / %." + decimalPlaces + "f = %." + decimalPlaces + "f\n", dividend, divisor, dividend / divisor);
         }
      }
   }
}

