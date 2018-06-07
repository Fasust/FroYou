package frozenyogurtbuilder.app.classes;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class RecourceLoader {

    /**
     * Load a Json String from Assets
     * @param filename name of the JSON file in Assets (end with .json)
     * @param assets Asset Manager with assets to look in (getAssets())
     * @return Json String
     */
    public static String loadJSONFromAsset(AssetManager assets, String filename) {
        String json = null;

        try {
            InputStream is = assets.open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
