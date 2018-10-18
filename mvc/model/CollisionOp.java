package _08final.mvc.model;

/**
 * The type Collision op.
 */


public class CollisionOp {

    /**
     * The enum Operation.
     */
//this could also be a boolean, but we want to be explicit about what we're doing
    public enum Operation {
        /**
         * Add operation.
         */
        ADD,
        /**
         * Remove operation.
         */
        REMOVE
    }

    //members
    private Movable mMovable;
    private Operation mOperation;

    /**
     * Instantiates a new Collision op.
     *
     * @param movable the movable
     * @param op      the op
     */
//constructor
    public CollisionOp(Movable movable, Operation op) {
        mMovable = movable;
        mOperation = op;
    }


    /**
     * Gets movable.
     *
     * @return the movable
     */
//getters
    public Movable getMovable() {
        return mMovable;
    }

    /**
     * Gets operation.
     *
     * @return the operation
     */
    public Operation getOperation() {
        return mOperation;
    }

}
