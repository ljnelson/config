/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jakarta.config;

/**
 * A {@link ConfigException} thrown when a configuration class was
 * found to be invalid.
 *
 * <p><strong>\u26A0 Caution:</strong> you are reading an incomplete
 * draft specification that is subject to change.</p>
 */
public class InvalidConfigurationClassException extends ConfigException {

    /**
     * Creates a new {@link InvalidConfigurationClassException}.
     */
    public InvalidConfigurationClassException() {
        super();
    }

    /**
     * Creates a new {@link InvalidConfigurationClassException}.
     *
     * @param message a detail message; may be {@code null}
     */
    public InvalidConfigurationClassException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link InvalidConfigurationClassException}.
     *
     * @param cause the {@link Throwable} responsible for this {@link
     * InvalidConfigurationClassException}'s existence; may be {@code null}
     */
    public InvalidConfigurationClassException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new {@link InvalidConfigurationClassException}.
     *
     * @param message a detail message; may be {@code null}
     *
     * @param cause the {@link Throwable} responsible for this {@link
     * InvalidConfigurationClassException}'s existence; may be {@code null}
     */
    public InvalidConfigurationClassException(String message, Throwable cause) {
        super(message, cause);
    }

}
