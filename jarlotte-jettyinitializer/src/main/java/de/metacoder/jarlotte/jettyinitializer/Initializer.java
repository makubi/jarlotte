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

package de.metacoder.jarlotte.jettyinitializer;

import de.metacoder.jarlotte.api.JarlotteContext;
import de.metacoder.jarlotte.api.JarlotteInitializer;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.lang.management.ManagementFactory;

public class Initializer implements JarlotteInitializer {

    private Server server;

    public void initialize(JarlotteContext context) {
        try {
            final String jettyPortProperty = System.getProperty("jetty.port");

            final int jettyPort;

            if(jettyPortProperty != null){
                System.out.println("Using custom jetty.port " + jettyPortProperty);
                jettyPort = Integer.parseInt(jettyPortProperty);
            } else {
                System.out.println("Starting jetty on port 8080, use -Djetty.port=<port> to override");
                jettyPort = 8080;
            }

            server = new Server(jettyPort);

            MBeanContainer mbContainer=new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
            server.addEventListener(mbContainer);
            server.addBean(mbContainer);
            server.addBean(Log.getLog());

            // required for working JSPs
            final Configuration.ClassList classlist = Configuration.ClassList.setServerDefault( server );
            classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

            WebAppContext wac = new WebAppContext();
            wac.setResourceBase(context.getWebappDir().getAbsolutePath());
            wac.setDescriptor(context.getWebappDir().getAbsolutePath() + "WEB-INF/web.xml");
            wac.setContextPath("/");
            wac.setClassLoader(new WebAppClassLoader(getClass().getClassLoader(), wac));

            server.setHandler(wac);

            server.start();
            server.join();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void stop() {
        if(server != null && server.isRunning()) {
            System.out.println("Shutting down Jetty server");
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
