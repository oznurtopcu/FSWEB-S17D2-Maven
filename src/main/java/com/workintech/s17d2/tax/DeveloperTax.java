package com.workintech.s17d2.tax;

import org.springframework.stereotype.Component;

@Component
public class DeveloperTax implements Taxable{

    //enum veya bu şekilde constant bırakabiliriz(static final)
    //    public static final double SIMPLE_TAX_RATE=15d;
    //    public static final double MIDDLE_TAX_RATE=25d;
    //    public static final double UPPER_TAX_RATE=35d;

    @Override
    public Double getSimpleTaxRate() {
        return 15d;
    }

    @Override
    public Double getMiddleTaxRate() {
        return 25d;
    }

    @Override
    public Double getUpperTaxRate() {
        return 35d;
    }
}
