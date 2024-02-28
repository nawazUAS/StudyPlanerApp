package edu.fra.uas.subjectservicemodel;

public class SemesterDTO {
    
        private String name;
        private int month;
        private int year;

        public void setYear(int year) {
             this.year = year;
        }
        public int getYear() {
             return year;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getMonth() {
            return month;
        }
        public void setMonth(int month) {
            this.month = month;
        }
        public String getName() {
            return name;
        }
}
