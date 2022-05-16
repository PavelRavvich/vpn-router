package com.vpnrouter.utils;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

public class StringUtils {

    @NotNull
    @SneakyThrows
    public static String toHost(@NotNull String url) {
        return new URI(url).getHost().replace("www.", "");
    }

}
