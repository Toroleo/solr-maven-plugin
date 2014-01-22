package com.ffbit.maven.solr.artefact.external;

/**
 * External Maven Artifact factory.
 */
public class ExternalArtifactsFactory {

    /**
     * Get external maven artifacts instance which represents artifacts
     * for current a given Apache Solr version.
     *
     * @param solrVersion current running Apache Solr version.
     * @return external maven artifacts instance for the current running Apache Solr version.
     */
    public ExternalArtifacts getExternalArtifacts(String solrVersion) {
    	return new CommonExternalArtifacts(solrVersion);
    }
}
