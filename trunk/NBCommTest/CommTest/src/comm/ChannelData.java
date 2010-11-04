
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comm;

import java.nio.ByteBuffer;

/**
 *
 * @author Administrator
 */
public class ChannelData {

    private byte[] data;
    private int nodeID;
    private int length;
    private String msg;
    private byte channelID;
    private final int CHANNEL_LENGTH = 8;
    private final byte RESERVE= (byte) 1&0xff;
   
//存储串口通道数据信息 private String msg;
//返回串口通道数据信息的字符串

    /** * 根据接收的字节数组创建串口通道数据信息 * @param data */
    public ChannelData(byte[] data) {
        this.data = data;
        this.nodeID = byte2int(data[0]) + byte2int(data[1]) * 256;
        this.channelID = data[2];
        this.length = byte2int(data[4]) + byte2int(data[5]) * 256;
        StringBuffer strBuffer = new StringBuffer();
        for (int i = 6; i < length + 6; i++) {
            String value;
            if (data[i] >= 16) {
                value = Integer.toHexString(data[i]).toUpperCase();
            } else if (data[i] >= 0) {
                value = "0".concat(Integer.toHexString(data[i])).toUpperCase();
            } else {
                value = Integer.toHexString(data[i]).substring(6).toUpperCase();
//当数据小于0时转换成十六进制显示时取后两位数据位,忽略符号位
            }
            strBuffer.append(value + " ");
        }
        this.msg = strBuffer.toString();
    }

    /** * 根据输入发送数据创建串口通道数据 * @param node * @param channelID * @param codingData 发送数据信息编码后的字节数组 */
    public ChannelData(int nodeID, byte channelID, byte[] codingData) {
        this.nodeID = (byte) nodeID;
        this.channelID = channelID;
        this.length = codingData.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(CHANNEL_LENGTH);
        byteBuffer.put(int2byte(nodeID));
//填加节点标识
        byteBuffer.put(channelID);
//填加穿口通道号
        byteBuffer.put(RESERVE);
//填加保留字节
        byteBuffer.put(int2byte(codingData.length));
//填加承载有效数据长度
        byteBuffer.put(codingData);
//将发送数据信息放入缓冲区
        byteBuffer.flip();
        data = new byte[byteBuffer.limit()];
        byteBuffer.get(data);
//将串口通道数据信息放入字节数组
    }

    /** * 将字节数据转换成整型数据 * @param value * @return */
    public int byte2int(byte value) {
        return value >= 0 ? value : value + 256;
    }

    /** * 将INT数据类型转换成字节数组 * @param id * @return */
    public byte[] int2byte(int id) {
        byte[] channelId = new byte[2];
        if (id < 256) {
            channelId[0] = (byte) id;
            channelId[1] = 0;
        } else if (id > 256) {
            channelId[0] = (byte) (id & 0x00ff);
//低位在前,高位在后
            channelId[1] = (byte) ((id & 0xff00) >>> 8);
        }
        return channelId;
    }

    /** * 返回发送数据信息的串口通道标识 * @return */
    public int getNodeID() {
        return nodeID;
    }

    /** * 返回发送数据信息的串口通道标识 * @return */
    public byte getChannelID() {
        return channelID;
    }

    /** * 返回发送数据信息的长度,去掉数据控制信息 * @return */
    public int getLength() {
        return length;
    }

    /** * 获得串口通道数据信息的字符串表示 * @return */
    public String getMsg() {
        return msg;
    }

    /** * 返回表示串口通道数据信息的字节数组 * @return */
    public byte[] getMsgData() {
        return data;
    }
}


