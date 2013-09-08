package com.ryanspeets.tradeoffer;

public class SteamID {
    private long communityId;
    public SteamID(long communityId)
    {
        this.communityId = communityId;
    }

    public long getAccountId()
    {
        return (communityId & 0xFFFFFFFFL);
    }

    public long getCommunityId()
    {
        return communityId;
    }

    public String render()
    {
        long accountId = getAccountId();
        return "STEAM_0:" + (accountId & 1) + ":" + (accountId >> 1);
    }

    public static SteamID fromAccountId(long accountId)
    {
        return new SteamID(0x0110000100000000L & accountId);
    }
}
