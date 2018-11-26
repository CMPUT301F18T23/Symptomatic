package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LocalSave {
    protected Context context;
    protected boolean isConnected;

    /*
      * When the user is offline, save changes made
        @param context
     */

    public LocalSave(Context context) {
        // Initialize LocalSave and pass in the activity's context
        this.context = context;
    }

    /**
     * creates a temporary cache file
     * @return void
     */
    public FileOutputStream createTempCacheFile(Context context, String fileName) throws IOException{
        // Create a temporary file in the cache
        // The app should be offline
        FileOutputStream tempFile;
//        try {
            tempFile = context.openFileOutput(fileName, Context.MODE_PRIVATE);
//        } catch (IOException e) {
//            Log.d("CACHE ERROR", "Temporary file was not created.");
//
//            // Create the file using another method
////            tempFile = new File(context.getCacheDir(), fileName);
//        }
       return tempFile;
    }


    public void writeToCacheFile(FileOutputStream tempOfflineFile, Object object) throws IOException {
        ObjectOutputStream newFile = new ObjectOutputStream(tempOfflineFile);
        newFile.writeObject(object);
        newFile.close();
        tempOfflineFile.close();

    }

    /**
     * deletes the temporary cache file
     * @return void
     */
    public void deleteTempCacheFile(String fileName) {
        // Delete the temporary cache file
        // The app should be up-to-date and online
        context.deleteFile(fileName);
    }

    /**
     * gets the context for the LocalSave
     * @return context
     */
    public Context getContext() {
        // Return the context
        return this.context;
    }

    /**
     * sets the context for the LocalSave
     * @return void
     */
    public void setContext(Context newContext) {
        // Set the context
        this.context = newContext;
    }

    /**
     * Checks whether the user is connected to Internet
     * @return true (meaning offline) or false (meaning online)
     */
    public boolean checkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = !(activeNetwork != null &&
                      activeNetwork.isConnectedOrConnecting());
        return isConnected;
    }

    /**
     * gets whether the user is offline
     * @return true if they're offline, false if they're not
     */
    public boolean getIsConnected() {
        return this.isConnected;
    }

    /**
     * sets whether the user is offline
     * @return void
     */
    public void setIsConnected(boolean newStatus) {
        this.isConnected = newStatus;
    }
}

