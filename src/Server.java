

public class Server {

    public static final int NUM_THREADS = 100;    // number of threads per experiment
    public static final int NUM_ATTEMPTS = 5;      //

    public static void main(String[] args) {
        //Write your code here to create your threads and test the 3 different algorithms
        //When making worker threads it is recommended for ID to start at 0 and increment by 1 

        Worker pettersonThreads[] = new Worker[NUM_THREADS];
        Worker knuthThreads[] = new Worker[NUM_THREADS];    
        Worker deBruijnThreads[] = new Worker[NUM_THREADS];

        int timesFinished[] = new int[NUM_THREADS];
        long threadStartTime[] = new long[NUM_THREADS];  // start time for the first attempt of each thread
        long threadEndTime[] = new long[NUM_THREADS];    // end time for the last attempt of each thread

        boolean waiting = false;

        // --- PETTERSON'S ALGORITHM TESTING ---

        // instantiate peterson's algorithm workers
        for (int i = 0; i < NUM_THREADS; i++) {
            pettersonThreads[i] = new Worker(new PetersonCriticalSection(), i);
            threadStartTime[i] = System.currentTimeMillis();
        }

        // test threads until all have finished their attempts
        do { 
            waiting = false;

            for (int i = 0; i < NUM_THREADS; i++) {
                if (pettersonThreads[i].getState() == Thread.State.TERMINATED) {
                    
                    // if there is a terminated thread with remaining attempts, restart it and repeat the do while
                    if (timesFinished[i] < NUM_ATTEMPTS) {
                        timesFinished[i]++;

                        pettersonThreads[i] = new Worker(new PetersonCriticalSection(), i);
                        pettersonThreads[i].start();

                        waiting = true;
                    }

                    // if there is a therad that has finished all attempts, record its end time
                    else if (timesFinished[i] == NUM_ATTEMPTS) {
                        threadEndTime[i] = System.currentTimeMillis();
                        timesFinished[i]++;  // to avoid re-entering this block
                    }
                }
            }
        } while (waiting);  

        // report peterson statistics
        System.out.println("CS Solution 1 - Peterson's (time in milli-seconds)");
        System.out.println("Threads\tAVG TAT");
        for (int i = 0; i < NUM_THREADS; i++) {
            long average_turnaround_time = (threadEndTime[i] - threadStartTime[i]) / NUM_ATTEMPTS;
            System.out.println(i + "\t" + average_turnaround_time);
        }
        System.out.println();



        // --- KNUTH'S ALGORITHM TESTING ---


        // reset attemtsFinshed array
        for (int i = 0; i < NUM_THREADS; i++) {
            timesFinished[i] = 0;
        }

        // instantiate knuth's algorithm workers
        for (int i = 0; i < NUM_THREADS; i++) {
            knuthThreads[i] = new Worker(new KnuthCriticalSection(), i);
            threadStartTime[i] = System.currentTimeMillis();
        }

        // test threads until all have finished their attempts
        do { 
            waiting = false;

            for (int i = 0; i < NUM_THREADS; i++) {
                if (knuthThreads[i].getState() == Thread.State.TERMINATED) {
                    
                    // if there is a terminated thread with remaining attempts, restart it and repeat the do while
                    if (timesFinished[i] < NUM_ATTEMPTS) {
                        timesFinished[i]++;

                        knuthThreads[i] = new Worker(new KnuthCriticalSection(), i);
                        knuthThreads[i].start();

                        waiting = true;
                    }

                    // if there is a therad that has finished all attempts, record its end time
                    else if (timesFinished[i] == NUM_ATTEMPTS) {
                        threadEndTime[i] = System.currentTimeMillis();
                        timesFinished[i]++;  // to avoid re-entering this block
                    }
                }
            }
        } while (waiting);  

        // report knuth statistics
        System.out.println("CS Solution 2 - Knuth's (time in milli-seconds)");
        System.out.println("Threads\tAVG TAT");
        for (int i = 0; i < NUM_THREADS; i++) {
            long average_turnaround_time = (threadEndTime[i] - threadStartTime[i]) / NUM_ATTEMPTS;
            System.out.println(i + "\t" + average_turnaround_time);
        }
        System.out.println();




        // --- DE BRUIJN'S ALGORITHM TESTING ---



        // reset attemtsFinshed array
        for (int i = 0; i < NUM_THREADS; i++) {
            timesFinished[i] = 0;
        }

        // instantiate de bruijn's algorithm workers
        for (int i = 0; i < NUM_THREADS; i++) {
            deBruijnThreads[i] = new Worker(new DeBruijnCriticalSection(), i);
            threadStartTime[i] = System.currentTimeMillis();
        }

        // test threads until all have finished their attempts
        do { 
            waiting = false;

            for (int i = 0; i < NUM_THREADS; i++) {
                if (deBruijnThreads[i].getState() == Thread.State.TERMINATED) {
                    
                    // if there is a terminated thread with remaining attempts, restart it and repeat the do while
                    if (timesFinished[i] < NUM_ATTEMPTS) {
                        timesFinished[i]++;

                        deBruijnThreads[i] = new Worker(new DeBruijnCriticalSection(), i);
                        deBruijnThreads[i].start();

                        waiting = true;
                    }

                    // if there is a therad that has finished all attempts, record its end time
                    else if (timesFinished[i] == NUM_ATTEMPTS) {
                        threadEndTime[i] = System.currentTimeMillis();
                        timesFinished[i]++;  // to avoid re-entering this block
                    }
                }
            }
        } while (waiting);  

        // report de bruijn statistics
        System.out.println("CS Solution 3 - De Bruijn's (time in milli-seconds)");
        System.out.println("Threads\tAVG TAT");
        for (int i = 0; i < NUM_THREADS; i++) {
            long average_turnaround_time = (threadEndTime[i] - threadStartTime[i]) / NUM_ATTEMPTS;
            System.out.println(i + "\t" + average_turnaround_time);
        }
        System.out.println();

    }
}


