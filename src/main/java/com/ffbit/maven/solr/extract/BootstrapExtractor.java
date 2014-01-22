package com.ffbit.maven.solr.extract;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import java.io.File;
import java.io.IOException;

/**
 * Extracts Apache Solr configuration files.
 */
class BootstrapExtractor {
    /**
     * Simple logger.
     */
    private Log log = new SystemStreamLog();

    /**
     * Directory to extract configuration files to.
     */
    private File destinationRoot;
    
    private File solrConfDir;

    /**
     * Bootstrapping strategy delegate.
     */
    private BootstrapConfiguration configuration;

    public BootstrapExtractor(BootstrapConfiguration conf) {
        this.destinationRoot = conf.getSolrHome();
        this.configuration = conf;
        this.solrConfDir = conf.getSolrConfDir();
    }

    public void extract() {
        checkDestinationRoot();
        copySolrConf();
        createSuccessFile();
    }

    private void checkDestinationRoot() {
        destinationRoot.mkdirs();
        boolean isDirectory = destinationRoot.isDirectory();
        boolean isWritable = destinationRoot.canWrite();

        if (isDirectory && isWritable) {
            log.info("Solr home directrory: " + destinationRoot.getAbsolutePath());
            return;
        }

        String format = "%s is not writable or is not a directory";
        String message = String.format(format, destinationRoot.getAbsolutePath());

        throw new RuntimeException(message);
    }

    private void copySolrConf() {
        try {
        	FileUtils.copyDirectory(solrConfDir, destinationRoot);
//        	if (solrConfDir.isDirectory()) {
//	        	for (File file : solrConfDir.listFiles()) {
//	        		copyFolder(file, destinationRoot);        		
//	        	}
//        	} else {
//        		copyFolder(solrConfDir, destinationRoot);
//        	}
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
   
    private void createSuccessFile() {
        try {
            configuration.getSuccessFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}