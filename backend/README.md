# UC Naming Convention
`Verb`+`Feature`+`UseCase`

Some examples:
* `UploadPDFUseCase`
* `DeletePDFUseCase`
* `CreatePageUseCase`
* ...

each UseCase must implement the UseCase interface (and thus the execute method)

the only public method of a UseCase should be its execute method!!!

example signature:
```java
public class UploadPDFUseCase implements UseCase<UploadPDFCommand, UploadPDFResponse> {/* ... */}
```