package utc2.itk62.e_reader.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Translator {
    private final MessageSource messageSource;

    public String translate(Locale locale, String key, Object... args) {
        return messageSource.getMessage(key, args, "Please set a message for the key " + key, Locale.ENGLISH);
    }

    public String translate(String key, Object... args) {
        return messageSource.getMessage(key, args, "Please set a message for the key " + key, Locale.ENGLISH);
    }
}
