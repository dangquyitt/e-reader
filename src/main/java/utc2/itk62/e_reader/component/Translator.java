package utc2.itk62.e_reader.component;

import lombok.AllArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class Translator {
    private final MessageSource messageSource;

    public String translate(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, locale);
    }
}
