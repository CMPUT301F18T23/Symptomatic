package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class LocalSave {
    protected Context context;


    /*
      * When the user is offline, save changes made
        @param context
     */

    public LocalSave(Context context) {
        // Initialize LocalSave and pass in the activity's context
        this.context = context;
    }

    public File createTempCacheFile(Context context, String uri) {
        // Create a temporary file in the cache
        // The app should be offline
        File tempFile;
        String fileName = Uri.parse(uri).getLastPathSegment();
        try {
            fileName = Uri.parse(uri).getLastPathSegment();
            tempFile = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            Log.d("CACHE ERROR", "Temporary file was not created.");

            // Create the file using another method
            tempFile = new File(context.getCacheDir(), fileName);
        }
       return tempFile;
    }

    public void deleteTempCacheFile(String fileName) {
        // Delete the temporary cache file
        // The app should be up-to-date and online
        context.deleteFile(fileName);
    }

    public Context getContext() {
        // Return the context
        return this.context;
    }

    public void setContext(Context newContext) {
        // Set the context
        this.context = newContext;
    }
}
