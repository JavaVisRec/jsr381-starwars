package jsr381.starwars;

import lombok.SneakyThrows;

import javax.visrec.ml.classification.ImageClassifier;
import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class UseModel {

    public static void main(String[] args) {
        // Tie Fighter
        List<File> tieFighters = List.of(
                new File("test_images/tie_fighter/1.jpg"),
                new File("test_images/tie_fighter/2.jpg"),
                new File("test_images/tie_fighter/3.jpg")
        );
        tieFighters.stream()
                .map(f -> createClassifier().classify(f))
                .forEach(System.out::println);

        // Millennium Falcon
        List<File> falcons = List.of(
                new File("test_images/millennium_falcon/1.jpg"),
                new File("test_images/millennium_falcon/2.jpg"),
                new File("test_images/millennium_falcon/3.jpg")
        );
        falcons.stream()
                .map(f -> createClassifier().classify(f))
                .forEach(System.out::println);
    }

    @SneakyThrows
    private static ImageClassifier<BufferedImage> createClassifier() {
        return NeuralNetImageClassifier.builder()
                .inputClass(BufferedImage.class)
                .imageHeight(50)
                .imageWidth(50)
                // Replace with "deepnetts/trained_model.dnet" to use your own trained model
                .importModel(Paths.get("starwars.dnet"))
                .build();
    }

}
