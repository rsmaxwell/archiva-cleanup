package com.rsmaxwell.archiva.cleanup;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class App {

	private static final String nl = System.getProperty("line.separator");

	private static CommandLine getCommandLine(String[] args) throws ParseException {

		// @formatter:off
		Option version = Option.builder("v")
				            .longOpt("version")
				            .argName("version")
				            .desc("show program version")
				            .build();
		
		Option help = Option.builder("h")
				            .longOpt("help")
				            .argName("help")
				            .desc("show program help")
				            .build();
	
		// @formatter:on

		Options options = new Options();
		options.addOption(version);
		options.addOption(help);

		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(options, args);

		if (line.hasOption("v")) {
			System.out.println("version:   " + Version.version());
			System.out.println("buildID:   " + Version.buildID());
			System.out.println("buildDate: " + Version.buildDate());
			System.out.println("gitCommit: " + Version.gitCommit());
			System.out.println("gitBranch: " + Version.gitBranch());
			System.out.println("gitURL:    " + Version.gitURL());
			System.exit(0);

		} else if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("extractor <OPTION> ", options);
			System.exit(0);
		}

		return line;
	}

	public static void main(String[] args) throws Exception {

		CommandLine line = getCommandLine(args);

		try {
			Cleanup cleanup = new Cleanup();
			cleanup.perform();

		} catch (AppException e) {
			System.out.println(e.getMessage());
		}
	}
}
