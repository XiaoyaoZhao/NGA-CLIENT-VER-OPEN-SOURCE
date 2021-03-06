package com.noname.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.noname.interfaces.EmotionCategorySelectedListener;


public abstract class BasePostActivity extends AppCompatActivity implements EmotionCategorySelectedListener {
    static protected final String EMOTION_CATEGORY_TAG = "emotion_category";
    static protected final String EMOTION_TAG = "emotion";

    @Override
    public void onEmotionCategorySelected(int category) {
        final FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        final Fragment categoryFragment = getSupportFragmentManager().findFragmentByTag(EMOTION_CATEGORY_TAG);
        if (categoryFragment != null)
            ft.remove(categoryFragment);
        ft.commit();

        ft = fm.beginTransaction();
        final Fragment prev = getSupportFragmentManager().findFragmentByTag(EMOTION_TAG);
        if (prev != null) {
            ft.remove(prev);
        }

        DialogFragment newFragment = null;
        switch (category) {
            case EmotionCategorySelectedListener.CATEGORY_BASIC:
                newFragment = new EmotionDialogFragment();
                break;
            default:
                Bundle args = new Bundle();
                args.putInt("index", category - 1);
                newFragment = new ExtensionEmotionFragment();
                newFragment.setArguments(args);
                break;
        }
        ft.commit();
        newFragment.show(fm, EMOTION_TAG);
    }
}
