package com.example.ftmkelection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>{

    private Context mCtx;
    private List<Candidate> candidateList;

    public CandidateAdapter(Context mCtx, List<Candidate> candidateList) {
        this.mCtx = mCtx;
        this.candidateList = candidateList;
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.candidate_layout, null);
        return new CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder candidateViewHolder, int i) {
        Candidate candidate = candidateList.get(i);
        candidateViewHolder.textViewCandidateID.setText(candidate.getCandidateID());
        candidateViewHolder.textViewName.setText(candidate.getCandidateName());

        Glide.with(mCtx)
                .load(candidate.getImageID())
                .into(candidateViewHolder.imageViewCandidate);
    }

    @Override
    public int getItemCount() {
        return candidateList.size();
    }

    class CandidateViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewCandidate;
        TextView textViewName, textViewCandidateID;

        public CandidateViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCandidate = itemView.findViewById(R.id.imageViewCandidate);
            textViewCandidateID = itemView.findViewById(R.id.textViewCandidateID_vote);
            textViewName = itemView.findViewById(R.id.textViewName);

        }
    }
}
