package lv.javaguru.cms.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
public class BaseDTO {

    private Long id;
    private String createdAt;
    private String modifiedAt;
    private String modifiedBy;

}
