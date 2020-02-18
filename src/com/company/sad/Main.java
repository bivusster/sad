package com.company.sad;

import java.io.File;

public class Main {
    public static String SOURCE_ROOT = "E:\\scala-task\\mcs-select.mcs-select-1.2019.5.1-SNAPSHOT";
    public static String DEST_ROOT = "E:\\mcs-select.mcs-select-1.2019.5.1-SNAPSHOT-COPY";

    public static void main(String[] args) {
        File source = new File(SOURCE_ROOT);
        File dest = new File(DEST_ROOT);
        Util util = new Util();
        util.copyFolderStructure(source, dest);
        util.scan(source, dest);
    }
}
