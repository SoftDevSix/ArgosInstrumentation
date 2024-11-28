package edu.usb.argosinstrumentation.agent;

import edu.usb.argosinstrumentation.export.IExport;
import edu.usb.argosinstrumentation.export.JsonExport;
import edu.usb.argosinstrumentation.transformer.CoverageTransformer;
import java.lang.instrument.Instrumentation;

public class Agent {

    public static void premain(String agentArgs, Instrumentation inst) {

        CoverageTransformer transformer = new CoverageTransformer(agentArgs);

        inst.addTransformer(transformer);

        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(
                                () -> {
                                    IExport jsonExport = new JsonExport();
                                    jsonExport.export(
                                            "org/example",
                                            transformer.getCoverageCollector().getFinalInfo());
                                }));
    }

    public static void main(String[] args) {
        // This method is empty because it is used as an entry point for the agent.
    }
}
