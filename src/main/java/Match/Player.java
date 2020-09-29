package Match;

public class Player {

    private String name;
    private double credits;
    private double estimatedScore;
    private double points;
    private Category category;
    private String team;

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getCredits() {
        return credits;
    }

    public double getEstimatedScore() {
        return estimatedScore;
    }

    public double getPoints() {
        return points;
    }

    public String getTeam() {
        return team;
    }

    public Player(String name, double credits, double estimatedScore, double points, Category category, String team) {
        this.name = name;
        this.credits = credits;
        this.category = category;
        this.team = team;
        this.estimatedScore = estimatedScore;
        this.points = points;
    }

    public static class Builder {

        private String name;
        private double credits;
        private double estimatedScore;
        private double points;
        private Category category;
        private String team;

        public Builder(String name) {
            this.name = name;
        }

        public Builder setCredits(double credits) {
            this.credits = credits;
            return this;
        }

        public Builder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder setTeam(String team) {
            this.team = team;
            return this;
        }

        public Builder setEstimatedScore(double estimatedScore) {
            this.estimatedScore = estimatedScore;
            return this;
        }

        public Builder setPoints(double points) {
            this.points = points;
            return this;
        }

        public Player build() {
            return new Player(name, credits, estimatedScore, points, category, team);
        }

    }

}
