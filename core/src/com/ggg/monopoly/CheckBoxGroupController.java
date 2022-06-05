package com.ggg.monopoly;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.utils.Null;

import java.util.ArrayList;

/**
 * Kontroler chceckboxow ograniczajacy mozliwosc wyboru do jednego chceckboxa z grupy
 */
public class CheckBoxGroupController {
    private ArrayList<CheckBox> checkBoxGroup;

    /**
     * Konstuktor grupy checkboxow
     */
    CheckBoxGroupController(){
        checkBoxGroup = new ArrayList<>();
    }

    /**
     * Dodanie checkboxa do grupy checkboxow
     * @param checkBox checkbox z grupy checkboxow
     */
    public void addCheckBox(CheckBox checkBox){
        checkBoxGroup.add(checkBox);
    }

    /**
     * Wybiera checkboxa
     * @param checkBox checkbox z grupy checkboxow
     */
    public void selectCheckBox(CheckBox checkBox){
        for(CheckBox c:checkBoxGroup){
            if(c!=checkBox){
                c.setChecked(false);
            }
        }
    }
    /**
     * Zwraca wybranego checkboxa
     * @return wybranego checkboxa
     */
    public CheckBox getSelected(){
        for (CheckBox c:checkBoxGroup){
            if(c.isChecked())
                return c;
        }
        return null;
    }
}
