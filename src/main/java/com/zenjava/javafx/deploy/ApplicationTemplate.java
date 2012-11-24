package com.zenjava.javafx.deploy;

public class ApplicationTemplate {

    public static final ApplicationTemplate DEFAULT_JNLP_TEMPLATE
            = new ApplicationTemplate("/templates/default-jnlp-template.vm", true);

    public static final ApplicationTemplate DEFAULT_WEBSTART_HTML_TEMPLATE
            = new ApplicationTemplate("/templates/default-webstart-html-template.vm", true);

    public static final ApplicationTemplate DEFAULT_WIN_BATCH_SCRIPT_TEMPLATE
            = new ApplicationTemplate("/templates/default-win-batch-script-template.vm", true);


    private String path;
    private boolean relativeToClasspath;

    public ApplicationTemplate(String path) {
        this.path = path;
    }

    public ApplicationTemplate(String path, boolean relativeToClasspath) {
        this.path = path;
        this.relativeToClasspath = relativeToClasspath;
    }

    public String getPath() {
        return path;
    }

    public boolean isRelativeToClasspath() {
        return relativeToClasspath;
    }

    @Override
    public String toString() {
        return (relativeToClasspath ? "classpath:" : "") + path;
    }
}
