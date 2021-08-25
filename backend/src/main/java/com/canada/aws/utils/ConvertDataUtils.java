package com.canada.aws.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConvertDataUtils {
    public <T> List<T> convertIterableToList(Iterable<T> iterable){
        List<T>list = new ArrayList<T>();
        iterable.forEach(list::add);
        return list;
    }
}
