package Match;

public enum Category {

    WK("WicketKeeper"),
    BAT("Batsman"),
    AR("AllRounder"),
    BOWL("Bowler");

    private String role;

    public String getRole() {
        return this.role;
    }

    Category(String role) {
        this.role = role;
    }
}
