package serial;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.text.SimpleDateFormat;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

/**
 * Class description
 *
 *
 * @version        Enter version here..., 09/09/07
 * @author         Enter your name here...
 */
public class SerialBean2 {
    static int           waitTime = 0;
    private String       PortName;
    private InputStream  in;
    private OutputStream out;
    CommPortIdentifier   portId;
    private SerialPort   serialPort;

    /**
     * Constructs ...
     *
     *
     * @param PortID
     */
    public SerialBean2(int PortID) {
        PortName = "COM" + PortID;

        if (PortName.equalsIgnoreCase("com6")) {}
    }

    /**
     *
     * 新修改的接口，直接传入串口名称
     *
     * @param PortID
     *
     */
    public SerialBean2(String PortID) {
        PortName = PortID;

        if (PortName.equalsIgnoreCase("com6")) {}
    }

    /**
     *
     * 本函数初始化所指定的串口并返回初始化结果。
     * 如果初始化成功返回1，否则返回-1。
     * 初始化的结果是该串口被SerialBean独占性使用，其参数被设置为9600, N, 8, 1。
     * 如果串口被成功初始化，则打开一个进程读取从串口传入的数据并将其保存在缓冲区中。
     *
     * @return
     */
    public synchronized int initialize() {
        int InitSuccess = 1;
        int InitFail    = -1;

        try {
            portId = CommPortIdentifier.getPortIdentifier(PortName);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            try {
                serialPort = (SerialPort) portId.open("Serial_Communication" + PortName, 3600);
            } catch (PortInUseException e) {
                e.printStackTrace();

                return InitFail;
            }

//          Use InputStream in to read from the serial port, and OutputStream
//          out to write to the serial port.
            InitFail = initStream();

//          Initialize the communication parameters to 9600, 8, 1, none.
            try {
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                                               SerialPort.PARITY_NONE);
            } catch (UnsupportedCommOperationException e) {
                e.printStackTrace();

                return InitFail;
            }
        } catch (NoSuchPortException e) {
            e.printStackTrace();

            return InitFail;
        }

        return InitSuccess;
    }

    private int initStream() {
        try {
            in  = serialPort.getInputStream();
            out = serialPort.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();

            return -1;
        }

        return 1;
    }

    /**
     *
     * This function returns a string with a certain length from the incomin messages.
     *
     * 本函数从串口(缓冲区)中读取指定长度的一个字符串。参数Length指定所返回字符串的长度。
     *
     * @param aByte
     *
     * @return
     */
    public synchronized byte[] readPort(byte[] aByte) {

//      logger.fatal("线程---串口：" + PortName + " 主机：" + aByte[0] + " 命令：" + aByte[1] + " 进入readPort方法");
//      initialize();
//      initStream();
//        PropertyUtil proUtil = new PropertyUtil();
//        waitTime = Integer.parseInt(proUtil.getCom("waitTime"));    // 读取配置文件中设置的等待时间
        waitTime = 100;

        byte[] bf      = new byte[100];    // 供接收使用
        byte[] content = new byte[0];

//      update in 20070918 by liulei ,锁当前资源
        try {
            for (int i = 0; i < aByte.length; i++) {
                out.write(aByte[i]);
            }

            out.flush();

//          Thread.sleep(waitTime);
            int j = 0;

            while (in.available() == 0) {
                Thread.sleep(waitTime);

                if (j > 2) {
                    System.err.println("跳出循环，当前j== " + j + "无法读到字节数组; 串口：" + PortName + ",主机：" + aByte[0] + " ,命令："
                                       + aByte[1]);

//                  update by liulei in 2007.10.20,关闭串口同时关闭输入输出流
//                  in.close();
//                  out.close();
//                  closePort();
                    return content;
                }

                j++;
            }

            System.out.println(PortName + ":读到的字节数组长度----------------" + in.available());
        } catch (Exception e) {
            e.printStackTrace();
        }

//      update in 20070918 by liulei ,锁当前资源
        try {

//          logger.info("读到的字节数组长度：=== " + in.available());
            while (in.available() > 0) {
                int num = in.read(bf);    // 从输入流中读取一定数量的字节并将其存储在缓冲区数组bf中

                content = new byte[num];

                for (int k = 0; k < num; k++) {
                    content[k] = bf[k];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//      logger.info("得到值，关闭串口正常退出！");
//      update by liulei in 2007.10.20,关闭串口同时关闭输入输出流

/*

        try {

        in.close();

        out.close();

        } catch (IOException e) {

        // TODO Auto-generated catch block

        e.printStackTrace();

        }



        closePort();

*/
        StringBuffer printResult = new StringBuffer();

        if (aByte.length > 0) {
            for (int i = 0; i < aByte.length; i++) {
                printResult.append(aByte[i]);
                printResult.append(",");
            }
        }

        System.err.println("向串口: " + PortName + " 发送指令 : " + printResult.toString());

        StringBuffer printResult1 = new StringBuffer();

        if (content.length > 0) {
            for (int i = 0; i < content.length; i++) {
                printResult1.append(content[i]);
                printResult1.append(",");
            }
        }

        System.err.println("从串口: " + PortName + " 返回值 : " + printResult1.toString());

//      判断10指令的处理结果
        if ((aByte[1] == 16) && (printResult1.toString().trim().length() == 9)) {
            if (aByte[2] == 0) {
                System.out.println("校验不成功......");
            }

            if (aByte[2] == 1) {
                System.out.println("校验成功......");
            }
        }

//      判断10指令的处理结果
        if ((aByte[1] == 5) && (printResult1.toString().trim().length() == 9)) {
            System.out.println("校验不成功......");
        }

        if ((aByte.length > 0) && (content.length > 0)) {
            if ((aByte[0] != content[0]) || (aByte[1] != content[1])) {

//              logger.error("当前线程：" + Thread.currentThread().getName() + "从串口名称: " + PortName + "得到值不一致，进行拦截处理");
                return new byte[0];
            }
        }

        return content;
    }

    /**
     *
     * This function sends a message through the serial port.
     *
     * @param aByte The data of byte to be sent.
     *
     * 本函数向串口发送一个字符串。参数Msg是需要发送的字符串。
     *
     */
    public void writePort(byte[] aByte) {

//      PropertyUtil proUtil = new PropertyUtil();
//      waitTime = Integer.parseInt(proUtil.getCom("waitTime"));    // 读取配置文件中设置的等待时间
        waitTime = 100;

//      update in 20070918 by liulei ,锁当前资源
        try {
            for (int i = 0; i < aByte.length; i++) {
                out.write(aByte[i]);
            }

            Thread.sleep(waitTime);

//          closePort();
//          initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     *
     *
     * This function closes the serial port in use.
     *
     * 本函数停止串口检测进程并关闭串口。
     *
     */
    public void closePort() {

//      RT.stop();
        serialPort.close();
    }
}
