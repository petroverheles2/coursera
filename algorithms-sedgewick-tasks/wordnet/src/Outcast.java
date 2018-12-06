public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException();
        }

        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int result = -1;
        int maxDis = -1;
        int[] dis = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            for (int j = i + 1; j < nouns.length; j++) {
                int curDis = wordNet.distance(nouns[i], nouns[j]);
                dis[i] += curDis;
                dis[j] += curDis;
            }
        }
        for (int i = 0; i < dis.length; i++) {
            if (dis[i] > maxDis) {
                maxDis = dis[i];
                result = i;
            }
        }
        return nouns[result];
    }


    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}