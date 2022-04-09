package com.rlirette.tools.rentalhelper.tools;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Common {

    public static String format(LocalDate dateToFormat){
        return dateToFormat.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static void popup(String message){
        JOptionPane.showMessageDialog(initJFrame(), message);
    }

    private static JFrame initJFrame() {
        final JFrame jFrame = new JFrame("Rental helper");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(centralize(jFrame.getSize().width, dim.width), centralize(jFrame.getSize().height, dim.height));
        return jFrame;
    }

    private static int centralize(int jFrameSize, int dimSize) {
        return dimSize / 2 - jFrameSize / 2;
    }
}
