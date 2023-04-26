/*
 * Copyright (c) 2022, 2023 Contributors to the Eclipse Foundation
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

import java.util.List;
import java.util.ServiceLoader;

/**
 * A loader of configuration-related objects.
 *
 * <p><strong>\u26A0 Caution:</strong> you are reading an incomplete
 * draft specification that is subject to change.</p>
 *
 * <p>Sample usage:</p>
 *
 * <blockquote><pre>{@linkplain Loader Loader} loader = {@linkplain Loader Loader}.{@linkplain Loader#bootstrap() bootstrap()};
 *MyConfigurationRelatedObject object = null;
 *try {
 *  object = loader.{@linkplain #load(List, Class) load(List.of("x", "y"), MyConfigurationRelatedObject.class)};
 *} catch ({@linkplain NoSuchObjectException} noSuchObjectException) {
 *  // object is <a href="doc-files/terminology.html#absent">absent</a>
 *} catch ({@linkplain ConfigException} configException) {
 *  // a {@linkplain #load(List, Class) loading}-related error occurred
 *}</pre></blockquote>
 *
 * @see #bootstrap()
 *
 * @see #bootstrap(ClassLoader)
 *
 * @see #load(List, Class)
 *
 * @see <a href="doc-files/terminology.html">Terminology</a>
 */
public interface Loader {

    /**
     * Loads a configuration object of the supplied {@code
     * configurationClass}, conceptually located within an
     * application's configuration at the supplied {@code
     * configurationPath}, and returns it.
     *
     * <p><strong>Note:</strong> The rules governing how it is
     * determined whether any given configuration-related object is
     * "of the supplied {@code configurationClass}" are currently
     * wholly undefined.</p>
     *
     * <p><strong>Note:</strong> All namespace concerns around the
     * supplied {@code configurationPath} are currently wholly
     * undefined.</p>
     *
     * <p>Implementations of this method must not return {@code
     * null}.</p>
     *
     * <p>Implementations of this method must be idempotent.</p>
     *
     * <p>Implementations of this method must be safe for concurrent
     * use by multiple threads.</p>
     *
     * <p>Implementations of this method may or may not return a <a
     * href="doc-files/terminology.html#determinate">determinate</a>
     * value.</p>
     *
     * @param <T> the type of object to load
     *
     * @param configurationPath a {@link List} of {@link String}s
     * forming a configuration path, where each element is a canonical
     * representation of a configuration key; must not be {@code null}
     *
     * @param configurationClass the configuration class defining the
     * configuraiton object to load; must not be {@code null}
     *
     * @return the loaded configuration object; never {@code null}
     *
     * @exception NoSuchObjectException if the invocation was sound
     * but the requested object was <a
     * href="doc-files/terminology.html#absent">absent</a>. This may
     * happen for a variety of reasons, including that the supplied
     * configuration path does not resolve in a given application in
     * which the caller of this method finds itself.
     *
     * @exception InvalidConfigurationClassException if the supplied
     * {@code configurationClass} did not conform to the requirements
     * of a configuration class as defined in the Jakarta Config
     * specification
     *
     * @exception IllegalArgumentException if the suplied {@code configurationClass}
     * was invalid for some other reason
     *
     * @exception ConfigException if the invocation was sound but the
     * object could not be loaded for any reason not related to <a
     * href="doc-files/terminology.html#absent">absence</a>
     *
     * @exception NullPointerException if either of the supplied
     * {@code configurationPath} or {@code configurationClass}
     * arguments was {@code null}
     */
    public <T> T load(List<String> configurationPath, Class<T> configurationClass);

    /**
     * <em>{@linkplain #bootstrap(ClassLoader) Bootstraps}</em> a
     * {@link Loader} instance for subsequent usage using the
     * {@linkplain Thread#getContextClassLoader() context
     * classloader}.
     *
     * <p>This method never returns {@code null}.</p>
     *
     * <p>This method is idempotent.</p>
     *
     * <p>This method is safe for concurrent use by multiple
     * threads.</p>
     *
     * <p>This method may or may not return a <a
     * href="doc-files/terminology.html#determinate">determinate</a>
     * value. See {@link #bootstrap(ClassLoader)} for details.</p>
     *
     * <p>Except as possibly noted above, the observable behavior of
     * this method is specified to be identical to that of the {@link
     * #bootstrap(ClassLoader)} method.</p>
     *
     * @return a {@link Loader}; never {@code null}
     *
     * @exception java.util.ServiceConfigurationError if bootstrapping
     * failed because of a {@link ServiceLoader#load(Class,
     * ClassLoader)} or {@link ServiceLoader#findFirst()} problem
     *
     * @exception ConfigException if bootstrapping failed because of a
     * {@link Loader#load(List, Class)} problem
     *
     * @see #bootstrap(ClassLoader)
     */
    public static Loader bootstrap() {
        return bootstrap(Thread.currentThread().getContextClassLoader());
    }

    /**
     * <em>Bootstraps</em> a {@link Loader} instance for subsequent
     * usage.
     *
     * <p>The bootstrap process proceeds as follows:</p>
     *
     * <ol>
     *
     * <li>A <em>primordial {@link Loader}</em> is located with
     * observable effects equal to those resulting from executing the
     * following code:
     *
     * <blockquote><pre>{@linkplain Loader} loader = {@linkplain ServiceLoader}.{@linkplain ServiceLoader#load(Class, ClassLoader) load(Loader.class, classLoader)}
     *  .{@linkplain java.util.ServiceLoader#findFirst() findFirst()}
     *  .{@linkplain java.util.Optional#orElseThrow() orElseThrow}({@linkplain NoSuchObjectException#NoSuchObjectException() NoSuchObjectException::new});</pre></blockquote></li>
     *
     * <li>The {@link #load(List, Class)} method is invoked on the resulting
     * {@link Loader} with {@link Loader Loader.class} as its sole
     * argument.
     *
     * <ul>
     *
     * <li>If the invocation throws a {@link NoSuchObjectException},
     * the primordial {@link Loader} is returned.</li>
     *
     * <li>If the invocation returns a {@link Loader}, that {@link
     * Loader} is returned.</li>
     *
     * </ul>
     *
     * </li>
     *
     * </ol>
     *
     * <p>This method never returns {@code null}.</p>
     *
     * <p>This method is idempotent.</p>
     *
     * <p>This method is safe for concurrent use by multiple
     * threads.</p>
     *
     * <p>This method may or may not return a <a
     * href="doc-files/terminology.html#determinate">determinate</a>
     * value depending on the implementation of the {@link Loader}
     * loaded in step 2 above.</p>
     *
     * <p><strong>Note:</strong> The implementation of this method may
     * change without notice between any two versions of this
     * specification.  The requirements described above, however, will
     * be honored in any minor version of this specification within a
     * given major version.</p>
     *
     * @param classLoader the {@link ClassLoader} used to {@linkplain
     * ServiceLoader#load(Class, ClassLoader) locate service provider
     * files}; may be {@code null} to indicate the system classloader
     * (or bootstrap class loader) in accordance with the contract of
     * the {@link ServiceLoader#load(Class, ClassLoader)} method;
     * often is the return value of an invocation of {@link
     * Thread#getContextClassLoader()
     * Thread.currentThread().getContextClassLoader()}
     *
     * @return a {@link Loader}; never {@code null}
     *
     * @exception java.util.ServiceConfigurationError if bootstrapping
     * failed because of a {@link ServiceLoader#load(Class,
     * ClassLoader)} or {@link ServiceLoader#findFirst()} problem
     *
     * @exception ConfigException if bootstrapping failed because of a
     * {@link Loader#load(List, Class)} problem
     */
    public static Loader bootstrap(ClassLoader classLoader) {
        Loader loader = ServiceLoader.load(Loader.class, classLoader)
            .findFirst()
            .orElseThrow(NoSuchObjectException::new);
        try {
            return loader.load(List.of(), Loader.class);
        } catch (NoSuchObjectException absentValueException) {
            System.getLogger(Loader.class.getName())
                .log(System.Logger.Level.DEBUG, absentValueException::getMessage, absentValueException);
            return loader;
        }
    }

}
