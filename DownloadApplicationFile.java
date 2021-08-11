/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.department.app.downloadapplicationfile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author timsebring
 */
public class DownloadApplicationFile {
   
    /**
     * @param args
     * @throws java.net.MalformedURLException
     */
    private static String urlfile = "";
    private static String outputfile = "";
    private static FileOutputStream fos;
    
    public static void main(String[] args) throws MalformedURLException, IOException, Exception{
        
        if (args.length != 2) {
            throw new Exception("Requires 2 parameters. Usage: DownloadApplicationFile <url> <local filename>");
        }
        urlfile = args[0];
        outputfile = args[1];
        
        if(urlfile.length() < 1) {
            throw new Exception("URL to download needs to be a valid URL.");
        }
        if(outputfile.length() < 1) {
            throw new Exception("Output filename needs to be a valid filename.");
        }
        
//        try {
            
            URL website = new URL(urlfile);

            // Create the channel to download the file
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            // Set the local output filename
            fos = new FileOutputStream(outputfile);
            // Transfer the file using getChannel()
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        
//        }
//        catch (IOException ioe) {
//                System.out.println("Error opening FileOutputStream for transfer");
//        }
        
//        finally {
            try {
                if (fos != null)
                {
                    fos.close();
                    
                }
            }
            catch (IOException ioe) {
                System.out.println("Error closing File Output Stream");
            }
 //       }
            
        System.exit(0); // Successful return code
    }
    
    
}