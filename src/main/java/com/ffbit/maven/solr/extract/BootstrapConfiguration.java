package com.ffbit.maven.solr.extract;

import com.ffbit.maven.solr.artefact.ArtifactResolver;
import com.ffbit.maven.solr.artefact.SolrVersionConfiguration;

import java.io.File;

/**
 * Represents configuration for bootstrapping Apache Solr configuration.
 */
public interface BootstrapConfiguration extends SolrVersionConfiguration {

    /**
     * Success marker file.
     */
    String SUCCESS_FILE = ".solr-maven-plugin-bootstrap-success-file";

    /**
     * Get Apache Solr Home directory.
     *
     * @return Apache Solr Home directory.
     */
    File getSolrHome();
    
    String getSolrVersion();
    
    File getSolrConfDir();

    /**
     * Get current Maven Artifact resolver.
     *
     * @return current Maven Artifact resolver.
     */
    ArtifactResolver getArtifactResolver();

    /**
     * Get configured configuration bootstrapping strategy type.
     *
     * @return configured configuration bootstrapping strategy type.
     */
    BootstrapStrategyType getBootstrapStrategyType();

    /**
     * Get success marker file.
     *
     * @return success marker file.
     */
    File getSuccessFile();

}
