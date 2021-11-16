package util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientMacAddress {

    public static String getClientMACAddress(String clientIp) {
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + clientIp);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    System.out.println(str);
                    if (str.indexOf("MAC Address") > 1) {
                        System.out.println("there??");
                        macAddress = str.substring(str.indexOf("MAC Address") + 14);
                        System.out.println(macAddress);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return macAddress;
    }

    public static void clientIpAndMacAddress(HttpServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String userIpAddress = httpServletRequest.getHeader("X-Forwarded-For");
        if (userIpAddress == null) {
            userIpAddress = request.getRemoteAddr();
        }
        System.out.println("Ip address : " + userIpAddress);

        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime()
                    .exec("nbtstat -A " + userIpAddress);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        macAddress = str.substring(
                                str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        System.out.println("Mac address : " + macAddress);
    }

    public static String findMacAddressOfLocal() {
        InetAddress ip;
        StringBuilder sb = new StringBuilder();
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i],
                        (i < mac.length - 1) ? "-" : ""));
            }

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        System.out.println("Mac address: " + sb);
        return sb.toString() + Math.random();
    }
}
