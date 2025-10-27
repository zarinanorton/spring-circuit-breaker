package flimflam.domain;

public class ResponseEntity {

    private ResponseEntity() {
    }
    private int statusCode;
    private String contentType;
    private byte[] content;

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return this.content;
    }

    public static ResponseEntity ok(String contentType, byte[] content) {
        var entity = new ResponseEntity();
        entity.setStatusCode(200);
        entity.setContentType(contentType);
        entity.setContent(content);

        return entity;
    }
}
