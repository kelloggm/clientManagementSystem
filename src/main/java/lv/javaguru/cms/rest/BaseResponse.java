package lv.javaguru.cms.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BaseResponse implements Serializable {

    private static final int OK_CODE = 200;
    private static final int BAD_CODE = 400;

    private List<CmsError> errors;

    @JsonIgnore
    public boolean isOk() {
        return this.getHttpCode() == 200;
    }

    @JsonIgnore
    public int getHttpCode() {
        return this.errors == null || this.errors.isEmpty() ? OK_CODE : BAD_CODE;
    }

}
