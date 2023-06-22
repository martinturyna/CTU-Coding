package cz.cvut.fel.pjv;

/**
 * Implementation of the {@link Queue} backed by fixed size array.
 */
public class CircularArrayQueue implements Queue {

    private String[] stringArray;
    private int head;
    private int tail;
    private int size;
    private int maxLength;

    /**
     * Creates the queue with capacity set to the value of 5.
     */
    public CircularArrayQueue() {
       this.stringArray = new String[5];
       this.maxLength = 5;
       this.head = 0;
       this.tail = 0;
       this.size = 0;
    }


    /**
     * Creates the queue with given {@code capacity}. The capacity represents maximal number of elements that the
     * queue is able to store.
     * @param capacity of the queue
     */
    public CircularArrayQueue(int capacity) {
        this.stringArray = new String[capacity];
        this.maxLength = capacity;
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {

        if(this.size == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isFull() {
        if(this.size == this.maxLength) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean enqueue(String obj) {

        if (obj == null || this.isFull()) {
            return false;
        }
        else {
            if (this.size == 0) {
                this.stringArray[this.tail] = obj;
                this.size++;
                return true;
            }
            else {
                if (this.tail == (this.maxLength - 1)) {
                    this.tail = 0;
                    this.stringArray[this.tail] = obj;
                    this.size++;
                    return true;
                }
                else {
                    this.tail++;
                    this.stringArray[this.tail] = obj;
                    this.size++;
                    return true;
                }
            }
        }
    }

    @Override
    public String dequeue() {
        String header;

        if (this.size == 0) {
            return null;
        }
        else if (this.size == 1) {
            header = stringArray[this.head];
            this.size--;
            return header;
        }
        else {
            if (this.head == (this.maxLength - 1)) {
                header = stringArray[this.head];
                this.head = 0;
                this.size--;
                return header;
            }
            else {
                header = stringArray[this.head];
                this.head++;
                this.size--;
                return header;
            }
        }
    }

    @Override
    public void printAllElements() {
        int helper = this.head;
        for (int i = 0; i < this.size; i++) {
            if(helper == (this.maxLength - 1)) {
                System.out.println(stringArray[helper]);
                helper = 0;
            }
            else {
                System.out.println(stringArray[helper]);
                helper++;
            }
        }
    }
}
