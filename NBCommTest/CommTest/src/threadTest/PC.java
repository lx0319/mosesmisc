
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package threadTest;

/**
 * Class description
 *
 *
 * @version        Enter version here..., 09/09/07
 * @author         Enter your name here...
 */
class Consumer implements Runnable {
    Q q;

    /**
     * Constructs ...
     *
     *
     * @param q
     */
    Consumer(Q q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    /**
     * Method description
     *
     */
    public void run() {
        while (true) {
            q.get();
        }
    }
}


/**
 * Class description
 *
 *
 * @version        Enter version here..., 09/09/07
 * @author         Enter your name here...
 */
class PCFixed {

    /**
     * Method description
     *
     *
     * @param args
     */
    public static void main(String args[]) {
        Q q = new Q();

        new Producer(q);
        new Consumer(q);
        System.out.println("Press Control-C to stop.");
    }
}


/**
 * Class description
 *
 *
 * @version        Enter version here..., 09/09/07
 * @author         Enter your name here...
 */
class Producer implements Runnable {
    Q q;

    /**
     * Constructs ...
     *
     *
     * @param q
     */
    Producer(Q q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }

    /**
     * Method description
     *
     */
    public void run() {
        int i = 0;

        while (true) {
            q.put(i++);
        }
    }
}


//A correct implementation of a producer and consumer.

/**
 * Class description
 *
 *
 * @version        Enter version here..., 09/09/07
 * @author         Enter your name here...
 */
class Q {
    boolean valueSet = false;
    int     n;

    /**
     * Method description
     *
     *
     * @return
     */
    synchronized int get() {
        if (!valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }

        System.out.println("Got: " + n);
        valueSet = false;
        notify();

        return n;
    }

    /**
     * Method description
     *
     *
     * @param n
     */
    synchronized void put(int n) {
        if (valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }

        this.n   = n;
        valueSet = true;
        System.out.println("Put: " + n);
        notify();
    }
}
