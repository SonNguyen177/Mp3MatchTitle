/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonnh.audiorenametitle;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author nguye
 */
public class Main {

    private static final String ROOT_FILE_PATH = "D:/Relax/English/Lyly/Part 2/ET4P2";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(" ----- Hi, this is rename audio tool has written by Son Nguyen. ----- ");
        System.out.println("To use, you only need to copy file 'Start' & folder 'lib to folder where contains your audio files.");
        System.out.println("Have a free-hand time!");
        System.out.println("--------------------------------------------------------------------------");

        try {
            String executionPath = System.getProperty("user.dir");

            String location = executionPath.replace("\\", "/");
           // int lastFolderIdx = location.lastIndexOf("/");
           // location = location.substring(0, lastFolderIdx);
            System.out.println("Ok, I will check this folder : " + location);

            exploreFolder(location);
        } catch (Exception e) {
            System.out.println("Exception caught =" + e.getMessage());
        }

    }

    private static void exploreFolder(String folderPath) {

        //System.out.println("START RENAME FILE (IF HAVE)!");

        File f = new File(folderPath);
        File[] allSubFiles = f.listFiles();
        for (File file : allSubFiles) {
            if (file.isDirectory()) {
                //System.out.println(file.getAbsolutePath() + " is directory");
                //Steps for directory
            } else {
                // System.out.println(file.getAbsolutePath());
                //steps for files

                String ext = FilenameUtils.getExtension(file.getAbsolutePath());
                if (ext.equals("mp3")) {
                    changeTitleByFileName(file);
                }
            }
        }

        System.out.println("Done all!");
    }

    private static void changeTitleByFileName(File file) {

        try {
            Mp3File mp3file = new Mp3File(file);
            ID3v2 id3v2Tag;
            if (mp3file.hasId3v1Tag()) {
                id3v2Tag = mp3file.getId3v2Tag();

            } else {
                // mp3 does not have an ID3v2 tag, let's create one..
                id3v2Tag = new ID3v24Tag();
                mp3file.setId3v1Tag(id3v2Tag);
            }

            String fileName = file.getName().substring(0, file.getName().length() - 4);

            // set name for title
            if (!id3v2Tag.getTitle().equals(fileName)) {

                System.out.println("Oh, I have discovered a miss match between Name= [" + fileName +"] and Title= [" + id3v2Tag.getTitle() +"]");
                id3v2Tag.setTitle(fileName);

                // save info
                mp3file.save(file.getName());
                System.out.println("Rename success!");
            }
//                System.out.println("Track: " + id3v2Tag.getTrack());
//                System.out.println("Artist: " + id3v2Tag.getArtist());

//                System.out.println("Album: " + id3v2Tag.getAlbum());
//                System.out.println("Year: " + id3v2Tag.getYear());
//                System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
//                System.out.println("Comment: " + id3v2Tag.getComment());
        } catch (IOException | UnsupportedTagException | InvalidDataException | NotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
