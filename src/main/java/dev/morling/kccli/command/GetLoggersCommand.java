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
package dev.morling.kccli.command;

import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;

import dev.morling.kccli.service.KafkaConnectApi;
import dev.morling.kccli.util.ConfigurationContext;
import picocli.CommandLine;

import static dev.morling.kccli.util.Colors.*;

@CommandLine.Command(name = "loggers", description = "Get LOG Levels of passed connector/All if nothing is passed")
public class GetLoggersCommand implements Runnable {

    @Inject
    ConfigurationContext context;

    @CommandLine.Option(names = { "-p", "--path" }, description = "Path of the connector", defaultValue = DEFAULT_PATH)
    String path;

    private final String DEFAULT_PATH = "ALL";

    @Override
    public void run() {
        KafkaConnectApi kafkaConnectApi = RestClientBuilder.newBuilder()
                .baseUri(context.getCluster())
                .build(KafkaConnectApi.class);

        String[][] data;
        if (path.equals(DEFAULT_PATH)) {
            // all
            ObjectNode connectorLoggers = kafkaConnectApi.getLoggers("");
            Iterator<String> classPaths = connectorLoggers.fieldNames();

            data = new String[connectorLoggers.size()][];

            int i = 0;
            for (final JsonNode header : (Iterable<JsonNode>) connectorLoggers::elements) {
                for (final Map.Entry<String, JsonNode> field : (Iterable<Map.Entry<String, JsonNode>>) header::fields) {
                    data[i] = new String[]{
                            classPaths.next(),
                            " " + field.getValue().textValue()
                    };
                }
                i++;
            }
        }
        else {
            ObjectNode connectorLoggers = kafkaConnectApi.getLoggers(path);
            data = new String[connectorLoggers.size()][];
            data[0] = new String[]{
                    path,
                    connectorLoggers.findValue("level").textValue()
            };
        }
        System.out.println();
        String table = AsciiTable.getTable(AsciiTable.NO_BORDERS,
                new Column[]{
                        new Column().header("CLASS").dataAlign(HorizontalAlign.LEFT),
                        new Column().header(" LOG_LEVEL").dataAlign(HorizontalAlign.LEFT)
                },
                data);
        System.out.println(table.replace("ERROR", ANSI_RED + "ERROR" + ANSI_RESET)
                .replace("WARN", ANSI_RED + "WARN" + ANSI_RESET)
                .replace("FATAL", ANSI_RED + "FATAL" + ANSI_RESET)
                .replace("DEBUG", ANSI_YELLOW + "DEBUG" + ANSI_RESET)
                .replace("INFO", ANSI_GREEN + "INFO" + ANSI_RESET)
                .replace("TRACE", ANSI_CYAN + "TRACE" + ANSI_RESET));
    }
}
