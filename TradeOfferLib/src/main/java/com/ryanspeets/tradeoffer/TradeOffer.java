package com.ryanspeets.tradeoffer;

public class TradeOffer {
    protected int id;
    protected boolean active;
    protected TradeUser user;

    protected TradeOffer(int id, boolean active, TradeUser user)
    {
        this.id = id;
        this.active = active;
        this.user = user;
    }

    public int getId()
    {
        return id;
    }

    public Trade getTrade() throws Exception
    {
        return user.getTrade(getId());
    }


}
