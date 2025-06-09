# Customer Churn Classifier (Java)

A machine learning pipeline for predicting customer churn using multiple classification algorithms implemented in Java with the Weka library.

## Architecture
```
ml-customer-churn-classifier/
├── src/main/java/com/churn/
│   ├── data/DataLoader.java           # CSV loading, preprocessing, splitting
│   ├── model/ClassifierFactory.java   # Factory pattern for classifiers
│   ├── model/ModelTrainer.java        # Multi-model training
│   ├── evaluation/ModelEvaluator.java # CV and test evaluation
│   ├── utils/ConfigLoader.java        # YAML config parser
│   └── Main.java                      # Pipeline entry point
├── src/test/java/com/churn/
│   └── ModelTrainerTest.java
├── config/config.yaml
└── pom.xml
```

## Models
| Model | Algorithm | Key Parameters |
|-------|-----------|---------------|
| Decision Tree | J48 (C4.5) | confidenceFactor=0.25 |
| Random Forest | 100 trees | maxDepth=15 |
| Naive Bayes | Gaussian NB | - |
| Logistic Regression | Multinomial | maxIts=100 |

## Installation
```bash
git clone https://github.com/mouachiqab/ml-customer-churn-classifier.git
cd ml-customer-churn-classifier
mvn clean install
```

## Usage
```bash
mvn exec:java -Dexec.mainClass="com.churn.Main" -Dexec.args="config/config.yaml"
```

## Technologies
- Java 17, Maven, Weka ML, OpenCSV, SnakeYAML, JUnit













