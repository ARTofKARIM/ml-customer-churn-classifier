package com.churn;

import com.churn.data.DataLoader;
import com.churn.model.ModelTrainer;
import com.churn.evaluation.ModelEvaluator;
import com.churn.utils.ConfigLoader;
import weka.classifiers.Classifier;
import weka.core.Instances;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String configPath = args.length > 0 ? args[0] : "config/config.yaml";

        ConfigLoader config = new ConfigLoader();
        config.load(configPath);

        String dataPath = config.getString("data", "path");
        String targetCol = config.getString("data", "target_column");
        double testRatio = config.getDouble("data", "test_ratio");
        List<String> modelNames = config.getList("models", null);
        int cvFolds = config.getInt("evaluation", "cv_folds");

        // Load data
        DataLoader loader = new DataLoader();
        Instances data = loader.load(dataPath);
        data = loader.handleMissingValues(data);
        data = loader.setClassAttribute(data, targetCol);
        data = loader.makeClassNominal(data);

        Instances[] splits = loader.split(data, 1.0 - testRatio);
        Instances trainData = splits[0];
        Instances testData = splits[1];

        // Train and evaluate
        ModelTrainer trainer = new ModelTrainer();
        ModelEvaluator evaluator = new ModelEvaluator();

        if (modelNames == null) {
            modelNames = List.of("DecisionTree", "RandomForest", "NaiveBayes", "LogisticRegression");
        }

        for (String name : modelNames) {
            Classifier clf = trainer.train(name, trainData);
            evaluator.evaluate(clf, name, trainData, testData, cvFolds);
        }

        evaluator.printComparisonTable();
        String best = evaluator.getBestModel("f1_score");
        System.out.println("\nBest model (F1): " + best);
    }
}
