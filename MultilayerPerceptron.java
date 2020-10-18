package sample;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class MultilayerPerceptron {

    public int hiddenNeurons = 10;
    public int outputNeurons = 5;
    public int inputNeurons = 36;
    public int numberOfClasses = 5;

    public double minError = 0.02;

    public double speed = 0.3;


    SimpleMatrix inputToHiddenWeights;
    SimpleMatrix hiddenToOutputWeights;

    SimpleMatrix currentNetOutputsMatrix;
    SimpleMatrix currentOutputNeuronErrorMatrix;

    public ArrayList<Double> netErrorsList;

    private double[] hiddenNeuronsArray;
    private double[] outputNeuronsArray;

    private double[] hiddenNeuronsErrorArray;
    private double[] outputNeuronsErrorArray;

    private double[] currentHiddenToOutputNeuronErrors;



    private double[] currentNetErrorsArray;


    public MultilayerPerceptron() {
        inputToHiddenWeights = new SimpleMatrix(inputNeurons, hiddenNeurons);
        hiddenToOutputWeights = new SimpleMatrix(hiddenNeurons, outputNeurons);
        currentNetOutputsMatrix = new SimpleMatrix(outputNeurons, numberOfClasses);
        currentOutputNeuronErrorMatrix = new SimpleMatrix(numberOfClasses, outputNeurons);
        netErrorsList = new ArrayList<Double>();


        hiddenNeuronsArray = new double[hiddenNeurons];
        outputNeuronsArray = new double[outputNeurons];

        hiddenNeuronsErrorArray = new double[hiddenNeurons];
        outputNeuronsErrorArray = new double[outputNeurons];
        currentNetErrorsArray = new double[outputNeurons];
        currentHiddenToOutputNeuronErrors = new double[hiddenNeurons];


    }

    public void calculateHiddenNeurons(int[] inputNeuronsArray) {
        for (int i = 0; i < hiddenNeurons; ++i) {
            hiddenNeuronsArray[i] = putOnActivationFunc(getHiddenPartialSum(inputNeuronsArray, i));
        }
    }

    public void calculateOutputNeurons(int numberOfClass) {
        for (int i = 0; i < outputNeurons; ++i) {
            outputNeuronsArray[i] = putOnActivationFunc(getOutputPartialSum(i));
            currentNetOutputsMatrix.set(i, numberOfClass, outputNeuronsArray[i]);
        }

    }

    public double getHiddenPartialSum(int[] inputNeuronsArray, int hiddenNeuronNumber) {
        double partialSum = 0.0;

        for (int i = 0; i < inputNeuronsArray.length; ++i) {
            partialSum += inputNeuronsArray[i] * inputToHiddenWeights.get(i, hiddenNeuronNumber);
        }
        return partialSum;
    }

    public double putOnActivationFunc(double partialSumValue) {
        return 1 / (1 + Math.exp(-1 * partialSumValue));
    }


    public void randomizeInputToHiddenWeights() {
        for (int i = 0; i < inputNeurons; ++i)
            for (int j = 0; j < hiddenNeurons; ++j)
                inputToHiddenWeights.set(i, j, -1.0 + Math.random() * 2.0);
    }

    public void randomizeHiddenToOutputWeights() {
        for (int i = 0; i < hiddenNeurons; ++i)
            for (int j = 0; j < outputNeurons; ++j)
                hiddenToOutputWeights.set(i, j, -1.0 + Math.random() * 2.0);
    }

    public double getOutputPartialSum(int outputNeuronNumber) {
        double partialSum = 0.0;
        for (int i = 0; i < hiddenNeuronsArray.length; ++i) {
            partialSum += hiddenNeuronsArray[i] * hiddenToOutputWeights.get(i, outputNeuronNumber);
        }
        return partialSum;
    }

    public void calculateCurrentNetError(int[] referenceNeurons, int numberOfClasses) {
        double currentError = 0.0;
        for (int i = 0; i < outputNeurons; ++i) {
            currentError += Math.pow(currentNetOutputsMatrix.get(numberOfClasses, i) - referenceNeurons[i], 2);
        }
        currentError /= 2;
        currentNetErrorsArray[numberOfClasses] = currentError;
    }

    public void printCurrentNetErrorArray()
    {
        for(int i = 0; i < currentNetErrorsArray.length; ++i)
            System.out.print(" " + currentNetErrorsArray[i]);
        System.out.println();
    }


    public void printOutputMatrix()
    {
        int rows = currentNetOutputsMatrix.numRows();
        int columns = currentNetOutputsMatrix.numCols();

        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < columns; ++j)
            {
                System.out.print(" " + currentNetOutputsMatrix.get(i,j) + " ");
            }
            System.out.println();
        }
    }

    public void calculateCurrentOutputNeuronError(int[] referenceNeurons, int numberOfClasses)
    {
        for(int i = 0; i < referenceNeurons.length; ++i)
        {
            double y = currentNetOutputsMatrix.get(numberOfClasses, i);
            double d = referenceNeurons[i];
            double currentNeuronError = (y - d) * (y * (1 - y));
            currentOutputNeuronErrorMatrix.set(numberOfClasses,i, currentNeuronError);
        }
    }

    public void calculateHiddenToOutputLayerErrors(int numberOfClasses, int outputNeuronNumber)
    {
        double currentOutputError = currentOutputNeuronErrorMatrix.get(outputNeuronNumber, numberOfClasses);
        for(int i = 0; i < hiddenNeurons; ++i)
        {
            double y = hiddenNeuronsArray[i];
            currentHiddenToOutputNeuronErrors[i] = currentOutputError * hiddenToOutputWeights.get(i, outputNeuronNumber) * y * ( 1 - y);
        }
    }

    public void updateInputToHiddenWeights(int[] inputNeuronsArray)
    {

        for(int j = 0; j < hiddenNeurons; ++j) {
            double[] deltas = new double[inputNeurons];
            for (int i = 0; i < inputNeurons; ++i) {
                deltas[i] = (-speed) *  currentHiddenToOutputNeuronErrors[j] * inputNeuronsArray[i];
                double currentWeight = inputToHiddenWeights.get(i, j);
                inputToHiddenWeights.set(i,j, currentWeight + deltas[i]);
            }
        }
    }

    public void updateHiddenToOutputWeights(int numberOfClasses, int outputNeuronNumber)
    {
        double[] deltas = new double[hiddenNeurons];
        for(int i = 0; i < hiddenNeurons; ++i)
        {
            deltas[i] = (-speed) * currentOutputNeuronErrorMatrix.get(outputNeuronNumber, numberOfClasses) * hiddenNeuronsArray[i];
            double currentWeight = hiddenToOutputWeights.get(i,outputNeuronNumber);
            hiddenToOutputWeights.set(i,outputNeuronNumber, currentWeight + deltas[i]);
        }
    }

}
