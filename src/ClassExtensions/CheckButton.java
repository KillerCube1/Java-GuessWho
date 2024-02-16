package ClassExtensions;

import java.awt.Color;

import javax.swing.JButton;

public class CheckButton extends JButton {

    public String CompareValue;

    public CheckButton(){
        super();
    }

    public CheckButton(String Label, String Value, Color Color){
        super();

        this.CompareValue = Value;
        this.setText(Label);
        this.setBackground(Color);
    }

    public void setCompareValue(String Value) {
        this.CompareValue = Value;
    }

    public String getCompareValue() {
        return CompareValue;
    }

}
