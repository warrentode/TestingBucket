package net.warrentode.recipebooktest.recipe.recipebook;

public enum CoinPressRecipeBookTab {
    COINS("coins"),
    BANK_NOTES("bank_notes"),
    MISC("misc");

    public final String name;

    CoinPressRecipeBookTab(String name) {
        this.name = name;
    }

    public static CoinPressRecipeBookTab findByName(String name) {
        for (CoinPressRecipeBookTab value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}