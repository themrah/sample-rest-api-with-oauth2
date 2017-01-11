package com.themrah.sample.resourceServer.model.response;

        import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckTokenResponse {
    private String userId;
    private String userName;
    private String email;
    private String clientId;
    private Integer exp;
    private List<String> scope;
    private String jti;
    private List<String> aud;
    private String sub;
    private String iss;
    private Integer iat;
    private String cid;
    private String grantType;
    private String azp;
    private Integer authTime;
    private String zid;
    private String revSig;
    private String origin;
    private Boolean revocable;
    private Map<String, Object> additionalProperties;
}