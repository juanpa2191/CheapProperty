package com.cheapproperty.trabajosena.cheapproperty.addeditproperty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cheapproperty.trabajosena.cheapproperty.R;
import com.cheapproperty.trabajosena.cheapproperty.property.PropertyActivity;

public class AddEditPropertyActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_PROPERTY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String lawyerId = getIntent().getStringExtra(PropertyActivity.EXTRA_PROPERTY_ID);

        setTitle(lawyerId == null ? "Añadir abogado" : "Editar abogado");

        AddEditPropertyFragment addEditPropertyFragment = (AddEditPropertyFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_lawyer_container);
        if (addEditPropertyFragment == null) {
            addEditPropertyFragment = AddEditPropertyFragment.newInstance(lawyerId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_lawyer_container, addEditPropertyFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
