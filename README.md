# Use the Force
This example trains and uses a really simple machine learning model using JSR-381.

Before getting started, the example was created using IntelliJ and JDK11 of GraalVM. You may need to adjust
the set paths in the code to different relative locations. And using any other Java 11 JVM should do fine as well.

## Training the model
The architecture of the model is small, it's based on that we used for MNIST, simply changed input and output layers.
The training data is originally from [Nick Bourdakos](https://github.com/bourdakos1/Custom-Object-Detection). 

Run this Java file to train the model:
```
jsr381.starwars.TrainModel
```  

Afterwards, it will export the trained model to `deepnetts/trained_model.dnet`

## Using the trained model
You can use the already provided trained model or you can train your own and use that one. In case
you'd like to use your own trained model, modify the path of `importModel(...)` in `jsr381.starwars.UseModel`

Run this Java file to use the model:
```
jsr381.starwars.UseModel
```
