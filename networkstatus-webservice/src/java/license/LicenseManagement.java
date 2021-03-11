/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package license;

/**
 *
 * @author Samrit
 */
//import com.project.util.CmdExec;
//import com.project.util.CryptAlogrithm;
//import com.project.util.OperatingSystem;
//import com.project.util.RegEx_Tool;
//import com.project.util.TerminalManagenment;
import com.project.core.models.entities.system.OperatingSystem;
import com.project.core.util.CryptAlogrithm;
import license.License;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import com.project.core.util.*;
import java.util.Scanner;

/**
 *
 * @author Samrit
 */
public class LicenseManagement {

    //salt key varies on length
    public final static String saltKey = "saltkey_no:1";
    private final static String licenseDB_FileName = "/license/LicenseDB.dat";
    private final static String licenseKey_FileName = "/license/LicenseKey.dat";
    private final static String licese_Directory = "/license/";

    public static String getSaltKey() {
        return saltKey;
    }

    public static String getLicenseDB_FileName() {
        return licenseDB_FileName;
    }

    public static String getLicenseKey_FileName() {
        return licenseKey_FileName;
    }

    public static String getLicese_Directory() {
        return licese_Directory;
    }

    public static void createNewLicenseDB(HttpServletRequest paramHttpServletRequest) {
        try {
            String licenseDB = paramHttpServletRequest.getSession().getServletContext().getRealPath(licenseDB_FileName);
            FileOutputStream fileOutputStream = new FileOutputStream(licenseDB);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            ArrayList<License> licenseDBList = new ArrayList<License>();
            License license = new License();
            license.setExpiraryDate(new Date());
            license.setId(1);
            license.setUsed(true);
            license.setRandomId(new Random().nextLong());

            license.setUserName(CryptAlogrithm.encrypt_AES(getAnyInterfaceMacAddressExceptBridge(), saltKey));
            licenseDBList.add(license);
            outputStream.writeObject(licenseDBList);
            System.out.println("New DataBase Created!.");
            outputStream.flush();
            fileOutputStream.flush();
            outputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("File Not Found!.");
        }
    }

    public static synchronized boolean isExpired(HttpServletRequest paramHttpServletRequest) {
        boolean expire = false;
        try {
            String realPath = paramHttpServletRequest.getSession().getServletContext().getRealPath(licenseDB_FileName);
            System.out.println(realPath);
            File f = new File(realPath);

            System.out.println(f.getCanonicalPath());
            if (!f.exists()) {
                createNewLicenseDB(paramHttpServletRequest);
            }
            //  if (f.exists()) {
            FileInputStream fileInputStream = new FileInputStream(f);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<License> licenseDB_Read = (List<License>) objectInputStream.readObject();
            if (licenseDB_Read.size() != 0) {
                License license = licenseDB_Read.get(licenseDB_Read.size() - 1);
                System.out.println(license.getUserName());
                System.out.println(license.getExpiraryDate());
                String macAddress = CryptAlogrithm.decrypt_AES(license.getUserName(), saltKey);
//                if (license.getExpiraryDate().before(new Date()) || !CryptAlogrithm.decrypt_AES(license.getUserName(),
//                        saltKey).equalsIgnoreCase(getMacAddress()))
                if (license.getExpiraryDate().before(new Date()) || !checkMacAddress(macAddress)) {
                    return true;
                }
            }
//            } else {
//                return true;
//            }

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return expire;
    }

    public static String getExpireyDate(HttpServletRequest paramHttpServletRequest) {

        String expiraryDate = "";
        try {
            String licenseDB = paramHttpServletRequest.getSession().getServletContext().getRealPath(licenseDB_FileName);
            FileInputStream fileInputStream = new FileInputStream(licenseDB);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<License> licenseDB_Read = (List<License>) objectInputStream.readObject();
            if (licenseDB_Read.size() != 0) {
                License license = licenseDB_Read.get(licenseDB_Read.size() - 1);
                //    expiraryDate = license.getExpiraryDate().toString();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                expiraryDate = df.format(license.getExpiraryDate()).toString();
            }

            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Expirary Date Cannot Found!.");
            expiraryDate = licenseDB_FileName + "is not Found.";
            expiraryDate = " Free Version ";
        }

        return expiraryDate;
    }

    public static boolean checkMacAddress(String macAddress) {
        boolean contains = false;

        if (OperatingSystem.isWindows()) {
            InetAddress ip;
            try {
                ip = InetAddress.getLocalHost();
                //    System.out.println("Current IP address : " + ip.getHostAddress());

                NetworkInterface network = NetworkInterface.getByInetAddress(ip);

                byte[] mac = network.getHardwareAddress();

                //  System.out.print("Current MAC address : ");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                //  System.out.println(sb.toString());
                contains = sb.toString().trim().equalsIgnoreCase(macAddress.trim());

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        } else {
            for (String macAddr : getAllInterfaceMacAddressExceptBridge()) {
                if (macAddr.trim().equalsIgnoreCase(macAddress.trim())) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    public static boolean registerLicenseWithUploadedFile(MultipartFile file, HttpServletRequest paramHttpServletRequest) throws Exception {
        try {
            // String licenseDB = paramHttpServletRequest.getSession().getServletContext().getRealPath(licenseDB_FileName);

            String licenseDB = paramHttpServletRequest.getSession().getServletContext().getRealPath(getLicenseDB_FileName());
            InputStream fileContent = file.getInputStream();
            ObjectInputStream license_objectInputStream = new ObjectInputStream(fileContent);

            License license_Read = (License) license_objectInputStream.readObject();

            FileInputStream fileInputStream = new FileInputStream(licenseDB);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<License> licenseDB_Read = (List<License>) objectInputStream.readObject();

            String userName = license_Read.getUserName();
            String macAddress = CryptAlogrithm.decrypt_AES(license_Read.getUserName(), saltKey);
            // if (userName.equals("") || !CryptAlogrithm.decrypt_AES(license_Read.getUserName(), LicenseManagement.getSaltKey()).equals(LicenseManagement.getMacAddress()) || !CryptAlogrithm.decrypt_AES(license_Read.getHostID(), LicenseManagement.getSaltKey()).equals(LicenseManagement.getHostID()))

            if (userName.equals("") || !checkMacAddress(macAddress) || !CryptAlogrithm.decrypt_AES(license_Read.getHostID(), LicenseManagement.getSaltKey()).equals(LicenseManagement.getHostId())) {
                System.out.println("Invalid License Key!!");
                return false;
            }

            for (License license : licenseDB_Read) {
                if (license.getId() == license_Read.getId() && license.getRandomId() == license_Read.getRandomId()) {
                    if (license.isUsed() == true) {
                        System.out.println("License Already Used!!");
                        return false;
                    }
                }
            }

            license_Read.setUsed(true);
            licenseDB_Read.add(license_Read);

            FileOutputStream fileOutputStream = new FileOutputStream(licenseDB);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(licenseDB_Read);
            outputStream.flush();
            fileOutputStream.flush();
            outputStream.close();
            fileOutputStream.close();
            objectInputStream.close();
            fileInputStream.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static synchronized boolean registerNewLicense(HttpServletRequest paramHttpServletRequest) {

        try {
            String licenseDB = paramHttpServletRequest.getSession().getServletContext().getRealPath(licenseDB_FileName);
            String licenseKey = paramHttpServletRequest.getSession().getServletContext().getRealPath(licenseKey_FileName);

            FileInputStream license_fileInputStream = new FileInputStream(licenseKey);
            ObjectInputStream license_objectInputStream = new ObjectInputStream(license_fileInputStream);
            License license_Read = (License) license_objectInputStream.readObject();

            FileInputStream fileInputStream = new FileInputStream(licenseDB);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<License> licenseDB_Read = (List<License>) objectInputStream.readObject();

            String userName = license_Read.getUserName();//getUserName(paramHttpServletRequest);
            String macAddress = CryptAlogrithm.decrypt_AES(license_Read.getUserName(), saltKey);
            if (userName.equals("") || !checkMacAddress(macAddress) || !CryptAlogrithm.decrypt_AES(license_Read.getHostID(), saltKey).equals(getHostId())) {
                System.out.println("Invalid License Key!!");
                return false;
            }

            for (License license : licenseDB_Read) {
                if (license.getId() == license_Read.getId() && license.getRandomId() == license_Read.getRandomId()) {
                    if (license.isUsed() == true) {
                        System.out.println("License Already Used!!");
                        return false;
                    }
                }
            }

            license_Read.setUsed(true);
            licenseDB_Read.add(license_Read);

            FileOutputStream fileOutputStream = new FileOutputStream(licenseDB);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(licenseDB_Read);
            outputStream.flush();
            fileOutputStream.flush();
            outputStream.close();
            fileOutputStream.close();
            objectInputStream.close();
            fileInputStream.close();

            System.out.println("License registered!. Till Date :" + license_Read.getExpiraryDate());
            return true;

        } catch (StreamCorruptedException se) {
            System.out.println(se.getMessage());
            System.out.println("Invalid File!.");
            return false;
        } catch (Exception e) {

            System.out.println(e.getMessage());
            System.out.println("File Not Found!.");
            return true;

        }

    }

    public static String getAnyInterfaceMacAddressExceptBridge() {
        List<String> macAddress = getAllInterfaceMacAddressExceptBridge();
        for (String line : macAddress) {
            if (line != null && !line.equals("")) {
                return line;
            }
        }
        return "";
    }

    public static List<String> getAllInterfaceMacAddressExceptBridge() {

        List<String> macAddress = getAllInterfaceMacAddress();
        String bridgeMac = getBridgeInterfaceMacAddress();
        List<String> macAddressOfInterfaces = new ArrayList<String>();
        for (String line : macAddress) {
            if (!line.trim().equalsIgnoreCase(bridgeMac.trim())) {
                macAddressOfInterfaces.add(line);
            }
        }
        return macAddressOfInterfaces;
    }

    public static List<String> getAllInterfaceMacAddress() {
        List<String> macAddress = new ArrayList<String>();

        if (OperatingSystem.isWindows()) {
            InetAddress ip;
            try {
                ip = InetAddress.getLocalHost();
                //    System.out.println("Current IP address : " + ip.getHostAddress());

                NetworkInterface network = NetworkInterface.getByInetAddress(ip);

                byte[] mac = network.getHardwareAddress();

                //  System.out.print("Current MAC address : ");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                //  System.out.println(sb.toString());
                macAddress.add(sb.toString().trim());

            } catch (UnknownHostException e) {

                e.printStackTrace();

            } catch (SocketException e) {

                e.printStackTrace();

            }

        } else {
            String command = "ifconfig";
            String pattern = ".*ether[\\s]*([a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}).*";
            List<String> lines = new CmdExec().run_with_output(command);
            for (String line : lines) {
                String ether = new RegEx_Tool().capture(pattern, line);
                if (ether != null) {
                    macAddress.add(ether.trim());
                }
            }
        }
        return macAddress;
    }

    public static String getBridgeInterfaceMacAddress() {
        String str = "";

        if (OperatingSystem.isWindows()) {
            return str;
        } else {
            String command = "ifconfig bridge0";
            String pattern = ".*ether[\\s]*([a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}).*";
            List<String> lines = new CmdExec().run_with_output(command);
            for (String line : lines) {
                String ether = new RegEx_Tool().capture(pattern, line);
                if (ether != null) {
                    str = ether;
                }
            }
        }
        return str;
    }

    public static String getHostId() {

        if (OperatingSystem.isWindows()) {
//            return "Requirement was not scoped for " + OperatingSystem.getOsName() + " platform.";

            try {
                // wmic command for diskdrive id: wmic DISKDRIVE GET SerialNumber
                // wmic command for cpu id : wmic cpu get ProcessorId
                Process process = Runtime.getRuntime().exec(new String[]{"wmic", "bios", "get", "serialnumber"});
                process.getOutputStream().close();
                Scanner sc = new Scanner(process.getInputStream());
                String property = sc.next();
                String serial = sc.next();
                System.out.println(property + ": " + serial);
                return serial;
            } catch (Exception e) {
                return "Requirement was not scoped for " + OperatingSystem.getOsName() + " platform.";
            }

        } else if (OperatingSystem.isLinux()) {
            String hostID = "";
//            String command = "top -n 1";
            String command = "hostid";

//            String regx = "(?i)Swap:\\s+(\\d+)(.*)\\s+(\\d*)(.*?)\\s.*";
            String regx = "(.*)";

            List<String> resultList = new CmdExec().run_with_shell_output(command);

            try {
                for (String str : resultList) {
                    System.out.println("Line " + str);
                    if (str.matches(regx)) {

                        hostID = new RegEx_Tool().capture(regx, str, 1);
                        if (hostID != null) {
                            hostID = hostID.trim();
                        }

                        if (!str.trim().equals("")) {
                            break;
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return hostID;
        } else {
            String command = "sysctl -a";
            // command = "cmd /c dir";
            List<String> commandLinesResult = new TerminalManagenment().getTerminalCommandOutput(command, false);
            String hostid = "";
            for (String st : commandLinesResult) {
                String id = new RegEx_Tool().capture("kern.hostid:[\\s]*((\\d)*).*", st, 1);
                if (id != null) {
                    hostid = id;
                    break;
                }
            }
            return hostid;
        }
    }

}
