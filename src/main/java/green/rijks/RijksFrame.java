package green.rijks;

import com.andrewoid.ApiKey;
import green.rijks.json.ArtObjects;
import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private int currPageQuery = 1;
    private ArtObjects artObjs;
    private JPanel paintingComponent = new JPanel();

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

        getPage(currPage);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPageField(search, 1);

            }
        });

        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPageField(search, -1);
            }
        });


        DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                currPageQuery = 1;
                getField(search.getText(), currPageQuery);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };

        search.getDocument().addDocumentListener(docListener);

    }

    private void getPageField(JTextField search, int sign) {
        if (search.getText().isEmpty()) {
            getPage(currPage += sign);
        } else {
            getField(search.getText(), currPageQuery += sign);
        }
    }

    private void getPage(int pageNo) {
        Disposable disposable = service.getPage(key.get(), pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        this::handleResponse,
                        Throwable::printStackTrace);
    }

    private void getField(String search, int pageNo) {
        Disposable disposable = service.getField(key.get(), search, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        this::handleResponse,
                        Throwable::printStackTrace);
    }

    private void handleResponse(ArtObjects artObjs) {
        paintingComponent.removeAll();
        JLabel[] label = new JLabel[artObjs.artObjects.length];

        for (int i = 0; i < label.length; i++) {
            label[i] = new JLabel();
            if (artObjs.artObjects[i].webImage != null) {
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
                    paintingComponent.add(label[i]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

}
