import java.io.*;
import java.util.*;

public class TrieMatching implements Runnable {
	List<Integer> solve (String text, List<String> patterns) {
		List<Integer> result = new ArrayList<Integer> ();

		List<Map<Character, Integer>> trie = buildTrie(patterns);

		for(int i = 0; i < text.length(); i++) {
			if(prefixTrieMatching(text, i, trie)) {
				result.add(i);
			}
		}

		return result;
	}

	boolean prefixTrieMatching(String text, int startIndex, List<Map<Character, Integer>> trie) {
		int v = 0;
		int i = startIndex;
		while (i < text.length()) {
			char currentSymbol = text.charAt(i);
			if(trie.get(v).containsKey(currentSymbol)) {
				v = trie.get(v).get(currentSymbol);
				i++;
			} else {
				return false;
			}
			if(trie.get(v).isEmpty()) {
				return true;
			}
		}

		return false;
	}

	public void run () {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String text = in.readLine ();
		 	int n = Integer.parseInt (in.readLine ());
		 	List <String> patterns = new ArrayList <String> ();
			for (int i = 0; i < n; i++) {
				patterns.add (in.readLine ());
			}

			List <Integer> ans = solve (text, patterns);

			for (int j = 0; j < ans.size (); j++) {
				System.out.print ("" + ans.get (j));
				System.out.print (j + 1 < ans.size () ? " " : "\n");
			}
		}
		catch (Throwable e) {
			e.printStackTrace ();
			System.exit (1);
		}
	}

	List<Map<Character, Integer>> buildTrie(List<String> patterns) {
		List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();

		int nodeCounter = 0;
		trie.add(new HashMap<Character, Integer>());
		for(String pattern : patterns) {
			char currentSymbol;
			int currentNode = 0;
			for(int i = 0; i < pattern.length(); i++) {
				currentSymbol = pattern.charAt(i);
				if(currentNode < trie.size() && trie.get(currentNode) != null && trie.get(currentNode).get(currentSymbol) != null) {
					currentNode = trie.get(currentNode).get(currentSymbol);
				} else {
					nodeCounter++;
					int newNode = nodeCounter;
					trie.get(currentNode).put(currentSymbol, newNode);
					currentNode = newNode;
					if(currentNode >= trie.size()) {
						trie.add(new HashMap<Character, Integer>());
					}
				}
			}
		}

		return trie;
	}

	public static void main (String [] args) {
		new Thread (new TrieMatching ()).start ();
	}
}
