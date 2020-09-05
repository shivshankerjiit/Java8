package com.java8.fp1;

public class Course {
        String name;
        String Section;
        int reviewScore;
        int price;

        public Course(String name, String section, int reviewScore, int price) {
            this.name = name;
            Section = section;
            this.reviewScore = reviewScore;
            this.price = price;
        }

        public Course(){}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSection() {
            return Section;
        }

        public void setSection(String section) {
            Section = section;
        }

        public int getReviewScore() {
            return reviewScore;
        }

        public void setReviewScore(int reviewScore) {
            this.reviewScore = reviewScore;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", Section='" + Section + '\'' +
                ", reviewScore=" + reviewScore +
                ", price=" + price +
                '}';
    }
}
