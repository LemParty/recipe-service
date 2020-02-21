package com.lemparty.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Ingredient implements Serializable {

        private String name;
        private String defaultAmount;
        private String defaultUnitOfMeasure;
        private boolean required;
        private String note;

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;

        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDefaultAmount() {
                return defaultAmount;
        }

        public void setDefaultAmount(String defaultAmount) {
                this.defaultAmount = defaultAmount;
        }

        public String getDefaultUnitOfMeasure() {
                return defaultUnitOfMeasure;
        }

        public void setDefaultUnitOfMeasure(String defaultUnitOfMeasure) {
                this.defaultUnitOfMeasure = defaultUnitOfMeasure;
        }

        public String getNote() {
                return note;
        }

        public void setNote(String note) {
                this.note = note;
        }
}
