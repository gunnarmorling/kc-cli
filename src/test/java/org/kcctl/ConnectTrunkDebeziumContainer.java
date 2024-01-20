/*
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Copyright The original authors
 *
 *  Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.kcctl;

import org.testcontainers.images.builder.ImageFromDockerfile;

import io.debezium.testing.testcontainers.DebeziumContainer;

public class ConnectTrunkDebeziumContainer extends DebeziumContainer {

    public static final String DEBEZIUM_CONNECT_TRUNK_IMAGE_NAME = "debezium/connect:trunk";
    public static final String DOCKERFILE_PATH = "docker/Dockerfile";
    public static final String DOCKERFILE_NAME = "Dockerfile";

    public ConnectTrunkDebeziumContainer() {

        super(new ImageFromDockerfile(DEBEZIUM_CONNECT_TRUNK_IMAGE_NAME, false)
                .withFileFromClasspath(DOCKERFILE_NAME, DOCKERFILE_PATH));
    }
}
