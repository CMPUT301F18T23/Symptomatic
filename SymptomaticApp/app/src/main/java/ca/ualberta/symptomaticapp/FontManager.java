/*
 * FontManager.java
 *
 * Version 1
 *
 * December, 3, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Custom fonts for views.
 *
 * Issues:
 *
 */
package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontManager {
    //https://code.tutsplus.com/tutorials/how-to-use-fontawesome-in-an-android-app--cms-24167

    public static final String ROOT = "fonts/",
            REGULARFONTAWESOME = ROOT + "fa-regular-400.ttf",SOLIDFONTAWESOME = ROOT + "fa-solid-900.ttf";

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
