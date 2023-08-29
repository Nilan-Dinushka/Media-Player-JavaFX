package lk.ijse.dep11;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import javax.naming.Binding;
import java.io.File;

public class MainViewController {
    public BorderPane root;
    public Button btnOpen;
    public Button btnPlay;
    public Button btnStop;
    public Button btnPause;
    public Button btnFastFastBackward;
    public Button btnFastBackward;
    public Button btnFastForward;
    public Button btnFastFastForward;
    public Button btnExit;
    public MediaView mvPlayer;
    public Slider sldVolume;
    MediaPlayer mediaPlayer;

    public void btnOpenOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Select a Media File","*.mp4","*.avi","*.wmv","*.mkv"));
        File mediaFile = fileChooser.showOpenDialog(root.getScene().getWindow());
        if(mediaFile != null){
            Media media = new Media(mediaFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mvPlayer.setMediaPlayer(mediaPlayer);
            DoubleProperty width = mvPlayer.fitWidthProperty();
            DoubleProperty height = mvPlayer.fitHeightProperty();

            width.bind(Bindings.selectDouble(mvPlayer.sceneProperty(),"width"));
            height.bind(Bindings.selectDouble(mvPlayer.sceneProperty(),"height"));

            sldVolume.setValue(mediaPlayer.getVolume() * 100);

            sldVolume.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(sldVolume.getValue()/100);
                }
            });
            mediaPlayer.play();
        }

    }

    public void btnPlayOnAction(ActionEvent actionEvent) {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    public void btnStopOnAction(ActionEvent actionEvent) {
        mediaPlayer.stop();
    }

    public void btnPauseOnAction(ActionEvent actionEvent) {
        mediaPlayer.pause();
    }

    public void btnFastFastBackwardOnAction(ActionEvent actionEvent) {
        mediaPlayer.setRate(.5);
    }

    public void btnFastBackwardOnAction(ActionEvent actionEvent) {
        mediaPlayer.setRate(.75);
    }

    public void btnFastForwardOnAction(ActionEvent actionEvent) {
        mediaPlayer.setRate(1.5);
    }

    public void btnFastFastForwardOnAction(ActionEvent actionEvent) {
        mediaPlayer.setRate(2.0);
    }

    public void btnExitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
