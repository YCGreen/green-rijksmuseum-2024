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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PaintingComponent extends JComponent {
    private static final int IMG_CT = 10;
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
        }

        for (int i = 0; i < label.length; i++) {


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

        pages.put(pageNum, label);
    }

    public JLabel[] getBlankRequest(int pageNum) {
        if(pages.containsKey(pageNum)) {
            return pages.get(pageNum);
        }

        sendBlankRequest(pageNum);
        return pages.get(pageNum);
    }

  /*  public JLabel[] getQueryRequest(String query) {
        setPageNum(1);
        sendQueryRequest(query);

    }
*/

}