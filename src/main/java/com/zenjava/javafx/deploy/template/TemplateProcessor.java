package com.zenjava.javafx.deploy.template;

import com.zenjava.javafx.deploy.log.Log;
import com.zenjava.javafx.deploy.webstart.WebstartBundlerException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class TemplateProcessor {

    private Log log;

    public TemplateProcessor(Log log) {
        this.log = log;
    }

    public void processTemplate(File outputFile, String templatePath, Map<String, Object> values)
            throws TemplateException {

        log.info("Processing template '%s' to create '%s'", templatePath, outputFile);

        log.debug("Initialising velocity templating engine");
        VelocityEngine velocityEngine = new VelocityEngine();
        if (templatePath.startsWith("classpath:")) {
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            templatePath = templatePath.substring("classpath:".length());
        } else {
            velocityEngine.setProperty("resource.loader", "file");
            velocityEngine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            velocityEngine.setProperty("file.resource.loader.path", "");
        }
        velocityEngine.init();


        // create base output directory

        File outputDir = outputFile.getParentFile();
        log.debug("Creating base output directory: '%s'", outputDir);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new WebstartBundlerException("Failed to create target directory '" + outputDir + "'for generation of '" + outputFile + "'");
        }

        // setup the velocity template

        Template template = velocityEngine.getTemplate(templatePath);
        VelocityContext context = new VelocityContext();
        for (String key : values.keySet()) {
            context.put(key, values.get(key));
        }

        // generate the output file using the velocity template

        log.info("Generating file using template '%s', output file is '%s'", templatePath, outputFile);
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(outputFile));
            template.merge(context, out);
        } catch (IOException e){
            throw new WebstartBundlerException("Failed to create file '" + outputFile + "' from template '" + templatePath + "'", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

        log.info("Successfully generated file at '%s'", outputFile);
    }
}
