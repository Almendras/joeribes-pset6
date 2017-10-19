package com.example.joeribes.joeribes_pset6;

import java.util.ArrayList;

public class CustomizationList  {
    //private String customizationList;
    private ArrayList<Customization> customizationArrayList;


    public CustomizationList(String aGroup) {
        //this.customizationList = aGroup;
        this.customizationArrayList = new ArrayList<>();
    }

    /*
    public void setCustomizationList(String aGroup) {
        this.customizationList = aGroup;
    }
    */

    public void setCustomizationArrayList(ArrayList<Customization> customizationArrayList) {
        this.customizationArrayList = customizationArrayList;
    }

    /*
    public String getCustomizationList() {
        return customizationList;
    }
    */

    public ArrayList<Customization> getCustomizationArrayList() {
        return customizationArrayList;
    }

    public void addCustomization(Customization customization) {
        customizationArrayList.add(customization);
    }


}
