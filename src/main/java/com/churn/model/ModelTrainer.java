package com.churn.model;

import weka.classifiers.Classifier;
import weka.core.Instances;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Trains and stores multiple classification models.
 */
public class ModelTrainer {
    private final Map<String, Classifier> trainedModels = new LinkedHashMap<>();

    public Classifier train(String modelName, Instances trainData) throws Exception {
        Classifier classifier = ClassifierFactory.create(modelName);
        long start = System.currentTimeMillis();
        classifier.buildClassifier(trainData);
        long elapsed = System.currentTimeMillis() - start;
        trainedModels.put(modelName, classifier);
        System.out.println(modelName + " trained in " + elapsed + "ms");
        return classifier;
    }

    public Map<String, Classifier> trainAll(String[] modelNames, Instances trainData) throws Exception {
        for (String name : modelNames) {
            train(name, trainData);
        }
        return trainedModels;
    }

    public Classifier getModel(String name) {
        return trainedModels.get(name);
    }

    public Map<String, Classifier> getAllModels() {
        return trainedModels;
    }
}
