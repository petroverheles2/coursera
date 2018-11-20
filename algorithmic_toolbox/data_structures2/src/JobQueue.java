import java.io.*;
import java.util.StringTokenizer;

public class JobQueue {
    protected int numWorkers;
    protected int[] jobs;

    protected int[] assignedWorker;
    protected long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

//    protected String getResult() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < jobs.length; ++i) {
//            stringBuilder.append(assignedWorker[i] + " " + startTime[i]);
//            stringBuilder.append("\n");
//        }
//        return stringBuilder.toString();
//    }

    protected void assignJobs() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];

        WorkerWithPriority[] workersWithPriorities = new WorkerWithPriority[numWorkers];

        for(int i = 0; i < numWorkers; i++) {
            workersWithPriorities[i] = new WorkerWithPriority(i, 0);
        }

        for(int i = 0; i < jobs.length - 1; i++) {
            WorkerWithPriority worker = workersWithPriorities[0];

            assignedWorker[i] = worker.number;
            startTime[i] = worker.nextStartTime;

            changePriority(worker, workersWithPriorities, jobs[i]);
        }

        assignedWorker[jobs.length - 1] = workersWithPriorities[0].number;
        startTime[jobs.length - 1] = workersWithPriorities[0].nextStartTime;

    }

    private void changePriority(WorkerWithPriority worker, WorkerWithPriority[] workers, long jobDuration) {
        worker.nextStartTime = worker.nextStartTime + jobDuration;
        siftDown(0, workers);
    }

    private void siftDown(int i, WorkerWithPriority[] data) {
        int minIndex = i;

        int l = leftChild(i);

        if(l < data.length
                &&
                (data[l].nextStartTime < data[minIndex].nextStartTime ||
                    (data[l].nextStartTime == data[minIndex].nextStartTime && data[l].number < data[minIndex].number))) {

                minIndex = l;
        }

        int r = rightChild(i);

        if(r < data.length
                &&
                (data[r].nextStartTime < data[minIndex].nextStartTime ||
                        (data[r].nextStartTime == data[minIndex].nextStartTime && data[r].number < data[minIndex].number))) {

            minIndex = r;
        }

        if(i != minIndex) {
            swap(i, minIndex, data);
            siftDown(minIndex, data);
        }
    }

    private int leftChild(int i) {
        return 2*i + 1;
    }

    private int rightChild(int i) {
        return 2*i + 2;
    }

    private void swap(int i, int j, WorkerWithPriority[] data) {
        WorkerWithPriority tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    private class WorkerWithPriority {
        private int number;
        private long nextStartTime;

        public WorkerWithPriority(int number, long nextStartTime) {
            this.number = number;
            this.nextStartTime = nextStartTime;
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
