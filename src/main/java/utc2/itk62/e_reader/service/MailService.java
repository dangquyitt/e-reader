package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.exception.EReaderException;

import java.util.Map;

public interface MailService {
    void send(String to, String subject, String template, Map<String, Object> metadata) throws EReaderException;
}
