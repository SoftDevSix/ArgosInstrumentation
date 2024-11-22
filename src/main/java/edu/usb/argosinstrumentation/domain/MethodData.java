package edu.usb.argosinstrumentation.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MethodData {
    private final String name;
    private final String desc;

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (super.equals(o)) return true;

        MethodData that = (MethodData) o;
        return that.name.equals(this.name) && that.desc.equals(this.desc);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + desc.hashCode();
        return result;
    }
}
