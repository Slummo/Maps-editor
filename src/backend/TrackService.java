package backend;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class TrackService {

    public static ArrayList<Segment> getTrackGpx(String gpxPath, String tfwPath) {
        FileReader fr;
        String line;
        ArrayList<Point> points = new ArrayList<>();
        try {
            fr = new FileReader(gpxPath);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if(line.contains("<trkpt")) {
                    String[] arr = line.split(" ");
                    String[] arr2 = new String[] {arr[7], arr[8]};

                    String lat = arr2[0].replaceAll("lat=\"", "");
                    lat = lat.replaceAll("\"", "");
                    double latDouble = Double.parseDouble(lat);

                    String lon = arr2[1].replaceAll("lon=\"", "");
                    lon = lon.replaceAll("\">", "");
                    double lonDouble = Double.parseDouble(lon);

                    Deg2UTM converter = new Deg2UTM(latDouble, lonDouble);

                    String[] info = getTrackGeneralInfo(tfwPath);

                    int eastingOffset = (int) Double.parseDouble(info[1]);
                    int northingOffset = (int) Double.parseDouble(info[2]);

                    points.add(
                        new Point (
                            (int) converter.Easting - eastingOffset,
                            (int) (northingOffset - converter.Northing)
                        )
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Segment> segments = new ArrayList<>();

        for(int i = 0; i < points.size(); i++) {
            if(i == points.size() - 1) {
                i = 0;
                segments.add(new Segment(points.get(i + points.size() - 1).x, points.get(i + points.size() - 1).y, points.get(i).x, points.get(i).y, Color.RED));
                break;
            }
            segments.add(new Segment(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y, Color.RED));
        }

        return segments;
    }

    public static String[] getTrackGeneralInfo(String path) {
        String text;
        String[] arr;
        try {
            text = readText(path);
            arr = text.split(";");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new String[]{arr[0], arr[4], arr[5]};
    }

    private static String readText(String path) throws IOException {
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

class Deg2UTM
{
    public double Easting;
    public double Northing;
    private int Zone;
    private char Letter;

    public Deg2UTM(double Lat, double Lon) {
        Zone= (int) Math.floor(Lon/6+31);
        if (Lat<-72)
            Letter='C';
        else if (Lat<-64)
            Letter='D';
        else if (Lat<-56)
            Letter='E';
        else if (Lat<-48)
            Letter='F';
        else if (Lat<-40)
            Letter='G';
        else if (Lat<-32)
            Letter='H';
        else if (Lat<-24)
            Letter='J';
        else if (Lat<-16)
            Letter='K';
        else if (Lat<-8)
            Letter='L';
        else if (Lat<0)
            Letter='M';
        else if (Lat<8)
            Letter='N';
        else if (Lat<16)
            Letter='P';
        else if (Lat<24)
            Letter='Q';
        else if (Lat<32)
            Letter='R';
        else if (Lat<40)
            Letter='S';
        else if (Lat<48)
            Letter='T';
        else if (Lat<56)
            Letter='U';
        else if (Lat<64)
            Letter='V';
        else if (Lat<72)
            Letter='W';
        else
            Letter='X';
        Easting=0.5*Math.log((1+Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180))/(1-Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180)))*0.9996*6399593.62/Math.pow((1+Math.pow(0.0820944379, 2)*Math.pow(Math.cos(Lat*Math.PI/180), 2)), 0.5)*(1+ Math.pow(0.0820944379,2)/2*Math.pow((0.5*Math.log((1+Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180))/(1-Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180)))),2)*Math.pow(Math.cos(Lat*Math.PI/180),2)/3)+500000;
        Easting=Math.round(Easting*100)*0.01;
        Northing = (Math.atan(Math.tan(Lat*Math.PI/180)/Math.cos((Lon*Math.PI/180-(6*Zone -183)*Math.PI/180)))-Lat*Math.PI/180)*0.9996*6399593.625/Math.sqrt(1+0.006739496742*Math.pow(Math.cos(Lat*Math.PI/180),2))*(1+0.006739496742/2*Math.pow(0.5*Math.log((1+Math.cos(Lat*Math.PI/180)*Math.sin((Lon*Math.PI/180-(6*Zone -183)*Math.PI/180)))/(1-Math.cos(Lat*Math.PI/180)*Math.sin((Lon*Math.PI/180-(6*Zone -183)*Math.PI/180)))),2)*Math.pow(Math.cos(Lat*Math.PI/180),2))+0.9996*6399593.625*(Lat*Math.PI/180-0.005054622556*(Lat*Math.PI/180+Math.sin(2*Lat*Math.PI/180)/2)+4.258201531e-05*(3*(Lat*Math.PI/180+Math.sin(2*Lat*Math.PI/180)/2)+Math.sin(2*Lat*Math.PI/180)*Math.pow(Math.cos(Lat*Math.PI/180),2))/4-1.674057895e-07*(5*(3*(Lat*Math.PI/180+Math.sin(2*Lat*Math.PI/180)/2)+Math.sin(2*Lat*Math.PI/180)*Math.pow(Math.cos(Lat*Math.PI/180),2))/4+Math.sin(2*Lat*Math.PI/180)*Math.pow(Math.cos(Lat*Math.PI/180),2)*Math.pow(Math.cos(Lat*Math.PI/180),2))/3);
        if (Letter<'M')
            Northing = Northing + 10000000;
        Northing=Math.round(Northing*100)*0.01;
    }
}


