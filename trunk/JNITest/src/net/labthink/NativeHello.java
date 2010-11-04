package net.labthink;

public class NativeHello {
    public native String HelloWorld(String str);
    static {
	System.loadLibrary("hello");
    }
    public static void main(String[] args) {
	String ret = new NativeHello().HelloWorld(" ‘ ‘÷–Œƒ");
	System.out.println("JAVA Received the String:"+ ret);
    }
}
 
