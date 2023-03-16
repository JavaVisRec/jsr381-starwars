package jsr381.starwars.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import lombok.Builder;
import lombok.Data;

import javax.visrec.ml.classification.ImageClassifier;
import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import javax.visrec.ml.model.ModelCreationException;

public class ClassifierController {

    @FXML
    private MenuBar menu;

    @FXML
    private ImageView image;

    @FXML
    private MenuItem openModelBtn;

    @FXML
    private MenuItem openImageBtn;

    @FXML
    private Button classifyBtn;

    private ImageClassifier<BufferedImage> classifier;
    private File imageFile;

    @FXML
    private void initialize() {
        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            menu.useSystemMenuBarProperty().set(true);
        }

        openImageBtn.setDisable(true);
        classifyBtn.setDisable(true);
    }

    @FXML
    private void openModel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Model");
        File file = fileChooser.showOpenDialog(null);
        try {
            classifier = NeuralNetImageClassifier.builder()
                    .inputClass(BufferedImage.class)
                    .importModel(file.toPath())
                    .build();
            openImageBtn.setDisable(false);
            System.out.println("Loaded model");
        } catch (ModelCreationException e) {
            System.out.println(e);
            openImageBtn.setDisable(true);
        } finally {
            imageFile = null;
            image.setImage(null);
            classifyBtn.setDisable(true);
        }
    }

    @FXML
    private void openImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        File file = fileChooser.showOpenDialog(null);
        image.setImage(new Image(file.toURI().toString()));
        centerImage();
        imageFile = file;
        classifyBtn.setDisable(false);
    }

    @Data
    @Builder
    private static class Result {
        private String name;
        private Float accuracy;
    }

    @FXML
    private void classifyImage(ActionEvent event) {
        var results = classifier.classify(imageFile.toPath());
        var sortedResults = results.entrySet().stream()
                .map(e -> Result.builder().name(e.getKey()).accuracy(e.getValue()).build())
                .sorted((v1, v2) -> v1.accuracy > v2.accuracy ? 1 : -1)
                .collect(Collectors.toList());

        StringBuilder contentTextBuilder = new StringBuilder();
        sortedResults.forEach(e ->
                contentTextBuilder.append(e.name)
                        .append(" : ")
                        .append(String.format("%.2f", e.accuracy * 100))
                        .append("%")
                        .append("\n")
        );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Classification result");
        alert.setHeaderText(null);
        alert.setContentText(contentTextBuilder.toString());
        alert.showAndWait();
    }


    private void centerImage() {
        Image img = image.getImage();
        if (img != null) {
            double ratioX = image.getFitWidth() / img.getWidth();
            double ratioY = image.getFitHeight() / img.getHeight();

            double reducCoeff;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            double w = img.getWidth() * reducCoeff;
            double h = img.getHeight() * reducCoeff;

            image.setX((image.getFitWidth() - w) / 2);
            image.setY((image.getFitHeight() - h) / 2);
        }
    }

}
