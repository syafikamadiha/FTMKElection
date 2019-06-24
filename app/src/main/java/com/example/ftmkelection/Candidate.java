package com.example.ftmkelection;

public class Candidate {

    private String candidateID, candidateName, imageID;

    public String getCandidateID() {
        return candidateID;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getImageID() {
        return imageID;
    }

    public Candidate(String candidateID, String candidateName, String imageID) {
        this.candidateID = candidateID;
        this.candidateName = candidateName;
        this.imageID = imageID;
    }
}
