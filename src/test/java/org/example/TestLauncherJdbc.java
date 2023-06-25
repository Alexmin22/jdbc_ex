package org.example;

import org.example.dao.jdbc.CompanyEntityDaoImplTest;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

public class TestLauncherJdbc {
    public static void main(String[] args) {
        final var launcher = LauncherFactory.create();

        final var summaryGeneratingListener = new SummaryGeneratingListener();

        final var request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectClass(CompanyEntityDaoImplTest.class))
//                .selectors(DiscoverySelectors.selectPackage("org/example/dao/jdbc"))
//                .filters(
//                        TagFilter.includeTags("login")
//                )
                .build();

        launcher.execute(request, summaryGeneratingListener);

        try (var printWriter = new PrintWriter(System.out)) {
            summaryGeneratingListener.getSummary().printTo(printWriter);
        }
    }
}
