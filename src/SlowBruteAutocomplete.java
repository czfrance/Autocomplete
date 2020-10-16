import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Extends BruteAutocomplete, overriding the topMatches method. Overridden method is
 * less efficient with complexity N + MlogM. All the terms are looped through to find all the
 * terms (there are M of them) with target prefix (N). Then, all M terms are sorted (MlogM) and the
 * top k are returned.
 *
 * This is the least efficient class out of all classes - BruteAutocomplete, BinarySearchAutocomplete,
 * and HashListAutocomplete as it searches through all N Terms and then sorts all subsequent M Terms.
 */

public class SlowBruteAutocomplete extends BruteAutocomplete{

    /**
     * Create immutable instance with terms constructed from parameter
     *
     * @param terms   words such that terms[k] is part of a word pair 0 <= k < terms.length
     * @param weights weights such that weights[k] corresponds to terms[k]
     * @throws NullPointerException     if either parameter is null
     * @throws IllegalArgumentException if terms.length != weights.length
     * @throws IllegalArgumentException if any elements of weights is negative
     * @throws IllegalArgumentException if any elements of terms is duplicate
     */


    public SlowBruteAutocomplete(String[] terms, double[] weights) {
        super(terms, weights);
    }

    //N + MlogM
    @Override
    public List<Term> topMatches(String prefix, int k) {
        List<Term> list = new ArrayList<>();
        for (Term t : myTerms) {
            if (t.getWord().startsWith(prefix)) {
                list.add(t);
            }
        }
        Collections.sort(list, Comparator.comparing(Term::getWeight).reversed());
        List<Term> ret = list.subList(0, Math.min(k, list.size()));
        return ret;
    }
}
