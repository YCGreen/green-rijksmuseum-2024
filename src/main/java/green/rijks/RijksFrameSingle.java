package green.rijks;

import javax.swing.*;
import java.awt.*;

public class RijksFrameSingle extends JFrame {

    private Image img;
    private String title;

    public RijksFrameSingle(Image img, String title) {
        this.img = img;
        this.title = title;
        setSize(800, 600);
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel();
        setContentPane(main);

        JLabel label = new JLabel();

        ImageIcon imgIcon = new ImageIcon(img
                .getScaledInstance(800, -1, Image.SCALE_DEFAULT));
        label.setIcon(imgIcon);

        main.add(label);

    }
}
