package com.appstone.keeptask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Task implements Serializable {

    public int taskID;
    public String taskTitle;
    public String taskItemString;
//    public ArrayList<Items> taskItems;


    public static String convertArrayListToJSONArrayString(ArrayList<Items> tasks) {
        String itemsArrayString = "";

        JSONArray itemsArray = new JSONArray();

        for (Items item : tasks) {
            try {
                JSONObject itemObject = new JSONObject();

                itemObject.put("item_id", item.itemID);
                itemObject.put("item_name", item.itemName);
                itemObject.put("item_is_checked", item.isItemChecked);

                itemsArray.put(itemObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        itemsArrayString = itemsArray.toString();

        return itemsArrayString;
    }


    public static ArrayList<Items> convertStringtoArrayList(String items) {
        ArrayList<Items> taskItems = new ArrayList<>();

        try {
            JSONArray itemsArray = new JSONArray(items);

            if (itemsArray != null && itemsArray.length() > 0) {
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject itemObject = itemsArray.optJSONObject(i);
                    Items item = new Items();
                    item.itemID = itemObject.optInt("item_id");
                    item.itemName = itemObject.optString("item_name");
                    item.isItemChecked = itemObject.optBoolean("item_is_checked");

                    taskItems.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return taskItems;
    }

}





