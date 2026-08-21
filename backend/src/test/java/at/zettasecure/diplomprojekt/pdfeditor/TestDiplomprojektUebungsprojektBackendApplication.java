package at.zettasecure.diplomprojekt.pdfeditor;

import org.springframework.boot.SpringApplication;

public class TestDiplomprojektUebungsprojektBackendApplication {

  public static void main(String[] args) {
    SpringApplication.from(DiplomprojektUebungsprojektBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}
