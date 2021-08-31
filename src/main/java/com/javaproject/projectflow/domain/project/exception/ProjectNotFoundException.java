package com.javaproject.projectflow.domain.project.exception;

public class ProjectNotFoundException extends RuntimeException {

    public static final RuntimeException EXCEPTION = new ProjectNotFoundException();

    private ProjectNotFoundException() {
        super("Project Not Found");
    }
}