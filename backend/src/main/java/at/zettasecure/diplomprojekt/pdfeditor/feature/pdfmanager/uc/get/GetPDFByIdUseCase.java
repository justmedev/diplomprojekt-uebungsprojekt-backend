package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.domain.PDF;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.domain.PDFRepository;
import at.zettasecure.diplomprojekt.pdfeditor.shared.NoInputUseCase;
import at.zettasecure.diplomprojekt.pdfeditor.shared.UseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class GetPDFByIdUseCase implements UseCase<Long, Optional<PDF>> {
  private final PDFRepository pdfRepository;

  @Transactional
  @Override
  public Optional<PDF> execute(Long id) {
    return pdfRepository.findById(id);
  }
}
