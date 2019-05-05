/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  NumberGenerator.java
 * Purpose    :  Program to generate 997940 random numbers between 1-20 in the distribution:
 * 1-12 83,000
 * 13 1,000
 * 14 500
 * 15 250
 * 16 100
 * 17 50
 * 18 25
 * 19 10
 * 20 5
 * with no consecutive numbers matching. This program writes data to test.output
 * and displays lines which 20 appears on the command line


 * @author    :  Megan Talbert
 * Date       :  2019-05-03
 * Description:  This program is used to generate random numbers as specified in purpose
 * Notes      :  Takes about 2 minutes to run right now, and sometimes there are consecutive 12s
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2019-05-03  Megan Talbert Initial release
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileWriter;

public class NumberGenerator {

  public ArrayList<Integer> possibleIndices = new ArrayList<Integer>();
  public ArrayList<Integer> answer = new ArrayList<Integer>();

  //Constructor
  public NumberGenerator(){

  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to make original list of all possible indices up to value length
   *  @param Int length, total numbers to be generated
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  private void makeIndicesList(int length){
    for (int i=0;i<length;i++){
      possibleIndices.add(i); //makes arrayList of all possible indices that can be used
      answer.add(0); //sets capacity of answer to the correct value so that we can add at any index
    }
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to randomly place value within available indices
   *  @param Int num the number to be placed
   * @param Int copies number of times to place num within the answer ArrayList
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   private void placeValues(int num, int copies){
     ArrayList<Integer> temp = new ArrayList<Integer>();
     int rand;
     int index;
     for (int n=0;n<copies;n++){
       rand = (int)(Math.random()*(possibleIndices.size()));
       //System.out.println("rand is "+rand);
       index = possibleIndices.get(rand);// gets random number from possibleIndices
       if (num ==20){
         System.out.println("20 is at line " + index);
       }
       answer.set(index,num); //sets num to answer at that index
       possibleIndices.remove(rand); //removes this index from the possibleIndices ArrayList
       if (rand<possibleIndices.size()){ //makes sure we won't have out of bound error
         if (index+1 == possibleIndices.get(rand)){//removes bordering indices so that consecutive numbers are not same
            temp.add(possibleIndices.get(rand));
            possibleIndices.remove(rand);
          }
       }
       if ((rand>0)){ //makes sure we won't have out of bound error
         if (index-1 == possibleIndices.get(rand-1)){//removes bordering indices so that consecutive numbers are not same
           temp.add(possibleIndices.get(rand-1));
           possibleIndices.remove(rand-1);
         }
       }
     }
     // System.out.println(Arrays.toString(answer.toArray()));
     // System.out.println(Arrays.toString(possibleIndices.toArray()));
     for (int i=0;i<temp.size();i++){
       possibleIndices.add(temp.get(i)); //adds bordering indices back into possibleIndices so that next number can use them
     }
     Collections.sort(possibleIndices); //sorts possibleIndices bc it will be out of order following temp being appended
   }

   /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to write to file
    *  @param String file of the filename which we are writing to
    *  @param ArrayList RAdata which is the array which we will write into the file
    * NOTE: This code was adapted from https://stackoverflow.com/questions/6548157/how-to-write-an-arraylist-of-strings-into-a-text-file
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   private void createFile(String file, ArrayList<Integer> RAdata){
     try{
        FileWriter writer = new FileWriter(file + ".txt");
        int size = RAdata.size();
        for (int i=0;i<size;i++) {
            String str = RAdata.get(i).toString();
            writer.write(str);
            if(i < size-1){//This prevent creating a blank like at the end of the file**
                writer.write("\n");
            }
        }
        writer.close();
      }
      catch(Exception e){
        System.out.println("Can't write to file");
        System.exit(1);
      }
    }


  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  the main method validates args then uses inputs from command line to run program
   *  @param  args  String array which contains command line arguments
   *  NOTE: this is used for testing while developing, DynamicChangemakerTestHarness.java has full tests
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public static void main(String args[]){
    NumberGenerator ng = new NumberGenerator();
    ng.makeIndicesList(997940);
    ng.placeValues(13,1000);
    //System.out.println("value 13 placed");
    ng.placeValues(14,500);
    //System.out.println("value 14 placed");
    ng.placeValues(15,250);
    //System.out.println("value 15 placed");
    ng.placeValues(16,100);
    //System.out.println("value 16 places");
    ng.placeValues(17,50);
    //System.out.println("value 17 placed");
    ng.placeValues(18,25);
    //System.out.println("Value 18 placed");
    ng.placeValues(19,10);
    //System.out.println("value 19 placed");
    ng.placeValues(20,5);
    //System.out.println(ng.possibleIndices.size() + " indices left");
    for (int num = 1;num<=11;num++){
      ng.placeValues(num,83000);
    //  System.out.println("value " + num + " placed");
    //  System.out.println(ng.possibleIndices.size() + " indices left");
    }
    for (int n=0; n<ng.answer.size();n++){ //this is a workaround because 12 causes an error if there are any consecutive zeros
      if (ng.answer.get(n)==0){
        ng.answer.set(n,12);
      }
    }
    // ng.makeIndicesList(10);
    // ng.placeValues(4,4);
    // ng.placeValues(2,2);
  //  System.out.println("20s are on lines " + Arrays.toString(ng.possibleIndices.toArray()));
    ng.createFile("test.output",ng.answer);
    System.out.println("results have been printed to test.output.txt");

  //  System.out.println(Arrays.toString(ng.answer.toArray()));
  //  System.out.println(Arrays.toString(ng.possibleIndices.toArray()));
  }

}
