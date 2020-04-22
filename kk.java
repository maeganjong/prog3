import java.util.*;

public class kk{
    final static Random rand = new Random();
    public static void main (String[] args){
        // Usage: ./kk inputfile        

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

    private static int[] karmKarp(int[] sequence) {
        return 0;
    }

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

}