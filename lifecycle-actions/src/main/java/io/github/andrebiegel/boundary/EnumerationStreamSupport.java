package io.github.andrebiegel.boundary;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface EnumerationStreamSupport {

    public default <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<T>() {
            public T next() {
                return e.nextElement();
            }

            public boolean hasNext() {
                return e.hasMoreElements();
            }
        }, Spliterator.ORDERED), false);
    }


    public default  void printElementsNonNull(HttpServletRequest container, String containerName, String lifecycleActionType) {
        if (Objects.nonNull(container)) {
            System.out.println(printElements(container.getAttributeNames(), containerName, lifecycleActionType));
        } else {
            System.out.println(lifecycleActionType + " :: " + containerName + " not existent ");
        }
    }

    public default  void printElementsNonNull(HttpSession container, String containerName, String lifecycleActionType) {
        if (Objects.nonNull(container)) {
            System.out.println(printElements(container.getAttributeNames(), containerName, lifecycleActionType));
        } else {
            System.out.println(lifecycleActionType + " :: " + containerName + " not existent ");
        }
    }

    public default String printElements(Enumeration<String> elements, String containerName, String lifecycleActionType) {

            return constructInfo(enumerationAsStream(elements), containerName, lifecycleActionType);

    }

    public default void printElementsNonNull(String[] elements, String containerName, String lifecycleActionType) {
        if (Objects.nonNull(elements)) {
            System.out.println(constructInfo(Arrays.asList(elements).stream(), containerName, lifecycleActionType));
        } else {
            System.out.println(lifecycleActionType + " :: " + containerName + " not existent ");
        }

    }

    public default String constructInfo(Stream<String> elements, String containerName, String lifecycleActionType) {

        return lifecycleActionType + " :: " + containerName + ": " + elements.collect(Collectors.joining(";"));
    }
}