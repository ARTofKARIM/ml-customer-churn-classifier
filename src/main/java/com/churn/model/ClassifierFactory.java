package com.churn.model;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating Weka classifiers.
 */
public class ClassifierFactory {
    private static final Map<String, ClassifierSupplier> registry = new HashMap<>();

    static {
        registry.put("DecisionTree", () -> {
            J48 tree = new J48();
            tree.setConfidenceFactor(0.25f);
            tree.setMinNumObj(5);
            return tree;
        });
        registry.put("RandomForest", () -> {
            RandomForest rf = new RandomForest();
            rf.setNumIterations(100);
            rf.setMaxDepth(15);
            rf.setSeed(42);
            return rf;
        });
        registry.put("NaiveBayes", NaiveBayes::new);
        registry.put("LogisticRegression", () -> {
            Logistic lr = new Logistic();
            lr.setMaxIts(100);
            return lr;
        });
    }

    public static Classifier create(String name) throws Exception {
        ClassifierSupplier supplier = registry.get(name);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown classifier: " + name);
        }
        return supplier.get();
    }

    public static String[] availableClassifiers() {
        return registry.keySet().toArray(new String[0]);
    }

    @FunctionalInterface
    interface ClassifierSupplier {
        Classifier get() throws Exception;
    }
}
