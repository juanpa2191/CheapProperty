package com.cheapproperty.trabajosena.cheapproperty.propertydetail;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheapproperty.trabajosena.cheapproperty.R;
import com.cheapproperty.trabajosena.cheapproperty.addeditproperty.AddEditPropertyActivity;
import com.cheapproperty.trabajosena.cheapproperty.data.Property;
import com.cheapproperty.trabajosena.cheapproperty.data.PropertyDbHelper;
import com.cheapproperty.trabajosena.cheapproperty.property.PropertyActivity;
import com.cheapproperty.trabajosena.cheapproperty.property.PropertyFragment;

/**
 * Vista para el detalle del abogado
 */
public class PropertyDetailFragment extends Fragment {
    private static final String ARG_PROPERTY_ID = "PropertyId";

    private String mPropertyId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpecialty;
    private TextView mBio;

    private PropertyDbHelper mPropertyDbHelper;


    public PropertyDetailFragment() {
        // Required empty public constructor
    }

    public static PropertyDetailFragment newInstance(String propertyId) {
        PropertyDetailFragment fragment = new PropertyDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROPERTY_ID, propertyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPropertyId = getArguments().getString(ARG_PROPERTY_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_property_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mPhoneNumber = (TextView) root.findViewById(R.id.tv_phone_number);
        mSpecialty = (TextView) root.findViewById(R.id.tv_specialty);
        mBio = (TextView) root.findViewById(R.id.tv_bio);

        mPropertyDbHelper = new PropertyDbHelper(getActivity());

        loadProperty();

        return root;
    }

    private void loadProperty() {
        new GetPropertyByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeletePropertyTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PropertyFragment.REQUEST_UPDATE_DELETE_LAWYER) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showProperty(Property property) {
        mCollapsingView.setTitle(property.getName());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + property.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mPhoneNumber.setText(property.getPhoneNumber());
        mSpecialty.setText(property.getSpecialty());
        mBio.setText(property.getBio());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditPropertyActivity.class);
        intent.putExtra(PropertyActivity.EXTRA_PROPERTY_ID, mPropertyId);
        startActivityForResult(intent, PropertyFragment.REQUEST_UPDATE_DELETE_LAWYER);
    }

    private void showPropertyScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar abogado", Toast.LENGTH_SHORT).show();
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
            }
        }

    }

    private class DeletePropertyTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mPropertyDbHelper.deleteLawyer(mPropertyId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showPropertyScreen(integer > 0);
        }

    }

}
