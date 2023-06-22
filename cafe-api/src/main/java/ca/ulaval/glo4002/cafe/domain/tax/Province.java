package ca.ulaval.glo4002.cafe.domain.tax;

public enum Province {
    AB(0),
    BC(0.07),
    MB(0.07),
    NB(0.1),
    NL(0.1),
    NT(0),
    NS(0.1),
    NU(0),
    ON(0.08),
    PE(0.1),
    QC(0.09975),
    SK(0.06),
    YT(0);

    private final double taxRate;

    Province(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getTaxRate() {
        return taxRate;
    }
}
