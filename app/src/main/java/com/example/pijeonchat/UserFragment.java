package com.example.pijeonchat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    RecyclerView recyclerView ;
    RecyclerViewAdapter recyclerViewAdapter ;
    private List<User> mUser ;
    ProgressBar progressBar ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progressBaruser);
        mUser = new ArrayList<>();
        readuser();


        //recyclerViewAdapter = new RecyclerViewAdapter(user_name,avater,getContext());
        //recyclerView.setAdapter(recyclerViewAdapter);


        return view;
    }

    public void readuser()
    {
        progressBar.setVisibility(View.VISIBLE);
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;
                    if (!user.getId().equals(firebaseUser.getUid()))
                    {
                        mUser.add(user);
                    }
                }
                progressBar.setVisibility(View.GONE);
                //mUser.sort();  // need minimum api level 24
                recyclerViewAdapter = new RecyclerViewAdapter(getContext(),mUser);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}