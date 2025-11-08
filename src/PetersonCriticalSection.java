
import java.util.concurrent.atomic.AtomicIntegerArray;

class PetersonCriticalSection extends CriticalSection_Base {

    private static AtomicIntegerArray flags;
    private static AtomicIntegerArray turn;

    static {
        // initialize flags and turn arrays
        flags = new AtomicIntegerArray(Server.NUM_THREADS);
        turn = new AtomicIntegerArray(Server.NUM_THREADS);

        for (int i = 0; i < Server.NUM_THREADS; i++) {
            flags.set(i, -1);
        }   
    }

    @Override
    public void EntrySection(Worker thread) {
        
        boolean waiting;

        for (int k = 0; k < Server.NUM_THREADS - 1; k++) {
            flags.set(thread.ID, k);
            turn.set(k, thread.ID);
            
            do {
                waiting = false;

                for (int j = 0; j < Server.NUM_THREADS; j++) {
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