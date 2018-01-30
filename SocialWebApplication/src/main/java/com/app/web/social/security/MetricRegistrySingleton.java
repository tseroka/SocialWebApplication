package com.app.web.social.security;

import com.codahale.metrics.MetricRegistry;

public final class MetricRegistrySingleton 
{
    public static final MetricRegistry metrics = new MetricRegistry();
}