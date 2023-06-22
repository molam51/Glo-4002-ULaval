package ca.ulaval.glo4002.cafe.domain.tax;

public enum Country {
    CA(0.05),
    US(0),
    CL(0.19),
    None(0);

    private final double taxRate;

    Country(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getTaxRate() {
        return taxRate;
    }
}
