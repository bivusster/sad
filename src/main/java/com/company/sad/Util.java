package com.company.sad;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Util {

    public static void scan(String src, File source, File dest, String lang, boolean isCopyFolderStructure) {
    	if ( isCopyFolderStructure ) {
    		copyFolderStructure(source, dest);
    	}
        File[] files = source.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String extension = FilenameUtils.getExtension(file.getName());
                if (extension.equals("class")) {
                    decompile(src, file, source.getAbsolutePath(), dest.getAbsolutePath(), lang);
                } else {
                    copy(file, source.getAbsolutePath(), dest.getAbsolutePath());
                }
            }

            if (file.isDirectory()) {
                scan(src, file, dest, lang, false);
            }
        }
    }

    public static void copyFolderStructure(File source, File dest) {
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
    
    private static boolean isJava(String lang) {
    	return "java".equals(lang);
    }

    public static void decompile(String src, File file, String sourceRoot, String destRoot, String lang) {
        Logger.debug(null, String.format("Decompile file - %s", file.getName()));
        try {

            String path = file.getAbsolutePath();
            int from = path.indexOf(src);

            
        	String className = isJava(lang) ? path.substring(from + src.length()+1).replaceAll(".class", "").replaceAll("\\\\",".") : file.getName().replaceAll(".class", "");
            
        	String classpath = isJava(lang) ? path.substring(0, from + src.length()+1) : file.getAbsolutePath().replace(file.getName(), "");

        			
            String[] command = new String[5];
            command[0] = "cmd";
            command[1] = "/c";
            command[2] = (isJava(lang) ? "javap -cp " : "scalap -cp ") + classpath + " " + className;
            command[3] = ">";
            command[4] = file.getAbsolutePath().replace(sourceRoot, destRoot).replace(".class", isJava(lang) ? ".java" : ".scala");
            ProcessBuilder prc = new ProcessBuilder(command);
            Process p = prc.start();
            readData(p);
        } catch (Exception e) {
            Logger.error(e, "Could not decompile");
        }
    }

    public static void copy(File file, String sourceRoot, String destRoot) {
        Logger.debug(null, String.format("Copy file - %s", file.getName()));
        try {
            String[] command = new String[5];
            command[0] = "cmd";
            command[1] = "/c";
            command[2] = "copy";
            command[3] = file.getAbsolutePath();
            command[4] = file.getAbsolutePath().replace(sourceRoot, destRoot);
            ProcessBuilder prc = new ProcessBuilder(command);
            Process p = prc.start();
            readData(p);
        } catch (Exception e) {
            Logger.error(e, "Could not copy");
        }
    }

    private static void readData(Process run) throws Exception {
        String line;
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(run.getInputStream()));
        BufferedReader outputReader = new BufferedReader(new InputStreamReader(run.getErrorStream()));
        while ((line = inputReader.readLine()) != null) {
        	Logger.debug(null, line);
        }
        inputReader.close();
        while ((line = outputReader.readLine()) != null) {
        	Logger.error(null, line);
        }
        outputReader.close();
        run.waitFor();
    }
}