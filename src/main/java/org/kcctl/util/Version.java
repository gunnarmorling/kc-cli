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
package org.kcctl.util;

public class Version {

    private final int major;
    private final int minor;

    /*
     * Valid Kafka versions are in the following formats:
     * - X.Y.Z
     * - X.Y.Z-SNAPSHOT
     */
    public Version(String version) {
        String[] parts = version.split("\\.");
        major = Integer.parseInt(parts[0]);
        minor = Integer.parseInt(parts[1]);
    }

    public Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    public boolean greaterOrEquals(Version version) {
        return (major > version.major) ||
                (major == version.major && minor >= version.minor);
    }

    public String toString() {
        return String.format("%d.%d", major, minor);
    }
}
