package apple;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private TextField firstRedApples;

    @FXML
    private TextField firstWhiteApples;

    @FXML
    private TextField firstBlackApples;

    @FXML
    private TextField secondRedApples;

    @FXML
    private TextField secondWhiteApples;

    @FXML
    private TextField secondBlackApples;

    @FXML
    private RadioButton moreDefined;

    @FXML
    private RadioButton moreUndefined;

    @FXML
    private Button calculateEntropy;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField entropyFirstBasket;

    @FXML
    private TextField entropySecondBasket;

    public static double log2(double n) {
        return (Math.log(n) / Math.log(2));
    }

    private double entropy(Double red, Double white, Double black) {
        double total = red + white + black;
        double pRed = red == 0 ? 0 : red / total;
        double pWhite = white == 0 ? 0 : white / total;
        double pBlack = black == 0 ? 0 : black / total;

        return -((pRed == 0 ? 0 : pRed * log2(pRed)) + pWhite == 0 ? 0 : pWhite * log2(pWhite) + pBlack == 0 ? 0 : pBlack * log2(pBlack));
    }

    @FXML
    private void calculateEntropy() {
        try {
            errorMessage.setVisible(false);
            entropyFirstBasket.clear();
            entropySecondBasket.clear();

            if (firstRedApples.getText() == null || firstRedApples.getText().isEmpty() ||
                    firstWhiteApples.getText() == null || firstWhiteApples.getText().isEmpty() ||
                    firstBlackApples.getText() == null || firstBlackApples.getText().isEmpty() ||
                    secondRedApples.getText() == null || secondRedApples.getText().isEmpty() ||
                    secondWhiteApples.getText() == null || secondWhiteApples.getText().isEmpty() ||
                    secondBlackApples == null || secondBlackApples.getText().isEmpty()) {
                errorMessage.setVisible(true);
                errorMessage.setText("Будь ласка, введіть коректні значення ймовірностей.");
                return;
            }

            Double pFirstRedApples = Double.parseDouble(firstRedApples.getText().replace(',', '.'));
            Double pFirstWhiteApples = Double.parseDouble(firstWhiteApples.getText().replace(',', '.'));
            Double pFirstBlackApples = Double.parseDouble(firstBlackApples.getText().replace(',', '.'));
            Double pSecondRedApples = Double.parseDouble(secondRedApples.getText().replace(',', '.'));
            Double pSecondWhiteApples = Double.parseDouble(secondWhiteApples.getText().replace(',', '.'));
            Double pSecondBlackApples = Double.parseDouble(secondBlackApples.getText().replace(',', '.'));

            Double entropyFirst = entropy(pFirstRedApples, pFirstWhiteApples, pFirstBlackApples);
            Double entropySecond = entropy(pSecondRedApples, pSecondWhiteApples, pSecondBlackApples);

            entropyFirstBasket.setText(String.valueOf(entropyFirst));
            entropySecondBasket.setText(String.valueOf(entropySecond));


            if (moreDefined.isSelected()) {
                if (entropyFirst < entropySecond) {
                    errorMessage.setVisible(true);
                    errorMessage.setText("Для першого кошика результат досліду більш визначений.");
                } else if (entropyFirst > entropySecond) {
                    errorMessage.setVisible(true);
                    errorMessage.setText("Для другого кошика результат досліду більш визначений.");
                } else {
                    errorMessage.setVisible(true);
                    errorMessage.setText("Обидва кошики мають однаковий рівень визначеності.");
                }
            } else if (moreUndefined.isSelected()) {
                if (entropyFirst > entropySecond) {
                    errorMessage.setVisible(true);
                    errorMessage.setText("Для першого кошика результат досліду більш невизначений.");
                } else if (entropyFirst < entropySecond) {
                    errorMessage.setVisible(true);
                    errorMessage.setText("Для другого кошика результат досліду більш невизначений.");
                } else {
                    errorMessage.setVisible(true);
                    errorMessage.setText("Обидва кошики мають однаковий рівень невизначеності.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setVisible(true);
            errorMessage.setText("Будь ласка, введіть коректні значення ймовірностей.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        moreDefined.setToggleGroup(toggleGroup);
        moreUndefined.setToggleGroup(toggleGroup);
        moreDefined.setSelected(true);
    }
}
