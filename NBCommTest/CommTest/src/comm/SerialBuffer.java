package comm;

import serial.*;

/**
 * This class implements the buffer area to store incoming data from the serial
 * port.
 */
public class SerialBuffer {
    private String  Content      = "";
    private int     LengthNeeded = 1;
    private boolean available    = false;
    private String  CurrentMsg, TempContent;

    /**
     * This function returns a string with a certain length from the incoming
     * messages.
     *
     * @param Length
     *            The length of the string to be returned.
     *
     * @return
     */
    public synchronized String GetMsg(int Length) {
        LengthNeeded = Length;
        notifyAll();

        if (LengthNeeded > Content.length()) {
            available = false;

            while (available == false) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
        }

        CurrentMsg   = Content.substring(0, LengthNeeded);
        TempContent  = Content.substring(LengthNeeded);
        Content      = TempContent;
        LengthNeeded = 1;
        notifyAll();

        return CurrentMsg;
    }

    /**
     *
     * This function stores a character captured from the serial port to the
     * buffer area.
     *
     * @param c
     */
    public synchronized void PutChar(int c) {
        Character d = new Character((char) c);

        Content = Content.concat(d.toString());

        if (LengthNeeded < Content.length()) {
            available = true;
        }

        notifyAll();
    }
}
