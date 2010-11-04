//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;

public class mainTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // TODO Auto-generated method stub
        listPortChoices();

        try {
            sendtest("COM3");
        } catch (SerialConnectionException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void listPortChoices() {
        CommPortIdentifier portId;
        Enumeration        en = CommPortIdentifier.getPortIdentifiers();
        // iterate through the ports.
        while (en.hasMoreElements()) {
            portId = (CommPortIdentifier) en.nextElement();

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println(portId.getName() + " :" + portId.getPortType() + "   :       "
                        + portId.isCurrentlyOwned() + "   :       "
                                   + portId.getCurrentOwner());
            }
        }
    }

    public static void sendtest(String PortName) throws SerialConnectionException {
        int PacketLength = 8;

        try {
            CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(PortName);
            SerialPort         sPort  = (SerialPort) portId.open("串口所有者名称", 10000);

            System.out.println("尝试向" + PortName + " 写入数据");

            // 用于对串口写数据
            int          data = 88599623;
            OutputStream os   = new BufferedOutputStream(sPort.getOutputStream());

            os.write(data);
            os.flush();

            // 用于从串口读数据
            InputStream is = new BufferedInputStream(sPort.getInputStream());

//          int receivedData = is.read();
//          System.out.println("收到："+receivedData);
            while (true) {

                // PacketLength为数据包长度
                byte[] msgPack = new byte[PacketLength];
                int    newData;

                for (int i = 0; i < PacketLength; i++) {
                    if ((newData = is.read()) != -1) {
                        os.write(newData);
                        msgPack[i] = (byte) newData;
                        System.out.println(msgPack[i]);
                    }

                    os.flush();
                }
            }
        } catch (NoSuchPortException e) {
            e.printStackTrace();

            throw new SerialConnectionException(e.getMessage());
        } catch (PortInUseException e) {    // 如果端口被占用就抛出这个异常
            e.printStackTrace();

            throw new SerialConnectionException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();

            throw new SerialConnectionException(e.getMessage());
        }
    }
}
