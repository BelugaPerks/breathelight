package com.example.breathelight_sleepassist.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.breathelight_sleepassist.R;
import com.example.breathelight_sleepassist.ui.light.LightPulse;

public class AboutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        //setup license view button
        final Button licenseButton = root.findViewById(R.id.button_license);
        licenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://raw.githubusercontent.com/BelugaPerks/breathelight/master/LICENSE"));
                startActivity(intent);
            }
        });

        //setup source code view button
        final Button viewSourceButton = root.findViewById(R.id.button_view_source);
        viewSourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/BelugaPerks/breathelight"));
                startActivity(intent);
            }
        });

        return root;
    }
}