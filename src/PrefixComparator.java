import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
       return new PrefixComparator(prefix);
    }


    @Override
    public int compare(Term v, Term w) {
        int minLen = Math.min(v.getWord().length(), w.getWord().length());

        for (int i = 0; i < Math.min(myPrefixSize, minLen); i++) {
            if (v.getWord().charAt(i) < w.getWord().charAt(i)) {
                return -1;
            }
            if (v.getWord().charAt(i) > w.getWord().charAt(i)) {
                return 1;
            }
        }
        if (v.getWord().length() > w.getWord().length() && minLen < myPrefixSize) {
            return 1;
        }
        else if (v.getWord().length() < w.getWord().length() && minLen < myPrefixSize) {
            return -1;
        }
        return 0;
    }
}
