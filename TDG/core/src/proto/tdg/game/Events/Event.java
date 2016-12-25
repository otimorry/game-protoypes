package proto.tdg.game.Events;

/**
 * Created by Olva on 12/5/16.
 */
public class Event {
    private boolean handled; // true means the event was handled (the stage will eat the input)
    private boolean stopped; // true means event propagation was stopped
    private boolean cancelled; // true means propagation was stopped and any action that this event would cause should not happen

    public void handle () {
        handled = true;
    }

    public void cancel () {
        cancelled = true;
        stopped = true;
        handled = true;
    }

    public void stop () {
        stopped = true;
    }

    public void reset () {
        handled = false;
        stopped = false;
        cancelled = false;
    }

    public boolean isHandled () {
        return handled;
    }

    public boolean isStopped () {
        return stopped;
    }

    public boolean isCancelled () {
        return cancelled;
    }
}
