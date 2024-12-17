package utc2.itk62.e_reader.component;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class Translator {
    private final MessageSource messageSource;

    public String translate(Locale locale, String key, Object... args) {
        return messageSource.getMessage(key, args, "Please set a message for the key " + key, locale);
    }
}
