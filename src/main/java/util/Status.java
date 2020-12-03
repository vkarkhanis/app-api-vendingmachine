package util;

public enum Status {

    MONEY_ADDED(1), PROCESSING_ORDER(2), REQUEST_COMPLETED(3), MONEY_REFUNDED(4);

    Status(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return this.statusId;
    }

    private int statusId;
}
