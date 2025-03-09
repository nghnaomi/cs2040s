import java.util.ArrayList;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';

    private class TrieNode {
        // TODO: Create your TrieNode class here.
        int[] presentChars = new int[62];
        private TrieNode[] children = new TrieNode[62];
        private boolean endOfWord;

        TrieNode() {
            endOfWord = false;
        }
    }

    private TrieNode root;

    public Trie() {
        // TODO: Initialise a trie class here.
        this.root = new TrieNode();
    }

    public int findIndex(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a';
        } else if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 26;
        } else if (c >= '0' && c <= '9') {
            return c - '0' + 52;
        } else {
            return -1;
        }
    }

    public char findChar(int index) {
        if (index < 26) {
            return (char) (index + 'a');
        } else if (index < 52) {
            return (char) (index + 'A' - 26);
        } else if (index < 62){
            return (char) (index + '0' - 52);
        } else {
            return '?';
        }
    }

        /**
         * Inserts string s into the Trie.
         *
         * @param s string to insert into the Trie
         */
        void insert(String s) {
            // TODO
            TrieNode node = root;
            for (char c : s.toCharArray()) {
                int charIndex = findIndex(c);
                if (node.children[charIndex] == null) {
                    node.children[charIndex] = new TrieNode();
                }
                node = node.children[charIndex];
            }
            node.endOfWord = true;
        }

        /**
         * Checks whether string s exists inside the Trie or not.
         *
         * @param s string to check for
         * @return whether string s is inside the Trie
         */
        boolean contains(String s) {
            // TODO
            TrieNode node = root;
            for (char c : s.toCharArray()) {
                int charIndex = findIndex(c);
                if (node.children[charIndex] == null) {
                    return false;
                }
                node = node.children[charIndex];
            }
            if (node.endOfWord) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
         * results into the specified ArrayList. Only returns at most the first limit results.
         *
         * @param s       pattern to match prefixes with
         * @param results array to add the results into
         * @param limit   max number of strings to add into results
         */
        void prefixSearch(String s, ArrayList<String> results, int limit) {
            // TODO
            prefixSearchHelper(s, 0, root, new StringBuilder(), results, limit);
        }

        private void prefixSearchHelper(String s, int i, TrieNode node, StringBuilder curr, ArrayList<String> results, int limit) {
            if (node == null || results.size() >= limit) {
                return;
            }

            if (i == s.length()) {
                if (node.endOfWord) {
                    results.add(curr.toString());
                }

                for (int j = 0; j < 62 && results.size() < limit; j++) {
                    if (node.children[j] != null) {
                        curr.append(findChar(j));
                        prefixSearchHelper(s, i, node.children[j], curr, results, limit);
                        curr.setLength(curr.length() - 1);
                    }
                }
                return;
            }

            char c = s.charAt(i);
            if (c == WILDCARD) {
                for (int j = 0; j < 62 && results.size() < limit; j++) {
                    if (node.children[j] != null) {
                        curr.append(findChar(j));
                        prefixSearchHelper(s, i + 1, node.children[j],
                                curr, results, limit);
                        curr.setLength(curr.length() - 1);
                    }
                }
            } else {
                int j = findIndex(c);
                if (node.children[j] != null) {
                    curr.append(c);
                    prefixSearchHelper(s, i + 1, node.children[j],
                            curr, results, limit);
                    curr.setLength(curr.length() - 1);
                }
            }
        }

        // Simplifies function call by initializing an empty array to store the results.
        // PLEASE DO NOT CHANGE the implementation for this function as it will be used
        // to run the test cases.
        String[] prefixSearch(String s, int limit) {
            ArrayList<String> results = new ArrayList<String>();
            prefixSearch(s, results, limit);
            return results.toArray(new String[0]);
        }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] result1 = t.prefixSearch("pe", 10);
        String[] result2 = t.prefixSearch("pe.", 10);
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
