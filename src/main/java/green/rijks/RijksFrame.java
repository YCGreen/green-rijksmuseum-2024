package green.rijks;

import com.andrewoid.ApiKey;
import green.rijks.json.ArtObjects;
import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class RijksFrame extends JFrame  {

    private ApiKey key = new ApiKey();
    private final RijksService service = new RijksServiceFactory().getService();
    private int currPage = 1;
    private ArtObjects artObjs;
    JPanel paintingComponent = new JPanel();

    public RijksFrame() {
        setSize(800, 600);
        setTitle("Rijks Museum Paintings");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        setContentPane(main);

        JButton next = new JButton("Next");
        JButton prev = new JButton("Previous");

        JPanel prevPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel nextPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        prevPanel.add(prev);
        nextPanel.add(next);

        main.add(prevPanel, BorderLayout.WEST);
        main.add(nextPanel, BorderLayout.EAST);

        JTextField search = new JTextField();
        main.add(search, BorderLayout.PAGE_START);

        main.add(paintingComponent, BorderLayout.CENTER);

        onStart();

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Disposable disposable = service.getPage(key.get(), ++currPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(SwingSchedulers.edt())
                        .subscribe(
                                (response) -> handleResponse(response),
                                Throwable::printStackTrace);
            }
        });

        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Disposable disposable = service.getPage(key.get(), --currPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(SwingSchedulers.edt())
                        .subscribe(
                                (response) -> handleResponse(response),
                                Throwable::printStackTrace);
            }
        });





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

    private void onStart() {
        Disposable disposable = service.getPage(key.get(), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        (response) -> handleResponse(response),
                        Throwable::printStackTrace);
    }

    private void handleResponse(ArtObjects artObjs) {
    //    this.artObjs = artObjs;
        JLabel[] label = new JLabel[artObjs.artObjects.length];

        for (int i = 0; i < label.length; i++) {
            label[i] = new JLabel();
            try {
                URL url = new URL(artObjs.artObjects[i].webImage.url);
                Image img = ImageIO.read(url);
                ImageIcon imgIcon = new ImageIcon(img
                        .getScaledInstance(200, -1, Image.SCALE_DEFAULT));
                label[i].setIcon(imgIcon);
                String title = artObjs.artObjects[i].title + "\n" + artObjs.artObjects[i].principalOrFirstMaker;
                label[i].setToolTipText(title);
                label[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        RijksFrameSingle sinFrame = new RijksFrameSingle(img, title);
                        sinFrame.setVisible(true);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        setImages(label);
    }

    private JLabel[] extractImages() {
        JLabel[] label = new JLabel[artObjs.artObjects.length];

        for (int i = 0; i < label.length; i++) {
            label[i] = new JLabel();
            try {
                URL url = new URL(artObjs.artObjects[i].webImage.url);
                Image img = ImageIO.read(url);
                ImageIcon imgIcon = new ImageIcon(img
                        .getScaledInstance(200, -1, Image.SCALE_DEFAULT));
                label[i].setIcon(imgIcon);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return label;
    }

    private void setImages(JLabel[] images) {
        for (int i = 0; i < images.length; i++) {
            paintingComponent.add(images[i]);
        }
    }


}



class Main {
    public static void main(String[] args) {
        new RijksFrame().setVisible(true);
      //  System.out.println(new PaintingComponent().getBlankRequest(3));
    }
}