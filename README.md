TradeOfferLib
=============

TradeOfferLib is a Java library that allows you to create, counter-offer, accept and decline trade offers on Steam. It should work for item that is normally tradable via Steam.

This library can:
* Create trades
* Modify incoming trades
* Accept/Decline trades
* List available inventories (That's right, it works with more than just TF2)
* Iterate through the items in the inventory

I don't have any games that use "currency" so I haven't been able to try trading that yet.

Example usage:
```
TradeUser bot = new TradeUser();

//Interactive login
//bot.login("sir tradesalot", "gimme yer scrap");

//but I like storing my cookies somewhere and just using them instead
bot.addCookie("steamMachineAuth76561198086629338", "CAFEBABEDEADBEEFD3501473DECAFB0075FED0RA", true);
bot.addCookie("steamLogin", "76561198086629338%7C%7CCAFEBABEDEADBEEFD3501473DECAFB0075FED0RA", false);

//Get an existing trade
Trade trade = bot.getTrade(123456);

//Or start a new one
//trade = bot.newTrade(new SteamID(76561198086629338L));


//These are things they've added to trade
System.out.println("Their assets:");
Trade.TradeStatusUser them = trade.tradeStatus.them;
for(Trade.TradeAsset tradeAsset : trade.tradeStatus.them.assets)
{
    System.out.println(them.getDescription(tradeAsset).name);
}

System.out.println("My assets:");
Trade.TradeStatusUser me = trade.tradeStatus.me;
for(Trade.TradeAsset tradeAsset : me.assets)
{
    Inventory.Description d = me.getDescription(tradeAsset);
    System.out.println(d.name);

    //Getting defindexes
    if(tradeAsset.appid == 440)
    {
        String defIndex = d.app_data.get("def_index");
        System.out.println(defIndex);
    }
}

//Or you could loop through their inventory and add items you like
Inventory inventory = them.fetchInventory(440, 2);
for(Inventory.Item item : inventory.getItems())
{
    Inventory.Description description = item.description;
    String def_index = description.app_data.get("def_index");
    if(def_index.equals("5000") || def_index.equals("5001") || def_index.equals("5002"))
    {
        //Take all their metal!
        them.addItem(item);
    }
}

//Update sends the changes you made to the other player so they can review the changes
trade.update("Gimme yer metal!");

// Or if you're responding to a trade sent to you
//trade.accept();
//trade.decline();
```