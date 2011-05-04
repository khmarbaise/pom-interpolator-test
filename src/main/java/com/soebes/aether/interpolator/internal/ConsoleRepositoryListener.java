package com.soebes.aether.interpolator.internal;

/*******************************************************************************
 * Copyright (c) 2010-2011 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * The Eclipse Public License is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 * The Apache License v2.0 is available at
 *   http://www.apache.org/licenses/LICENSE-2.0.html
 * You may elect to redistribute this code under either of these licenses.
 *******************************************************************************/

import org.apache.log4j.Logger;
import org.sonatype.aether.AbstractRepositoryListener;
import org.sonatype.aether.RepositoryEvent;

/**
 * A simplistic repository listener that logs events to the console.
 */
public class ConsoleRepositoryListener extends AbstractRepositoryListener {

    private static Logger LOGGER = Logger.getLogger(ConsoleRepositoryListener.class);

    public ConsoleRepositoryListener() {
    }

    public void artifactDeployed(RepositoryEvent event) {
        LOGGER.trace("Deployed " + event.getArtifact() + " to "
                + event.getRepository());
    }

    public void artifactDeploying(RepositoryEvent event) {
        LOGGER.trace("Deploying " + event.getArtifact() + " to "
                + event.getRepository());
    }

    public void artifactDescriptorInvalid(RepositoryEvent event) {
        LOGGER.warn("Invalid artifact descriptor for " + event.getArtifact()
                + ": " + event.getException().getMessage());
    }

    public void artifactDescriptorMissing(RepositoryEvent event) {
        LOGGER.warn("Missing artifact descriptor for " + event.getArtifact());
    }

    public void artifactInstalled(RepositoryEvent event) {
        LOGGER.trace("Installed " + event.getArtifact() + " to "
                + event.getFile());
    }

    public void artifactInstalling(RepositoryEvent event) {
        LOGGER.trace("Installing " + event.getArtifact() + " to "
                + event.getFile());
    }

    public void artifactResolved(RepositoryEvent event) {
        LOGGER.trace("Resolved artifact " + event.getArtifact() + " from "
                + event.getRepository());
    }

    public void artifactDownloading(RepositoryEvent event) {
        LOGGER.trace("Downloading artifact " + event.getArtifact() + " from "
                + event.getRepository());
    }

    public void artifactDownloaded(RepositoryEvent event) {
        LOGGER.trace("Downloaded artifact " + event.getArtifact() + " from "
                + event.getRepository());
    }

    public void artifactResolving(RepositoryEvent event) {
        LOGGER.trace("Resolving artifact " + event.getArtifact());
    }

    public void metadataDeployed(RepositoryEvent event) {
        LOGGER.trace("Deployed " + event.getMetadata() + " to "
                + event.getRepository());
    }

    public void metadataDeploying(RepositoryEvent event) {
        LOGGER.trace("Deploying " + event.getMetadata() + " to "
                + event.getRepository());
    }

    public void metadataInstalled(RepositoryEvent event) {
        LOGGER.trace("Installed " + event.getMetadata() + " to "
                + event.getFile());
    }

    public void metadataInstalling(RepositoryEvent event) {
        LOGGER.trace("Installing " + event.getMetadata() + " to "
                + event.getFile());
    }

    public void metadataInvalid(RepositoryEvent event) {
        LOGGER.warn("Invalid metadata " + event.getMetadata());
    }

    public void metadataResolved(RepositoryEvent event) {
        LOGGER.trace("Resolved metadata " + event.getMetadata() + " from "
                + event.getRepository());
    }

    public void metadataResolving(RepositoryEvent event) {
        LOGGER.trace("Resolving metadata " + event.getMetadata() + " from "
                + event.getRepository());
    }

}
