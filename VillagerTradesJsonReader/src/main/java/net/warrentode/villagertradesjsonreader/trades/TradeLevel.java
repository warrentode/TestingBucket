package net.warrentode.villagertradesjsonreader.trades;

// AUTHOR: MrCrayfish https://github.com/MrCrayfish/GoblinTraders/tree/1.19.X
public enum TradeLevel {
    UNEMPLOYED ("unemployed", true),
    NOVICE ("novice", true),
    APPRENTICE ("apprentice", true),
    JOURNEYMAN ("journeyman", true),
    EXPERT ("expert", true),
    MASTER ("master", true);

    private final String key;
    private boolean shuffle;

    TradeLevel(String key, boolean shuffle) {
        this.key = key;
        this.shuffle = shuffle;
    }

    public String getKey() {
        return this.key;
    }
    public Integer getOrdinal() {
        return this.ordinal();
    }

    public boolean shouldShuffle() {
        return this.shuffle;
    }
}