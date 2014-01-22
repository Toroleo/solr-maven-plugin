package com.ffbit.maven.solr.jetty;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Start point for Jetty embedded Servlet Container.
 */
public class JettyRunner {
    /**
     * Simple logger.
     */
    private Log log = new SystemStreamLog();

    /**
     * Jetty configuration.
     */
    private JettyConfiguration configuration;

    /**
     * Jetty server.
     */
    private Server server;

    public JettyRunner(JettyConfiguration config) {
        this.configuration = config;
        server = new Server();

        initialize();
    }

    private void initialize() {
        server.setStopAtShutdown(true);
        applyJettyXml();
    }

    public void run() {
        start();
        join();
    }

    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void join() {
        try {
            Thread.currentThread().join(configuration.getServerWaitingTimeout());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

    private void applyJettyXml() {
        try {
            for (File xmlFile : getJettyXmlFiles()) {
                log.info("Configuring Jetty from xml configuration file = " + xmlFile.getCanonicalPath());
                XmlConfiguration xmlConfiguration = new XmlConfiguration(Resource.toURL(xmlFile));
                addProperties(xmlConfiguration);
                xmlConfiguration.configure(server);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private List<File> getJettyXmlFiles() {
        List<File> jettyXmlFiles = new ArrayList<File>();
        String jettyXml = configuration.getJettyXml();

        if (jettyXml.trim().isEmpty()) {
            return jettyXmlFiles;
        }

        for (String path : jettyXml.split(",")) {
            File file = new File(path);

            if (file.exists()) {
                jettyXmlFiles.add(file);
            } else {
                log.warn("Jetty XML configuration path `" + path
                        + "` does not exists.\nCheck Solr Maven plugin configuration.");
            }
        }

        return jettyXmlFiles;
    }

    private void addProperties(XmlConfiguration xmlConfiguration) {
    	for (Map.Entry<String, Object> entry : configuration.getSystemPropertiesToSet().entrySet()) {
        	if (entry.getValue() != null) {
        		xmlConfiguration.getProperties().put(entry.getKey(), entry.getValue().toString());
        	}
        }
    }
}