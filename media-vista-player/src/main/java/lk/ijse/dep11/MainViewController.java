package lk.ijse.dep11;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.naming.Binding;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class MainViewController {
    public BorderPane root;
    public Button btnOpen;
    public Button btnPlay;
    public Button btnStop;
    public Button btnFastFastBackward;
    public Button btnFastBackward;
    public Button btnFastForward;
    public Button btnFastFastForward;
    public Button btnExit;
    public MediaView mvPlayer;
    public Slider sldVolume;
    public Slider sldSeek;
    public Button btnVolume;
    public StackPane stpView;
    MediaPlayer mediaPlayer;

    public void initialize(){
        btnPlay.setStyle("-fx-background-image: url(asset/playBtn.png); -fx-background-size: 20 30;-fx-background-repeat: no-repeat;-fx-background-position: center;");
        btnVolume.setStyle("-fx-background-image: url(asset/volumeBtn.png); -fx-background-size: 30 30;-fx-background-repeat: no-repeat;-fx-background-position: center;");
    }

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

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    sldSeek.setValue(t1.toSeconds());
                }
            });

            sldSeek.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(sldSeek.getValue()));
                }
            });
            mediaPlayer.play();
        }

    }

    public void btnPlayOnAction(ActionEvent actionEvent) {
        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/asset/playBtn.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        btnPlay.setBackground(background);
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            btnPlay.setStyle("-fx-background-image: url(asset/pauseBtn.png); -fx-background-size: 20 30;-fx-background-repeat: no-repeat;-fx-background-position: center;");
            mediaPlayer.pause();
        }else if(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED){
            btnPlay.setStyle("-fx-background-image: url(asset/playBtn.png); -fx-background-size: 20 30;-fx-background-repeat: no-repeat;-fx-background-position: center;");
            mediaPlayer.play();
        }

        mediaPlayer.setRate(1);
    }

    public void btnStopOnAction(ActionEvent actionEvent) {
        mediaPlayer.stop();
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

    public void btnVolumeOnAction(ActionEvent actionEvent) {
        if(mediaPlayer.isMute()==false){
            mediaPlayer.setMute(true);
            btnVolume.setStyle("-fx-background-image: url(asset/muteBtn.png); -fx-background-size: 30 30;-fx-background-repeat: no-repeat;-fx-background-position: center;");
        }else{
            btnVolume.setStyle("-fx-background-image: url(asset/volumeBtn.png); -fx-background-size: 30 30;-fx-background-repeat: no-repeat;-fx-background-position: center;");
            mediaPlayer.setMute(false);
        }
    }

    public void stpViewOnDragDropped(DragEvent dragEvent) {
        List<File> filesList = dragEvent.getDragboard().getFiles();
            Media media = new Media(filesList.get(0).toURI().toString());
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

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    sldSeek.setValue(t1.toSeconds());
                }
            });

            sldSeek.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(sldSeek.getValue()));
                }
            });
            mediaPlayer.play();

    }

    public void stpViewOnDragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }
}
