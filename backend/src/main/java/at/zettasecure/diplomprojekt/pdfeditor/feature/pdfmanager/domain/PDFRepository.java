package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PDFRepository extends JpaRepository<PDF, Long> {
}