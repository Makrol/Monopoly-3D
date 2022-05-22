package com.ggg.monopoly;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.utils.Null;

import java.util.ArrayList;

public class CheckBoxGroupController {
    private ArrayList<CheckBox> checkBoxGroup;

    CheckBoxGroupController(){
        checkBoxGroup = new ArrayList<>();
    }
    public void addCheckBox(CheckBox checkBox){
        checkBoxGroup.add(checkBox);
    }
    public void selectCheckBox(CheckBox checkBox){
        for(CheckBox c:checkBoxGroup){
            if(c!=checkBox){
                c.setChecked(false);
            }
        }
    }
    public CheckBox getSelected(){
        for (CheckBox c:checkBoxGroup){
            if(c.isChecked())
                return c;
        }
        return null;
    }
}
