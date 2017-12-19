/* Copyright 2015 Felix Becker

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

package de.metacoder.jarlotte.mainclassinitializer;

import de.metacoder.jarlotte.api.JarlotteInitializer;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Initializer implements JarlotteInitializer {

    public void initialize(File webAppDir) {
        try {
            List<URL> jarUrls = new ArrayList<URL>();
            for (File jar : webAppDir.getAbsoluteFile().listFiles()) {
                jarUrls.add(jar.toURI().toURL());
            }

            ClassLoader classLoader = new URLClassLoader(jarUrls.toArray(new URL[jarUrls.size()]));
            Thread.currentThread().setContextClassLoader(classLoader);
            Class<?> aClass = classLoader.loadClass("TODO");
            String[] args = null;
            aClass.getMethod("main", String[].class).invoke(null, (Object) args); // TODO
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
    }
}
