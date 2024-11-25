package com.proempresa.urlenmascarar.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlResponse {
	private String url;
}
 