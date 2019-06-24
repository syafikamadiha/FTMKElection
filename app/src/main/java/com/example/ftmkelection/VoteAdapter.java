package com.example.ftmkelection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteViewHolder>{

    private Context mCtx;
    private List<Vote> voteList;

    public VoteAdapter(Context mCtx, List<Vote> voteList) {
        this.mCtx = mCtx;
        this.voteList = voteList;
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.result_layout, null);
        return new VoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder voteViewHolder, int i) {
        Vote vote = voteList.get(i);
        voteViewHolder.textViewCandidateID_vote.setText(vote.getCandidateID());
        voteViewHolder.textViewVoting.setText(vote.getVoting());
    }

    @Override
    public int getItemCount() {
        return voteList.size();
    }

    class VoteViewHolder extends RecyclerView.ViewHolder {

        TextView textViewVoting, textViewCandidateID_vote;

        public VoteViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewVoting = itemView.findViewById(R.id.textViewVoting);
            textViewCandidateID_vote = itemView.findViewById(R.id.textViewCandidateID_vote);
        }
    }
}
