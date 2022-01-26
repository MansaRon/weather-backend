package co.za.demo.repository.mongo;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntityUpdate {

    private EntityFilter filter;

    private Map<String, Object> values; //fieldName -> value

}
