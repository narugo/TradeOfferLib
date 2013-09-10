package com.ryanspeets.tradeoffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ScrapOffer {
    public static void main(String[] args) throws Exception
    {
        TradeUser bot = new TradeUser();

        Scanner readIn = new Scanner(System.in);

        System.out.print("Username: ");
        String username = readIn.nextLine();
        System.out.print("Password: ");
        String password = readIn.nextLine();

        bot.login(username, password);

        while(true)
        {
            TradeOffer[] tradeOffers = bot.getIncomingTradeIds();
            for(TradeOffer tradeOffer : tradeOffers)
            {
                if(tradeOffer.active)
                {
                    Trade trade = tradeOffer.getTrade();
                    System.out.println("New incoming trade!");
                    System.out.println("Their items:");
                    printItems(trade.tradeStatus.them);
                    System.out.println("My items:");
                    printItems(trade.tradeStatus.me);

                    if(!containsInvalidItem(trade.tradeStatus.me) && !containsInvalidItem(trade.tradeStatus.them))
                    {
                        int myWorth = getAssetWorth(trade.tradeStatus.me);
                        int theirWorth = getAssetWorth(trade.tradeStatus.them);
                        if(myWorth > theirWorth)
                        {
                            System.out.println("Cheapass user.");
                        } else {
                            System.out.println("Trade accepted.");
                            trade.accept();
                            continue;
                        }
                    } else {
                        System.out.println("Contains invalid item");
                    }
                    trade.decline();
                }
            }
            Thread.sleep(10000);
        }
    }

    private static void printItems(Trade.TradeStatusUser tradeStatusUser) throws Exception
    {
        for(Trade.TradeAsset tradeAsset : tradeStatusUser.assets)
        {
            System.out.println("\t" + tradeStatusUser.getDescription(tradeAsset).name);
        }
    }

    public static boolean containsInvalidItem(Trade.TradeStatusUser tradeStatusUser) throws Exception
    {
        for(Trade.TradeAsset tradeAsset : tradeStatusUser.assets)
        {
            if(!isMetal(tradeStatusUser.getDescription(tradeAsset)) || !isWeapon(tradeStatusUser.getDescription(tradeAsset)))
                return false;
        }
        return true;
    }

    public static int getAssetWorth(Trade.TradeStatusUser tradeStatusUser) throws Exception
    {
        int totalWorth = 0;
        for(Trade.TradeAsset tradeAsset : tradeStatusUser.assets)
        {
            totalWorth += getWorth(tradeStatusUser.getDescription(tradeAsset));
        }
        return totalWorth;
    }

    public static int getWorth(Inventory.Description description)
    {
        if(isWeapon(description))
        {
            return 1;
        } else if (isMetal(description))
        {
            String defIndex = description.app_data.get("def_index");
            if(defIndex.equals("5000"))
            {
                return 2;
            } else if(defIndex.equals("5001"))
            {
                return 6;
            } else if(defIndex.equals("5002"))
            {
                return 18;
            }
        }
        return 0;
    }

    public static boolean isMetal(Inventory.Description description)
    {
        if(!description.appid.equals("440"))
            return false;
        String defIndex = description.app_data.get("def_index");
        if(!defIndex.equals("5000") && !defIndex.equals("5001") && !defIndex.equals("5002"))
            return false;
        return true;
    }

    public static boolean isWeapon(Inventory.Description description)
    {
        if(!description.appid.equals("440"))
            return false;
        if(!description.app_data.get("quality").equals("6")) //not unique quality
            return false;
        String type = getType(description);
        if(!type.equals("primary") && !type.equals("secondary") && !type.equals("melee") && !type.equals("pda") && !type.equals("pda2"))
            return false;
        return true;
    }

    public static String getType(Inventory.Description description)
    {
        for(HashMap<String, String> tag : description.tags)
        {
            if(tag.get("category").equals("Type"))
            {
                return tag.get("internal_name");
            }
        }
        return "unknown";
    }
}
