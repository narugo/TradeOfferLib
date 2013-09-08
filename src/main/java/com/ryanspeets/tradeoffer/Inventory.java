package com.ryanspeets.tradeoffer;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    public class Item {
        public String id;
        public String classid;
        public String instanceid;
        public String amount;
        public String pos;
        public transient Description description;
        public transient Inventory inventory;

    }
    public class Description {
        public String appid;
        public String classid;
        public String instanceid;
        public String name;
        public ArrayList<HashMap<String, String>> tags;
        public HashMap<String, String> app_data;
    }
    public boolean success;
    public transient long appId;
    public transient long contextId;
    public HashMap<String, Item> rgInventory;
    public HashMap<String, Description> rgDescriptions;

    protected void updateItems()
    {
        for(Item item : rgInventory.values())
        {
            item.inventory = this;
            item.description = rgDescriptions.get(item.classid+"_"+item.instanceid);
        }
    }

    public ArrayList<Item> getItems()
    {
        return new ArrayList< Item >(rgInventory.values());
    }

    public Description getDescription(Trade.TradeAsset tradeAsset)
    {
        return rgInventory.get(Long.toString(tradeAsset.assetid)).description;
    }
}
