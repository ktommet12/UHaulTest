package com.kyletommet.uhaultest.DB;

import org.json.JSONObject;

/**
 * Created by Kyle Tommet on 4/14/2017.
 */

public interface DBWorkerDelegate {
    public void taskFinished(JSONObject returnedJSON);
}
