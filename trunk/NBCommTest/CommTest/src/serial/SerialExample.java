package serial;

//~--- non-JDK imports --------------------------------------------------------

import serial.*;

//~--- JDK imports ------------------------------------------------------------

import java.io.*;

/**
 *
 *
 *
 * This is an example of how to use the SerialBean. It opens COM1 and reads
 *
 * six messages with different length form the serial port.
 *
 *
 *
 */
class SerialExample {

    /**
     * Method description
     *
     *
     * @param args
     */
    public static void main(String[] args) {

        // TO DO: Add your JAVA codes here
        SerialBean SB = new SerialBean(3);
        String     Msg;

        SB.Initialize();

        for (int i = 5; i <= 10; i++) {
            Msg = SB.ReadPort(i);
            SB.WritePort("Reply: " + Msg);
        }

        SB.ClosePort();
    }
}
