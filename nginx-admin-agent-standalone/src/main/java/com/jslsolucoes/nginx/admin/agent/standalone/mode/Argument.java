package com.jslsolucoes.nginx.admin.agent.standalone.mode;

public class Argument {

    private String conf;

    private Boolean quit;

    public String getConf() {
	return conf;
    }

    public void setConf(String conf) {
	this.conf = conf;
    }

    public Boolean getQuit() {
	return quit;
    }

    public void setQuit(Boolean quit) {
	this.quit = quit;
    }
}