/*
Seyed Program Request:

write an app that can keep track of the car's inventory in a dealership. Here is the UML
Car Class
//list of the instance variables
-make: String
-model: String
-color: String
-stockNum:int
-year: int
-mileage: int
-salePrice: double
-purchasePrice: double
-cost: double
//list of the methods
+Car (String make, String model, String color, int stockNum, int year, int mileage, double salePrice, double purchadePrice, double cost)  //constructor
+getMake() : String
+getModel() : String
+getColor(): String
+getStockNum(): int
+getYear(): int
+getMileage(): int
+getSalePrice(): double
+getPurchasePrice() : double
+getCost() : double
+setMake() : String
+setModel() : String
+setColor(): String
+setStockNum(): int
+setYear(): int
+setMileage(): int
+setSalePrice(): double
+setPurchasePrice() : double
+setCost() : double
+getProfit(): double
+toString(): String


Author: Jonathan Lee
Professor: Gita Faroughi
Class: Sierra College CSCI13
Date: Feb 18 2022

NOTES:

!!!!
This program will run on its own or with the driver file named LeeShowGridLayout
The reason for this program is to help with learning Objects. The GUI based file is additonal and optional to use to help you see 
that file only calls the setter methods from object instantiation needs it must be in the same location as this file
!!!!
*/

import java.util.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.*;
import java.io.*;
public class carsLeeWeek4 implements Serializable
{
   private String make, model, color, dateTime;
   private int stockNum, year, mileage;
   private double salePrice, purchasePrice, cost;
   private static int count = 0;
   public carsLeeWeek4 (String make, String model, String color, int stockNum, int year, int mileage, double salePrice, double purchasePrice, String dateTime)
   {
      this.make = make;
      this.model = model;
      this.color = color;
      this.stockNum = stockNum;
      this.year = year;
      this.mileage = mileage;
      this.salePrice = salePrice;
      this.purchasePrice = purchasePrice;
      this.dateTime = dateTime;
      count ++;
   }
   /* Getter Methods */
   public String getMake()
   {
      return this.make;
   }

   public String getModel()
   {
      return this.model;
   }

   public String getColor()
   {
      return this.color;
   }

   public int getStockNum()
   {
      return this.stockNum;
   }

   public int getYear()
   {
      return this.year;
   }

   public int getMileage()
   {
      return this.mileage;
   }

   public double getSalePrice()
   {
      return this.salePrice;
   }
   
   public static int getCount()
   {
      return count;
   }
   
   public double getPurchasePrice()
   {
      return this.purchasePrice;
   }
   
   public String getdateTime()
   {
      return this.dateTime;
   }

   /* Setter Methods*/
   public void setMake(String make)
   {
      this.make = make;
   }

   public void setModel(String model)
   {
      this.model = model;
   }

   public void setColor(String color)
   {
      this.color = color;
   }

   public void setStockNum(int stockNum)
   {
      this.stockNum = stockNum;
   }

   public void setYear(int year)
   {
      this.year = year;
   }

   public void setMileage(int mileage)
   {
      this.mileage = mileage;
   }

   public void setSalePrice(double salePrice)
   {
      this.salePrice = salePrice;
   }
   
   public void setdateTime(String time) //overrider for sales time
   {
      this.dateTime = time;
   }
   
    
   /* This method creates a new list of cars based on user entry */
   public static carsLeeWeek4[] Arr(Scanner console, String time)
   {
      String name = "";
      String confirm = "";
      int currcount = carsLeeWeek4.getCount();
      System.out.println(time);
      System.out.print("How many new entries are needed for inventory system? ");
      while (!console.hasNextInt()) //data validation system will only accept int based numbers
      {
         System.out.println("Numerical entry only");
         System.out.print("Enter a number only: --> ");
         console.next();
      }
      int entry2 = 0; //for temp array for object array add
      int entry = console.nextInt(); // start size
      carsLeeWeek4[] carsArray = new carsLeeWeek4[entry]; //array of objects created based on variable entry
      for (int i = 0; i < carsArray.length; i++) //iteration over array of cars that was created for entry entry 2 will control location of iteration count based on this while loop grow if needed
      {  
         do{ // do this until the entry is confirmed and after iterator to next array element
            console.nextLine();
            System.out.print("Entry #"+(i+1)+", Enter automobile \"MAKE\": ");
            String make = console.nextLine();
            System.out.print("Enter "+make+" \"MODEL\": ");
            String model = console.next();
            System.out.print("Enter "+make+" "+model+" \"COLOR\": ");
            String color = console.next();
            System.out.print("Enter "+color+" "+make+" "+model+" \"STOCK NUMBER\": ");
            int stockNum = console.nextInt();
            System.out.print("Enter Stock# "+stockNum+" "+color+" "+make+" "+model+" \"YEAR\": "); 
            int year = console.nextInt();
            System.out.print("Enter Stock# "+stockNum+" "+color+" "+year+" "+make+" "+model+" \"MILEAGE\": ");
            int mileage = console.nextInt();
            System.out.print("Enter Stock # "+stockNum+" "+color+" "+year+" "+make+" "+model+" \"SALE PRICE\": ");
            double salePrice = console.nextDouble();
            System.out.print("Enter Stock# "+stockNum+" "+color+" "+year+" "+make+" "+model+" \"DEALER PURCHASE PRICE\": ");
            double purchasePrice = console.nextDouble();
            carsArray[i] = new carsLeeWeek4(make, model, color, stockNum, year, mileage, salePrice, purchasePrice, time); // array of object creations that calls the car class constructors, instanceation clause
            System.out.println("\n*******************");
            System.out.println(carsArray[i]);
            System.out.println("Confirm entry is correct Y/y ?"); // confirmation required before i is incremented or it will continue with object array position as the same and replace orginal values
            confirm = console.next();
         } while (!confirm.equalsIgnoreCase("y")); //confirmation of each item
      }
      System.out.println("\n*******************************************************************");
      System.out.println("*"+time+"*"); // display current time I want to add to each entry fix me
      System.out.println("*******************************************************************");
      System.out.println();
      System.out.println("Printout of current inventory in system: "); //printout of all cars
      System.out.println("\n*******************");
      for (int i = 0; i< carsArray.length; i++) //iteration of over new cars for printout display total inventory
      {
         System.out.println(carsArray[i]);
         System.out.println("\n*******************");
      }
      System.out.println();
      return carsArray;
   }
   
   /* this method is called recursively inside of class cars when growarray is ran */
   public static carsLeeWeek4[] addInventory(Scanner console, String time, int entry2, carsLeeWeek4[] carsArray)
   {
      String name = "";
      String confirm = "";
      for (int i = (carsLeeWeek4.getCount()); i < carsArray.length; i++) //iteration over array of cars that was created for entry entry 2 will control location of iteration count based on this while loop grow if needed
      {  
         do{ // do this until the entry is confirmed and after iterator to next array element
            console.nextLine();
            System.out.print("Entry #"+(i+1)+", Enter automobile \"MAKE\": ");
            String make = console.nextLine();
            System.out.print("Enter "+make+" \"MODEL\": ");
            String model = console.next();
            System.out.print("Enter "+make+" "+model+" \"COLOR\": ");
            String color = console.next();
            System.out.print("Enter "+color+" "+make+" "+model+" \"STOCK NUMBER\": ");
            int stockNum = console.nextInt();
            System.out.print("Enter Stock# "+stockNum+" "+color+" "+make+" "+model+" \"YEAR\": "); 
            int year = console.nextInt();
            System.out.print("Enter Stock# "+stockNum+" "+color+" "+year+" "+make+" "+model+" \"MILEAGE\": ");
            int mileage = console.nextInt();
            System.out.print("Enter Stock # "+stockNum+" "+color+" "+year+" "+make+" "+model+" \"SALE PRICE\": ");
            double salePrice = console.nextDouble();
            System.out.print("Enter Stock# "+stockNum+" "+color+" "+year+" "+make+" "+model+" \"DEALER PURCHASE PRICE\": ");
            double purchasePrice = console.nextDouble();
            carsArray[i] = new carsLeeWeek4(make, model, color, stockNum, year, mileage, salePrice, purchasePrice, time); // array of object creations that calls the car class constructors, instanceation clause
            System.out.println("\n*******************");
            System.out.println(carsArray[i]);
            System.out.println("Confirm entry is correct Y/y ?"); // confirmation required before i is incremented or it will continue with object array position as the same and replace orginal values
            confirm = console.next();
         } while (!confirm.equalsIgnoreCase("y")); //confirmation of each item
      }
      System.out.println("\n*******************************************************************");
      System.out.println("*"+time+"*"); // display current time I want to add to each entry fix me
      System.out.println("*******************************************************************");
      System.out.println();
      System.out.println("UPDATED----->\n\tInventory in system: "); //printout of all cars
      System.out.println("\n*******************");
      for (int i = 0; i< carsArray.length; i++) //iteration of over new cars for printout display total inventory
      {
         System.out.println(carsArray[i]);
         System.out.println("\n*******************");
      }
      System.out.println();
      return carsArray;
   }
   
   /* This method will grow the array without deleting the data in it based on how many cars you need added */
   public static carsLeeWeek4[] growArray(carsLeeWeek4[] carsArray, Scanner console, String time)
   {
      System.out.print("How many new entries are needed for inventory system? "); // new entry needed?
      while (!console.hasNextInt()) //data validation system will only accept int based numbers
      {
         System.out.println("Numerical entry only"); //data validation 
         System.out.print("Enter a number only: --> ");
         console.next();
      }
      int entry2 = console.nextInt(); // entry for temp array
      carsLeeWeek4[] temp = new carsLeeWeek4[carsArray.length + entry2]; 
      for (int i = 0; i < carsArray.length; i++) // loop to grow original array with new size and all values stay the same except null for new lines 
      {
         temp[i] = carsArray[i];
      }
      carsArray = temp;
      carsArray = carsLeeWeek4.addInventory(console, time, entry2, carsArray);
      return carsArray;
   }
   
   /* This method saves all inventory to String s */
   public static String getInventory(carsLeeWeek4[] inventory, String time)
   {
      String s = ("Time this report was generated \n"+time);
      s += ("\n*******************\n"); 
      for (int i = 0; i< inventory.length; i++) //iteration of over new cars for printout display total inventory
      {
         s += (inventory[i]);
         s += ("\n*******************\n");
      }
      return s;
   }

   /* This method is tostring */
   public String toString()
   {
      String s = ("Dealer Acquisition Date: " +this.dateTime+ "\nStock Number: " +this.stockNum+ "\nYear: " +this.year+ "\nColor: " +this.color+ "\nMake: " +this.make+ "\nModel: " +this.model+ "\nMileage: " +this.mileage+ "\nSale Price: $" +this.salePrice+ "\nPurchase Price: $" +this.purchasePrice);
      return s;
   }
}


/* Driver Class */
class carsDriver
{
   public static void main (String[] args)
   throws FileNotFoundException {
      {
         String menu = "";
         Scanner console = new Scanner(System.in); //Scanner object
         GregorianCalendar calendar = new GregorianCalendar(); //calendar object
         DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.US);//formatter object
         TimeZone timezone = TimeZone.getTimeZone("PST");//timezone object
         formatter.setTimeZone(timezone);
         String time = (formatter.format(calendar.getTime())); //Time creation call string object to be passed
         carsLeeWeek4 inventory[] = carsLeeWeek4.Arr(console, time); //create a new array of objects named inventory for object cars
         while(!menu.equalsIgnoreCase("q")) //menu program
         {
            System.out.println("\tEnter q/Q to quit");
            //System.out.println("\tEnter 0 to create a new inventory"); //fix me need new code for dual reference calls new array
            System.out.println("\tEnter 1 to add to inventory");
            System.out.println("\tEnter 2 to print current inventory");
            System.out.println("\tEnter 3 to save to text file");
            menu = console.next();
            /*if(menu.equals("0"))
            {
               carsLeeWeek4 OtherInventory[] = carsLeeWeek4.Arr(console, time); //pass scanner and time
               continue;
            }*/
            if (menu.equals("1"))
            {
               inventory = carsLeeWeek4.growArray(inventory, console, time); //pass array of objects scanner and time
               continue;
            }
            else if (menu.equals("2"))
            {
               System.out.println(carsLeeWeek4.getInventory(inventory, time)); //pass array of objects and time
               System.out.println();
               continue;
            }
            else if (menu.equals("3"))
            {
               PrintStream output = new PrintStream(new File("Inventory.txt")); // outputs file to where you have the .java file saved 
               output.println(carsLeeWeek4.getInventory(inventory, time)); // calls inventory from cars and sends the inventory and time for print out
               System.out.println("File Saved in Current working Directory"); //display message
            }
         }
      }
   }
}