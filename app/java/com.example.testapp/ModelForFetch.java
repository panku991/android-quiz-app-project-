package com.example.testapp;

public class ModelForFetch {

    String TestName , TestId , TOTQuestions;

    public ModelForFetch(String testName, String testId, String TOTQuestions) {
        TestName = testName;
        TestId = testId;
        this.TOTQuestions = TOTQuestions;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getTestId() {
        return TestId;
    }

    public void setTestId(String testId) {
        TestId = testId;
    }

    public String getTOTQuestions() {
        return TOTQuestions;
    }

    public void setTOTQuestions(String TOTQuestions) {
        this.TOTQuestions = TOTQuestions;
    }
}
