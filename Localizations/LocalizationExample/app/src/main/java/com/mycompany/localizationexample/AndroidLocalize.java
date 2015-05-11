package com.mycompany.localizationexample;

import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AndroidLocalize extends Activity {
	Spinner spinnerctrl;
	Button btn;
	Locale myLocale;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		spinnerctrl = (Spinner) findViewById(R.id.spinner1);
		spinnerctrl.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					                   int pos, long id) {

                switch(pos){
                    case 1:
                        Toast.makeText(parent.getContext(),
                                "You have selected English", Toast.LENGTH_SHORT)
                                .show();
                        setLocale("en");
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(),
                                "Has seleccionado Español", Toast.LENGTH_SHORT)
                                .show();
                        setLocale("es");
                        break;
                }
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
	}

	public void setLocale(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
        String msg="changing locale from " + conf.locale.getLanguage() + " to "+lang;
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
        Log.v("setLocale", msg);
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		Intent refresh = new Intent(this, AndroidLocalize.class);
		startActivity(refresh);
	}
}
