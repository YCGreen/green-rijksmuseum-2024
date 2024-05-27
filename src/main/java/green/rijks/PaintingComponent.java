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
/*
//interface adapted from https://www.baeldung.com/java-multiple-keys-map
//maybe a bad idea let's find out
interface MultiKeyWrapper {}

record IntegerMultiKeyWrapper(Integer value) implements MultiKeyWrapper {}
record StringMultiKeyWrapper(String value) implements MultiKeyWrapper {} //use for query requests
*/

public class PaintingComponent extends JComponent {

    public PaintingComponent() {
        GridLayout layout = new GridLayout(4, 3);
        this.setLayout(layout);
    }

    public void setImages(JLabel[] images) {
        for (int i = 0; i < images.length; i++) {
            this.add(images[i]);
        }
    }



}