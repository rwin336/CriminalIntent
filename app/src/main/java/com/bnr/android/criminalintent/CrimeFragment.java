package com.bnr.android.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {

    private static final String TAG = "CrimeFragment";
    public static final String EXTRA_CRIME_ID = "com.bnr.android.criminalintent.crime_id";
    public static final String DIALOG_DATE = "date";
    public static final String DIALOG_TIME = "time";
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 1;
    private Crime mCrime;
    private EditText mTitleField;
    private TextView mDateLabel;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;
    private Date date;

    public static CrimeFragment newInstance(UUID crimeID) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeID);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "Received onActivityResult, Request code = " + requestCode +
                ", Result code = " + resultCode);
        Log.d(TAG, "Acivity.RESULT_OK = " + Activity.RESULT_OK);
        if( resultCode != Activity.RESULT_OK) {
            return;
        }

        if( requestCode == REQUEST_DATE) {
            Log.d(TAG, "Executing Request Date code");
            date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Log.d(TAG, "Date = " + date.toString());
            mCrime.setDate( date);
            updateDate();
        }

        if( requestCode == REQUEST_TIME ) {
            Log.d(TAG, "Executing Request Time code");
            date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            Log.d(TAG, "Date = " + date.toString());
            mCrime.setTime( date);
            updateTime();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.fragment_crime, container, false);

        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.HONEYCOMB) {
            if(NavUtils.getParentActivityName(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mCrime.setTitle(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // This space intentionally left blank
            }

            public void afterTextChanged( Editable c) {
                // This one too.
            }
        });

        mDateLabel = (TextView)v.findViewById(R.id.crime_date_label);
        mDateLabel.setText("Crime Date:   " + mCrime.getDate().toString());

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        mDateButton.setText( "Date of Crime");
        mDateButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();

                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.time_date);
        mTimeButton.setText("Time of Crime");
        mTimeButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment time_dialog = TimePickerFragment.newInstance(mCrime.getDate());
                time_dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                time_dialog.show(fm, DIALOG_TIME);
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged( CompoundButton buttonView, boolean isChecked) {
                // Set the crime's solved property
                mCrime.setSolved(isChecked);
            }

        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }

    private void updateDate() {
        mDateLabel.setText( mCrime.getDate().toString());
    }


    private void updateTime() {
        mDateLabel.setText( mCrime.getDate().toString());
    }

}
