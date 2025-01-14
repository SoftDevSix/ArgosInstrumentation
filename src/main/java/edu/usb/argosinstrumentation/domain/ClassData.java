package edu.usb.argosinstrumentation.domain;

import edu.usb.argosinstrumentation.LogMessages;
import edu.usb.argosinstrumentation.exceptions.InvalidParametersException;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Builder
public class ClassData {
    @Getter @Builder.Default
    private final HashMap<MethodData, SortedSet<Integer>> methods = new HashMap<>();

    @Getter private final String className;
    private static final Logger logger = LoggerFactory.getLogger(ClassData.class);

    public MethodData createMethodData(String name, String desc) {
        if (name.isEmpty() || desc.isEmpty()) {
            logger.atError().log(String.format(LogMessages.EMPTY_PARAMETER_VALUES, name, desc));
            throw new InvalidParametersException("Cannot accept empty names");
        }

        return MethodData.builder().name(name).desc(desc).build();
    }

    public void saveMethodData(@NonNull MethodData method, int line) {
        if (methods.containsKey(method)) {
            methods.get(method).add(line);
        } else {
            SortedSet<Integer> set = new TreeSet<>();
            set.add(line);
            methods.put(method, set);
        }

        logger.atInfo().log(
                String.format(
                        LogMessages.METHOD_STORAGE,
                        className,
                        method.getName(),
                        method.getDesc(),
                        line));
    }
}
