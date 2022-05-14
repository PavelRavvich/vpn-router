package com.vpn.router.service;

import java.util.List;
import java.util.Set;

public interface BashService {

    Set<String> findIpsByDomainName(String domain);
}
