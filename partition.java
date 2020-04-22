import java.util.*;

public class partition{
    final static int MAX_ITER = 25000;
    final static Random rand = new Random();
    public static void main (String[] args){
        // Usage: ./partition inputfile

        int[] random = generateStd();
        for (int i = 0; i < random.length; i++) {
            System.out.print(random[i] + ", ");
        }
        // System.out.println("residue: " + stdResidue());

        int[] prepartitioned = generatePrePart();
        for (int i = 0; i < prepartitioned.length; i++) {
            System.out.print(prepartitioned[i] + ", ");
        }
    }

    // Implement KK Algorithm
    private static int karmKarp(int[] sequence) {
        Arrays.sort(sequence); // Sorts in decreasing order
        while(sequence.length != 1){
            sequence.push(sequence.pop() - sequence.pop());
            Arrays.sort(sequence);
        }
        return sequence[0];
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

    private static int stdResidue(int[] sequence, int[] solution) {
        int residue = 0;
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

    private static int prePartResidue(int[] sequence, int[] solution) {
        int[] modified = new int[100];
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
    private static int[] stdHill(int[] sequence){
      int[] S = generateStd();
      for (int i = 0; i < MAX_ITER; i++){
        int[] neighbor = stdNeighbor(S);
        if (stdResidue(sequence, neighbor) < stdResidue(sequence, S)){
          S = neigbor;
        }
      }
      return S;
    }

    private static int[] prePartHill(){
      int[] S = generatePrePart();
      for (int i = 0; i < MAX_ITER; i++){
        int[] neighbor = prePartNeighbor(S);
        if (prePartResidue(sequence, neighbor) < prePartResidue(sequence, S)){
          S = neigbor;
        }
      }
      return S;
      return S;
    }


    // Implement Simulated Annealing

}
