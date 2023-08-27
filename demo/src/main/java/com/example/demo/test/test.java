package com.example.demo.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        try {
            Scanner scanner=new Scanner(System.in);
            String test=scanner.next();


            InetAddress localHost = InetAddress.getLocalHost();
            String ipAddress = localHost.getHostAddress();
            test="http://"+ipAddress+"/audio/"+test;
            System.out.println("Local IP Address: " + test);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
