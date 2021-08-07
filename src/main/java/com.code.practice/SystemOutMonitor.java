package com.code.practice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SystemOutMonitor {

    private String sysOutStr;
    private int size;

    public SystemOutMonitor() {
        this(1024);
    }

    public SystemOutMonitor(int size) {
        this.size = size;
    }

    public void process(Command command, String[] args) {
        try (
                ByteArrayOutputStream baoStream = new ByteArrayOutputStream(this.size);
                PrintStream cacheStream = new PrintStream(baoStream)// 临时输出
        ) {
            PrintStream oldStream = System.out;// 缓存系统输出
            System.setOut(cacheStream);

            // callback
            command.execute(args);

            this.sysOutStr = baoStream.toString();
            System.setOut(oldStream);// 还原到系统输出
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSysOutStr() {
        return sysOutStr;
    }

    public void setSysOutStr(String sysOutStr) {
        this.sysOutStr = sysOutStr;
    }
}
