package frontend;

import backend.FileFilterGpx;
import backend.TrackService;
import backend.ZoomSlider;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//TODO Aggiungere listener (e creare relative funzioni) per alcune voci di menu

public class MainFrame extends JFrame {
    private LayerManager layerManager;
    private ZoomSlider zoomSlider;

    public MainFrame(int width, int height) {
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Maps editor");
        setLocationRelativeTo(null);
        init();
        setVisible(true);
    }

    public MainFrame() {
        this(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void init() {
        layerManager = new LayerManager();
        add(layerManager, BorderLayout.CENTER);

        zoomSlider = new ZoomSlider();
        zoomSlider.addChangeListener(e -> {
            layerManager.setScale(zoomSlider.getValue());
            layerManager.setZoomFactor(zoomSlider.getValue());
        });
        add(zoomSlider, BorderLayout.NORTH);
        layerManager.setSlider(zoomSlider);

        setJMenuBar(createMenuBar());
    }

    private JMenuBar createMenuBar() {
        //Menu file
        JMenu file = new JMenu("File");

        /*JMenuItem f_openMap = new JMenuItem("Apri");
        f_open.addActionListener(e -> {
            layerManager.getMapLayer().removeText();
            JFileChooser fc = new JFileChooser();
            int returnValue = fc.showOpenDialog(this);
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                layerManager.getMapLayer().addMapImage(f.getAbsolutePath());
            }
            layerManager.startToListen();
        });
        */

        /*JMenuItem f_openTrack = new JMenuItem("Apri traccia");
        f_openTrack.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileFilterGpx());
            fc.setAcceptAllFileFilterUsed(false);
            int returnValue = fc.showOpenDialog(this);
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                try{
                    TrackService.getTrackGpx(f);
                }catch(IOException exception){
                    //option pane
                }
            }
        }); */

        JMenuItem f_export = new JMenuItem("Esporta");
        f_export.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int returnValue = fc.showSaveDialog(this);
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                layerManager.getRoadLayer().exportAsGpx(f.getAbsolutePath());
            }
        });

        JMenuItem f_exit = new JMenuItem("Esci");
        f_exit.addActionListener(e -> System.exit(0));
        //file.add(f_open);
        file.addSeparator();
        file.add(f_export);
        file.addSeparator();
        file.add(f_exit);

        //Menu strumenti
        JMenu tools = new JMenu("Strumenti");
        JMenuItem t_AddSegment = new JMenuItem("Aggiungi segmento");
        t_AddSegment.addActionListener(e -> layerManager.newSegment());
        JMenuItem t_RemoveSegments = new JMenuItem("Rimuovi tutti i segmenti");
        t_RemoveSegments.addActionListener(e -> layerManager.clearSegments());
        tools.add(t_AddSegment);
        tools.add(t_RemoveSegments);

        JMenuBar bar = new JMenuBar();
        bar.add(file);
        bar.add(tools);

        return bar;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}