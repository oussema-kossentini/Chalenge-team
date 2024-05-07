import sys
from joblib import load

# Load the saved classifier
loaded_classifier = load(r'C:\Users\ASUS\Downloads\BAKKKK\Chalenge-team-forummbackk\src\main\java\tn\esprit\spring\controllers\sentiemnt_model_classifier.joblib')

# Load the saved CountVectorizer
loaded_cv = load(r'C:\Users\ASUS\Downloads\BAKKKK\Chalenge-team-forummbackk\src\main\java\tn\esprit\spring\controllers\sentiemnt_model.joblib')

# Read the input sentence from command-line argument
new_sentence = " ".join(sys.argv[1:])

# Preprocess the sentence using the loaded CountVectorizer
new_sentence_transformed = loaded_cv.transform([new_sentence]).toarray()

# Predict sentiment using the loaded classifier
predicted_sentiment = loaded_classifier.predict(new_sentence_transformed)

if(predicted_sentiment == 0):
     print("Negative")
if(predicted_sentiment == 1):
     print("Neutral")
if(predicted_sentiment == 2):
     print("Positive")
