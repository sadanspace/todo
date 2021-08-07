package com.code.practice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SystemOutMonitorTest {

    @Test
    void testProcess(){
        String args0 = "add";
        String[] args = new String[]{args0, args0};

        SystemOutMonitor systemOutMonitor = new SystemOutMonitor();
        systemOutMonitor.process(new Todo(), args);
        String sysOutStr = systemOutMonitor.getSysOutStr();

        Assertions.assertTrue(sysOutStr.contains(args0));
    }
}
