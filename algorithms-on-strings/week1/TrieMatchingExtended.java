import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieMatchingExtended implements Runnable {
	List<Integer> solve (String text, List<String> patterns) {
		List<Integer> result = new ArrayList<Integer> ();

		List<Map<Character, Node>> trie = buildTrie(patterns);

		for(int i = 0; i < text.length(); i++) {
			if(prefixTrieMatching(text, i, trie)) {
				result.add(i);
			}
		}

		return result;
	}

	boolean prefixTrieMatching(String text, int startIndex, List<Map<Character, Node>> trie) {
		Node v = Node.ROOT;
		int i = startIndex;
		while (i < text.length()) {
			char currentSymbol = text.charAt(i);
			if(trie.get(v.index).containsKey(currentSymbol)) {
				v = trie.get(v.index).get(currentSymbol);
				if(v.patternEnd) {
					return true;
				}
				i++;
			} else {
				return false;
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

	List<Map<Character, Node>> buildTrie(List<String> patterns) {
		List<Map<Character, Node>> trie = new ArrayList<Map<Character, Node>>();

		int nodeCounter = 0;
		trie.add(new HashMap<Character, Node>());
		for(String pattern : patterns) {
			char currentSymbol;
			Node currentNode = Node.ROOT;
			for(int i = 0; i < pattern.length(); i++) {
				currentSymbol = pattern.charAt(i);
				if(currentNode.index < trie.size()
						&& trie.get(currentNode.index) != null
						&& trie.get(currentNode.index).get(currentSymbol) != null) {
					currentNode = trie.get(currentNode.index).get(currentSymbol);
					currentNode.patternEnd = currentNode.patternEnd || (i == pattern.length() - 1);
				} else {
					nodeCounter++;
					Node newNode = new Node(nodeCounter, i == pattern.length() - 1);
					trie.get(currentNode.index).put(currentSymbol, newNode);
					currentNode = newNode;
					if(currentNode.index >= trie.size()) {
						trie.add(new HashMap<Character, Node>());
					}
				}
			}
		}

		return trie;
	}

	static class Node {
		static Node ROOT = new Node(0, false);

		Integer index;
		Boolean patternEnd;

		Node(Integer index, Boolean patternEnd) {
			this.index = index;
			this.patternEnd = patternEnd;
		}

		@Override
		public String toString() {
			return "(" + index + ", " + patternEnd + ")";
		}
	}

	public static void main (String [] args) {
		new Thread (new TrieMatchingExtended()).start ();
	}
}
