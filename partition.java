import java.util.*;

public class partition{
    final static long MAX_ITER = 25000;
    final static Random rand = new Random();

    public static void main (String[] args){
        // Usage: ./partition inputfile

        int[] random = generateStd();
        for (int i = 0; i < random.length; i++) {
            System.out.println(random[i] + ", ");
        }
        // System.out.println("residue: " + stdResidue());

        int[] prepartitioned = generatePrePart();
        for (int i = 0; i < prepartitioned.length; i++) {
            System.out.println(prepartitioned[i] + ", ");
        }
    }

    // Implement KK Algorithm
    private static long karmKarp(long[] sequence) {
        LinkedList<long> seq = new LinkedList<>(Arrays.asList(sequence));
        Collections.sort(seq); // Sorts in decreasing order
        while(seq.size() != 1){
            seq.add(seq.pop() - seq.pop());
            Collections.sort(seq);
        }
        return seq.get(0);
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

        System.out.println("Finished initializing standard array");
        return rep;
    }

    private static long stdResidue(long[] sequence, int[] solution) {
        long residue = 0;
        for (int i = 0; i < sequence.length; i++) {
            residue += sequence[i] * solution[i];
        }
        return residue;
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
            rep[i] = rand.nextInt(rep.length + 1) + 1;
        }

        System.out.println("Finished initializing prepartitioned array");
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

    // Implement Hill Climbing
    private static int[] stdHill(long[] sequence){
        int[] S = generateStd();
        for (long i = 0; i < MAX_ITER; i++){
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
