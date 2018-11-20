import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by petroverheles on 7/4/16.
 */
public class JobQueueStressTest {
    public static void main(String args[]) throws IOException {
        Random random = new Random();

        for(int i = 0; i < 100000; i++) {
            int workersNumber = random.nextInt(10) + 1;
            int jobsNumber = random.nextInt(100) + 1;
            int[] jobs = new int[jobsNumber];
            for(int k = 0; k < jobs.length; k++) {
                jobs[k] = random.nextInt(1000000000);
            }

            JobQueueNaive jobQueueNaive = new JobQueueNaive();
            jobQueueNaive.numWorkers = workersNumber;
            jobQueueNaive.jobs = Arrays.copyOf(jobs, jobs.length);
            jobQueueNaive.assignJobs();
            String naiveResult = jobQueueNaive.getResult();

            JobQueue jobQueue = new JobQueue();
            jobQueue.numWorkers = workersNumber;
            jobQueue.jobs = Arrays.copyOf(jobs, jobs.length);
            jobQueue.assignJobs();
            String result = jobQueue.getResult();

            if(!result.equals(naiveResult)) {
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("Wrong result");
                System.out.println("INPUT:");
                System.out.println(workersNumber + " " + jobsNumber);
                System.out.println(Arrays.toString(jobs).replace(",", ""));

                System.out.println();
                System.out.println("NAIVE RESULT: ");
                System.out.println(naiveResult);

                System.out.println();
                System.out.println("RESULT: ");
                System.out.println(result);
                break;
            }
        }
    }
}
