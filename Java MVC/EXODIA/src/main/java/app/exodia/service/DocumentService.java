package app.exodia.service;

import app.exodia.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {

    DocumentServiceModel save(DocumentServiceModel documentServiceModel);

    DocumentServiceModel findById(String id);

    List<DocumentServiceModel> findAllDocuments();

    boolean printDocumentById(String id);
}
