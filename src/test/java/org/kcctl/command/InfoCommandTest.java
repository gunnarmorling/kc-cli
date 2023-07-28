/*
 *  Copyright 2021 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.kcctl.command;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.kcctl.IntegrationTest;
import org.kcctl.IntegrationTestProfile;
import org.kcctl.support.InjectCommandContext;
import org.kcctl.support.KcctlCommandContext;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestProfile(IntegrationTestProfile.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class InfoCommandTest extends IntegrationTest {

    @InjectCommandContext
    KcctlCommandContext<InfoCommand> context;

    @Test
    public void should_print_info() throws Exception {
        context.runAndEnsureExitCodeOk();
        assertThat(context.output().toString().trim())
                .matches("URL:\\s+" + kafkaConnect.getTarget() + "\\n" +
                        "Version:\\s+" + getConnectVersion() + "\\n" +
                        "Commit:\\s+[\\dabcdef]{16}\\n" +
                        "Kafka Cluster ID:\\s+[\\w-]{22}");
    }
}
