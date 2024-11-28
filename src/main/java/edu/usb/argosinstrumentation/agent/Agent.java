package edu.usb.argosinstrumentation.agent;

import edu.usb.argosinstrumentation.export.IExport;
import edu.usb.argosinstrumentation.export.JsonExport;
import edu.usb.argosinstrumentation.transformer.CoverageTransformer;

import java.lang.instrument.Instrumentation;

public class Agent {

    private static volatile boolean agentInitialized = false;

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Inicializando el agente...");

        CoverageTransformer transformer = new CoverageTransformer(agentArgs);
        System.out.println("Agregando Transformer al Instrumentation");

        inst.addTransformer(transformer);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Ejecución finalizada. Imprimiendo resultados...");
            IExport jsonExport = new JsonExport();
            jsonExport.export("org/example", transformer.getCoverageCollector().getFinalInfo());
            //transformer.getCoverageCollector().printResult();

        }));

        try {
            Class<?> coverageTransformerClass = Class.forName("edu.usb.argosinstrumentation.transformer.CoverageTransformer");
            System.out.println("CoverageTransformer cargado correctamente: " + coverageTransformerClass.getName());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: CoverageTransformer no se encuentra en el classpath.");
            e.printStackTrace();
            return;
        }

        agentInitialized = true;
        System.out.println("Agente inicializado con éxito.");
    }

    public static void main(String[] args) {
    }
}


