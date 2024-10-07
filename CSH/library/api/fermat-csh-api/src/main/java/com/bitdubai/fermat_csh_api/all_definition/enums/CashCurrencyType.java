package com.bitdubai.fermat_csh_api.all_definition.enums;


import com.bitdubai.fermat_csh_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
 
public enum CashCurrencyType {
    //TODO: Delete this file. Must be replaced by fermat-api\src\main\java\com\bitdubai\fermat_api\layer\world\enums\FiatCurrency.java
    DOLARUSA("USD"),
    EURO("EUR"),
    DOLARAUSTRALIANO("AUD"),
    DOLARCANADIENCE("CAD"),
    FRANCOSUIZP("CHF"),
    LIBRAESTERLINA("GBP"),
    YENJAPONES("JPY"),
    BOLIVARES("BS");

    private String code;

    CashCurrencyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static CashCurrencyType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "USD": return CashCurrencyType.DOLARUSA;
            case "EUR": return CashCurrencyType.EURO;
            case "AUD": return CashCurrencyType.DOLARAUSTRALIANO;
            case "CAD": return CashCurrencyType.DOLARCANADIENCE;
            case "CHF": return CashCurrencyType.FRANCOSUIZP;
            case "GBP": return CashCurrencyType.LIBRAESTERLINA;
            case "JPY": return CashCurrencyType.YENJAPONES;
            case "BS": return CashCurrencyType.BOLIVARES;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CashCurrencyType enum");
        }
    }
}
