package com.churn;

import com.churn.model.ClassifierFactory;
import org.junit.Test;
import weka.classifiers.Classifier;
import static org.junit.Assert.*;

public class ModelTrainerTest {
    @Test
    public void testCreateDecisionTree() throws Exception {
        Classifier clf = ClassifierFactory.create("DecisionTree");
        assertNotNull(clf);
    }

    @Test
    public void testCreateRandomForest() throws Exception {
        Classifier clf = ClassifierFactory.create("RandomForest");
        assertNotNull(clf);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidModel() throws Exception {
        ClassifierFactory.create("InvalidModel");
    }

    @Test
    public void testAvailableClassifiers() {
        String[] available = ClassifierFactory.availableClassifiers();
        assertTrue(available.length >= 4);
    }
}
