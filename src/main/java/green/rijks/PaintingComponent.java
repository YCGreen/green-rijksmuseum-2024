package green.rijks;

import com.andrewoid.ApiKey;
import green.rijks.json.ArtObjects;
import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

//interface adapted from https://www.baeldung.com/java-multiple-keys-map
//maybe a bad idea let's find out
interface MultiKeyWrapper {}

record IntegerMultiKeyWrapper(Integer value) implements MultiKeyWrapper {}
record StringMultiKeyWrapper(String value) implements MultiKeyWrapper {} //use for query requests

public class PaintingComponent extends JComponent {
    static RijksService service;
    static ApiKey key;
    Map<Integer, JLabel[]> pages = new ConcurrentHashMap<>();

    public PaintingComponent() {
        service = new RijksServiceFactory().getService();
        key = new ApiKey();
    }

    private void sendBlankRequest(int pageNum) {
        Disposable disposable = service.getPage(key.get(), pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        (response) -> setUrls(response, pageNum),
                        Throwable::printStackTrace);
      /*  ArtObjects artObjs = service.getPage(key.get(), pageNum).blockingGet(); --works
        setUrls(artObjs, pageNum); */
    }

    private void sendQueryRequest(String query, int pageNum) {
        Disposable disposable = service.getField(key.get(), query, pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        (response) -> setUrls(response, pageNum),
                        Throwable::printStackTrace);
    }

    private void setUrls(ArtObjects artObjs, int pageNum) {
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

      //  pages.put(new IntegerMultiKeyWrapper(pageNum), label); -- add type to method variables?
        pages.put(pageNum, label);
    }

    public JLabel[] getBlankRequest(int pageNum) {
        if (!pages.containsKey(pageNum)) {
            sendBlankRequest(pageNum);
        }
        return pages.get(pageNum);
    }

}