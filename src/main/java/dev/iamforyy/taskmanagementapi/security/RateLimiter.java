package dev.iamforyy.taskmanagementapi.security;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.caffeine.Bucket4jCaffeine;
import io.github.bucket4j.caffeine.CaffeineProxyManager;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RateLimiter {

    private final CaffeineProxyManager<String> buckets = Bucket4jCaffeine.<String>builderFor(
                    Caffeine.newBuilder()
                            .maximumSize(10_000)
            )
            .build();

    public Bucket getBucket(final String remoteAddress) {
        return this.buckets.getProxy(remoteAddress, this::createConfiguration);
    }

    private BucketConfiguration createConfiguration() {
        return BucketConfiguration.builder()
                .addLimit(limit -> limit
                        .capacity(50)
                        .refillGreedy(50, Duration.ofMinutes(1))
                )
                .build();
    }

}
