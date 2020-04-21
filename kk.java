import java.util.*;

public class kk{
    final static rand = new Random();
    public static void main (String[] args){
        // args[0] - 0 if running normally with inputfile
        // args[0] - 1 if creating a new inputfile
        // args[1] - input file

        // Creating a new input file to test with
        if (args[0] == 1) {
            
        }

        

        int[] random = generateStdRep();
        for (int i = 0; i < random.length; i++) {
            System.out.print(random[i] + ", ");
        }
        System.out.println("residue: " + stdResidue())
    }

    private static int[] generateStdRep() {
        Random rand = new Random();
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

        System.out.println("Finished initializing array");
        return rep;
    }

    private static int stdResidue(int[] sequence, int[] solution) {
        int residue = 0;
        for (int i = 0; i < sequence.length; i++) {
            residue += sequence[i] * solution[i];
        }
    }
    
    private static void generatePPRep() {

    }
}