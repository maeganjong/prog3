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

        // KK algorithm
        if (alg == 0) {
          System.out.println("Karmarkar-Karp residue: " + karmKarp(sequence));
        }

        // Standard representation
        else if (alg == 1 || alg == 2 || alg == 3) {
          int[] random = generateStd();
          if (alg == 1) {

          }
          else if (alg == 2) {
            // Standard hill climbing
          }
          else {
            // Simulated annealing
          }

        // Prepartitioned repeated random
        else if (alg == 11) {
          System.out.println("Preapartitioned repeated random");
        }

        // Prepartitioned hill climbing
        else if (alg == 12) {
          System.out.println("Prepartitioned hill climbing");
        }

        // Prepartitioned simulated annealing
        else if (alg == 13) {
          System.out.println("Prepartitioned simulated annealing");
        }
        
        int[] prepartitioned = generatePrePart();
        System.out.println("PP residue: " + prePartResidue(sequence, prepartitioned));

      }
      catch(Exception e){System.out.println("Usage: java partition 0 alg inputfile");}
    }

    // Implement KK Algorithm
    private static long karmKarp(long[] sequence) {
        Arrays.sort(sequence); // Sorts in decreasing order
        int i = sequence.length - 1;
        while(sequence[i - 1] != 0){
            sequence[i] -= sequence[i - 1];
            sequence[i-1] = 0;
            Arrays.sort(sequence);
        }
        return sequence[i];
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
        int[] S = generateStd();
        for (int i = 0; i < MAX_ITER; i++){
          int[] neighbor = stdNeighbor(S);
          if (stdResidue(sequence, neighbor) < stdResidue(sequence, S)){
            S = neighbor;
          }
        }
        return S;
    }

    private static int[] prePartHill(long[] sequence){
        int[] S = generatePrePart();
        for (int i = 0; i < MAX_ITER; i++){
          int[] neighbor = prePartNeighbor(S);
          if (prePartResidue(sequence, neighbor) < prePartResidue(sequence, S)){
            S = neighbor;
          }
        }
        return S;
    }

    // Implement Simulated Annealing
    private static int[] stdSimAnn(long[] sequence){
        int[] S = generateStd();
        int[] buffer = S;
        for (int i = 0; i < MAX_ITER; i++){
            int[] neighbor = stdNeighbor(S);
            if (stdResidue(sequence, neighbor) < stdResidue(sequence, S)){
              S = neighbor;
            }
            else{
              if(rand.nextDouble() < Math.exp(-1 * (stdResidue(sequence, neighbor) - stdResidue(sequence, S) / T(i)))){
                S = neighbor;
              }
            }

            if (stdResidue(sequence, S) < stdResidue(sequence, buffer)){
              buffer = S;
            }
        }

        return buffer;
    }

    private static int[] prePartSimAnn(long[] sequence){
      int[] S = generatePrePart();
      int[] buffer = S;
      for (int i = 0; i < MAX_ITER; i++){
          int[] neighbor = prePartNeighbor(S);
          if (prePartResidue(sequence, neighbor) < prePartResidue(sequence, S)){
            S = neighbor;
          }
          else{
            if(rand.nextDouble() < Math.exp(-1 * (prePartResidue(sequence, neighbor) - prePartResidue(sequence, S)) / T(i))){
              S = neighbor;
            }
          }

          if (prePartResidue(sequence, S) < prePartResidue(sequence, buffer)){
            buffer = S;
          }
      }

      return buffer;
    }

    private static double T(int iter){
      return Math.pow(10,10) * Math.pow(0.8, Math.floor(iter / 300));
    }
}
