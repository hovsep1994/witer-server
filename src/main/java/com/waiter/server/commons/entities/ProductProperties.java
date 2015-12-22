package com.waiter.server.commons.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/23/2015.
 */
public class ProductProperties {

    private static class CheckBox {
        private int id;
        private String name;
        private double price;
    }

    private static class RadioButton {
        private int id;
        private String name;
        private double price;
    }

    private static class RadioButtonGroup {
        private int id;
        private String groupName;
        private List<RadioButton> radioButtons;
    }

    private List<CheckBox> checkBoxes = new ArrayList<>();
    private List<RadioButtonGroup> radioButtonGroups = new ArrayList<>();

    public static CheckBox createCheckBox() {
        return new CheckBox();
    }

    public static RadioButtonGroup createRadioButtonGroup(){
        return new RadioButtonGroup();
    }
}
