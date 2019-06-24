package com.example.ftmkelection;

public class Vote {
    private String studentID, candidateID;

    public int getVoting() {
        return voting;
    }

    public void setVoting(int voting) {
        this.voting = voting;
    }

    private int voting;

    public Vote(String candidateID, int voting) {
        this.candidateID = candidateID;
        this.voting = voting;
    }



    public Vote(String studentID, String candidateID) {
        this.studentID = studentID;
        this.candidateID = candidateID;
    }

    public Vote(String studentID, String candidateID, int voting) {
        this.studentID = studentID;
        this.candidateID = candidateID;
        this.voting = voting;
    }

    public Vote(String studentID) {
        this.studentID = studentID;
    }
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(String candidateID) {
        this.candidateID = candidateID;
    }
}
