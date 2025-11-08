public class Server {

    public static final int NUM_THREADS = 100;    // number of threads per experiment
    public static final int NUM_ENTRIES = 5;      //

    public static void main(String[] args) {
        //Write your code here to create your threads and test the 3 different algorithms
        //When making worker threads it is recommended for ID to start at 0 and increment by 1 



        int id = 0;

        // test peterson's algorithm
        for (int i = 0; i < NUM_THREADS; i++) {
            Worker worker = new Worker(new KnuthCriticalSection(), id);
            worker.start();
            id++;
        }

        System.out.println("CS Solution 1 - Peterson's (time in milli-seconds)");
        System.out.println("Threads\tAVG TAT");

    }
}


