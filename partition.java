import java.util.*;

public class partition{
    final static long MAX_ITER = 25000;
    final static Random rand = new Random();
    public static void main (String[] args){
        // Usage: ./partition inputfile

        long[] random = generateStd();
        for (long i = 0; i < random.length; i++) {
            System.out.prlong(random[i] + ", ");
        }
        // System.out.prlongln("residue: " + stdResidue());

        long[] prepartitioned = generatePrePart();
        for (long i = 0; i < prepartitioned.length; i++) {
            System.out.prlong(prepartitioned[i] + ", ");
        }
    }

    // Implement KK Algorithm
    private static long karmKarp(long[] sequence) {
        Arrays.sort(sequence); // Sorts in decreasing order
        while(sequence.length != 1){
            sequence.push(sequence.pop() - sequence.pop());
            Arrays.sort(sequence);
        }
        return sequence[0];
    }

    // Implement Standard Representation
    private static long[] generateStd() {
        long[] rep = new long[100];
        for (long i = 0; i < rep.length; i++) {
            long prob = rand.nextLong(2);
            if (prob == 0) {
                rep[i] = 1;
            }
            else {
                rep[i] = -1;
            }
        }

        System.out.prlongln("Finished initializing standard array");
        return rep;
    }

    private static long stdResidue(long[] sequence, long[] solution) {
        long residue = 0;
        for (long i = 0; i < sequence.length; i++) {
            residue += sequence[i] * solution[i];
        }
        return residue;
    }

    private static long[] stdNeighbor(long[] solution) {
      long length = solution.length;
      long i = rand.nextLong(length);
      long j = rand.nextLong(length);
      while(i == j){
        j = rand.nextLong(length);
      }

      solution[i] *= -1;
      long prob = rand.nextLong(2);
      if (prob == 0){
        solution[j] *= -1;
      }

      return solution;
    }

    // Implement Prepartition Representation
    private static long[] generatePrePart() {
        long[] rep = new long[100];
        for (long i = 0; i < rep.length; i++) {
            rep[i] = rand.nextLong(rep.length + 1) + 1;
        }

        System.out.prlongln("Finished initializing prepartitioned array");
        return rep;
    }

    private static long prePartResidue(long[] sequence, long[] solution) {
        long[] modified = new long[100];
        for (long i = 0; i < modified.length; i++) {
            modified[solution[i]] += sequence[i];
        }
        return karmKarp(modified);
    }

    private static long[] prePartNeighbor(long[] solution){
        long length = solution.length;
        long i = rand.nextLong(length);
        long j = rand.nextLong(length);
        while(solution[i] == j){
          j = rand.nextLong(length);
        }

        solution[i] = j;

        return solution;
    }

    // Implement Repeated Random

    // Implement Hill Climbing
    private static long[] stdHill(long[] sequence){
      long[] S = generateStd();
      for (long i = 0; i < MAX_ITER; i++){
        long[] neighbor = stdNeighbor(S);
        if (stdResidue(sequence, neighbor) < stdResidue(sequence, S)){
          S = neigbor;
        }
      }
      return S;
    }

    private static long[] prePartHill(){
      long[] S = generatePrePart();
      for (long i = 0; i < MAX_ITER; i++){
        long[] neighbor = prePartNeighbor(S);
        if (prePartResidue(sequence, neighbor) < prePartResidue(sequence, S)){
          S = neigbor;
        }
      }
      return S;
      return S;
    }


    // Implement Simulated Annealing

}
