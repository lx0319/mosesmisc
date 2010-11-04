
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comm;

//~--- non-JDK imports --------------------------------------------------------
import utils.ListUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

/**
 *
 * @author Administrator
 */
public class CommUtils {

    private static final String[] BAUDRATE = {
        "300", "600","1200", "2400","4800", "9600", "19200", "38400", "43000","56000", "57600", "115200"
    };
    private static final String[] DATABITS = {"8", "7", "6"};
    private static final String[] PARITY = {"None", "Odd", "Even"};
    private static final String[] STOPBITS = {"1", "2"};

    /**
     * Method description
     *
     *
     * @return
     */
    public static String[] ListPorts() {
        CommPortIdentifier portId;
        List portList = new ArrayList();
        Enumeration en = CommPortIdentifier.getPortIdentifiers();

        // iterate through the ports.
        while (en.hasMoreElements()) {
            portId = (CommPortIdentifier) en.nextElement();

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portList.add(portId.getName());
            }
        }

        return ListUtils.List2StringArray(portList);
    }


    

    public void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String[] getBAUDRATE() {
        return BAUDRATE;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String[] getDATABITS() {
        return DATABITS;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String[] getPARITY() {
        return PARITY;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String[] getSTOPBITS() {
        return STOPBITS;
    }
}
