package com.company.sad;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
	
    public static void main(String[] args) {
		final Option sourceOption = new Option("s", "source", true, "Source");
		final Option destOption = new Option("d", "dest", true, "Dest");
		final Option langOption = new Option("l", "lang", true, "Lang");
		final CommandLineParser cmdLinePosixParser = new DefaultParser();
		final Options posixOptions = new Options();
		
		String source = "assets\\src";
		String dest = "assets\\dest";
		String lang = "java";
		
		posixOptions.addOption(sourceOption);
		posixOptions.addOption(destOption);
		posixOptions.addOption(langOption);
		try {
			final CommandLine commandLine = cmdLinePosixParser.parse(posixOptions, args);
			if (commandLine.hasOption("s")) 
				source = commandLine.getOptionValue("s");
			if (commandLine.hasOption("d")) 
				dest = commandLine.getOptionValue("d");
			if (commandLine.hasOption("l")) 
				lang = commandLine.getOptionValue("l");
		} catch (Exception e) {
			Logger.error(e, "Sad error");
		}
		
		Logger.debug(null, "Sad launch. Source: " + source + ". Dest: " + dest + ". Lang: " + lang);
		
        Util.scan(source, new File(source), new File(dest), lang, true);
    }
}
