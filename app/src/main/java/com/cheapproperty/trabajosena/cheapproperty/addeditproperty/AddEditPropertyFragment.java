package com.cheapproperty.trabajosena.cheapproperty.addeditproperty;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cheapproperty.trabajosena.cheapproperty.R;
import com.cheapproperty.trabajosena.cheapproperty.data.Property;
import com.cheapproperty.trabajosena.cheapproperty.data.PropertyDbHelper;

/**
 * Vista para creación/edición de un abogado
 */
public class AddEditPropertyFragment extends Fragment {
    private static final String ARG_PROPERTY_ID = "arg_property_id";

    private String mPropertyId;

    private PropertyDbHelper mPropertyDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mSpecialtyField;
    private TextInputEditText mBioField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mSpecialtyLabel;
    private TextInputLayout mBioLabel;


    public AddEditPropertyFragment() {
        // Required empty public constructor
    }

    public static AddEditPropertyFragment newInstance(String lawyerId) {
        AddEditPropertyFragment fragment = new AddEditPropertyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROPERTY_ID, lawyerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPropertyId = getArguments().getString(ARG_PROPERTY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_property, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mPhoneNumberField = (TextInputEditText) root.findViewById(R.id.et_phone_number);
        mSpecialtyField = (TextInputEditText) root.findViewById(R.id.et_specialty);
        mBioField = (TextInputEditText) root.findViewById(R.id.et_bio);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mPhoneNumberLabel = (TextInputLayout) root.findViewById(R.id.til_phone_number);
        mSpecialtyLabel = (TextInputLayout) root.findViewById(R.id.til_specialty);
        mBioLabel = (TextInputLayout) root.findViewById(R.id.til_bio);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditProperty();
            }
        });

        mPropertyDbHelper = new PropertyDbHelper(getActivity());

        // Carga de datos
        if (mPropertyId != null) {
            loadLawyer();
        }

        return root;
    }

    private void loadLawyer() {
        new GetPropertyByIdTask().execute();
    }

    private void addEditProperty() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String specialty = mSpecialtyField.getText().toString();
        String bio = mBioField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(specialty)) {
            mSpecialtyLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(bio)) {
            mBioLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Property property = new Property(name, specialty, phoneNumber, bio, "");

        new AddEditPropertyTask().execute(property);

    }

    private void showPropertyScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showProperty(Property property) {
        mNameField.setText(property.getName());
        mPhoneNumberField.setText(property.getPhoneNumber());
        mSpecialtyField.setText(property.getSpecialty());
        mBioField.setText(property.getBio());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetPropertyByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mPropertyDbHelper.getLawyerById(mPropertyId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showProperty(new Property(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditPropertyTask extends AsyncTask<Property, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Property... properties) {
            if (mPropertyId != null) {
                return mPropertyDbHelper.updateLawyer(properties[0], mPropertyId) > 0;

            } else {
                return mPropertyDbHelper.saveLawyer(properties[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showPropertyScreen(result);
        }

    }

}
