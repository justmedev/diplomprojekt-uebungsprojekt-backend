package at.zettasecure.diplomprojekt.pdfeditor.feature.thumbnailgenerator.uc.generate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ThumbnailGenerationUseCase {

  private final RestClient restClient;
  private final Path storagePath;

  public ThumbnailGenerationUseCase(
      @Value("${fastapi.thumbnail-service.url}") String baseUrl,
      @Value("${app.storage.thumbnail-dir}") String thumbnailDir) {
    this.restClient = RestClient.builder()
        .requestFactory(new SimpleClientHttpRequestFactory())
        .baseUrl(baseUrl)
        .build();
    this.storagePath = Paths.get(thumbnailDir).toAbsolutePath().normalize();
  }

  public void generateThumbnail(Path file, UUID id) {
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", new FileSystemResource(file));

    byte[] image = this.restClient.post()
        .uri("/render-thumbnail")
        .body(body)
        .retrieve()
        .body(byte[].class);

    if (image == null || image.length == 0) {
      throw new IllegalStateException("FastAPI returned empty thumbnail data");
    }

    try {
      Files.createDirectories(this.storagePath);
      Path destination = this.storagePath.resolve(id + ".webp");
      Files.write(destination, image);
    } catch (IOException e) {
      throw new RuntimeException("Could not save thumbnail for UUID: " + id, e);
    }
  }
}