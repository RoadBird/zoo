package io;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyLogger {
    public static void log(String str) {
        Date date = new Date();
        Calendar c = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("dd_MM_YYYY");
        DateFormat tf = new SimpleDateFormat("YYYY_MM_dd HH:mm:ss.SSS");
        String fileName = df.format(date);
        File dir = new File("../logs");
        if(!dir.exists())
            dir.mkdirs();
        File fileLog = new File("../logs/log_" + fileName + ".txt");
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileLog, true)));
            pw.println(tf.format(new Date()) + " " + str);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
