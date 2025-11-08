
import java.util.concurrent.atomic.AtomicIntegerArray;

class DeBruijnCriticalSection extends CriticalSection_Base {

    private static AtomicIntegerArray flags;
    private static volatile int turn;

    private static final int IDLE = 0;
    private static final int REQUESTING = 1;
    private static final int IN_CS = 2;

    static {
        // initialize flags and turn arrays
        flags = new AtomicIntegerArray(Server.NUM_THREADS);
        turn = 0;
    }

    @Override
    public void EntrySection(Worker thread) {
        /*
        Entry section code for DeBruijn's algorithm. Note that this is the same code as Knuth's. Only th exit section is different.

        args: 
            thread - the worker thread trying to enter the critical section
        */

        boolean waiting;

        do { 
            flags.set(thread.ID, REQUESTING);
            int j = turn;

            while (j != thread.ID) {
                if (flags.get(j) != IDLE)   j = turn;
                else                        j = (j - 1) % Server.NUM_THREADS;
            }

            flags.set(thread.ID, IN_CS);

            // Check for all j != i, flag[j] != IN_CS
            waiting = false;

            for (int k = 0; k < Server.NUM_THREADS; k++) {
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
            turn = (turn - 1) % Server.NUM_THREADS;
        }
        flags.set(i, IDLE);
    }

}