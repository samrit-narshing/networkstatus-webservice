/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

/**
 *
 * @author Samrit
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CmdExec {

    public void run(String paramString) throws Exception {
        Process localProcess = Runtime.getRuntime().exec(paramString);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
        String str;
        while ((str = bufferedReader.readLine()) != null);
        bufferedReader.close();
    }

    public ArrayList<String> run_with_output(String paramString) {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(0, "");
        int i = 1;
        try {
            Process localProcess = Runtime.getRuntime().exec(paramString);
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
            String str;
            while ((str = localBufferedReader.readLine()) != null) {
                localArrayList.add(i++, str);
            }
            localBufferedReader.close();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return localArrayList;
    }

    public String run_with_shell(String[] paramArrayOfString) {
        try {
            Process localProcess = Runtime.getRuntime().exec(paramArrayOfString);
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
            String str;
            while ((str = localBufferedReader.readLine()) != null);
            localBufferedReader.close();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "";
    }

    public ArrayList<String> run_with_shell_output(String[] paramArrayOfString, String[] env, File directory) {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(0, "");
        int i = 1;
        try {
            Process localProcess = Runtime.getRuntime().exec(paramArrayOfString, env, directory);
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
            String str;
            while ((str = localBufferedReader.readLine()) != null) {
                localArrayList.add(i++, str);
            }
            localBufferedReader.close();

            localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getErrorStream()));
            //localArrayList.add(i++, "Error");
            while ((str = localBufferedReader.readLine()) != null) {
                localArrayList.add(i++, "TRACED MESSAGE>> :" + str);
            }
            localBufferedReader.close();

        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return localArrayList;
    }

    public ArrayList<String> run_with_shell_output(String paramArrayOfString, String[] env) {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(0, "");
        int i = 1;
        try {
            Process localProcess = Runtime.getRuntime().exec(paramArrayOfString, env);
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
            String str;
            while ((str = localBufferedReader.readLine()) != null) {
                localArrayList.add(i++, str);
            }
            localBufferedReader.close();

            localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getErrorStream()));
            //localArrayList.add(i++, "Error");
            while ((str = localBufferedReader.readLine()) != null) {
                localArrayList.add(i++, "TRACED MESSAGE>> :" + str);
            }
            localBufferedReader.close();

        } catch (Exception localException) {
            localException.printStackTrace();
            return null;
        }
        return localArrayList;
    }

    public ArrayList<String> run_with_shell_output(String paramArrayOfString) {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(0, "");
        int i = 1;
        try {
            Process localProcess = Runtime.getRuntime().exec(paramArrayOfString);
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
            String str;
            while ((str = localBufferedReader.readLine()) != null) {
                localArrayList.add(i++, str);
            }
            localBufferedReader.close();

            localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getErrorStream()));
            //localArrayList.add(i++, "Error");
            while ((str = localBufferedReader.readLine()) != null) {
                localArrayList.add(i++, "TRACED MESSAGE>> :" + str);
            }
            localBufferedReader.close();

        } catch (Exception localException) {
            localException.printStackTrace();
            return null;
        }
        return localArrayList;
    }

    public ArrayList<String> run_with_shell_output_arraycommand(String[] command) throws Exception {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(0, "");
        String[] env = {"PATH=/usr/local/bin/:/usr/kerberos/sbin:/usr/kerberos/bin:/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin"};

        int i = 1;

        Process localProcess = Runtime.getRuntime().exec(command, env);
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
        String str;
        while ((str = localBufferedReader.readLine()) != null) {
            localArrayList.add(i++, str);
        }
        localBufferedReader.close();
        return localArrayList;
    }

}
