package co.za.demo.repository.mongo;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pageable {

    private Integer pageNumber;

    private Integer pageSize;

    private EntityFilter filter;

    private String sortBy; //field name to sort results by

    private Order order;

    private enum Order {ASC, DESC}

    public Order getOrder() {return order != null ? order : Order.ASC;}

    public boolean asc() {return getOrder() == Order.ASC;}

}
