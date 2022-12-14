import java.util.*;
import java.io.*;


public class AutocompleteDriver {


    public static void main(String[] args) throws FileNotFoundException {

        String fname = "data/words-333333.txt";
        File f = new File(fname);
        Scanner scan = new Scanner(f);
        Scanner input = new Scanner(System.in);
        int size = scan.nextInt();
        String[] words = new String[size];
        double[] weights = new double[size];
        for(int k=0; k < size; k++) {
            double weight = scan.nextDouble();
            String word = scan.next();
            words[k] = word;
            weights[k] = weight;
        }

        //Autocompletor auto = new BruteAutocomplete(words,weights);
        //Autocompletor auto = new BinarySearchAutocomplete(words,weights);
        Autocompletor auto = new HashListAutocomplete(words,weights);

        int k = 20;
        while (true) {
            System.out.println("-------------------");
            System.out.print("query [return to exit] ");
            String query = input.nextLine();
            if (query.length() == 0) break;
            List<Term> matches = auto.topMatches(query,k);
            for(Term t : matches) {
                System.out.println(t);
            }
        }
    }
}
