import java.util.*;
import java.io.*;

public class partition{
    final static long MAX_ITER = 25000;
    final static Random rand = new Random();

    public static void main (String[] args){
      try{
        // Usage: java partition 0 alg inputfile
        // algorithm codes: 0 - KK; 1 - RR; 2 -- Hill; 3 -- SimAnneal
        //                  11 - PP RR; 12 -- PP Hill; 13 -- PP SimAnneal
        int bool = Integer.parseInt(args[0]);
        int alg = Integer.parseInt(args[1]);
        String filepath = args[2];
        long[] sequence = new long[100];


        // Read file into array
        Scanner scanner = new Scanner(new File(filepath));
        int counter = 0;
        while (scanner.hasNextLong()){
          sequence[counter] = scanner.nextLong();
          counter++;
        }
        long startTime = 0;

        if (bool == 1) {
          startTime = System.nanoTime(); // stores the current time
        }
        // KK algorithm
        if (alg == 0) {
          //System.out.println("Karmarkar-Karp residue: " + karmKarp(sequence));
          System.out.println(karmKarp(sequence));
        }

        // Standard repeated random
        if (alg == 1) {
          int[] sol = stdRepRand(sequence);
          //System.out.println("STD repeated random: " + stdResidue(sequence, sol));
          System.out.println(stdResidue(sequence, sol));
        }

        // Standard hill
        else if (alg == 2) {
          int[] sol = stdHill(sequence);
          //System.out.println("STD hill: " + stdResidue(sequence, sol));
          System.out.println( stdResidue(sequence, sol));
        }

        // Standard simulated annealing
        else if (alg == 3) {
          int[] sol = stdSimAnn(sequence);
          //System.out.println("STD hill: " + stdResidue(sequence, sol));
          System.out.println(stdResidue(sequence, sol));
        }

        // Prepart repeated random
        else if (alg == 11) {
          int[] sol =  prePartRepRand(sequence);
          //System.out.println("PP repeated random: " + prePartResidue(sequence, sol));
          System.out.println(prePartResidue(sequence, sol));
        }

        // Prepart hill climbing
        else if (alg == 12) {
          int[] sol =  prePartHill(sequence);
          //System.out.println("PP hill climbing: " + prePartResidue(sequence, sol));
          System.out.println(prePartResidue(sequence, sol));
        }

        // Prepart simulated annealing
        else if (alg == 13) {
          int[] sol =  prePartSimAnn(sequence);
          //System.out.println("PP sim anneal: " + prePartResidue(sequence, sol));
          System.out.println(prePartResidue(sequence, sol));
        }

        if (bool == 1) {
        System.out.println("Time: " + (System.nanoTime() - startTime)/Math.pow(10,9) + "sec");
        startTime = System.nanoTime();
        }

      }
      catch(Exception e){System.out.println("Usage: java partition 0 alg inputfile");}      
    }

    // Implement KK Algorithm
    private static long karmKarp(long[] sequence) {
      int size = sequence.length;
      buildHeap(sequence);

      while (size > 1) {
        long max1 = extractMax(sequence, size);
        size--;
        long max2 = extractMax(sequence, size);
        long diff = max1 - max2;
        insert(sequence, diff, size);
      }

      return sequence[0];
    }

    // Inserts value into the heap by changing one of the 0s to val
    private static void insert(long[] arr, long val, int size) {
      arr[size - 1] = val;
      int index = size - 1;
      while (index != 0) {
        int parent = (index - 1) / 2;
        if (arr[parent] < arr[index]) {
          long temp = arr[parent];
          arr[parent] = arr[index];
          arr[index] = temp;
          index = parent;
        }
        else {
          break;
        }
      }
    }

    // Returns the max element from the heap and fixes everything else
    private static long extractMax(long[] arr, int size) {
      long max = arr[0];
      arr[0] = arr[size - 1];
      arr[size - 1] = 0;
      maxHeapify(arr, 0);
      return max;
    }

    // Function for creating max heap
    private static void buildHeap(long[] arr) {
      int start = arr.length / 2;
      for (int i = start; i >= 0; i--) {
        maxHeapify(arr, i);
      }
    }

    // Recursive call for max heap algorithm
    private static void maxHeapify(long[] arr, int i) {
      int max = i;
      int left = 2 * i + 1;
      int right = 2 * i + 2;

      if ((left < arr.length) && (arr[left] > arr[max])) {
        max = left;
      }

      if ((right < arr.length) && (arr[right] > arr[max])) {
        max = right;
      }

      if (max != i) {
        long temp = arr[i];
        arr[i] = arr[max];
        arr[max] = temp;

        maxHeapify(arr, max);
      }

    }

    // Implement Standard Representation
    private static int[] generateStd() {
        int[] rep = new int[100];
        for (int i = 0; i < rep.length; i++) {
            int prob = rand.nextInt(2);
            if (prob == 0) {
                rep[i] = 1;
            }
            else {
                rep[i] = -1;
            }
        }
        return rep;
    }

    private static long stdResidue(long[] sequence, int[] solution) {
        long residue = 0;
        for (int i = 0; i < sequence.length; i++) {
            residue += sequence[i] * solution[i];
        }
        return Math.abs(residue);
    }

    private static int[] stdNeighbor(int[] solution) {
      int length = solution.length;
      int i = rand.nextInt(length);
      int j = rand.nextInt(length);
      while(i == j){
        j = rand.nextInt(length);
      }

      solution[i] *= -1;
      int prob = rand.nextInt(2);
      if (prob == 0){
        solution[j] *= -1;
      }

      return solution;
    }

    // Implement Prepartition Representation
    private static int[] generatePrePart() {
        int[] rep = new int[100];
        for (int i = 0; i < rep.length; i++) {
            rep[i] = rand.nextInt(rep.length);
        }

        return rep;
    }

    private static long prePartResidue(long[] sequence, int[] solution) {
        long[] modified = new long[100];
        for (int i = 0; i < modified.length; i++) {
            modified[solution[i]] += sequence[i];
        }
        return karmKarp(modified);
    }

    private static int[] prePartNeighbor(int[] solution){
        int length = solution.length;
        int i = rand.nextInt(length);
        int j = rand.nextInt(length);
        while(solution[i] == j){
          j = rand.nextInt(length);
        }
        solution[i] = j;
        return solution;
    }

    // Implement Repeated Random
    private static int[] stdRepRand(long[] sequence) {
      int[] S = generateStd();
      for (int i = 0; i < MAX_ITER; i++) {
        int[] random = generateStd();
        if (stdResidue(sequence, random) < stdResidue(sequence, S)) {
          S = random;
        }
      }
      return S;
    }

    private static int[] prePartRepRand(long[] sequence) {
      int[] S = generatePrePart();
      for (int i = 0; i < MAX_ITER; i++) {
        int[] random = generatePrePart();
        if (prePartResidue(sequence, random) < prePartResidue(sequence, S)) {
          S = random;
        }
      }
      return S;
    }

    // Implement Hill Climbing
    private static int[] stdHill(long[] sequence){
        int[] curr = generateStd();
        int[] best = curr.clone();

        for (int i = 0; i < MAX_ITER; i++){
          curr = stdNeighbor(curr);
          if (stdResidue(sequence, curr) < stdResidue(sequence, best)) {
            best = curr.clone();
          }
        }
        return best;
    }

    private static int[] prePartHill(long[] sequence){
        int[] curr = generatePrePart();
        int[] best = curr.clone();

        for (int i = 0; i < MAX_ITER; i++){
          curr = prePartNeighbor(curr);
          if (prePartResidue(sequence, curr) < prePartResidue(sequence, best)){
            best = curr.clone();
          }
        }
        return best;
    }

    // Implement Simulated Annealing
    private static int[] stdSimAnn(long[] sequence){
        int[] curr = generateStd();
        int[] better = curr.clone();
        int[] best = curr.clone();

        for (int i = 0; i < MAX_ITER; i++){
            curr = stdNeighbor(curr);
            double cool = Math.exp(-1 * (stdResidue(sequence, curr) - stdResidue(sequence, better) / T(i)));
            if (stdResidue(sequence, curr) < stdResidue(sequence, better) || rand.nextDouble() < cool){
              better = curr.clone();
            }
            if (stdResidue(sequence, better) < stdResidue(sequence, best)){
              best = better.clone();
            }
        }

        return best;
    }

    private static int[] prePartSimAnn(long[] sequence){
      int[] curr = generatePrePart();
      int[] better = curr.clone();
      int[] best = curr.clone();
      for (int i = 0; i < MAX_ITER; i++){
          curr = prePartNeighbor(curr);
          double cool = Math.exp(-1 * (prePartResidue(sequence, curr) - prePartResidue(sequence, better)) / T(i));
          if (prePartResidue(sequence, curr) < prePartResidue(sequence, better) || rand.nextDouble() < cool){
            better = curr.clone();
          }

          if (prePartResidue(sequence, better) < prePartResidue(sequence, best)){
            best = better.clone();
          }
      }

      return best;
    }

    private static double T(int iter){
      return Math.pow(10,10) * Math.pow(0.8, Math.floor(iter / 300));
    }
}
