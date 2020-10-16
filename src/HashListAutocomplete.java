import java.util.*;

public class HashListAutocomplete implements Autocompletor {

    private Term[] myTerms;
    private int mySize;
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap = new HashMap<>();


    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        initialize(terms,weights);
    }


    //O(1)
    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (myMap.get(prefix) == null) {
            return new ArrayList<>();
        }

        List<Term> all = myMap.get(prefix);
        return all.subList(0, Math.min(k, all.size()));
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap.clear();

        myTerms = new Term[terms.length];

        for (int i = 0; i < terms.length; i++) {
            myTerms[i] = new Term(terms[i], weights[i]);
        }
        Arrays.sort(myTerms);


        for (Term t : myTerms) {
            for (int i = 0; i < Math.min(t.getWord().length(), MAX_PREFIX) + 1; i++) {
                String prefix = t.getWord().substring(0, i);
                if (!myMap.containsKey(prefix)) {
                    myMap.put(prefix, new ArrayList<Term>());
                }
                myMap.get(prefix).add(t);
            }
        }

        for (String p : myMap.keySet()) {
            Collections.sort(myMap.get(p), Comparator.comparing(Term::getWeight).reversed());
        }
    }

    @Override
    public int sizeInBytes() {
        if (mySize == 0) {

            for(String s : myMap.keySet()) {
                mySize += BYTES_PER_CHAR*s.length();
            }

            for(Term t : myTerms) {
                mySize += BYTES_PER_DOUBLE +
                        BYTES_PER_CHAR*t.getWord().length();
            }
        }
        return mySize;
    }
}
