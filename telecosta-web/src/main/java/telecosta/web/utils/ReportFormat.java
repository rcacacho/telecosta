package telecosta.web.utils;

/**
 *
 * @author rcacacho
 */
public enum ReportFormat {

    EXCEL(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    WORD(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    PDF(".pdf", "application/pdf"), ODT(".odt", "application/vnd.oasis.opendocument.text"),
    ODS(".ods", "application/vnd.oasis.opendocument.spreadsheet"),
    ZIP(".zip", "application/zip");

    private String extension;
    private String fileContentType;

    private ReportFormat(String extension, String fileContentType) {
        this.extension = extension;
        this.fileContentType = fileContentType;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getFileContentType() {
        return fileContentType;
    }

}
