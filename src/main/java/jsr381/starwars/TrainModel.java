package jsr381.starwars;

import lombok.SneakyThrows;

import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

public class TrainModel {

    @SneakyThrows
    public static void main(String[] args) {
        NeuralNetImageClassifier.builder()
                .inputClass(BufferedImage.class)
                .imageHeight(50)
                .imageWidth(50)
                .labelsFile(Paths.get("deepnetts/labels.txt"))
                .trainingFile(Paths.get("deepnetts/train.txt"))
                .networkArchitecture(Paths.get("deepnetts/arch.json"))
                .exportModel(Paths.get("deepnetts/trained_model.dnet"))
                .maxError(0.03f)
                .maxEpochs(100)
                .learningRate(0.01f)
                .build();
    }
}
