package com.weather.ming.newcards;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Ming on 2/14/2016.
 */
public class CardEntryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_card_entry, container, false);

        Button okButton = (Button) view.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                OK_Click();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void OK_Click() {
        View view = getView();
        EditText text1 = (EditText) view.findViewById(R.id.editText);
        EditText text2 = (EditText) view.findViewById(R.id.editText2);
        String sText1 = (String) text1.getText().toString();
        String sText2 = (String) text2.getText().toString();

        CardViewActivity middleNode = (CardViewActivity) getActivity();
    }
}