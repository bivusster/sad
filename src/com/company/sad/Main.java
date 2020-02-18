package com.company.sad;

import java.io.File;

public class Main {
    public static String DEFAULT_SOURCE = "E:\\scala-task\\mcs-select.mcs-select-1.2019.5.1-SNAPSHOT";
    public static String DEFAULT_DEST = "E:\\mcs-select.mcs-select-1.2019.5.1-SNAPSHOT-COPY";

    public static void main(String[] args) {
        File source = new File(DEFAULT_SOURCE);
        File dest = new File(DEFAULT_DEST);
        Util util = new Util();
        util.copyFolderStructure(source, dest);
        util.scan(source, dest);
    }
}
