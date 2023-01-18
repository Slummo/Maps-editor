package backend;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TrackService {

    public void getTrackGpx(File f) throws IOException {
       FileReader fr = new FileReader(f);
       BufferedReader br = new BufferedReader(fr);
       String line, lon, lat;
       String arr[] = new String[3];

       while(br.readLine() != null){
          lon = lat = null;
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
}


