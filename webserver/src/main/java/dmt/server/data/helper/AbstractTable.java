package dmt.server.data.helper;

import java.util.Objects;

/**
 * @author Marco Romagnolo
 */
public abstract class AbstractTable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract Long getVersion();

    public abstract void setVersion(Long id);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTable that = (AbstractTable) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
