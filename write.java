import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class write{
  final static int N = 100;
  public static void main(String[] args) {
    try {
      File myObj = new File(N + ".txt");
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
      FileWriter myWriter = new FileWriter(N + ".txt");

      for(int i = 0; i < N; i++){
        myWriter.write(ThreadLocalRandom.current().nextLong(10^12) + 1);
      }
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
