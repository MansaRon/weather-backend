package co.za.demo.repository.mongo;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntityFilter {
    private Map<String, Object> values;

    private FilterType type;

    public FilterType getType() {
        return type != null ? type : FilterType.AND;
    }
}
