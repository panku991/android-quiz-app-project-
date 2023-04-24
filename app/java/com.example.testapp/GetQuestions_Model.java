package com.example.testapp;

public class GetQuestions_Model {

    public GetQuestions_Model() {
    }

    String Question , Option1 , Option2 , Option3 , Option4 ,right;

    public GetQuestions_Model(String question, String option1, String option2, String option3, String option4, String right) {
        this.Question = question;
        this.Option1 = option1;
        this.Option2 = option2;
        this.Option3 = option3;
        this.Option4 = option4;
        this.right = right;
    }

    public String getQuestion() {
        return this.Question;
    }

    public void setQuestion(String question) {
        this.Question = question;
    }

    public String getOption1() {
        return this.Option1;
    }

    public void setOption1(String option1) {
        this.Option1 = option1;
    }

    public String getOption2() {
        return this.Option2;
    }

    public void setOption2(String option2) {
        this.Option2 = option2;
    }

    public String getOption3() {
        return this.Option3;
    }

    public void setOption3(String option3) {
        this.Option3 = option3;
    }

    public String getOption4() {
        return this.Option4;
    }

    public void setOption4(String option4) {
        this.Option4 = option4;
    }

    public String getRight() {
        return this.right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
