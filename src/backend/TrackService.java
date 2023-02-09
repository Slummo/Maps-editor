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

                    CordsConverter.Deg2UTM converter = new CordsConverter.Deg2UTM(latDouble, lonDouble);

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

    public static void exportAsGpx(String path, ArrayList<Segment> arr) {

    }

    public static String[] getTrackGeneralInfo(String path) {
        String text;
        String[] arr;
        text = readText(path);
        arr = text.split(";");

        return new String[]{arr[0], arr[4], arr[5]};
    }

    private static String readText(String path) {
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

    private static void writeText(String path, String text) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(path);
            fw.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }
}




