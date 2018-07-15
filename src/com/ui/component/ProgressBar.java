package com.ui.component;



import com.ui.constant.UIConstant;

import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class ProgressBar extends JProgressBar{
    public ProgressBar(int min, int max) {
        super(min,max);

        this.setBorderPainted(false);
    }

    @Override
    public void setUI(ProgressBarUI ui) {
        // TODO Auto-generated method stub
        super.setUI(new ProgressUI(this, UIConstant.NAVI_BAR_BACK_COLOR));
    }

    public class ProgressUI extends BasicProgressBarUI {

        private JProgressBar jProgressBar;
        private Color forecolor;

        /**
         * @param foreColor The Color of Progress Bar
         */
        ProgressUI(JProgressBar jProgressBar,Color foreColor) {
            this.jProgressBar = jProgressBar;
            this.forecolor = foreColor;
        }

        @Override
        protected void paintDeterminate(Graphics g, JComponent c) {
            this.jProgressBar.setBackground(UIConstant.MAIN_BACK_COLOR);
            this.jProgressBar.setForeground(forecolor);

            super.paintDeterminate(g, c);
        }

    }
}
