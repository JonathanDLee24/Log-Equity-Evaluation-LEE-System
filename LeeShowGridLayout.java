/*
Author: Jonathan Lee
Professor: Gita Faroughi
Class: CSCI13
Date: Sep 20 2022
Project: Help to Learn objects

!!!
This program is the driver for the dynamic database must have .dat files in same folder to run
!!!
*/

import javax.swing.*; //for GUI
import java.awt.*; //for GUI
import java.awt.event.*; //for actions use
import java.awt.Graphics;
import java.util.*; //for ArrayList
import java.text.*; //text
import java.io.*; //for serializeable
import javax.imageio.ImageIO;//needed for image
import java.awt.image.BufferedImage;//needed to buffer images

enum INVENTORYCHANGE{INCREASE, DECREASE, HOLD};//for use with increasing cost of each inventory item by a percentage rate

public class LeeShowGridLayout extends JFrame//Log Equity Evaluation (LEE) System this will log all changes in a file as well as run dynamically durring normal useage can be cross refrerenced with record file
{
   private JTextField jtfmake = new JTextField(20);//text box area for make
   private JTextField jtfmodel = new JTextField(20);//text box area for model   
   private JTextField jtfcolor = new JTextField(20);//text box area for color
   private JTextField jtfstocknum = new JTextField(20);//text box area for stocknum
   private JTextField jtfyear = new JTextField(20);//text box area for year
   private JTextField jtfmileage = new JTextField(20);//text box area for mileage
   private JTextField jtfsalesprice = new JTextField(20);//text box area for sales price
   private JTextField jtfpurchasePrice = new JTextField(20);//text box area for purchase price
   private JButton jbtCenter = new JButton("Enter");//button for enter action
   private JButton jbtClear = new JButton("Clear");//button for clear action
   private JButton jbtInventory = new JButton("Inventory");//buton for inventory action
   private JButton jbtDeleteItem = new JButton("RemoveStock/Sold");//button for deleting element
   private JButton jbtUpdateInfo = new JButton("Update");//button for changing info based on stock number
   private JButton jbtInventoryPrintText = new JButton("TextFile/Print");//saves to file and saves current working ArrayList for next run
   private JButton jbtDashboardButtons = new JButton("Dashboard");//report
   private JPanel p1 = new JPanel (new FlowLayout());//create panel as a private item
   private JPanel p2 = new JPanel (new FlowLayout(FlowLayout.CENTER)); //create panel for buttons
   private ArrayList<carsLeeWeek4> carsArray = new ArrayList<carsLeeWeek4>();//for carsArry for program
   private ArrayList<carsLeeWeek4> soldArray = new ArrayList<carsLeeWeek4>();//for soldarray for loggs sales Ad Hoc
   private String dateTime = "";//for use with object instantiation for time
   private boolean time_flag = false;//flag for time method
   private boolean update_flag = false;//flat for use with update non enter issue
   private carsLeeWeek4 k;//empty object for use with time method
   private INVENTORYCHANGE change = INVENTORYCHANGE.HOLD;//used to help expand knowledge on enum class
   
   public LeeShowGridLayout(ArrayList<carsLeeWeek4> carsArray, ArrayList<carsLeeWeek4> soldArray, String dateTime)
   {
      this.carsArray = carsArray;//passes in the serializable file and sets to public for all classes
      this.soldArray = soldArray;
      this.dateTime = TimeCapsule();
      p2.add(jbtCenter);//add on the button for enter notice p1 that was the object that was instantiated above JPanel p1
      p2.add(jbtClear);//add on the button for clear
      p2.add(jbtInventory);// add on the button for inventory
      p2.add(jbtDeleteItem);//add on the button for deleting an item
      p2.add(jbtUpdateInfo);//add on the button for updating database
      p2.add(jbtInventoryPrintText);//add on the button for Inventory
      p2.add(jbtDashboardButtons);//add on the button to go to AdHoc Dashboard
      p1.setLayout(new GridLayout(8,2));//arrangement of grid in GUI window 8 windows 2 wide
      p2.setLayout(new GridLayout(2,4));
      p1.add(new JLabel("Enter Make:"));//add text for textbox
      p1.add(jtfmake);
      p1.add(new JLabel("Enter Model:"));//add text for text box
      p1.add(jtfmodel);
      p1.add(new JLabel("Enter Color:"));//add text for text box
      p1.add(jtfcolor);
      p1.add(new JLabel("Enter Unique Stock Number:"));//add text for text box
      p1.add(jtfstocknum);
      p1.add(new JLabel("Enter Year:"));//add text for text box
      p1.add(jtfyear);
      p1.add(new JLabel("Enter Mileage:"));//add text for text box
      p1.add(jtfmileage);
      p1.add(new JLabel("Enter Sales Price:"));//add text for text box
      p1.add(jtfsalesprice);
      p1.add(new JLabel("Enter Dealer Purchase Price:"));//add text for text box
      p1.add(jtfpurchasePrice);
      add(p1, "North");//add p1 to the program top of windows p1 is text and entry boxes
      add(p2, "South");//add p2 to the program bottom of window p2 is buttons
      jbtCenter.addActionListener(new ButtonListener());//set your button actions enter when pressed
      jbtClear.addActionListener(new ClearListener());//set your button actions for clear when pressed
      jbtInventory.addActionListener(new InventoryListener());//set your actions for inventory press
      jbtDeleteItem.addActionListener(new DeleteItemListener());//set your actions for delete item uses stock number
      jbtUpdateInfo.addActionListener(new UpdateInfoListener());//set your actions for update info based on stock number
      jbtInventoryPrintText.addActionListener(new InventoryPrintText());//set your actions for printout
      jbtDashboardButtons.addActionListener(new DashboardButtons());//set your actions for adhoc
   }
         
   
   /* This class is what occurs when ENTER button is pushed ButtonListener */
   private class ButtonListener implements ActionListener//action when enter is pressed main runs adds items to list array
   {
      carsLeeWeek4 s;//for while loop creates null object carsLeeWeek4 CheckStockNumForUniqueVal use only
      public void actionPerformed(ActionEvent e) 
      {
         try
         {
            String make = jtfmake.getText();//make variable for storage           
            String model =  jtfmodel.getText();//model variable for storage
            String color = jtfcolor.getText();//color variable for storage
            int stockNum =  Integer.parseInt(jtfstocknum.getText());//dealer stock variable for storage 
            String date ="";//create empty string
            if(time_flag)//if timeflag is true grab the object k's old date and time this enter run was used for update time of aqusiton must stay the same
               date = k.getdateTime();//for use with update button needs to hold value of acquisition date to keep that so updates do not change this 
            else if(!time_flag)//if enter is pressed for non update use use timeCapsule method to get accurate time time_flag is a global variable for use with this program adapted in update class
               date = TimeCapsule();//for use with new inventory items
            if(!CheckStockNumForUniqueVal(stockNum))//calls method and gets the return to check if it is true or not stocknumber must not already be in use in current inventory system
            {
               int year = Integer.parseInt(jtfyear.getText());//year variable for storage 
               int mileage = Integer.parseInt(jtfmileage.getText());//mileage variable for storage
               double salePrice = Double.parseDouble(jtfsalesprice.getText());//sales price for storage
               double purchasePrice= Double.parseDouble(jtfpurchasePrice.getText());//Purchase price variable for storage
               carsArray.add (new carsLeeWeek4(make, model, color, stockNum, year, mileage, salePrice, purchasePrice, date));//dymanic programming add to Arraylist here ArrayList of Objects type carsLeeWeek4
               time_flag = false;//reset time flag
               update_flag = false;//reset the clear issue
               log(carsArray);//log changes with log method
               //clear text feilds after dynamic add on GUI screen
               jtfmake.setText(""); 
               jtfmodel.setText("");
               jtfcolor.setText("");
               jtfstocknum.setText("");
               jtfyear.setText("");
               jtfmileage.setText("");
               jtfsalesprice.setText("");
               jtfpurchasePrice.setText("");
            }
            else 
               jtfstocknum.setText("!!ERROR!! "+stockNum+" Already Used"); //error checking
         }
         catch(Exception s)//if any of the input text fields from user are invalid display requirements and ERROR
         {
            jtfmake.setText("Error");//add notes to text fields for correct useage error checking
            jtfmodel.setText("String");
            jtfcolor.setText("String");
            jtfstocknum.setText("Integer");
            jtfyear.setText("Integer");
            jtfmileage.setText("Integer");
            jtfsalesprice.setText("Double");
            jtfpurchasePrice.setText("Double");
         }
      }
      /* This private method is under class ButtonListener and checks for Unique stock number when enter is pushed prior to save returns boolean called from while loop */
      private boolean CheckStockNumForUniqueVal(int stock_number)
      {
         int stockNum = stock_number;//for testing stock numbers
         int num = 0;//pre init for num that is found in iteration for compairsion
         Iterator<carsLeeWeek4> iterator = carsArray.iterator();//list array iterator for print outs iterates over carsArray created on line 29
         while(iterator.hasNext())//iterate over all inventory to find stock number that is inputted
         {
            this.s = iterator.next();//sets s to the iteration 
            num = s.getStockNum();//sets num for current iteration for comparison
            if (num == stockNum)//conditional if statement
               return true;//set found flag on
         }
         return false;//if end of iterator reached and not found return false
      }
   }
   
   
   /* This class is for the UPDATE button updates the stock number for mistakes */
   private class UpdateInfoListener implements ActionListener
   {
      carsLeeWeek4 s;//for while loop
      boolean found = false;//flag to check if stock number was already in use in inventory
      public void actionPerformed(ActionEvent eva)
      {
         try
         {
            int stockNum =  Integer.parseInt(jtfstocknum.getText());//grab stock number from GUI screen store as int
            int count = 0;//count what number has this stock number
            int num = 0;//pre init for num that is found in iteration for compairsion
            Iterator<carsLeeWeek4> iterator = carsArray.iterator();//list array iterator for print outs iterates over carsArray created on line 29
            while(iterator.hasNext())//iterate over all inventory to find stock number that is inputted
            {
               this.s = iterator.next();//sets s to the iteration 
               num = s.getStockNum();//sets num for current iteration for comparison
               if (num == stockNum)//conditional if statement
               {
               /* make, model, color, stockNum, year, mileage, salePrice, purchasePrice so object s would work for getters once found */
                  found = true; //set found flag on
                  update_flag = true; //used if clear is pressed and enter not pressed inventory returns to orginal state
                  k = s;//sets empty object to k for use with enter pushed again keeps aquisition time
                  carsArray.remove(count);//removes for repacement with new data
                  jtfmake.setText(s.getMake());//adds old data to be seen now stored in object s that was global for this method s.gettter methods used
                  jtfmodel.setText(s.getModel());// fills windows with values
                  jtfcolor.setText(s.getColor());
                  jtfstocknum.setText(Integer.toString(s.getStockNum()));//convert from int to string so gui can place into text box reverse
                  jtfyear.setText(Integer.toString(s.getYear()));//grabs text from cars array and redisplays them for user n GUI
                  jtfmileage.setText(Integer.toString(s.getMileage()));
                  jtfsalesprice.setText(Double.toString(s.getSalePrice()));
                  jtfpurchasePrice.setText(Double.toString(s.getPurchasePrice()));
                  time_flag = true;//set time flag to true when enter is presseed aqusition time will stay the same
                  break;//any values on gui windows remain break for adaption when enter is pressed uses other class and recreates element
               }
               count++;//increment count to check for stock number we are trying to update
            }
            if (!found)//if not found note and show in GUI
            {
               jtfmake.setText("STOCK NOT FOUND CLEAR AND TRY AGAIN");//shows in make text box in GUI
               jtfmodel.setText("");
               jtfcolor.setText("");
               jtfstocknum.setText("");
               jtfyear.setText("");
               jtfmileage.setText("");
               jtfsalesprice.setText("");
               jtfpurchasePrice.setText("");
            }
         }
         catch (Exception NumberFormatException)//if non int used or update not used correctly
         {
            jtfmodel.setText("");
            jtfcolor.setText("");
            jtfstocknum.setText("Number Required for update");
            jtfyear.setText("");
            jtfmileage.setText("");
            jtfsalesprice.setText("");
            jtfpurchasePrice.setText("");
         
         }
      }
   }


   /* This class CLEAR button all items on screen that are written in as mistakes */
   private class ClearListener implements ActionListener//action when clear is pressed
   {
      public void actionPerformed(ActionEvent eva) 
      {
         if(update_flag)//if update flag is on restore old inventory item this could have also been done with setter items, however updated items move to the end of the list like this
         {
            jtfmake.setText(k.getMake());//adds old data to be seen now stored in object s that was global for this method s.gettter methods used
            jtfmodel.setText(k.getModel());// fills windows with values
            jtfcolor.setText(k.getColor());
            jtfstocknum.setText(Integer.toString(k.getStockNum()));//convert from int to string so gui can place into text box reverse
            jtfyear.setText(Integer.toString(k.getYear()));//grabs text from cars array and redisplays them for user n GUI
            jtfmileage.setText(Integer.toString(k.getMileage()));
            jtfsalesprice.setText(Double.toString(k.getSalePrice()));
            jtfpurchasePrice.setText(Double.toString(k.getPurchasePrice()));
            time_flag = true;//set time flag to true when enter is presseed aqusition time will stay the same
            return;//any values on gui windows remain break for adaption when enter is pressed uses other class and recreates element
         }
         //clear all mistakes on screen text boxes
         jtfmake.setText(""); 
         jtfmodel.setText("");
         jtfcolor.setText("");
         jtfstocknum.setText("");
         jtfyear.setText("");
         jtfmileage.setText("");
         jtfsalesprice.setText("");
         jtfpurchasePrice.setText("");
      }
   }
   
   
   /* This class prints current dynamic inventory to console */
   private class InventoryListener implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {
         Iterator<carsLeeWeek4> iterator = carsArray.iterator();//list array iterator for console screen display
         while(iterator.hasNext()) 
         {
            System.out.println("\n*******************");
            System.out.println(iterator.next());
         }
      }
   }
   
   /* This class TEXT/FILE PRINT button and prints to file in working directory as Inventory.txt */
   private class InventoryPrintText implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {
         Iterator<carsLeeWeek4> iterator = carsArray.iterator();//must use iterator do to ArrayList use
         try
         {
            FileOutputStream FileStreamOut = new FileOutputStream(new File("Inventory.txt"));//text log file will create new text file each use to be used with printer or a server if needed
            PrintStream output = new PrintStream(FileStreamOut);//outputs file to where you have the .java file saved can be changed if needed path not used currernly 
            output.println("\nThis File Was Generated On: " + TimeCapsule() +"\n\nInventory During This Time Stamp Held Was ----->");//time stamp added to text file so each file has a date stamp
            while(iterator.hasNext())//adds all current inventory to the Inventory.txt file with stars used as a spacer
            {
               output.println("\n*******************");
               output.println(iterator.next());//calls inventory from cars and sends the inventory and time for print out
            }
            output.println("\nEND OF FILE: \n" + TimeCapsule());
            System.out.println("File Saved in Current working Directory Inventory.txt");//display message on console
         }
         catch(FileNotFoundException rw)
         {
         }
      }
   }
   
   /* This class REMOVE STOCK/SOLD Button removes only one unique element after it finds that stock number "iteraction" iteration + action */ 
   private class DeleteItemListener implements ActionListener
   {
      carsLeeWeek4 s;//for while loop notice this is placed above so all methods can access this durring its use it would fail if placed in public void area
      boolean found = false;//for found use if statement
      public void actionPerformed(ActionEvent eva)
      {
         try
         {
            int stockNum =  Integer.parseInt(jtfstocknum.getText());//must use parse to int as the getText comes in as a string
            int count = 0;//count what number has this stock number
            int num = 0;//pre init for num that is found in iteration for compairsion
            Iterator<carsLeeWeek4> iterator = carsArray.iterator();//list array iterator for print outs iterates over carsArray created on line 29
            while(iterator.hasNext())//iterate over all inventory to find stock number that is inputted
            {
               this.s = iterator.next();//sets s to the iteration 
               num = s.getStockNum();//sets num for current iteration for comparison
               if (num == stockNum)//conditional if statement
               {
                  found = true;
                  System.out.println("!!Stock Number "+ num + " Removed from system!!");//printout when found
                  carsArray.remove(count);// removal of inventory based on location
                  //System.out.println(s); //test point prints s
                  String Acquire_Date = s.getdateTime();//element is still held as s currently get old date
                  s.setdateTime("\nAcquisition Date: " + Acquire_Date + "\nDate Sold: "+TimeCapsule());//sale date will be added to history as string dateTime stored in object carsleeweek4       
                  soldArray.add(s);//adds to sold array we must have a record that we can adapt and use data from for graphs later on
                  log(carsArray);//log all changes to master log and for serilizable dat file of current inventory
                  try
                  {
                     FileOutputStream fileout = new FileOutputStream("SoldHistory.dat");//full save of dynamic ArrayList of Objects for next run
                     ObjectOutputStream outputDat = new ObjectOutputStream(fileout);
                     outputDat.writeObject(soldArray);
                     outputDat.close();
                     fileout.close();
                  }
                  catch (Exception s)//empty catch FileOutput requires try or throws Exception 
                  {
                     System.out.println("SYSTEM FILE MISSING STOP WORKING CONTACT MANAGER ERROR CODE DATMISS");//will display a code if the dat file is missing dat file must be present for program to run correctly
                  }
                  //clear all text boxes in GUI
                  jtfmake.setText("");
                  jtfmodel.setText("");
                  jtfcolor.setText("");
                  jtfstocknum.setText("");
                  jtfyear.setText("");
                  jtfmileage.setText("");
                  jtfsalesprice.setText("");
                  jtfpurchasePrice.setText("");
                  break;//leave 
               }
               count ++;
            }
            if (!found)//if found was never adapted 
            {
               jtfmake.setText("");
               jtfmodel.setText("");
               jtfcolor.setText("");
               jtfstocknum.setText(stockNum+": NOT FOUND");//not found display on GUI
               jtfyear.setText("");
               jtfmileage.setText("");
               jtfsalesprice.setText("");
               jtfpurchasePrice.setText("");
            }
            else//if item was found clear GUI
            {
               jtfmake.setText("");//clear all windows
               jtfmodel.setText("");
               jtfcolor.setText("");
               jtfstocknum.setText("");
               jtfyear.setText("");
               jtfmileage.setText("");
               jtfsalesprice.setText("");
               jtfpurchasePrice.setText("");
            }
         }
         catch (Exception s)//if user does not use correct button action useage
         {
            jtfmake.setText("");
            jtfmodel.setText("");
            jtfcolor.setText("");
            jtfstocknum.setText("!!Corect Stock Number Required!!");
            jtfyear.setText("");
            jtfmileage.setText("");
            jtfsalesprice.setText("");
            jtfpurchasePrice.setText("");
         }
      }
   }
   
   
   /* This class DASHBOARD button right now is just graphics */
   private class DashboardButtons extends JFrame implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {  
         JFrame Box2 = new JFrame();//must have seperate frame or frame will double Box 2 every call 
         Box2.setTitle("Dynamic Dashboard");//set box title Dashboard sub menu
         Box2.setSize(300,200); //set size
         Box2.setLocationRelativeTo(null);//set location auto adjustment to null
         Box2.setLayout(new GridLayout(6,2));//we have a 6 2 grid on this box how many lines and items this forms the box
         //Buttons and names for Dashboard
         JButton jbtInventory = new JButton("Inventory Report");//Button name
         JButton jbtHistogram = new JButton("Histogram of Inventory On Hand Report");
         JButton jbtSaleReport = new JButton("Sales Report");//Button name
         JButton jbtPieChart = new JButton("Pie Chart of Historical Sales");//Button name
         JButton jbtHistogramSold = new JButton("Histogram of Historical Sales");//Button name
         JButton jbtElectricTrend = new JButton("Total Inventory Cost Percentage Changes");//Button name 
         //Adding buttons we created to our frame 
         Box2.add(jbtInventory);
         Box2.add(jbtSaleReport);
         Box2.add(jbtHistogram);
         Box2.add(jbtPieChart);
         Box2.add(jbtHistogramSold);
         Box2.add(jbtElectricTrend);
         //creationg action listerns !! must have methods already created to code a actionlistener or it will fail on compile
         jbtInventory.addActionListener(new DashboardInventoryButtonListener());
         jbtHistogram.addActionListener(new DashboardHistogramButtonListener());
         jbtSaleReport.addActionListener(new DashboardSalesReportButtonListener());
         jbtPieChart.addActionListener(new DashboardPieChartButtonListener());
         jbtHistogramSold.addActionListener(new DashboardHistogramSoldButtonListener());
         jbtElectricTrend.addActionListener(new DashboardInventoryPriceAdjustment());
         //notice Dispose on close this is used for the action when we close the window this means dispose of this frame however main program keep running
         Box2.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         //show the frame
         Box2.setVisible(true);
      }
   }
   
   
   /* This class is for the dashboard INVENTORY REPORT button */
   private class DashboardInventoryButtonListener extends JFrame implements ActionListener//note must extends JFramne and implement action for buttons 
   {
      public void actionPerformed(ActionEvent eva)
      {
         JFrame Box2Sub = new JFrame();//make a new JFRAME or Window sub window created box 3
         Box2Sub.setTitle("Current Inventory Report"); //title for the Window we just created notice Box2Sub in front
         Box2Sub.setSize(560,500);//set size of Window
         Box2Sub.setLocationRelativeTo(null);//the way the box opens what it is next to
         Iterator<carsLeeWeek4> iterator = carsArray.iterator(); // list array iterator for print outs
         String textforGUI = "\t\t       Current Inventory";
         while(iterator.hasNext())//fills windows with all the current inventory like a text file
         {
            textforGUI+=("\n********************************************************************************************");
            textforGUI+=("\n"+iterator.next());
         }
         JTextArea text_hold = new JTextArea(textforGUI);//create a object to hold the text area for the String we just created
         JScrollPane text = new JScrollPane(text_hold);//Create a scroll option on window for Text area for when we have very large lists
         Box2Sub.add(text);//add to the Box GUI the pane
         Box2Sub.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//close operation note dispose that means the rest of the program keeps going just this window closes
         Box2Sub.setVisible(true);//turn the box2sub on 
      }
   }
   
     /* This class is for SALES REPORT button */
   private class DashboardSalesReportButtonListener extends JFrame implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {
         JFrame Box3Sub = new JFrame(); //create a sub box this is also box 3 because it is a seperate class to run at the same time with the JFrame
         Box3Sub.setTitle("Historical Sales Report");//title of window
         Box3Sub.setSize(560,500);//size
         Box3Sub.setLocationRelativeTo(null);//we are not using the auto location placement on the screen
         Iterator<carsLeeWeek4> iterator = soldArray.iterator(); //list array iterator for print outs
         String textforGUI = "\t\t   Historical Data on Sales"; //title for the text file to help with understanding
         while(iterator.hasNext()) //fill window
         {
            textforGUI+=("\n********************************************************************************************");
            textforGUI+=("\n"+iterator.next());
         }
      
         JTextArea GUI_Title = new JTextArea(textforGUI); // adds text to GUI top
         JScrollPane text = new JScrollPane(GUI_Title); //adds scroll buttons for this GUI screen
         Box3Sub.add(text);//add on our title
         Box3Sub.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //clears sub menu items without killing program
         Box3Sub.setVisible(true); //make visable
      }
   }

   
   /* This class is for the Dashboard Inventory on hand report Button creates a histogram and saves a file reuses that file for expanding knowledge on how to do that*/
   private class DashboardHistogramButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {
         Iterator<carsLeeWeek4> iterator = carsArray.iterator();
         imageFile(iterator); //my iteractor for use with the while loop below); //generate a new myimage.jpg file each time button is pressed
         JFrame DashboardHistorgram = new JFrame();
         DashboardHistorgram.dispose(); //dispose or null only frame if still present
         DashboardHistorgram.setTitle("Projected Profit/Loss Report of Inventory on Hand"); //set title can also be placed when frame is created 
         DashboardHistorgram.setSize(1000,1000); //size limits of windows
         try
         {
            BufferedImage image = ImageIO.read(getClass().getResource("/myimage.jpg")); //take new buffer of this file so its not static
            ImageIcon icon = new ImageIcon(image); //create image icon with image file
            JLabel label = new JLabel(); //create blank JLabel
            label.setIcon(icon); // set icon in label
            JScrollPane scroll = new JScrollPane(label); //set scroll with label
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//always on scroll not really needed
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //can see scroll bar has no button slide
            DashboardHistorgram.add(scroll);//add scroll to JFrame
            DashboardHistorgram.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // clears sub menu items without killing program
            DashboardHistorgram.pack();//pack for auto windows size
            DashboardHistorgram.setVisible(true);//turn on
         }
         catch (Exception es)
         {
            System.out.println("ERROR AT HISTOGRAM METHOD" + es);
         }
      }
   }
      

   /* This class creates a pie chart without the use of a file or a seperate method uses imageicon componet */
   private class DashboardPieChartButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {
         double NetExpenses = 0;
         double NetRevenue = 0;
         JFrame PieGraph = new JFrame("Historical Sales Report");
         PieGraph.setSize(400, 400);
         Iterator<carsLeeWeek4> iterator = soldArray.iterator();//we want to use the sold array for this graph 
         carsLeeWeek4 s;
         int LineMoveXRight = 0;
         int NextLine15Point = 0;
         int count = 0;
         int height = 400;
         int width = 400;
         BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
         Graphics2D g = image.createGraphics();
         g.setColor(Color.white);
         g.fillRect(0,0,width,height);
         g.setColor(Color.black);
         while(iterator.hasNext()) //just like a for loop goes over every element of my custom objects and sets it to s I call it iteraction as it iterates and performs an action from the getter methods
         {
            s = iterator.next();
            NetExpenses += s.getPurchasePrice(); //gets the data stored with getter methods 
            NetRevenue += s.getSalePrice(); //gets the data stored with getter methods
         }
         int whole = (int)NetExpenses+(int)NetRevenue;
         int startAngle = 0;
         int RevArchAngle = (int)((NetRevenue/whole)*361); // Example for 50% is 50/100*360 = 180 so we have percentages in the form of circles
         g.setColor(Color.green); //fill green for total NetRevenue in sales overall
         g.fillArc(75,75,200,200,startAngle,RevArchAngle);
         g.setColor(Color.red); //fill red for total NetExpesnse occured in historical sales reports
         int ExpeArchAngle = (int)((NetExpenses/whole)*361); //361 is used to provide room for line space issue
         g.fillArc(75,75,200,200,RevArchAngle,ExpeArchAngle);
         g.setColor(Color.black);
         g.drawOval(75,75,200,200);
         g.setColor(Color.black);
         g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
         g.drawString("Net Expenses: $"+NetExpenses,10,300);
         g.drawString("Net Revenue: $"+NetRevenue,10,320);
         ImageIcon icon = new ImageIcon(image); //create image icon component with image file must be done for graphics
         JLabel label = new JLabel(); //create blank JLabel
         label.setIcon(icon); // set icon in label
         JScrollPane scroll = new JScrollPane(label); //set scroll with label
         scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//always on scroll not really needed
         scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //can see scroll bar has no button slide
         PieGraph.add(scroll);//add scroll to JFrame
         PieGraph.pack(); //use of pack auto sets the window
         PieGraph.setVisible(true);
      }
   }
   
   
    /* This class will use component of Imageicon to help with adding buffered image directly into the GUI window without use of a file */
   private class DashboardHistogramSoldButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {
         Iterator<carsLeeWeek4> iterator = soldArray.iterator();
         JFrame DashboardHistogramSold = new JFrame();
         DashboardHistogramSold.dispose(); //dispose or null only frame if still present
         DashboardHistogramSold.setTitle("Actual Profit/Loss Report of Inventory on Hand"); //set title can also be placed when frame is created 
         DashboardHistogramSold.setSize(1000,1000); //size limits of windows
         carsLeeWeek4 s;
         int LineMoveXRight = 0;
         int NextLine15Point = 0;
         int count = 0;
         double NetExpenses = 0;
         double NetRevenue = 0;
         int height = 6000; //for screen size
         int width = 6000;
         BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //use of BufferedImage gives programmers the ability to save the drawing after as it is buffered as an object to convert to file
         Graphics2D g = bufferedImage.createGraphics(); // use of graphics open the ability to draw for the file
         g.setColor(Color.white); //sets color for next draw or fill
         g.fillRect(0,0,width,height); //fills all of screen as white
         g.setColor(Color.black);//sets color or next draw
         //break even line on grid
         g.setFont(new Font("Lucida Grande", Font.BOLD, 11)); //Zero line sets font for a dark 0
         g.drawString(""+0, 5, 254);//draw string with 0 on the line
         while(iterator.hasNext()) //create my graphics now inside the buffer
         {
            s = iterator.next(); //s grabs my element
            g.setColor(Color.BLACK);
            double cost = s.getPurchasePrice(); //gets the data stored with getter methods 
            double profit = s.getSalePrice();
            g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
            //System.out.println(g.getFont()); //test point to display current default font for graphics class
            //Loss percentage(L%) = (Loss/Cost price) × 100
            //if profit is less than cost this will display red bar
            if(profit<cost) //projected loss if statement
            {
               double loss = (cost-profit); //calculations for each element of s that is a carsLeeWeek4 object each time
               double LossPercentage = ((loss/cost)*100);
               //System.out.println(loss); //test point
               g.setColor(Color.RED);
               g.fillRect(30+LineMoveXRight,250,10,(int)LossPercentage);//(x,y)(Height,Width) must be mid of 500 side box so x 20+i moves 20 each time y (0,0) is upper left of box (gridwith overall, 0) is right
               g.setColor(Color.RED); //lost is red bars
               g.drawRect(30+LineMoveXRight,250,10,(int)LossPercentage);
               g.setColor(Color.RED);
               g.drawString(""+(count)+": "+s.getMake()+" "+s.getModel()+" Stock Number: "+s.getStockNum(), 20, 425+NextLine15Point);  //325
               g.drawString("       Cost: $"+cost+" For Sale: $"+profit+" EST Loss %: -"+(int)Math.round(LossPercentage)+"%", 20, 440+NextLine15Point);
               NetExpenses += cost;
               NetRevenue += profit;
               g.setColor(Color.BLACK);
               g.setFont(new Font("Lucida Grande", Font.BOLD, 13));
               g.drawString(""+(count), 30+LineMoveXRight, 265+(int)LossPercentage);
            }
            //Profit percentage(P%) = (Profit/Cost Price) × 100
            //if you made profit black bar
            else if(cost<=profit) //projected profit if statement or wash
            {
               double profitfromsale = (profit-cost);
               g.setColor(Color.GRAY); //was told that gray looks better for buiness this is for my bars 
               double LossPercentage = ((profitfromsale/cost)*100);
               //System.out.println(profitfromsale);//test point
               g.drawRect(30+LineMoveXRight,250-(int)LossPercentage,10,(int)LossPercentage); //draws bars for outline if you want black outline or any color
               g.fillRect(30+LineMoveXRight,250-(int)LossPercentage,10,(int)LossPercentage); //fills bars as solid color
               g.setColor(Color.BLACK); //sets color to black for use with string remeber as it goes it will print the information below x=0 y=0 is upper left of graph and as it increaases goes lower
               g.drawString(""+(count)+": "+s.getMake()+" "+s.getModel()+" Stock Number: "+s.getStockNum(), 20, 425+NextLine15Point);  //325
               g.drawString("       Cost: $"+cost+" For Sale: $"+profit+" EST Profit %: +"+(int)Math.round(LossPercentage)+"%", 20, 440+NextLine15Point);
               NetExpenses += cost;
               NetRevenue += profit;
               g.setFont(new Font("Lucida Grande", Font.BOLD, 13));
               g.drawString(""+(count), 30+LineMoveXRight, 245-(int)LossPercentage);
            }
            LineMoveXRight+=30; //my standard movment for X as I use a bar that is 30 I want a space also that is the same each time
            NextLine15Point+=30; //movement for my text prints must be told where to paint the words in the image
            count+=1; //for keeping the count 
         }
         //this section is just for the lines, this is dynamically built so each line must continue with each use
         g.setFont(new Font("Lucida Grande", Font.PLAIN, 9)); //set font back to default
         int linelen = LineMoveXRight+10;
         for(int i = 10; i<=100; i+=10)
         {
            g.setColor(Color.BLACK);
            g.drawString(" "+i, 1, (253-i));
            g.setColor(Color.GRAY);
            g.drawLine(25,(250-i),linelen,(250-i));
            g.setColor(Color.BLACK);
            g.drawString("-"+i, 1, (253+i));
            g.setColor(Color.GRAY);
            g.drawLine(25,(250+i),linelen,(250+i));
         }
         g.setColor(Color.BLACK);
         g.drawLine(20,250,linelen,250); 
         //Actuals and Expenses overall
         g.setColor(Color.BLACK);
         g.setFont(new Font("Lucida Grande", Font.BOLD, 13));
         g.drawString("ACTUAL REVENUE: $"+(NetRevenue), 20, 440+NextLine15Point); 
         g.drawString("EXPENSES: $"+(NetExpenses), 20, 455+NextLine15Point);
         g.drawString("ACTUAL NET REVENUE: $"+(NetRevenue-NetExpenses), 20, 470+NextLine15Point);
         ImageIcon icon = new ImageIcon(bufferedImage); //create image icon component with image that is buffered must be done for graphics
         bufferedImage.flush(); //flush buffer its got to be clean for next use
         g.dispose(); //flush graphics graphics are abstract so I want to flush also
         JLabel label = new JLabel(); //create blank JLabel
         label.setIcon(icon); // set icon in label
         JScrollPane scroll = new JScrollPane(label); //set scroll with label
         scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//always on scroll not really needed
         scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //can see scroll bar has no button slide
         //now that image is buffered add it as the icon inside the label inside the scroll remeber component is required not raw image
         DashboardHistogramSold.add(scroll);//add scroll to JFrame
         DashboardHistogramSold.pack(); //use of pack auto sets the window
         DashboardHistogramSold.setVisible(true); //notice with use of ImageIcon no file is needed ImageIcon is a component
      }
   }


    /* FIX ME CREATE USE OF EMUM FOR VIEW AND UNDERSTANDING CALL IT FOR USE WITH ITERACTOR FOR FULL PRICE CHANGE OF INVENTORY ON HAND */
   public JTextField jtfpercentadjustment = new JTextField(20);//text box area for sales price
   private class DashboardInventoryPriceAdjustment implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {
         JFrame DashboardInventoryPriceAdjustment = new JFrame(); //create a sub box this is also box 3 because it is a seperate class to run at the same time with the JFrame
         DashboardInventoryPriceAdjustment.setTitle("Total Inventory Price Adjustment Dashboard");//title of window
         DashboardInventoryPriceAdjustment.setSize(600,100); //set size
         DashboardInventoryPriceAdjustment.setLocationRelativeTo(null);//set location auto adjustment to null
         DashboardInventoryPriceAdjustment.setLayout(new GridLayout(3,2));//we have a 6 2 grid on this box how many lines and items this forms the box
         DashboardInventoryPriceAdjustment.setLocationRelativeTo(null);//we are not using the auto location placement on the screen
         JButton jbtIncrease = new JButton("INCREASE SALES PRICES");//Button name
         JButton jbtdecrease = new JButton("DECREASE SALES PRICES");
         DashboardInventoryPriceAdjustment.add(jbtIncrease);
         DashboardInventoryPriceAdjustment.add(jbtdecrease);
         JPanel p1 = new JPanel (new FlowLayout(FlowLayout.CENTER)); //create panel for buttons
         DashboardInventoryPriceAdjustment.add(p1);
         p1.add(new JLabel("                       Enter Percent:"));//add text for textbox
         DashboardInventoryPriceAdjustment.add(jtfpercentadjustment);
         DashboardInventoryPriceAdjustment.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //clears sub menu items without killing program
         DashboardInventoryPriceAdjustment.setVisible(true); //make visable
         //this has button actions required also for postive and negative buttons 
         jbtIncrease.addActionListener(new DashboardPercentChangeIncrease());
         jbtdecrease.addActionListener(new DashboardPercentChangeDecrease());
      }
   }
   
   /* This method is when the increase precentage is used */
   private class DashboardPercentChangeIncrease implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {
         change = INVENTORYCHANGE.INCREASE;
         try //we want a try catch as if you enter the nothing it will not crash the program 
         {
            double salePercentAdjuster = Double.parseDouble(jtfpercentadjustment.getText());//sales price for storage
            adjustment(salePercentAdjuster, change);
         }
         catch (Exception p)
         {
         }        
      }
   }
   
   /* This method is when the decrease precentage is used */
   private class DashboardPercentChangeDecrease implements ActionListener
   {
      public void actionPerformed(ActionEvent eva)
      {
         change = INVENTORYCHANGE.DECREASE;
         try
         {
            double salePercentAdjuster = Double.parseDouble(jtfpercentadjustment.getText());//sales price for storage
            adjustment(salePercentAdjuster, change);
         }
         catch (Exception p)
         {
         }
      }
   }

   /* This private method is for the adjustment actions by way of INVENTORYCHANGE enum class */
   private void adjustment (double salePercentAdjuster, INVENTORYCHANGE change)
   {
      Iterator<carsLeeWeek4> iterator = carsArray.iterator(); //list array iterator for print outs
      carsLeeWeek4 s;
      if(change == INVENTORYCHANGE.INCREASE)
      {
         while(iterator.hasNext()) //create my graphics now inside the buffer
         {
            s = iterator.next(); //s grabs my element
            double currentPrice = s.getSalePrice();
            double pricePer = currentPrice/100;//Divide the number by 100 (move the decimal place two places to the left).
            pricePer *= salePercentAdjuster;//Multiply this new number by the percentage you want to take off.
            s.setSalePrice(currentPrice+pricePer);//Subtract the number from step 2 from the original number. This is your percent off number.
         }
      }
      if(change == INVENTORYCHANGE.DECREASE)
      { 
         while(iterator.hasNext()) //create my graphics now inside the buffer
         {
            s = iterator.next(); //s grabs my element
            double currentPrice = s.getSalePrice();
            double pricePer = currentPrice/100;//Divide the number by 100 (move the decimal place two places to the left).
            pricePer *= salePercentAdjuster;//Multiply this new number by the percentage you want to take off.
            s.setSalePrice(currentPrice-pricePer);//Subtract the number from step 2 from the original number. This is your percent off number.
         }
      }
      jtfpercentadjustment.setText("Full Inventory Update Completed");
   }


   /* This method returns a accurate time as a string when called must be used for dealer aqustion */
   private static String TimeCapsule() //this is required as time is not static it is ever changing and requires a new pull with each use and it is a string so to store it must be the same time it is required
   {
      GregorianCalendar calendar = new GregorianCalendar(); //calendar object
      DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.US);//formatter object
      TimeZone timezone = TimeZone.getTimeZone("PST");//timezone object
      formatter.setTimeZone(timezone);
      return (formatter.format(calendar.getTime())); //Time creation call string object to be passed with each use for current time stratum by java program
   }
   
   
   /* This method will log and seralize a dat file with current inventory when called */
   private static void log(ArrayList<carsLeeWeek4> carsArray)
   {
      Iterator<carsLeeWeek4> iterator = carsArray.iterator(); //must use iterator do to ArrayList use
      //this try block is for logging every change performed by staff for record keeping and cross checking if it is ever required
      try
      {
         FileOutputStream FileStreamOut = new FileOutputStream(new File("InventoryLogofChanges.txt"), true); //text log file master log will apend to the file and every change of inventory or updates is logged
         PrintStream output = new PrintStream(FileStreamOut); // outputs file to where you have the .txt file saved 
         output.println("\nFile updated:" + TimeCapsule() +"\n");//log of changes with the real time 
         while(iterator.hasNext()) //iteraction every iteraction adds to the output file in long term record file !!!!! This should have specific hidden location and hidden file
         {
            output.println("\n*******************"); //in file they helps break up each car item
            output.println(iterator.next()); //calls inventory from cars and sends the inventory and time for print out
         }
         output.println("END UPDATE FOR: "+TimeCapsule());
         //this try block is for serializing the carsArray List of objects into a .dat file for reload when program is ran again
         try
         { 
            FileOutputStream fileout = new FileOutputStream("Inventory.dat"); //full save of dynamic ArrayList of Objects for next run
            ObjectOutputStream outputDat = new ObjectOutputStream(fileout); //create a object for output data
            outputDat.writeObject(carsArray); //write the file
            outputDat.close(); //close the output
            fileout.close(); //close the file so it saves
         }
         catch(IOException i)
         {
            System.out.println("SYSTEM FILE MISSING STOP WORKING CONTACT MANAGER ERROR CODE DATMISS"); //will display a code if the dat file is missing simple DAT MISS manager will understand if trained and can get a archive file
         }
      }
      catch(FileNotFoundException r)
      {
         System.out.println("SYSTEM FILE MISSING STOP WORKING CONTACT MANAGER ERROR CODE ILCMISS"); //will display a error code if the Inventory Log Control is missing
      
      }
   }
   
   
   /* This method creates a report file when called to be used with scrollable inside of Jpanel dynamically for use with histogram to create files */
   private void imageFile(Iterator<carsLeeWeek4> iterator)
   {
   /*
   Reason here is to save the file you can also set it to save it each time 
   This method will only save it and repalce it for use with calling it again for learning
   
   Profit percentage(P%) = (Profit/Cost Price) × 100
   Loss percentage(L%) = (Loss/Cost price) × 100
   S.P. = {(100 + P%)/100} × CP(if SP > CP)
   S.P. = {(100 – L%)/100} × CP(if SP < CP)
   C.P. = {100/(100 + P%)} × SP(if SP > CP)
   C.P. = {100/(100 – L%)} × SP(if SP < CP)
   */ 
      carsLeeWeek4 s;
      int LineMoveXRight = 0;
      int NextLine15Point = 0;
      int count = 0;
      double NetExpenses = 0;
      double NetRevenue = 0;
      int height = 6000; //for screen size
      int width = 6000;
      BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //use of BufferedImage gives programmers the ability to save the drawing after as it is buffered as an object to convert to file
      Graphics2D g = bufferedImage.createGraphics(); // use of graphics open the ability to draw for the file
      g.setColor(Color.white); //sets color for next draw or fill
      g.fillRect(0,0,width,height); //fills all of screen as white
      g.setColor(Color.black);//sets color or next draw
      //break even line on grid
      g.setFont(new Font("Lucida Grande", Font.BOLD, 11)); //Zero line sets font for a dark 0
      g.drawString(""+0, 5, 254);//draw string with 0 on the line
      while(iterator.hasNext()) //create my graphics now inside the buffer
      {
         s = iterator.next(); //s grabs my element
         g.setColor(Color.BLACK);
         double cost = s.getPurchasePrice(); //gets the data stored with getter methods 
         double profit = s.getSalePrice();
         g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
         //System.out.println(g.getFont()); //test point to display current default font for graphics class
         //Loss percentage(L%) = (Loss/Cost price) × 100
         //if profit is less than cost this will display red bar
         if(profit<cost) //projected loss if statement
         {
            double loss = (cost-profit); //calculations for each element of s that is a carsLeeWeek4 object each time
            double LossPercentage = ((loss/cost)*100);
            //System.out.println(loss); //test point
            g.setColor(Color.RED);
            g.fillRect(30+LineMoveXRight,250,10,(int)LossPercentage);//(x,y)(Height,Width) must be mid of 500 side box so x 20+i moves 20 each time y (0,0) is upper left of box (gridwith overall, 0) is right
            g.setColor(Color.RED); //lost is red bars
            g.drawRect(30+LineMoveXRight,250,10,(int)LossPercentage);
            g.setColor(Color.RED);
            g.drawString(""+(count)+": "+s.getMake()+" "+s.getModel()+" Stock Number: "+s.getStockNum(), 20, 425+NextLine15Point);  //325
            g.drawString("       Cost: $"+cost+" For Sale: $"+profit+" EST Loss % at current sale price: -"+(int)Math.round(LossPercentage)+"%", 20, 440+NextLine15Point);
            NetExpenses += cost;
            NetRevenue += profit;
            g.setColor(Color.BLACK);
            g.setFont(new Font("Lucida Grande", Font.BOLD, 13));
            g.drawString(""+(count), 30+LineMoveXRight, 265+(int)LossPercentage);
         }
         //Profit percentage(P%) = (Profit/Cost Price) × 100
         //if you made profit black bar
         else if(cost<=profit) //projected profit if statement or wash
         {
            double profitfromsale = (profit-cost);
            g.setColor(Color.GRAY); //was told that gray looks better for buiness this is for my bars 
            double LossPercentage = ((profitfromsale/cost)*100);
            //System.out.println(profitfromsale);//test point
            g.drawRect(30+LineMoveXRight,250-(int)LossPercentage,10,(int)LossPercentage); //draws bars for outline if you want black outline or any color
            g.fillRect(30+LineMoveXRight,250-(int)LossPercentage,10,(int)LossPercentage); //fills bars as solid color
            g.setColor(Color.BLACK); //sets color to black for use with string remeber as it goes it will print the information below x=0 y=0 is upper left of graph and as it increaases goes lower
            g.drawString(""+(count)+": "+s.getMake()+" "+s.getModel()+" Stock Number: "+s.getStockNum(), 20, 425+NextLine15Point);  //325
            g.drawString("       Cost: $"+cost+" For Sale: $"+profit+" EST Profit % at current sale price: +"+(int)Math.round(LossPercentage)+"%", 20, 440+NextLine15Point);
            NetExpenses += cost;
            NetRevenue += profit;
            g.setFont(new Font("Lucida Grande", Font.BOLD, 13));
            g.drawString(""+(count), 30+LineMoveXRight, 245-(int)LossPercentage);
         }
         LineMoveXRight+=30; //my standard movment for X as I use a bar that is 30 I want a space also that is the same each time
         NextLine15Point+=30; //movement for my text prints must be told where to paint the words in the image
         count+=1; //for keeping the count 
      }
      //this section is just for the lines, this is dynamically built so each line must continue with each use
      g.setFont(new Font("Lucida Grande", Font.PLAIN, 9)); //set font back to default
      int linelen = LineMoveXRight+10;
      for(int i = 10; i<=100; i+=10)
      {
         g.setColor(Color.BLACK);
         g.drawString(" "+i, 1, (253-i));
         g.setColor(Color.GRAY);
         g.drawLine(25,(250-i),linelen,(250-i));
         g.setColor(Color.BLACK);
         g.drawString("-"+i, 1, (253+i));
         g.setColor(Color.GRAY);
         g.drawLine(25,(250+i),linelen,(250+i));
      }
      g.setColor(Color.BLACK);
      g.drawLine(20,250,linelen,250); 
      //Projections and Expenses overall
      g.setColor(Color.BLACK);
      g.setFont(new Font("Lucida Grande", Font.BOLD, 13));
      g.drawString("PROJECTED REVENUE: $"+(NetRevenue), 20, 440+NextLine15Point); 
      g.drawString("EXPENSES: $"+(NetExpenses), 20, 455+NextLine15Point);
      g.drawString("PROJECTED NET REVENUE: $"+(NetRevenue-NetExpenses), 20, 470+NextLine15Point);
      //File Generation for JScroll use this is when we save the files 
      File file = new File("Graph.png"); //png file
      File file2 = new File("myimage.jpg");//jpg you can also point to a path where to save even a server if needed
      try
      {
         ImageIO.write(bufferedImage, "png", file); //now I am going to take this buffer and dump it to the file
         ImageIO.write(bufferedImage, "jpg", file2);
         bufferedImage.flush(); //flush buffer its got to be clean for next use
         g.dispose(); //flush graphics graphics are abstract so I want to flush also
      }
      catch (Exception im)
      {
         System.out.println("ERROR AT IMAGE FILE GENERATION");
      }
   }
   
      
  /* !!!MAIN RUN!!!! */  
   
   
   /* !!!!MAIN RUN!!!!! for LeeShowGridLayout class that is action button driver class for carleeweek4class objects file */
   public static void main(String[] args)
   throws ClassNotFoundException, IOException 
   {
      FileInputStream fis = new FileInputStream("Inventory.dat"); //must have this with files to work correctly
      FileInputStream fis_sold = new FileInputStream("SoldHistory.dat"); //must have this with files to work correctly to generate new one run with commented out lines
      ObjectInputStream input = new ObjectInputStream(fis); //input items inside of Input streamer
      ObjectInputStream input_log = new ObjectInputStream(fis_sold); //input sales report log 
      ArrayList<carsLeeWeek4> carsArray = new ArrayList<carsLeeWeek4>(); //use of arraylist of objects dynamic programming public for use with full program calls carsLeeWeek4 for object creations
      ArrayList<carsLeeWeek4> soldArray = new ArrayList<carsLeeWeek4>(); //use of arraylist of objects dynamic programming public for use with full program calls carsLeeWeek4 for object creations
      carsArray = (ArrayList<carsLeeWeek4>) input.readObject(); //must input with seralizable for ArrayList of Objects that is pulled from the .dat file
      soldArray = (ArrayList<carsLeeWeek4>) input_log.readObject(); //must implement serializeable for ArrayList of Objects of type carsLeeWeek4 note the typecast this must be done or it will not work
      LeeShowGridLayout frame = new LeeShowGridLayout(carsArray, soldArray, ""); //create a instance of frame we have programmed above
      frame.pack(); //pack all items for run
      frame.setTitle("Dynamic Inventory Systems"); //name GUI window inventory
      frame.setSize(590, 300); //set size of window
      frame.setLocationRelativeTo(null); //does not need to be aligned
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //action on pressing close main frame or GUI window when close is pushed closes program main action This will quit when the GUI main window is closed
      frame.setVisible(true); //turn this gui on now GUI takes over
   }
}