package edu.usb.argosinstrumentation.domain;

import edu.usb.argosinstrumentation.exceptions.InvalidParametersException;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassData {
    @Getter private final HashMap<MethodData, SortedSet<Integer>> methods;
    private final String className;
    private static final Logger logger = LoggerFactory.getLogger(ClassData.class);

    public ClassData(String className) {
        this.className = className;
        methods = new HashMap<>();
    }

    public MethodData createMethodData(String name, String desc) {
        if (name.isEmpty() || desc.isEmpty()) {
            logger.atError()
                    .log(
                            String.format(
                                    "Attempt to store empty.%n Method name: %s, Method desc: %s",
                                    name, desc));
            throw new InvalidParametersException("Cannot accept empty names");
        }

        return new MethodData(name, desc);
    }

    @SuppressWarnings({"squid:S3776"})
    public void saveMethodData(MethodData method, int line) {
        if (methods.containsKey(method)) {
            methods.get(method).add(line);
        } else {
            SortedSet<Integer> set = new TreeSet<>();
            set.add(line);
            methods.put(method, set);
        }

        logger.atInfo().log(
                String.format(
                        "Stored method%nClass: %s%nMethod: %s %s%nAt line: %d",
                        className, method.getName(), method.getDesc(), line));
    }
}
