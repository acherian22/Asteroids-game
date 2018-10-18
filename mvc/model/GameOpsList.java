package _08final.mvc.model;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


/**
 * The type Game ops list.
 */
public class GameOpsList extends LinkedList {

    private ReentrantLock lock;

    /**
     * Instantiates a new Game ops list.
     */
    public GameOpsList() {
        this.lock =   new ReentrantLock();
    }

    /**
     * Enqueue.
     *
     * @param mov       the mov
     * @param operation the operation
     */
    public void enqueue(Movable mov, CollisionOp.Operation operation) {

       try {
            lock.lock();
            addLast(new CollisionOp(mov, operation));
        } finally {
            lock.unlock();
        }
    }


    /**
     * Dequeue collision op.
     *
     * @return the collision op
     */
    public CollisionOp dequeue() {
        try {
            lock.lock();
           return (CollisionOp) super.removeFirst();
        } finally {
            lock.unlock();
        }

    }
}
