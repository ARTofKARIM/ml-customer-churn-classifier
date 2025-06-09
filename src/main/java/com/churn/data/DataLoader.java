package com.churn.data;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import java.io.File;

/**
 * Handles loading and preprocessing of customer churn datasets.
 */
public class DataLoader {
    private Instances data;

    public Instances load(String filepath) throws Exception {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filepath));
        data = loader.getDataSet();
        System.out.println("Loaded " + data.numInstances() + " instances, " + data.numAttributes() + " attributes");
        return data;
    }

    public Instances handleMissingValues(Instances dataset) throws Exception {
        ReplaceMissingValues filter = new ReplaceMissingValues();
        filter.setInputFormat(dataset);
        return Filter.useFilter(dataset, filter);
    }

    public Instances setClassAttribute(Instances dataset, String className) {
        for (int i = 0; i < dataset.numAttributes(); i++) {
            if (dataset.attribute(i).name().equals(className)) {
                dataset.setClassIndex(i);
                break;
            }
        }
        if (dataset.classIndex() == -1) {
            dataset.setClassIndex(dataset.numAttributes() - 1);
        }
        return dataset;
    }

    public Instances makeClassNominal(Instances dataset) throws Exception {
        NumericToNominal filter = new NumericToNominal();
        filter.setAttributeIndicesArray(new int[]{dataset.classIndex()});
        filter.setInputFormat(dataset);
        return Filter.useFilter(dataset, filter);
    }

    public Instances[] split(Instances dataset, double trainRatio) {
        int trainSize = (int) Math.round(dataset.numInstances() * trainRatio);
        int testSize = dataset.numInstances() - trainSize;
        dataset.randomize(new java.util.Random(42));
        Instances train = new Instances(dataset, 0, trainSize);
        Instances test = new Instances(dataset, trainSize, testSize);
        System.out.println("Train: " + train.numInstances() + " | Test: " + test.numInstances());
        return new Instances[]{train, test};
    }
}
