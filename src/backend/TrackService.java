package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class TrackService {

    public static void getTrackGpx(File f) throws IOException {
       FileReader fr = new FileReader(f);
       BufferedReader br = new BufferedReader(fr);
       String line, lon, lat;
       String arr[];

       while(br.readLine() != null){
           br.readLine();
           line = br.toString();
           if(line.startsWith("<trkpt")){
               arr = line.split(" ");

               lat = arr[1];
               lat.replaceAll("lat=\"", "");
               lat.replaceAll("\"", "");

               lon = arr[2];
               lon.replaceAll("lon=\"", "");
               lon.replaceAll("\">", "");

           }
       }
    }

    public static String[] getTrackGeneralInfo(String path) {
        String text;
        String[] arr;
        try {
            text = tfwReading(path);
            System.out.println(text);
            arr = text.split(";");
            System.out.println(Arrays.toString(arr));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new String[]{arr[0], arr[4], arr[5]};
    }

    private static String tfwReading(String path) throws IOException {
        FileReader fr = null;
        StringBuilder text = new StringBuilder();
        try {
            fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null){
                text.append(line);
                text.append(";");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
        return text.toString();
    }
}


