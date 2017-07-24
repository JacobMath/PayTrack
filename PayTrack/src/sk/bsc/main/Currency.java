package sk.bsc.main;

/**
 * Holds all information about Currency, as name, initialsShortcut and exchange rate.
 */
public class Currency {

    private String initials;
    private String name;
    private float unitsPerUSD;
    private float USDPerUnits;

    public Currency(String initials, String name, float unitsPerUSD, float USDPerUnits) {
        this.initials = initials;
        this.name = name;
        this.unitsPerUSD = unitsPerUSD;
        this.USDPerUnits = USDPerUnits;
    }

    /**
     * Gets currency initials.
     * @return currency initials in String.
     */
    public String getInitials() {
        return initials;
    }

    /**
     * Gets currency name.
     * @return currency name in String.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets exchange rate.
     * @return float value, how many unit of this currency is in USD.
     */
    public float getUnitsPerUSD() {
        return unitsPerUSD;
    }

    /**
     * Gets exchange rate.
     * @return float value, how many USD is in another currency.
     */
    public float getUSDPerUnits() {
        return USDPerUnits;
    }
}
