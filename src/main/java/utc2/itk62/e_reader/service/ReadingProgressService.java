package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.ReadingProgress;

public interface ReadingProgressService {
    ReadingProgress getCurrentProgress(Long userId, Long bookId);

    ReadingProgress createReadingProgress(ReadingProgress progress);
}
