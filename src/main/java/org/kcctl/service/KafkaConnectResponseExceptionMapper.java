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
package org.kcctl.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class KafkaConnectResponseExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    @Override
    public RuntimeException toThrowable(Response response) {
        if (response.getStatusInfo() == Status.UNAUTHORIZED) {
            return new KafkaConnectException(response.readEntity(String.class), Status.UNAUTHORIZED.getStatusCode());
        }

        ErrorResponse error = response.readEntity(ErrorResponse.class);
        return new KafkaConnectException(error);
    }
}
