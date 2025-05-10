package api.giybat.uz.dto;

import api.giybat.uz.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FilterResultDTO<T> {
    private List<T> list;
    private Long totalCount;

}
