package com.pp.config.security;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyClockClaimsExtractor implements ClaimsExtractor{
    @Override
    public UserDetails getUserDetails(JWTClaimsSet claimsSet){
        String userName =  (String)claimsSet.getClaim("preferred_username");
        List<String> groups = (List)claimsSet.getClaim("groups");
        List<String> correctedGroups = groups.stream().map(group -> group.replace("/", "")).collect(Collectors.toList());
        return new UserDetailsImpl(userName, correctedGroups);
    }
}
