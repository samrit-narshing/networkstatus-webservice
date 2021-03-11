/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class TerminalManagenment {

    public ArrayList<String> getTerminalCommandOutput(String command, boolean advance) {
        CmdExec localCmdExec = new CmdExec();
        String[] env = {"PATH=/usr/local/bin/:/usr/kerberos/sbin:/usr/kerberos/bin:/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin"};
        ArrayList localArrayList;
        if (advance) {
            localArrayList = localCmdExec.run_with_shell_output(command, env);
        } else {
            localArrayList = localCmdExec.run_with_shell_output(command);
        }
        return localArrayList;
    }

//    public void runCommand(String command, boolean advance, String userName) {
//
//        List<String> commandLines = new ArrayList<String>();
//        OperatingSystem_ValueHolder.getOutputStatusTable().put(userName, commandLines);
//        try {
//            String[] env = {"PATH=/usr/local/bin/:/usr/kerberos/sbin:/usr/kerberos/bin:/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin"};
//            Process localProcess;
//            if (advance) {
//                localProcess = Runtime.getRuntime().exec(command, env);
//            } else {
//                localProcess = Runtime.getRuntime().exec(command);
//            }
//            //Process localProcess = Runtime.getRuntime().exec(command);
//            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
//            OperatingSystem_ValueHolder.getProcessTable().put(userName, localProcess);
//
//            String str;
//            while ((str = localBufferedReader.readLine()) != null) {
//                System.out.println(str);
//                commandLines.add(str);
//            }
//            localBufferedReader.close();
//
//            localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getErrorStream()));
//            //localArrayList.add(i++, "Error");
//            while ((str = localBufferedReader.readLine()) != null) {
//                commandLines.add("TRACED MESSAGE>> :" + str);
//            }
//            localBufferedReader.close();
//
//        } catch (Exception localException) {
//            commandLines.add("<br/>");
//            commandLines.add("No Results.");
//            commandLines.add("Command Is Invalid.");
//            localException.printStackTrace();
//        }
//    }

}
