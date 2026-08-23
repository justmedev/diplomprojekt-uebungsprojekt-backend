package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PdfInfoRepository extends JpaRepository<PdfInfo, Long> {
    Optional<PdfInfo> findByFileUUID(UUID fileUUID);
}