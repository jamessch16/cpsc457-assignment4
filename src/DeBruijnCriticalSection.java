
import java.util.concurrent.atomic.AtomicIntegerArray;

class DeBruijnCriticalSection extends CriticalSection_Base {

    private static AtomicIntegerArray flags;
    private static volatile int turn;

    private static final int IDLE = 0;
    private static final int REQUESTING = 1;
    private static final int IN_CS = 2;

    private static int numThreads;

    public static void newSimulation(int n) {
        /*
        Re-initialize the static variables for a new simulation with n threads
        args: 
            n - number of threads in the new simulation
        */

        numThreads = n;

        // initialize flags and turn arrays
        flags = new AtomicIntegerArray(n);
        turn = 0;

        for (int i = 0; i < n; i++) {
            flags.set(i, IDLE);
        } 
    }

    private int modulo (int a, int b) {
        /*
        Modified modulo function that always returns a positive result

        args: 
            a - the dividend
            b - the divisor

        returns:
            a mod b
        */

        int result = a % b;
        if (result < 0) {
            result += b;
        }
        return result;
    }

    @Override
    public void EntrySection(Worker thread) {

        boolean waiting;

        do { 
            flags.set(thread.ID, REQUESTING);
            int j = turn;

            while (j != thread.ID) {        
                if (flags.get(j) != IDLE)   j = turn;
                else                        j = modulo(j - 1, numThreads);
            }

            flags.set(thread.ID, IN_CS);

            // Check for all j != i, flag[j] != IN_CS
            waiting = false;

            for (int k = 0; k < numThreads; k++) {
                if (k != thread.ID && flags.get(k) == IN_CS) {
                    waiting = true;
                    break;
                }
            }
            
        } while (waiting);
        turn = thread.ID;
    }

    @Override
    public void ExitSection(Worker thread) {
        if (flags.get(turn) == IDLE || turn == thread.ID) {
            turn = modulo(turn - 1, numThreads);
        }
        flags.set(thread.ID, IDLE);
    }

}