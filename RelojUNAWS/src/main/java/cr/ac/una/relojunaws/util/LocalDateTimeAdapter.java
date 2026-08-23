package cr.ac.una.relojunaws.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String v) {
        return (v == null || v.isBlank()) ? null : LocalDateTime.parse(v);
    }

    @Override
    public String marshal(LocalDateTime v) {
        return (v == null) ? null : v.toString();
    }

}
