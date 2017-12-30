package com.jslsolucoes.nginx.admin.standalone.mode;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentMode {

	private String[] args;

	public ArgumentMode(String[] args) {
		this.args = args;
	}

	public Argument parse() throws ParseException {
		Options options = new Options();
		options.addOption(new Option("help", false, "Help"));
		options.addOption(new Option("c", true, "Conf file location"));

		CommandLineParser commandLineParser = new DefaultParser();
		CommandLine commandLine = commandLineParser.parse(options, args);

		Argument argument = new Argument();
		argument.setQuit(false);

		if (commandLine.hasOption("help")) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp("java -jar **.jar", options);
			argument.setQuit(true);
		}

		for (Option option : options.getOptions()) {
			if (option.hasArg() && commandLine.getOptionValue(option.getOpt()) == null) {
				System.out
						.println("Argument -" + option.getOpt() + " is required to launch. Use -help to see arguments");
				argument.setQuit(true);
			}
		}

		if (!argument.getQuit()) {
			argument.setConf(commandLine.getOptionValue("c"));
		}

		return argument;
	}

}