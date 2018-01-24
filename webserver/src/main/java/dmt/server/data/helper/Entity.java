package dmt.server.data.helper;

/**
 * @author Marco Romagnolo
 */
public interface Entity {

    Long getId();

    void setId(Long id);

    Long getVersion();

    void setVersion(Long version);

}
