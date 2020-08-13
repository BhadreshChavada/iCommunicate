package com.icommunicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.icommunicate.R;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiGetCallRecords;
import com.icommunicate.common.FragmentStateManager;
import com.icommunicate.fragment.BaseFragment;
import com.icommunicate.fragment.HolderMoreFragment;
import com.icommunicate.fragment.HolderContactsFragment;
import com.icommunicate.fragment.HolderMessageFragment;
import com.icommunicate.fragment.HolderKeypadFragment;
import com.icommunicate.fragment.HolderRecentFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MasterActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    FragmentStateManager fragmentStateManager;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    LinearLayout container;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int position = getNavPositionFromMenuItem(item);
            if (position != -1) {
                fragmentStateManager.changeFragment(getNavPositionFromMenuItem(item));
                return true;
            }

            return false;
        }

    };

    private BottomNavigationView.OnNavigationItemReselectedListener
            mOnNavigationItemReselectedListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    int position = getNavPositionFromMenuItem(item);
                    if (position != -1) {
                        fragmentStateManager.removeFragment(position);
                        fragmentStateManager.changeFragment(position);
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        ButterKnife.bind(this);


        fragmentStateManager = new FragmentStateManager(content, getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                // A switch case should be here for showing different fragments for
                // different positions which is omitted for simplicity
                if (position == 0) {
                    return new HolderContactsFragment();
                } else if (position == 1) {
                    return new HolderKeypadFragment();
                } else if (position == 2) {
                    return new HolderRecentFragment();
                } else if (position == 3) {
                    return new HolderMessageFragment();
                }  else if (position == 4) {
                    return new HolderMoreFragment();
                }else {
                    return new HolderContactsFragment();
                }

            }
        };

        if (savedInstanceState == null) {
            fragmentStateManager.changeFragment(0);
        }

        setUpInitialComponent();

    }

    @Override
    protected void onResume() {
        super.onResume();
     }

    int getNavPositionFromMenuItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_dashboard:
                return 0;
            case R.id.navigation_private_league:
                return 1;
            case R.id.navigation_create_league:
                return 2;
            case R.id.navigation_profile:
                return 3;
            case R.id.navigation_menu:
                return 4;
            default:
                return -1;
        }
    }

    private void setUpInitialComponent() {

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReselectedListener);

        navigation.setSelectedItemId(R.id.navigation_dashboard);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                f.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (BaseFragment.CurrentCounter == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;

                }
            }, 2000);
        } else {
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
            super.onBackPressed();
        }
    }



}
