package ca.ulaval.glo4002.cafe.domain.tax;

public enum State {
    AL(0.04),
    AZ(0.056),
    CA(0.0725),
    FL(0.06),
    ME(0.055),
    NY(0.04),
    TX(0.0625);

    private final double taxRate;

    State(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getTaxRate() {
        return taxRate;
    }
}
