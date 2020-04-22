import java.io.*;
import java.util.*;

public class write{
  final static int N = 2046;
  public static void main(String[] args) {
    Random r = new Random();
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
        for(int j = 0; j < N; j++){
          myWriter.write(r.nextInt(3));
          myWriter.write(r.nextInt(3));
        }
      }
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
