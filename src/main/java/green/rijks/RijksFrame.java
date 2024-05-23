package green.rijks;

import com.andrewoid.ApiKey;

import javax.swing.*;
import java.awt.*;

public class RijksFrame extends JFrame  {

    private ApiKey apiKey = new ApiKey();
    private int currPage = 1;

    public RijksFrame() {
        setSize(800, 600);
        setTitle("Rijks Museum Paintings");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        setContentPane(main);

        JTextField search = new JTextField();
        main.add(search, BorderLayout.PAGE_START);

        PaintingComponent paintingComponent = new PaintingComponent();
        main.add(paintingComponent);

        JLabel[] label = paintingComponent.getBlankRequest(1);

        GridLayout layout = new GridLayout(4, 3);

        paintingComponent.setLayout(layout);

        for (int i = 0; i < label.length; i++) {
            paintingComponent.add(label[i]);
        }

      /*  DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(paintingComponent.searchPainting(search.getText(), 0)) {
                    //write something to update urls jlabels
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        }; */

      //  search.getDocument().addDocumentListener(docListener);


    }
}

class Main {
    public static void main(String[] args) {
        new RijksFrame().setVisible(true);

    }
}