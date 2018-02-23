package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {

    private int currentSize, maxSize, Front, Rear;
    private E[] storage;

    public ArrayLinearList(int max) {
        Front = Rear = currentSize = 0;
        maxSize = max;
        storage = (E[]) new Object[maxSize];
    }

    public ArrayLinearList() {
        this(DEFAULT_MAX_CAPACITY);
    }

    /*  Adds the Object obj to the beginning of list and returns true if the list is not full.
            returns false and aborts the insertion if the list is full. */
    public boolean addFirst(E obj) {
        if (isFull())                    /* Checks if the list is full */
            return false;

        if (currentSize != 0) {   /* Checks if cZ is 0, moves Front pointer to new Front position */
            Front--;

            if (Front < 0) {
                Front = maxSize-1;    /* If Front falls of array, moves Front pointer to back of array loop */
            }
        }
        storage[Front] = obj;           /* Stores obj to Front and increases size */
        currentSize++;
        return true;
    }

    /* Adds the Object obj to the end of list and returns true if the list is not full.
   returns false and aborts the insertion if the list is full..  */
    public boolean addLast(E obj) {
        if (isFull())
            return false;

        if (currentSize != 0) {    /* Checks if cZ is 0, moves Rear pointer to new Rear position */
            Rear++;

            if (Rear == maxSize) {      /* If Rear falls of the array, moves Rear pointer to front of the loop */
                Rear = 0;
            }
        }
        storage[Rear] = obj;         /* Stores obj to Rear and increases size */
        currentSize++;
        return true;
    }

    /*  Removes and returns the parameter object obj in first position in list if the list is not empty,
        null if the list is empty. */
    public E removeFirst() {
        if (isEmpty()) {                    /* Checks if empty and will return if so */
            return null;
        }
        E element = storage[Front];     /* value that is being removed */
        if (Front == Rear)
            Front = Rear = 0;
        else {
            Front++;                         /* Shifts the Front pointer */
            if (Front == maxSize)           /* Checks if Front falls off, sets to 0 */
                Front = 0;
        }
        currentSize--;                      /* Decreases size */
        return element;
    }

    /*  Removes and returns the parameter object obj in last position in list if the list is not empty,
        null if the list is empty. */
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        E element = storage[Rear];
        if (Rear == Front)
            Rear = Front = 0;
        else {
            Rear--;                     /* Shifts the Rear pointer */
            if (Rear < 0)               /* Checks if Rear falls off, sets to 0 */
                Rear = 0;
        }
        currentSize--;
        return element;
    }

    /*  Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
        The ordering of the list is preserved.  The list may contain duplicate elements.  This method
        removes and returns the first matching element found when traversing the list from first position.
        Note that you may have to shift elements to fill in the slot where the deleted element was located. */
    public E remove(E obj) {
        if ((isEmpty()))
            return null;
        int where = -1;
        int tracker = Front; /* Creates a tracker to go through list */
        for (int i = 0; i < currentSize; i++) {
            if (((Comparable<E>) obj).compareTo(storage[tracker]) == 0) {
                where = tracker;
                break;
            }
            if  (tracker == maxSize-1)
                    tracker=0;
            tracker++;
        }

        E tmp = storage[where];
            while (where!=Rear) {                     /* Shifts where til it reaches Rear */
                if (where == maxSize - 1) {          /* Brings where back to the front */
                    storage[where] = storage[0];
                    where = 0;
                }
                storage[where] = storage[where + 1]; /* Increments where so it shifts */
                where++;
            }
            Rear--;                                  /* Moves to new Rear*/
            if (Rear < 0)
                Rear = maxSize-1;
                                                  /* Shifts the the elements */
        currentSize--;
        return tmp;
    }

    /* Returns the first element in the list, null if the list is empty. The list is not modified.*/
    public E peekFirst() {
        if (isEmpty())
            return null;
        return storage[Front];
    }

    /*  Returns the last element in the list, null if the list is empty. The list is not modified.*/
    public E peekLast() {
        if (isEmpty())
            return null;
        return storage[Rear];
    }

    /* Returns true if the parameter object obj is in the list, false otherwise.
       The list is not modified. */
    public boolean contains(E obj) {
        return find(obj) != null;

    }

    /*  Returns the element matching obj if it is in the list, null otherwise.
        In the case of duplicates, this method returns the element closest to front.
        The list is not modified.   */
    public E find(E obj) {
        for (E tmp : this) {
            if (((Comparable<E>) obj).compareTo(tmp) == 0)
                return tmp;
        }
        return null;
    }

    /*  The list is returned to an empty state.*/
    public void clear() {currentSize = 0;}

    /*  Returns true if the list is empty, otherwise false */
    public boolean isEmpty() {return currentSize == 0;}

    /*  Returns true if the list is full, otherwise false*/
    public boolean isFull() {return currentSize == maxSize;}

    /*  Returns the number of Objects currently in the list.*/
    public int size() {return currentSize;}

    /*  Returns an Iterator of the values in the list, presented in
        the same order as the underlying order of the list. (front first, rear last)*/
    public Iterator<E> iterator() {return new IteratorHelper();}

    class IteratorHelper implements Iterator<E> {
        private int count, index;
        public IteratorHelper() {
            index = Front;
            count = 0;
        }
        public boolean hasNext() {return count != currentSize;}

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E tmp = storage[index++];
            if (index == maxSize)
                index = 0;
            count++;
            return tmp;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }


    }


}
