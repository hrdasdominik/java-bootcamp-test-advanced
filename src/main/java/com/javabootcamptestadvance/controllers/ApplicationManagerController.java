package main.java.com.javabootcamptestadvance.controllers;

import main.java.com.javabootcamptestadvance.models.ApplicationManager;

public class ApplicationManagerController {
    private final ApplicationManager applicationManager;

    public ApplicationManagerController() {
        this.applicationManager = new ApplicationManager();
    }
    public void run() {
        applicationManager.getApplicationManagerService().run();
    }
}
