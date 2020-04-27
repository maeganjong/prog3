import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class write{
  public static void main(String[] args) {
    for (int i = 1; i <= 100; i++){
      try {
        File myObj = new File(i + ".txt");
        if (myObj.createNewFile()) {
          System.out.println("File created: " + myObj.getName());
        } else {
          System.out.println("File already exists.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

      try {
        BufferedWriter myWriter = new BufferedWriter(new FileWriter(i + ".txt"));

        for(int j = 0; j < 100; j++){
          myWriter.write(Long.toString(ThreadLocalRandom.current().nextLong((long) Math.pow(10,12)) + 1) + "\n");
          // myWriter.write(ThreadLocalRandom.current().nextLong((long) Math.pow(10,12)) + 1);
        }
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }

  }
}
