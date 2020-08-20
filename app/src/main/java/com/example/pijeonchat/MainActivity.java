package com.example.pijeonchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout ;
    ViewPager2 viewPager2 ;
    DatabaseReference reference ;
    FirebaseUser firebaseUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpagerid);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        viewPager2.setAdapter(new Customadapter(this));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                        tab.setText("Chats");
                        tab.setIcon(R.drawable.chat);
                        break;
                    case 1:
                        tab.setText("Users");
                        tab.setIcon(R.drawable.group);
                        break;
                    case 2:
                        tab.setText("Profile");
                        tab.setIcon(R.drawable.user);
                        break;
                }
            }
        }
        );
        tabLayoutMediator.attach();



    }

    public class Customadapter extends FragmentStateAdapter{

        public Customadapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position)
            {
                case 0:
                    return new ChatFragment();
                case 1:
                    return new UserFragment();
                case 2:
                    return new ProfileFragment();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    public void Status (String status)
    {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status",status);
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Status("offline");
    }
}