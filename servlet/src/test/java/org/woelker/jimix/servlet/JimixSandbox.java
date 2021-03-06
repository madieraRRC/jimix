package org.woelker.jimix.servlet;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.core.MetricName;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JimixSandbox {

    public static void main(String[] args) throws Exception {
        new JimixSandbox().run();
    }

    private void run() throws Exception {
        Metrics.newGauge(JimixSandbox.class, "foo", new Gauge<Long>() {
            long current = 0;

            @Override
            public Long value() {
                return current++;
            }
        });
        Metrics.newGauge(JimixSandbox.class, "foo", "bar", new Gauge<Long>() {
            long current = 0;

            @Override
            public Long value() {
                return current++;
            }
        });
        Metrics.newGauge(JimixSandbox.class, "foo", "double", new Gauge<Double>() {

            @Override
            public Double value() {
                return Double.NaN;
            }
        });
        final MetricName metricName = new MetricName("fizz", "buzz", "foo");
        Metrics.newGauge(metricName, new Gauge<Long>() {
            long current = 0;

            @Override
            public Long value() {
                return current++;
            }
        });

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new JimixServlet()), "/jimix/*");
        server.start();
        server.join();
    }

}
