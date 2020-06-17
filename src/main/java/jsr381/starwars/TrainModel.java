package jsr381.starwars;

import lombok.SneakyThrows;

import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

public class TrainModel {

    @SneakyThrows
    public static void main(String[] args) {
        NeuralNetImageClassifier.builder()
                .inputClass(BufferedImage.class)
                .imageHeight(50)
                .imageWidth(50)
                .labelsFile(new File("deepnetts/labels.txt"))
                .trainingFile(new File("deepnetts/train.txt"))
                .networkArchitecture(new File("deepnetts/arch.json"))
                .exportModel(Paths.get("deepnetts/trained_model.dnet"))
                .maxError(0.03f)
                .maxEpochs(100)
                .learningRate(0.01f)
                .build();
    }
}
