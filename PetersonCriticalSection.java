
import java.util.concurrent.atomic.AtomicIntegerArray;

class PetersonCriticalSection extends CriticalSection_Base {

    private static AtomicIntegerArray flags;
    private static AtomicIntegerArray turn;

    private static int numThreads;

    public static void newSimulation(int n) {
        /*
        Re-initialize the static variables for a new simulation with n threads
        args: 
            n - number of threads in the new simulation
        */

        numThreads = n;

        // initialize flags and turn arrays
        flags = new AtomicIntegerArray(numThreads);
        turn = new AtomicIntegerArray(numThreads);

        for (int i = 0; i < n; i++) {
            flags.set(i, -1);
        }   
    }

    @Override
    public void EntrySection(Worker thread) {
        
        boolean waiting;

        for (int k = 0; k < numThreads - 1; k++) {
            flags.set(thread.ID, k);
            turn.set(k, thread.ID);
            
            do {
                waiting = false;

                for (int j = 0; j < numThreads; j++) {
                    if (j != thread.ID && flags.get(j) >= k) {
                        waiting = true;
                        break;
                    }
                }
                
            } while (waiting && turn.get(k) == thread.ID);
        }
    }

    @Override
    public void ExitSection(Worker thread) {
        flags.set(thread.ID, -1);
    }

}