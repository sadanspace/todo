package com.code.practice;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TodoTest {


    @Test
    void tmp() {
        ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
        PrintStream cacheStream = new PrintStream(baoStream);// 临时输出
        PrintStream oldStream = System.out;// 缓存系统输出
        System.setOut(cacheStream);
        System.out.print("控制台打印信息测试...");// 不会打印到控制台
        String message = baoStream.toString();
        System.setOut(oldStream);// 还原到系统输出
        System.out.println("获取到的数据为【" + message + "】");
    }

    @Test
    void testAdd() {
        String args0 = "add";
        String[] args = new String[]{args0, "make up", " 3344"};

        SystemOutMonitor systemOutMonitor = new SystemOutMonitor();
        systemOutMonitor.process(new Todo(), args);
        String sysOutStr = systemOutMonitor.getSysOutStr();

        System.out.println(sysOutStr);
    }


    @Test
    void testDone() {
        String args0 = "done";
        String[] args = new String[]{args0, "3"};

        SystemOutMonitor systemOutMonitor = new SystemOutMonitor();
        systemOutMonitor.process(new Todo(), args);
        String sysOutStr = systemOutMonitor.getSysOutStr();
        System.out.println(sysOutStr);
    }

    @Test
    void testDefaultList() {
        String args0 = "list";
        String[] args = new String[]{args0};

        SystemOutMonitor systemOutMonitor = new SystemOutMonitor();
        systemOutMonitor.process(new Todo(), args);
        String sysOutStr = systemOutMonitor.getSysOutStr();
        System.out.println(sysOutStr);
    }

    @Test
    void testListAll() {
        String args0 = "list";
        String[] args = new String[]{args0, "--all"};

        SystemOutMonitor systemOutMonitor = new SystemOutMonitor();
        systemOutMonitor.process(new Todo(), args);
        String sysOutStr = systemOutMonitor.getSysOutStr();
        System.out.println(sysOutStr);
    }

}
