package com.bnr.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.util.Log;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class CrimePagerActivity extends FragmentActivity {

    private static final String TAG = "CrimePagerActivity";
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mCrimes = CrimeLab.get(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter( new FragmentStatePagerAdapter(fm) {

            @Override
            public Fragment getItem(int pos) {
                Crime crime = mCrimes.get(pos);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }

        });


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int pos) {
                Crime crime = mCrimes.get(pos);
                Log.d(TAG, "OnPageSelected: Position = " + pos);
                if (crime.getTitle() != null) {
                   setTitle(crime.getTitle());
                }
            }

            @Override
            public void onPageScrolled(int pos, float posOffset,
                                       int posOffSetPixels) {
               Log.d(TAG, "OnPageScrolled: Position = " + pos);
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
                Log.d(TAG, "OnPageScrollStateChanged: Position = " + pos);
            }
        });

        // Set Page to the currently selected crime
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        Log.d(TAG, "Looking for the page");
        for( int i = 0; i < mCrimes.size(); i++) {
            if( mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                Log.d(TAG, "Setting mViewPager Current item to: " + i);
                break;
            }
        }
    }

}
