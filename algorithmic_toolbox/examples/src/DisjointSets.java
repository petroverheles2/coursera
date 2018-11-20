/**
 * Created by petroverheles on 6/27/16.
 */
public class DisjointSets {
    public static void main(String[] args) {
        DisjointSetArray disjointSet = new DisjointSetArray(12);
        disjointSet.union(2, 10);
        disjointSet.union(7, 5);
        disjointSet.union(6, 1);
        disjointSet.union(3, 4);
        disjointSet.union(5, 11);
        disjointSet.union(7, 8);
        disjointSet.union(7, 3);
        disjointSet.union(12, 2);
        disjointSet.union(9, 6);

        System.out.println(disjointSet.find(6));
        System.out.println(disjointSet.find(3));
        System.out.println(disjointSet.find(11));
        System.out.println(disjointSet.find(9));

    }

    static class DisjointSetArray {
        int[] arr;

        DisjointSetArray(int n) {
            arr = new int[n + 1];
            for(int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
        }

        public void union(int a, int b) {
            int a_id = find(a);
            int b_id = find(b);

            if(a_id < b_id) {
                arr[b] = a_id;
            } else {
                arr[a] = b_id;
            }
        }

        public int find(int a) {
            return arr[a];
        }


    }
}
