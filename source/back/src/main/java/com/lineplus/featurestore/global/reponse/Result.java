package com.lineplus.featurestore.global.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Result{
    private List<?> message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    public <T, C extends Collection<T>> C typesafeAdd(Iterable<?> from, C to, Class<T> listClass) {
        for (Object item: from) {
            to.add(listClass.cast(item));
        }
        return to;
    }

    public List<?> getMessage() {
        if(this.message==null) {
            return new ArrayList();
        }else {
            return message;

        }
    }

    public Integer getCount() {
        return count;
    }
}
