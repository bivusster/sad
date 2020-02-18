package com.company.sad;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Util {

    public void scan(File source, File dest) {
        File[] files = source.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String extension = FilenameUtils.getExtension(file.getName());
                if (extension.equals("class")) {
                    decompile(file);
                } else {
                    copy(file);
                }
            }

            if (file.isDirectory()) {
                scan(file, dest);
            }
        }
    }

    public void copyFolderStructure(File source, File dest) {
        if (source.isDirectory()) {
            if (!dest.isDirectory()) {
                dest.mkdir();
            }
        }

        File[] files = source.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                File srcFile = new File(source, file.getName());
                File destFile = new File(dest, file.getName());
                copyFolderStructure(srcFile, destFile);
            }
        }
    }

    public void decompile(File file) {
        System.out.println(String.format("Decompile file - %s", file.getName()));
        try {
            String className = file.getName().replaceAll(".class", "");
            String[] command = new String[7];
            command[0] = "cmd";
            command[1] = "/c";
            command[2] = "scalap -cp";
            command[3] = file.getAbsolutePath().replace(file.getName(), "");
            command[4] = className;
            command[5] = ">";
            command[6] = file.getAbsolutePath().replace(Main.SOURCE_ROOT, Main.DEST_ROOT).replace(".class", ".scala");
            ProcessBuilder prc = new ProcessBuilder(command);
            Process p = prc.start();
            readData(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copy(File file) {
        System.out.println(String.format("Copy file - %s", file.getName()));
        try {
            String[] command = new String[5];
            command[0] = "cmd";
            command[1] = "/c";
            command[2] = "copy";
            command[3] = file.getAbsolutePath();
            command[4] = file.getAbsolutePath().replace(Main.SOURCE_ROOT, Main.DEST_ROOT);
            ProcessBuilder prc = new ProcessBuilder(command);
            Process p = prc.start();
            readData(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readData(Process run) throws Exception {
        String line;

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(run.getInputStream()));
        BufferedReader outputReader = new BufferedReader(new InputStreamReader(run.getErrorStream()));
        while ((line = inputReader.readLine()) != null) {
            System.out.println(line);
        }
        inputReader.close();
        while ((line = outputReader.readLine()) != null) {
            System.out.println(line);
        }
        outputReader.close();
        run.waitFor();
    }
}