package com.churn.evaluation;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;
import java.util.*;

/**
 * Evaluates classifiers with cross-validation and test set metrics.
 */
public class ModelEvaluator {
    private final Map<String, Map<String, Double>> results = new LinkedHashMap<>();

    public Map<String, Double> evaluate(Classifier classifier, String name,
                                         Instances trainData, Instances testData, int cvFolds) throws Exception {
        // Cross-validation
        Evaluation cvEval = new Evaluation(trainData);
        cvEval.crossValidateModel(classifier, trainData, cvFolds, new java.util.Random(42));

        // Test set evaluation
        Evaluation testEval = new Evaluation(trainData);
        testEval.evaluateModel(classifier, testData);

        Map<String, Double> metrics = new LinkedHashMap<>();
        metrics.put("cv_accuracy", cvEval.pctCorrect() / 100.0);
        metrics.put("test_accuracy", testEval.pctCorrect() / 100.0);
        metrics.put("precision", testEval.weightedPrecision());
        metrics.put("recall", testEval.weightedRecall());
        metrics.put("f1_score", testEval.weightedFMeasure());
        metrics.put("auc", testEval.weightedAreaUnderROC());

        results.put(name, metrics);
        System.out.printf("%s: Accuracy=%.4f, F1=%.4f, AUC=%.4f%n",
                name, metrics.get("test_accuracy"), metrics.get("f1_score"), metrics.get("auc"));
        return metrics;
    }

    public void printComparisonTable() {
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("%-20s %-12s %-12s %-12s %-12s %-12s%n",
                "Model", "CV Acc", "Test Acc", "Precision", "F1", "AUC");
        System.out.println("-".repeat(80));
        for (Map.Entry<String, Map<String, Double>> entry : results.entrySet()) {
            Map<String, Double> m = entry.getValue();
            System.out.printf("%-20s %-12.4f %-12.4f %-12.4f %-12.4f %-12.4f%n",
                    entry.getKey(), m.get("cv_accuracy"), m.get("test_accuracy"),
                    m.get("precision"), m.get("f1_score"), m.get("auc"));
        }
    }

    public String getBestModel(String metric) {
        return results.entrySet().stream()
                .max(Comparator.comparingDouble(e -> e.getValue().getOrDefault(metric, 0.0)))
                .map(Map.Entry::getKey).orElse("none");
    }
}
