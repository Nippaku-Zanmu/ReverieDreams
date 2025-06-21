package cc.thonly.reverie_dreams.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;

@Slf4j
public class NetworkingUtils {
    public static String getContent(String address, String proxyHost, int proxyPort) {
        StringBuilder sb = new StringBuilder();
        try {
            URI uri = new URI(address);
            URL url = uri.toURL();

            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyHost, proxyPort));
            URLConnection connection = url.openConnection(proxy);

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();
            inputStream.close();
        } catch (Exception e) {
            log.error("Can't get network content via proxy", e);
            return null;
        }
        return sb.toString();
    }


    public static String getContent(String address) {
        StringBuilder sb = new StringBuilder();
        try {
            URI uri = new URI(address);
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();
            inputStream.close();
        } catch (Exception e) {
            log.error("Can't get network content", e);
            return null;
        }
        return sb.toString();
    }

    public static boolean isReachable(String ipAddress, int port, int timeout, String proxyHost, int proxyPort) {
        try {
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyHost, proxyPort));
            Socket socket = new Socket(proxy);
            socket.connect(new InetSocketAddress(ipAddress, port), timeout);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public static boolean isReachable(String ipAddress, int timeout) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            return address.isReachable(timeout);
        } catch (Exception e) {
            return false;
        }
    }
}
