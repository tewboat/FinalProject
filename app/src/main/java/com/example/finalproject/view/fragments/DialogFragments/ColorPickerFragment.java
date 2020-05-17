package com.example.finalproject.view.fragments.DialogFragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.finalproject.R;
import com.example.finalproject.interfaces.OnSetColor;

public class ColorPickerFragment extends DialogFragment implements View.OnClickListener {
    private View colorF, colorS, colorT, colorD;
    private OnSetColor onSetColor;

    public ColorPickerFragment(OnSetColor onSetColor){
        this.onSetColor = onSetColor;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(R.string.color_choice);
        View view = inflater.inflate(R.layout.color_picker_fragment, null, false);
        colorF = view.findViewById(R.id.color_1);
        colorS = view.findViewById(R.id.color_2);
        colorT = view.findViewById(R.id.color_3);
        colorD = view.findViewById(R.id.color_4);
        setColors();
        setOnClickListeners();
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

    }

    private void setColors(){
        colorF.setBackgroundColor(getResources().getColor(R.color.colorMaterialGreen));
        colorS.setBackgroundColor(getResources().getColor(R.color.colorMaterialOrange));
        colorT.setBackgroundColor(getResources().getColor(R.color.colorMaterialBlue));
        colorD.setBackgroundColor(getResources().getColor(R.color.colorMaterialYellow));
    }

    private void setOnClickListeners(){
        colorF.setOnClickListener(this);
        colorS.setOnClickListener(this);
        colorT.setOnClickListener(this);
        colorD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.color_1:
                onSetColor.onSetColor(R.color.colorMaterialGreen);
                break;
            case R.id.color_2:
                onSetColor.onSetColor(R.color.colorMaterialOrange);
                break;
            case R.id.color_3:
                onSetColor.onSetColor(R.color.colorMaterialBlue);
                break;
            case R.id.color_4:
                onSetColor.onSetColor(R.color.colorMaterialYellow);
                break;
        }
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
