package sample;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;


public class Main extends Application {

    public static int DIMENSION = 6;

    public static int[] firstClass = {
            1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 1,
            1, 1, 1, 1, 1, 1,
            1, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1
    };

    public static int[] secondClass = {
            0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 1,
            0, 0, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 1,
            0, 1, 1, 1, 1, 1
    };

    public static int[] thirdClass = {
            0, 1, 0, 0, 0, 1,
            0, 1, 0, 0, 0, 1,
            0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 1
    };

    public static int[] fourthClass = {
            0, 1, 1, 1, 1, 0,
            0, 1, 0, 0, 0, 0,
            0, 1, 1, 1, 1, 0,
            0, 0, 0, 0, 1, 0,
            0, 0, 0, 0, 1, 0,
            0, 1, 1, 1, 1, 0
    };

    public static int[] fifthClass = {
            0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 1, 0,
            0, 0, 0, 1, 0, 0
    };

    public static int[] firstClassReference = {1, 0, 0, 0, 0};
    public static int[] secondClassReference = {0, 1, 0, 0, 0};
    public static int[] thirdClassReference = {0, 0, 1, 0, 0};
    public static int[] fourthClassReference = {0, 0, 0, 1, 0};
    public static int[] fifthClassReference = {0, 0, 0, 0, 1};

    public static int NUMBER_OF_CLASSES = 5;


    public void teachNet(MultilayerPerceptron multilayerPerceptron, int[] currentArray, int numberOfClass) {
        for (int i = 0; i < NUMBER_OF_CLASSES; ++i) {
            multilayerPerceptron.calculateHiddenToOutputLayerErrors(i, numberOfClass);
            multilayerPerceptron.updateInputToHiddenWeights(currentArray);
            multilayerPerceptron.updateHiddenToOutputWeights(i, numberOfClass);

        }
    }

    public void calculateNetError(MultilayerPerceptron multilayerPerceptron) {
        multilayerPerceptron.calculateCurrentNetError(firstClassReference, 0);
        multilayerPerceptron.calculateCurrentNetError(secondClassReference, 1);
        multilayerPerceptron.calculateCurrentNetError(thirdClassReference, 2);
        multilayerPerceptron.calculateCurrentNetError(fourthClassReference, 3);
        multilayerPerceptron.calculateCurrentNetError(fifthClassReference, 4);
    }

    public void calculateHiddenNeurons(MultilayerPerceptron multilayerPerceptron) {
        multilayerPerceptron.calculateHiddenNeurons(firstClass);
        multilayerPerceptron.calculateOutputNeurons(0);
        multilayerPerceptron.calculateHiddenNeurons(secondClass);
        multilayerPerceptron.calculateOutputNeurons(1);
        multilayerPerceptron.calculateHiddenNeurons(thirdClass);
        multilayerPerceptron.calculateOutputNeurons(2);
        multilayerPerceptron.calculateHiddenNeurons(fifthClass);
        multilayerPerceptron.calculateOutputNeurons(4);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        BufferedImage bufferedImage = new BufferedImage(187, 187, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.setColor(Color.RED);


//        ClassImageCreator classImageCreator = new ClassImageCreator(firstClass);
//        ImageView firstClassImage = classImageCreator.getClassImageView();
//        classImageCreator = new ClassImageCreator(secondClass);
//        ImageView secondClassImage = classImageCreator.getClassImageView();
//        classImageCreator = new ClassImageCreator(thirdClass);
//        ImageView thirdImageView = classImageCreator.getClassImageView();
//        classImageCreator = new ClassImageCreator(fourthClass);
//        ImageView fourthImageView = classImageCreator.getClassImageView();
//        classImageCreator = new ClassImageCreator(fifthClass);
//        ImageView fifthImageView = classImageCreator.getClassImageView();


        MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
        multilayerPerceptron.randomizeInputToHiddenWeights();
        multilayerPerceptron.randomizeHiddenToOutputWeights();


        for(int epoch = 0; epoch < 1000; ++epoch) {
            calculateHiddenNeurons(multilayerPerceptron);

            calculateNetError(multilayerPerceptron);
            multilayerPerceptron.printCurrentNetErrorArray();


            multilayerPerceptron.calculateCurrentOutputNeuronError(firstClassReference, 0);
            multilayerPerceptron.calculateHiddenNeurons(firstClass);
            multilayerPerceptron.calculateOutputNeurons(0);
            teachNet(multilayerPerceptron, firstClass, 0);

            multilayerPerceptron.calculateCurrentOutputNeuronError(secondClassReference, 1);
            multilayerPerceptron.calculateHiddenNeurons(secondClass);
            multilayerPerceptron.calculateOutputNeurons(1);
            teachNet(multilayerPerceptron, secondClass, 1);


            multilayerPerceptron.calculateCurrentOutputNeuronError(thirdClassReference, 2);
            multilayerPerceptron.calculateHiddenNeurons(thirdClass);
            multilayerPerceptron.calculateOutputNeurons(2);
            teachNet(multilayerPerceptron, thirdClass, 2);


            multilayerPerceptron.calculateCurrentOutputNeuronError(fourthClassReference, 3);
            multilayerPerceptron.calculateHiddenNeurons(fourthClass);
            multilayerPerceptron.calculateOutputNeurons(3);
            teachNet(multilayerPerceptron, fourthClass, 3);


            multilayerPerceptron.calculateCurrentOutputNeuronError(fifthClassReference, 4);
            multilayerPerceptron.calculateHiddenNeurons(fifthClass);
            multilayerPerceptron.calculateOutputNeurons(4);
            teachNet(multilayerPerceptron, firstClass, 4);
        }


//        HBox newRoot = new HBox();
//        newRoot.setSpacing(3);
////        newRoot.getChildren().addAll(firstClassImage, secondClassImage, thirdImageView, fourthImageView, fifthImageView);
//
//
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(newRoot, 1000, 600));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
