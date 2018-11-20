import java.util.*;
import java.io.*;

public class tree_height {
    class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}

	public class TreeHeight {
		int n;
		int parents[];
        Tree[] nodes;
        Tree root;

		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
            parents = new int[n];
			for (int i = 0; i < n; i++) {
                parents[i] = in.nextInt();
			}
		}

		int computeHeight() {
                        // Replace this code with a faster implementation
			nodes = new Tree[n];
			for(int i = 0; i < n; i++) {
				Tree tree = new Tree();
				tree.parent = parents[i];
				nodes[i] = tree;
			}

			for(int i = 0; i < n; i++) {
				Tree tree = nodes[i];
                if(tree.parent != -1) {
                    Tree parent = nodes[tree.parent];
                    parent.children.add(tree);
                } else {
                    root = tree;
                }
			}

			return height(root);
		}

        int height(Tree root) {
            if(root == null) {
                return 0;
            }

            int height = 1;
            int maxChildHeight = 0;
            if(root.children.size() == 1) {
                maxChildHeight = height(root.children.get(0));
            } else if(root.children.size() > 1) {
                maxChildHeight = height(root.children.get(0));
                int nextHeight = height(root.children.get(1));
                maxChildHeight = max(maxChildHeight, nextHeight);

                for(int i = 2; i < root.children.size(); i++) {
                    nextHeight = height(root.children.get(i));
                    maxChildHeight = max(maxChildHeight, nextHeight);
                }
            }

            return height + maxChildHeight;
        }

        int max(int a, int b) {
            if(a > b) {
                return a;
            } else {
                return b;
            }
        }
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}

	public void run() throws IOException {
		TreeHeight tree = new TreeHeight();
		tree.read();
		System.out.println(tree.computeHeight());
	}

	public class Tree {
		private int parent;
		private List<Tree> children = new ArrayList<>();
	}
}
