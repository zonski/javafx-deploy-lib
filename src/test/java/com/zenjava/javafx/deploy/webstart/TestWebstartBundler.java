/*
 * The JavaFX Deploy Lib provides a core functionality for assembling JavaFX applications
 * Copyright (C) 2012  Daniel Zwolenski
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.zenjava.javafx.deploy.webstart;

import junitx.framework.FileAssert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestWebstartBundler {

    @Test
    public void testDefaultTemplate() throws Exception {

        File outputDir = createTempDir();
        String jnlpFileName = "test-default.jnlp";

        WebstartBundleConfig config = new WebstartBundleConfig(outputDir, jnlpFileName, null, "Test Title",
                "Test Vendor", new String[] {"test-jar-file.jar"}, "com.test.TestMainClass");
        WebstartBundler action = new WebstartBundler();
        action.bundle(config);

        FileAssert.assertEquals("Generated JNLP file based on default template was not as expected",
                new File(getClass().getResource("/expected-default-jnlp.jnlp").toURI()), new File(outputDir, jnlpFileName));
    }

    @Test
    public void testCustomTemplate() throws Exception {

        File outputDir = createTempDir();
        String jnlpFileName = "test-custom.jnlp";
        File jnlpTemplateFile = new File(getClass().getResource("/expected-custom-jnlp.jnlp").toURI());
        WebstartBundleConfig config = new WebstartBundleConfig(outputDir, jnlpFileName, jnlpTemplateFile, "Test Title",
                "Test Vendor", new String[] {"test-jar-file.jar"}, "com.test.TestMainClass");

        WebstartBundler action = new WebstartBundler();
        action.bundle(config);

        FileAssert.assertEquals("Generated JNLP file based on custom template was not as expected",
                new File(getClass().getResource("/expected-custom-jnlp.jnlp").toURI()), new File(outputDir, jnlpFileName));
    }

    protected File createTempDir() throws IOException {
        String tempDir = System.getProperty("tempDir");
        if (tempDir == null) {
            File tempFile = File.createTempFile("test", "jnlp");
            File outputDir = new File(tempFile.getParent(), "jfxtest");
            outputDir.deleteOnExit();
            tempFile.delete();
            return outputDir;
        } else {
            File outputDir = new File(tempDir);
            outputDir.mkdirs();
            return outputDir;
        }
    }
}
