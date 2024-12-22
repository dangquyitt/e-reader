package utc2.itk62.e_reader.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.domain.entity.ReadingProgress;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.ReadingProgressRepository;
import utc2.itk62.e_reader.service.ReadingProgressService;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReadingProgressServiceImpl implements ReadingProgressService {
    private final ReadingProgressRepository readingProgressRepository;

    @Override
    public ReadingProgress getCurrentProgress(Long userId, Long bookId) {
        return readingProgressRepository.findByUserIdAndBookId(userId, bookId).orElseThrow(() -> new EReaderException("not found"));
    }

    @Override
    public ReadingProgress createReadingProgress(ReadingProgress progress) {
        return readingProgressRepository.save(progress);
    }
}
