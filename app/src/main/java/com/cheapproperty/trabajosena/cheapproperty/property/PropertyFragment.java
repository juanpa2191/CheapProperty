package com.cheapproperty.trabajosena.cheapproperty.property;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cheapproperty.trabajosena.cheapproperty.R;
import com.cheapproperty.trabajosena.cheapproperty.addeditproperty.AddEditPropertyActivity;
import com.cheapproperty.trabajosena.cheapproperty.data.PropertyDbHelper;
import com.cheapproperty.trabajosena.cheapproperty.propertydetail.PropertyDetailActivity;

import static com.cheapproperty.trabajosena.cheapproperty.data.PropertyContract.PropertyEntry;


/**
 * Vista para la lista de abogados del gabinete
 */
public class PropertyFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_LAWYER = 2;

    private PropertyDbHelper mPropertyDbHelper;

    private ListView mPropertyList;
    private PropertyCursorAdapter mPropertyAdapter;
    private FloatingActionButton mAddButton;


    public PropertyFragment() {
        // Required empty public constructor
    }

    public static PropertyFragment newInstance() {
        return new PropertyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_property, container, false);

        // Referencias UI
        mPropertyList = (ListView) root.findViewById(R.id.lawyers_list);
        mPropertyAdapter = new PropertyCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mPropertyList.setAdapter(mPropertyAdapter);

        // Eventos
        mPropertyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mPropertyAdapter.getItem(i);
                String currentPropertyId = currentItem.getString(
                        currentItem.getColumnIndex(PropertyEntry.ID));

                showDetailScreen(currentPropertyId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        getActivity().deleteDatabase(PropertyDbHelper.DATABASE_NAME);

        // Instancia de helper
        mPropertyDbHelper = new PropertyDbHelper(getActivity());

        // Carga de datos
        loadProperty();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditPropertyActivity.REQUEST_ADD_PROPERTY:
                    showSuccessfullSavedMessage();
                    loadProperty();
                    break;
                case REQUEST_UPDATE_DELETE_LAWYER:
                    loadProperty();
                    break;
            }
        }
    }

    private void loadProperty() {
        new PropertyLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Abogado guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditPropertyActivity.class);
        startActivityForResult(intent, AddEditPropertyActivity.REQUEST_ADD_PROPERTY);
    }

    private void showDetailScreen(String PropertyId) {
        Intent intent = new Intent(getActivity(), PropertyDetailActivity.class);
        intent.putExtra(PropertyActivity.EXTRA_PROPERTY_ID, PropertyId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);
    }

    private class PropertyLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mPropertyDbHelper.getAllProperty();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mPropertyAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
