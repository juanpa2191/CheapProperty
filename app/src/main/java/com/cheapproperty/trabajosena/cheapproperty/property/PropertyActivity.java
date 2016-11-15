package com.cheapproperty.trabajosena.cheapproperty.property;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cheapproperty.trabajosena.cheapproperty.R;

public class PropertyActivity extends AppCompatActivity {

    public static final String EXTRA_PROPERTY_ID = "extra_property_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PropertyFragment fragment = (PropertyFragment)
                getSupportFragmentManager().findFragmentById(R.id.property_container);

        if (fragment == null) {
            fragment = PropertyFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.property_container, fragment)
                    .commit();
        }
    }
}
