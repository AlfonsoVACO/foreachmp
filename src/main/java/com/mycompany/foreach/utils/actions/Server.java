/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils.actions;

import com.mycompany.foreach.models.Mws82;
import com.mycompany.foreach.models.Mws98;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author 6_Delta
 */
public class Server {

    public final Mws98 mwsno;
    public final Mws82 mwsod;

    public Server(Mws98 mwsno, Mws82 mwsod) {
        this.mwsno = mwsno;
        this.mwsod = mwsod;
    }

    public void obtenerXML() {

    }

    public void obtenerStats() {

    }

    private void getConexion() {
        ServerSocket server;
        Socket connection;
        DataOutputStream output;
        BufferedInputStream bis;
        BufferedOutputStream bos;

        byte[] receivedData;
        int in;
        String file;

        try {
            server = new ServerSocket(5000);
            while (true) {
                connection = server.accept();
                receivedData = new byte[1024];
                bis = new BufferedInputStream(connection.getInputStream());
                DataInputStream dis = new DataInputStream(connection.getInputStream());
                file = dis.readUTF();
                file = file.substring(file.indexOf('\\') + 1, file.length());
                bos = new BufferedOutputStream(new FileOutputStream(file));
                while ((in = bis.read(receivedData)) != -1) {
                    bos.write(receivedData, 0, in);
                }
                bos.close();
                dis.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void cliente() {
        DataInputStream input;
        BufferedInputStream bis;
        BufferedOutputStream bos;
        int in;
        byte[] byteArray;
        final String filename = "c:\\test.pdf";

        try {
            final File localFile = new File(filename);
            Socket client = new Socket("localhost", 5000);
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bos = new BufferedOutputStream(client.getOutputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(localFile.getName());
            byteArray = new byte[8192];
            while ((in = bis.read(byteArray)) != -1) {
                bos.write(byteArray, 0, in);
            }
            bis.close();
            bos.close();

        } catch (IOException e) {
            System.err.println(e);
        }
    }
} 