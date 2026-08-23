package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pdfs")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PDF {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  @Size(
      min = 1,
      max = 255
  )
  private String name;

  @Column(nullable = false)
  private UUID fileUUID;
}
