package com.jslsolucoes.nginx.admin.standalone;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.undertow.WARArchive;

public class Main {

	public static void main(String[] args) throws Exception {
		
		
		Options options = new Options();
	    options.addOption(new Option("help", false, "Help"));
	    options.addOption(new Option("bind", true, "Port to manager listening (default localhost/0.0.0.0)"));
	    options.addOption(new Option("port", true, "Port to manager listening (default 3000)"));
		options.addOption(new Option("dbuser", true, "Database user"));
		options.addOption(new Option("dbpassword", true, "Database password"));
		
		CommandLineParser commandLineParser = new DefaultParser();
		CommandLine commandLine = commandLineParser.parse(options, args);
		
		if (commandLine.hasOption("help")) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp("java -jar nginx-admin-standalone-1.0.0-swarm.jar", options);
			return;
		}
				
		for(Option option : options.getOptions()){
			if(option.hasArg() && !commandLine.hasOption(option.getOpt())){
				System.out.println(option.getOpt() + " is required to run manager");
				return;
			}
		}
		
		args = new String []{"-Dswarm.http.port="+commandLine.getOptionValue("port"),
				"-Dswarm.bind.address="+commandLine.getOptionValue("bind")};
		
		Swarm swarm = new Swarm(args);
		swarm.fraction(new DatasourcesFraction().dataSource("AdminDS", (ds) -> {
			ds.driverName("h2");
			ds.connectionUrl("jdbc:h2:~/nginx-admin/database;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
			ds.userName(commandLine.getOptionValue("dbuser"));
			ds.password(commandLine.getOptionValue("dbpassword"));
			ds.jndiName("java:jboss/datasources/AdminDS");
		}));
		swarm.start();

		InputStream war = Main.class.getResourceAsStream("/nginx-admin-ui.war");
		File file = File.createTempFile(UUID.randomUUID().toString(), ".war");
		FileUtils.copyInputStreamToFile(war, file);
		file.deleteOnExit();

		WARArchive warArchive = ShrinkWrap.createFromZipFile(WARArchive.class, file);
		swarm.deploy(warArchive);
	}
}
