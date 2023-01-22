package com.pp.config.security;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClaimsExtractor {
    UserDetails getUserDetails(JWTClaimsSet claimsSet);
}
