package src;

public enum Shipping {
    STANDARD(5),
    RUSHED(2),
    OVERNIGHT(1);

    int days;
    Shipping(int days) {
        this.days = days;
    }
}
