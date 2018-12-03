package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontManager {
    //https://code.tutsplus.com/tutorials/how-to-use-fontawesome-in-an-android-app--cms-24167

    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fa-regular-400.ttf",BOLDFONTAWESOME = ROOT + "fa-solid-900.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public static void markAsIconContainer(View v, Typeface typeface) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                markAsIconContainer(child,typeface);
            }
        } else if (v instanceof TextView) {
            ((TextView) v).setTypeface(typeface);
        }
    }

}
