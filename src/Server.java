

public class Server {

    public static final int NUM_THREADS = 100;    // number of threads per experiment
    public static final int NUM_ATTEMPTS = 5;      //

    public static long testPeterson(int n) {
        /*
        Tests the average turnaround time for n threads entering and exiting a CS NUM_ATTEMPTS times using Peterson's algorithm

        args: 
            n - number of threads to test
        */

        Worker pettersonThreads[] = new Worker[NUM_THREADS];

        int timesFinished[] = new int[NUM_THREADS];
        long threadStartTime[] = new long[NUM_THREADS];  // start time for the first attempt of each thread
        long threadEndTime[] = new long[NUM_THREADS];    // end time for the last attempt of each thread

        boolean waiting;

        long average_turnaround_time = 0;

        // instantiate peterson's algorithm workers
        for (int i = 0; i < NUM_THREADS; i++) {
            pettersonThreads[i] = new Worker(new PetersonCriticalSection(), i);
            pettersonThreads[i].start();
            threadStartTime[i] = System.currentTimeMillis();
        }


        // test threads until all have finished their attempts
        do { 
            waiting = false;

            for (int i = 0; i < n; i++) {
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
                else {
                    waiting = true;
                }
            }
        } while (waiting);  

        // calculate turnaround time
        for (int i = 0; i < n; i++) {
            average_turnaround_time += (threadEndTime[i] - threadStartTime[i]);
        }

        return average_turnaround_time / n;
    }

    public static long testKnuth(int n) {
        /*
        Tests the average turnaround time for n threads entering and exiting a CS NUM_ATTEMPTS times using Knuth's algorithm

        args: 
            n - number of threads to test
        */

        Worker knuthThreads[] = new Worker[NUM_THREADS];

        int timesFinished[] = new int[NUM_THREADS];
        long threadStartTime[] = new long[NUM_THREADS];  // start time for the first attempt of each thread
        long threadEndTime[] = new long[NUM_THREADS];    // end time for the last attempt of each thread

        boolean waiting;

        long average_turnaround_time = 0;

        // instantiate knuth's algorithm workers
        for (int i = 0; i < NUM_THREADS; i++) {
            knuthThreads[i] = new Worker(new KnuthCriticalSection(), i);
            knuthThreads[i].start();
            threadStartTime[i] = System.currentTimeMillis();
        }

        // test threads until all have finished their attempts
        do { 
            waiting = false;

            for (int i = 0; i < n; i++) {
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
                else {
                    waiting = true;
                }
            }
        } while (waiting);  

        // calculate turnaround time
        for (int i = 0; i < n; i++) {
            average_turnaround_time += (threadEndTime[i] - threadStartTime[i]);
        }

        return average_turnaround_time / n;
    }

    public static long testDeBruijn(int n) {
        /*
        Tests the average turnaround time for n threads entering and exiting a CS NUM_ATTEMPTS times using De Bruijn's algorithm

        args: 
            n - number of threads to test
        */

        Worker deBruijnThreads[] = new Worker[NUM_THREADS];

        int timesFinished[] = new int[NUM_THREADS];
        long threadStartTime[] = new long[NUM_THREADS];  // start time for the first attempt of each thread
        long threadEndTime[] = new long[NUM_THREADS];    // end time for the last attempt of each thread

        boolean waiting;

        long average_turnaround_time = 0;

        // instantiate de bruihn's algorithm workers
        for (int i = 0; i < NUM_THREADS; i++) {
            deBruijnThreads[i] = new Worker(new DeBruijnCriticalSection(), i);
            deBruijnThreads[i].start();
            threadStartTime[i] = System.currentTimeMillis();
        }


        // test threads until all have finished their attempts
        do { 
            waiting = false;

            for (int i = 0; i < n; i++) {
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
                else {
                    waiting = true;
                }
            }
        } while (waiting);  

        // calculate turnaround time
        for (int i = 0; i < n; i++) {
            average_turnaround_time += (threadEndTime[i] - threadStartTime[i]);
        }

        return average_turnaround_time / n;
    }

    public static void main(String[] args) {
        //Write your code here to create your threads and test the 3 different algorithms
        //When making worker threads it is recommended for ID to start at 0 and increment by 1 


        // report peterson statistics
        System.out.println("CS Solution 1 - Peterson's (time in milli-seconds)");
        System.out.println("Threads\tAVG TAT");
        for (int i = 2; i < NUM_THREADS; i++) {
            System.out.println(i + "\t" + testPeterson(i));
        }
        System.out.println();


        // report knuth statistics
        System.out.println("CS Solution 1 - Knuth's (time in milli-seconds)");
        System.out.println("Threads\tAVG TAT");
        for (int i = 2; i < NUM_THREADS; i++) {
            System.out.println(i + "\t" + testKnuth(i));
        }
        System.out.println();


        // report de bruijn statistics
        System.out.println("CS Solution 1 - De Bruijn's (time in milli-seconds)");
        System.out.println("Threads\tAVG TAT");
        for (int i = 2; i < NUM_THREADS; i++) {
            System.out.println(i + "\t" + testDeBruijn(i));
        }
        System.out.println();

    }
}


