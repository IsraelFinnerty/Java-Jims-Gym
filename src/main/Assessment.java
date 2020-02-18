public class Assessment {

    private float weight;
    private float thigh;
    private float waist;
    private String comment;
    private String personalTrainer;
    boolean weightTrend = false;
    boolean waistTrend = false;

    public Assessment(float weight, float thigh, float waist, String comment, String personalTrainer) {
        this.weight = weight;
        this.thigh = thigh;
        this.waist = waist;
        this.comment = comment;
        this.personalTrainer = personalTrainer;
    }

    public Assessment(float weight, float thigh, float waist, String comment) {
        this.weight = weight;
        this.thigh = thigh;
        this.waist = waist;
        this.comment = comment;

    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getThigh() {
        return thigh;
    }

    public void setThigh(float thigh) {
        this.thigh = thigh;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(String personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

     public String toString() {
        return " | Weight: " + weight + " | Thigh: " + thigh + " | Waist: " + waist + " | Comment: " + comment + " | Trainer: " + personalTrainer;
    }


    public boolean isWeightTrend() {
        return weightTrend;
    }

    public void setWeightTrend(boolean weightTrend) {
        this.weightTrend = weightTrend;
    }

    public boolean isWaistTrend() {
        return waistTrend;
    }

    public void setWaistTrend(boolean waistTrend) {
        this.waistTrend = waistTrend;
    }
}
