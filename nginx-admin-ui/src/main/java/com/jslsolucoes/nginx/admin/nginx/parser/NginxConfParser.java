package com.jslsolucoes.nginx.admin.nginx.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.StringUtils;

public class NginxConfParser {


	public static void main(String[] args) throws IOException {
		
		
		String location = "/Volumes/hd2/workspace/github/nginx-admin/nginx-admin-ui/src/main/resources/nginx/nginx.conf";
		
		String conf = FileUtils.readFileToString(new File(location), "UTF-8");
		
		Matcher matcher = Pattern.compile("include (.*)/(.*);").matcher(conf);
		while(matcher.find()){
			String directory = matcher.group(1).trim();
			String pattern = matcher.group(2).trim().replaceAll("\\*", "\\.\\*");
			for(File file : FileUtils.listFiles(new File(directory), new RegexFileFilter(pattern), null)){
				String content = FileUtils.readFileToString(file, "UTF-8");
				
				
				Matcher upstreamers = Pattern.compile("upstream(\\s{1,})(.*?)\\{(.*?)\\}",Pattern.DOTALL).matcher(content);
				while(upstreamers.find()){
					System.out.println(upstreamers.group(2).trim());
					
					String body = upstreamers.group(3);
					
					if(Pattern.compile("ip_hash;").matcher(body).find()){
						System.out.println("ip_hash");
					}
					
					Matcher ips = Pattern.compile("server(\\s{1,})(.*?)(:([0-9]{2,}))?(\\s(.*?))?;").matcher(body);
					while(ips.find()){
						System.out.println(ips.group(2).trim());
						if(!StringUtils.isEmpty(ips.group(4))){
							System.out.println(ips.group(4).trim());
						}	
					}
				}
				
				
				if(Pattern.compile("server(\\s{1,})\\{").matcher(content).find()){
					
					System.out.println(file.getAbsolutePath());
					
					List<String> lines = FileUtils.readLines(file,"UTF-8");
					AtomicInteger atomicInteger = new AtomicInteger(0);
					AtomicInteger currentLine = new AtomicInteger(1);
					Integer indexStart = 0;
					for(String line : lines){
						
						if(line.contains("{")){
							atomicInteger.getAndIncrement();
							if(line.contains("server")){
								indexStart = currentLine.get() - 1; 
							}
						} else if(line.contains("}")){
							atomicInteger.getAndDecrement();
							if(atomicInteger.get() == 0){
								String block = StringUtils.join(lines.subList(indexStart, currentLine.get()),"\n");
								Matcher listen = Pattern.compile("listen(\\s{1,})([0-9]{2,})(\\s(.*?))?;").matcher(block);
								while(listen.find()){
									System.out.println(listen.group(2));
								}
								
								Matcher serverName = Pattern.compile("server_name(\\s{1,})(.*?);").matcher(block);
								while(serverName.find()){
									for(String name : serverName.group(2).split(" ")){
										System.out.println(name);
									}
								}
								
								Matcher sslCertificate = Pattern.compile("ssl_certificate(\\s{1,})(.*?);").matcher(block);
								while(sslCertificate.find()){
									System.out.println(sslCertificate.group(2));
								}
								
								Matcher sslCertificateKey = Pattern.compile("ssl_certificate_key(\\s{1,})(.*?);").matcher(block);
								while(sslCertificateKey.find()){
									System.out.println(sslCertificateKey.group(2));
								}
								
							}
						}
						
						currentLine.getAndIncrement();
						
					}
					
				}
				
				
				
				
				
			}
		}
		
		
	}
}
