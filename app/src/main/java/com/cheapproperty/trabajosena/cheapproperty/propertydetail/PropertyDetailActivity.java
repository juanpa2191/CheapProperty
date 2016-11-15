package com.cheapproperty.trabajosena.cheapproperty.propertydetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.cheapproperty.trabajosena.cheapproperty.R;
import com.cheapproperty.trabajosena.cheapproperty.property.PropertyActivity;

public class PropertyDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(PropertyActivity.EXTRA_PROPERTY_ID);

        PropertyDetailFragment fragment = (PropertyDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.lawyer_detail_container);
        if (fragment == null) {
            fragment = PropertyDetailFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.lawyer_detail_container, fragment)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_property_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
