import nl.captcha.text.producer.TextProducer;

public class CustomTextProducer implements TextProducer {
    private final String _word;

    public CustomTextProducer(String word) {
        this._word = word;
    }

    public String getText() {
        return this._word;
    }
}
